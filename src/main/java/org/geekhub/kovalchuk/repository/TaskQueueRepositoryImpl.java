package org.geekhub.kovalchuk.repository;

import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.Queue;

@Service
public class TaskQueueRepositoryImpl implements TaskQueueRepository {
    private final Queue<Runnable> taskQueue = new LinkedList<>();

    public Queue<Runnable> getTaskQueue() {
        return taskQueue;
    }

}
