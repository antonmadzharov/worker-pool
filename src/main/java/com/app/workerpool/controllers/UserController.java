package com.app.workerpool.controllers;

import com.app.workerpool.models.User;
import com.app.workerpool.repositories.UserRepository;
import com.app.workerpool.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.io.IOException;
import java.util.Optional;

@CrossOrigin(maxAge = 3600)
@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping(value = "users")
public class UserController {
    private final UserRepository userRepository;
    private final UserService userService;


    @GetMapping(value = "/me")
    public ResponseEntity<User> getUserOwnInfo(Authentication authentication) throws IOException {
        return Optional
                .ofNullable( userRepository.findFirstByUsername(
                        authentication.getName()) )
                .map( user -> ResponseEntity.ok().body(user) )
                .orElseGet( () -> ResponseEntity.notFound().build() );

    }
    @PatchMapping(value = "/me/update-password")
    public ResponseEntity<User> updateUserOwnInfo(@RequestParam final String password,Authentication authentication){

        return Optional
                .ofNullable( userService.updateCurrentUserPassword(password,userRepository.findFirstByUsername(
                        authentication.getName())) )
                .map( user -> ResponseEntity.ok().body(user) )
                .orElseGet( () -> ResponseEntity.badRequest().build() );

    }
    @PatchMapping(value = "/me/update-email")
    public ResponseEntity<User> updateUserOwnEmail(@RequestParam final String email,Authentication authentication){
        return Optional
                .ofNullable( userService.updateCurrentUserEmail(email,userRepository.findFirstByUsername(
                        authentication.getName())) )
                .map( user -> ResponseEntity.ok().body(user) )
                .orElseGet( () -> ResponseEntity.badRequest().build() );
    }

    @PostMapping(value = "/sign-up")
    public ResponseEntity<User> save(@Valid @RequestBody final User user) {
        System.out.println(user.toString());
        return new ResponseEntity<>(userService.save(user), HttpStatus.OK);
    }
}
