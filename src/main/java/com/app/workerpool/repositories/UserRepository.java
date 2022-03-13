package com.app.workerpool.repositories;

import com.app.workerpool.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findFirstByUsername(String username);

    Optional<User> findById(Long modelId);

//    List<User> findAllByDeletedIsFalse(Pageable pageable);
//
//    List<User> findAllByDeletedIsTrue(Pageable pageable);


}