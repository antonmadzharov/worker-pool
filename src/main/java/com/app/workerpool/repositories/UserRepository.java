package com.app.workerpool.repositories;

import com.app.workerpool.models.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findFirstByUsername(String username);

//    List<User> findAllByDeletedIsFalse(Pageable pageable);
//
//    List<User> findAllByDeletedIsTrue(Pageable pageable);


}