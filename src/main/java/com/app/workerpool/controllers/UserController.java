package com.app.workerpool.controllers;

import com.app.workerpool.models.User;
import com.app.workerpool.repositories.UserRepository;
import com.app.workerpool.security.AuthenticationService;
import com.app.workerpool.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@CrossOrigin(maxAge = 3600)
@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping(value = "users")
public class UserController {

    private final UserRepository userRepository;
    private final AuthenticationService authenticationService;
    private final UserService userService;


    @GetMapping(value = "/me")
    public ResponseEntity<User> getUserOwnInfo(final HttpServletRequest req) throws IOException {
        System.out.println(req.getReader());
        return Optional
                .ofNullable( userRepository.findFirstByUsername(
                        authenticationService.getUsername(req)) )
                .map( user -> ResponseEntity.ok().body(user) )
                .orElseGet( () -> ResponseEntity.notFound().build() );

    }
    @PatchMapping(value = "/me/update-password")
    public ResponseEntity<User> updateUserOwnInfo(@RequestParam final String password,final HttpServletRequest req){

        return Optional
                .ofNullable( userService.updateCurrentUserPassword(password,userRepository.findFirstByUsername(
                        authenticationService.getUsername(req))) )
                .map( user -> ResponseEntity.ok().body(user) )
                .orElseGet( () -> ResponseEntity.badRequest().build() );

    }
    @PatchMapping(value = "/me/update-email")
    public ResponseEntity<User> updateUserOwnEmail(@RequestParam final String email,final HttpServletRequest req){
        return Optional
                .ofNullable( userService.updateCurrentUserEmail(email,userRepository.findFirstByUsername(
                        authenticationService.getUsername(req))) )
                .map( user -> ResponseEntity.ok().body(user) )
                .orElseGet( () -> ResponseEntity.badRequest().build() );
    }

    @PostMapping(value = "/sign-up")
    public ResponseEntity<User> save(@Valid @RequestBody final User user) {
        System.out.println(user.toString());
        return new ResponseEntity<>(userService.save(user), HttpStatus.OK);
    }
}
