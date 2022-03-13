package com.app.workerpool.service;

import com.app.workerpool.models.Worker;

public interface WorkerService {

    Worker save(Worker worker);

    Worker updateWorkerRating(double rating, Worker worker);

}
