package org.geekhub.kovalchuk.config;

import org.geekhub.kovalchuk.config.ApplicationPropertiesConfig;
import org.geekhub.kovalchuk.scheduler.MainTaskExecutor;
import org.quartz.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

import java.util.Properties;

@Configuration
public class QuartzConfig {
    ApplicationContext applicationContext;
    ApplicationPropertiesConfig properties;

    public QuartzConfig(ApplicationContext applicationContext, ApplicationPropertiesConfig properties) {
        this.applicationContext = applicationContext;
        this.properties = properties;
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setApplicationContext(applicationContext);
        schedulerFactoryBean.setJobFactory(springBeanJobFactory());
        schedulerFactoryBean.setAutoStartup(true);
        schedulerFactoryBean.setQuartzProperties(quartzProperties());
        schedulerFactoryBean.setJobDetails(myJobDetail());
        schedulerFactoryBean.setTriggers(myTrigger());
        return schedulerFactoryBean;
    }

    @Bean
    public SpringBeanJobFactory springBeanJobFactory() {
        return new SpringBeanJobFactory();
    }

    @Bean
    public Properties quartzProperties() {
        Properties properties = new Properties();
        properties.setProperty("org.quartz.scheduler.instanceName", "MyScheduler");
        properties.setProperty("org.quartz.scheduler.instanceId", "AUTO");
        properties.setProperty("org.quartz.threadPool.threadCount", "5");
        return properties;
    }

    @Bean
    public JobDetail myJobDetail() {
        return JobBuilder.newJob(MainTaskExecutor.class)
                .withIdentity("myTask")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger myTrigger() {
        return TriggerBuilder.newTrigger()
                .forJob(myJobDetail())
                .withIdentity("myTrigger")
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(properties.getRequestIntervalInSeconds())
                        .repeatForever())
                .build();
    }

}
