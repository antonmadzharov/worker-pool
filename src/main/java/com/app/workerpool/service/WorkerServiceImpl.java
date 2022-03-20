package com.app.workerpool.service;

import com.app.workerpool.models.Worker;
import com.app.workerpool.repositories.WorkerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;

@Service
@RequiredArgsConstructor
public class WorkerServiceImpl implements WorkerService {

    private final WorkerRepository workerRepository;


    public Worker save(final Worker worker) {
        Worker newWorker = new Worker();
        newWorker.setFirstName(worker.getFirstName());
        newWorker.setLastName(worker.getLastName());
        newWorker.setEmail(worker.getEmail());
        newWorker.setExperience(worker.getExperience());
        newWorker.setSpecialization(worker.getSpecialization());
        newWorker.setRating(worker.getRating());
        return workerRepository.save(newWorker);
    }

    @Override
    public Worker updateWorkerRating(final double rating, final Worker worker) {
        double currentRating = worker.getRating() * worker.getCounter() * 4;
        worker.setCounter(worker.getCounter() + 1);
        System.out.println("rating: " + rating);
        System.out.println("currentRating: " + currentRating);
        System.out.println("worker.getCounter(): " + worker.getCounter());
        double newRating = (rating + currentRating) / (worker.getCounter() * 4);
        worker.setRating(Double.parseDouble(new DecimalFormat("#.##").format(newRating)));
        System.out.println("SHOULD BE: " + newRating);

        return workerRepository.save(worker);
    }
}
