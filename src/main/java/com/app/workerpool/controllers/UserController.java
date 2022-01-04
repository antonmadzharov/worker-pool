package com.app.workerpool.controllers;

import com.app.workerpool.models.User;
import com.app.workerpool.repositories.UserRepository;
import com.app.workerpool.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;

@CrossOrigin(maxAge = 3600)
@RestController
@RequiredArgsConstructor
public class UserController {

}
