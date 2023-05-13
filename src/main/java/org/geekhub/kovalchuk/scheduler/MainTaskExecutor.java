package org.geekhub.kovalchuk.scheduler;

import org.geekhub.kovalchuk.config.ApplicationPropertiesConfig;
import org.geekhub.kovalchuk.model.entity.Currency;
import org.geekhub.kovalchuk.repository.TaskQueueRepository;
import org.geekhub.kovalchuk.service.*;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Component;

import java.util.Queue;

@DisallowConcurrentExecution
@Component
public class MainTaskExecutor implements Job {
    private final LocationsService locationsService;
    private final CityInOperationService cityInOperationService;
    private final CurrencyService currencyService;
    private final RouteService routeService;
    private final TaskQueueRepository taskQueueRepository;
    private final MonthPricesService monthPricesService;
    private final RoleService roleService;
    private final ApplicationPropertiesConfig properties;
    private static boolean isFirstRunning = true;

    public MainTaskExecutor(LocationsService locationsService,
                            CityInOperationService cityInOperationService,
                            CurrencyService currencyService,
                            RouteService routeService,
                            TaskQueueRepository taskQueueRepository,
                            MonthPricesService monthPricesService,
                            RoleService roleService,
                            ApplicationPropertiesConfig properties) {
        this.locationsService = locationsService;
        this.cityInOperationService = cityInOperationService;
        this.currencyService = currencyService;
        this.routeService = routeService;
        this.taskQueueRepository = taskQueueRepository;
        this.monthPricesService = monthPricesService;
        this.roleService = roleService;
        this.properties = properties;
    }

    @Override
    public void execute(JobExecutionContext context) {
        if (isFirstRunning) {
            initRoles();
            initLocations();
            updateAbbreviations();
            initCitiesInOperation();
            initCurrencies();
            initRoutes();
            isFirstRunning = false;
        }
        checkNecessityToUpdatePrices();
        executeTasksFromMainQueue();
    }

    private void initRoles() {
        if (roleService.isRolesTableEmpty()) {
            roleService.addRolesToDb();
        }
    }

    private void initLocations() {
        if (locationsService.isLocationTableEmpty()) {
            locationsService.saveOrUpdateLocationsToDb();
        }
    }

    private void updateAbbreviations() {
        if (!locationsService.isLocationTableEmpty()) {
            locationsService.updateAbbreviations();
        }
    }

    private void initCitiesInOperation() {
        if (cityInOperationService.isCityInOperationTableEmpty()) {
            cityInOperationService.addOrUpdateTop12AirportCitiesToDb();
        }
    }

    private void initCurrencies() {
        if (currencyService.isCurrencyTableEmpty()) {
            currencyService.addMainCurrenciesToDb();
        }
    }

    private void initRoutes() {
        if (routeService.isRouteTableEmpty()) {
            routeService.runRoutesUpdater();
        }
    }

    private void checkNecessityToUpdatePrices() {
        Queue<Runnable> taskQueue = taskQueueRepository.getTaskQueue();
        int taskQueueSize = taskQueue.size();

        if (taskQueueRepository.isNeedToStartUpdateRoutes() || taskQueueRepository.isNeedToFinishRoutesUpdating()) {
            if (taskQueueRepository.isNeedToStartUpdateRoutes()) {
                taskQueueRepository.getTaskQueue().clear();
                routeService.runRoutesUpdater();
                taskQueueRepository.setNeedToFinishRoutesUpdating(true);
                taskQueueRepository.setNeedToStartUpdateRoutes(false);
            }
            if (taskQueueSize == 0) {
                taskQueueRepository.setNeedToFinishRoutesUpdating(false);
            }
        }

        if (taskQueueSize < properties.getRequestsPerMinute() && !taskQueueRepository.isNeedToFinishRoutesUpdating()) {
            Currency currency = currencyService.getCurrencyByCode("USD");
            monthPricesService.runFlightPricesUpdater(currency);
            System.out.printf("Flight prices updater is running, added %d new tasks!\n",
                    taskQueue.size() - taskQueueSize);
        }
    }

    protected void executeTasksFromMainQueue() {
        Queue<Runnable> taskQueue = taskQueueRepository.getTaskQueue();
        int requestsPerMinute = properties.getRequestsPerMinute();

        System.out.printf("There are %d tasks in the main queue now!\n", taskQueue.size());

        int errorCounter = 0;
        for (int i = 0; i < requestsPerMinute; i++) {
            Runnable task = taskQueue.poll();
            if (task != null) {
                boolean isDone = false;
                do {
                    try {
                        task.run();
                        isDone = true;
                    } catch (Exception e) {
                        errorCounter++;
                        try {
                            Thread.sleep(properties.getErrorTimeoutBeforeNextTryInSeconds() * 1000L);
                        } catch (InterruptedException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                } while (!isDone);
            }

            if (i == requestsPerMinute - 1 && errorCounter > 0) {
                System.err.printf("%d tasks have been restarted for the current iteration due to errors!\n",
                        errorCounter);
                errorCounter = 0;
            }
        }
    }
}
