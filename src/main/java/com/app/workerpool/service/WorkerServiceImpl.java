package com.app.workerpool.service;

import com.app.workerpool.models.User;
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
        newWorker.setAge(worker.getAge());
        newWorker.setEmail(worker.getEmail());
        newWorker.setExperience(worker.getExperience());
        newWorker.setSpecialization(worker.getSpecialization());
        newWorker.setRating(worker.getRating());
        return workerRepository.save(newWorker);
    }
//    4           6       8
//    3           10      8
//    4           8       8
//    5           4       8   (6 + 10 + 8 + 4) / 4 = 7        (4 + 7) / 2 = 5.5       (8 + 8 + 8 + 8) / 4 = 8
//
//            (2 + 3 + 4 + 5)/4 = 4 / 1 = 4
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
