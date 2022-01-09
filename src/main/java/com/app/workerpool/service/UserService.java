package com.app.workerpool.service;

import com.app.workerpool.models.User;

public interface UserService {

    User save(User user);

    User updateCurrentUserPassword(String password, User user);

    User updateCurrentUserEmail(String password,User user);
}