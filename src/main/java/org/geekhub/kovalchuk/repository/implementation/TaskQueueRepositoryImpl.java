package org.geekhub.kovalchuk.repository.implementation;

import org.geekhub.kovalchuk.repository.TaskQueueRepository;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.Queue;

@Repository
public class TaskQueueRepositoryImpl implements TaskQueueRepository {
    private final Queue<Runnable> taskQueue = new LinkedList<>();
    private boolean needToStartUpdateRoutes = false;
    private boolean needToFinishRoutesUpdating = false;

    public Queue<Runnable> getTaskQueue() {
        return taskQueue;
    }

    @Override
    public boolean isNeedToStartUpdateRoutes() {
        return needToStartUpdateRoutes;
    }

    @Override
    public void setNeedToStartUpdateRoutes(boolean needToStartUpdateRoutes) {
        this.needToStartUpdateRoutes = needToStartUpdateRoutes;
    }

    @Override
    public boolean isNeedToFinishRoutesUpdating() {
        return needToFinishRoutesUpdating;
    }

    @Override
    public void setNeedToFinishRoutesUpdating(boolean needToFinishRoutesUpdating) {
        this.needToFinishRoutesUpdating = needToFinishRoutesUpdating;
    }
}
