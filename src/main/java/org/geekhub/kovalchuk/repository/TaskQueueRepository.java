package org.geekhub.kovalchuk.repository;

import java.util.Queue;

public interface TaskQueueRepository {
    Queue<Runnable> getTaskQueue();
    public boolean isNeedToStartUpdateRoutes();
    public void setNeedToStartUpdateRoutes(boolean needToUpdateRoutes);
    public boolean isNeedToFinishRoutesUpdating();
    public void setNeedToFinishRoutesUpdating(boolean needToUpdateRoutes);
}
