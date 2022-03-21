package com.app.workerpool.repositories;

import com.app.workerpool.models.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkerRepository extends JpaRepository<Worker, Long>{

    List<Worker> findWorkerByFirstName(String firstName);

    List<Worker> findWorkerByLastName(String lastName);

    List<Worker> findWorkerBySpecialization(String specialization);

}
