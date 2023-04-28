package org.geekhub.kovalchuk.repository;

import java.util.Queue;

public interface TaskQueueRepository {
    Queue<Runnable> getTaskQueue();
}
