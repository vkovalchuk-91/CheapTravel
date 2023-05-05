package org.geekhub.kovalchuk.repository;

import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.Queue;

@Service
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
