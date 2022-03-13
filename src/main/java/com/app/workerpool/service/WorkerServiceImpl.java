package com.app.workerpool.service;

import com.app.workerpool.models.User;
import com.app.workerpool.models.Worker;
import com.app.workerpool.repositories.WorkerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WorkerServiceImpl implements WorkerService {

    private final WorkerRepository workerRepository;


    public Worker save(final Worker worker) {
        Worker newWorker = new Worker();
        newWorker.setFirstName(worker.getFirstName());
        newWorker.setLastName(worker.getLastName());
        newWorker.setAge(worker.getAge());
        newWorker.setEmail(worker.getEmail());
        newWorker.setExperience(worker.getExperience());
        newWorker.setSpecialization(worker.getSpecialization());
        return workerRepository.save(newWorker);
    }
}
