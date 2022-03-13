package com.app.workerpool.controllers;

import com.app.workerpool.models.Image;
import com.app.workerpool.models.User;
import com.app.workerpool.repositories.UserRepository;
import com.app.workerpool.service.ImageService;
import com.app.workerpool.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@CrossOrigin(maxAge = 3600)
@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping(value = "users")
public class UserController {
    private final UserRepository userRepository;
    private final UserService userService;
    private final ImageService imageService;


    @PostMapping(value = "/sign-up")
    public ResponseEntity<User> save(@Valid @RequestBody final User user) {
        System.out.println(user.toString());
        return new ResponseEntity<>(userService.save(user), HttpStatus.OK);
    }

    @GetMapping(value = "/self")
    public ResponseEntity<User> getUserOwnInfo(Authentication authentication) throws IOException {
        return Optional
                .ofNullable( userRepository.findFirstByUsername(
                        authentication.getName()) )
                .map( user -> ResponseEntity.ok().body(user) )
                .orElseGet( () -> ResponseEntity.notFound().build() );

    }

    @PatchMapping(value = "/self/update-password")
    public ResponseEntity<User> updateUserOwnPassword(@RequestParam final String password,Authentication authentication){

        return Optional
                .ofNullable( userService.updateCurrentUserPassword(password,userRepository.findFirstByUsername(
                        authentication.getName())) )
                .map( user -> ResponseEntity.ok().body(user) )
                .orElseGet( () -> ResponseEntity.badRequest().build() );

    }

    @PatchMapping(value = "/self/update-email")
    public ResponseEntity<User> updateUserOwnEmail(@RequestParam final String email,Authentication authentication){
        return Optional
                .ofNullable( userService.updateCurrentUserEmail(email,userRepository.findFirstByUsername(
                        authentication.getName())) )
                .map( user -> ResponseEntity.ok().body(user) )
                .orElseGet( () -> ResponseEntity.badRequest().build() );
    }

    @GetMapping (value = "/{modelId}")
    public ResponseEntity<?> getUser(@PathVariable() final Long modelId){
        return Optional
                .of( userRepository.findById(modelId) )
                .map( user -> ResponseEntity.ok().body(user) )
                .orElseGet( () -> ResponseEntity.notFound().build() );
    }

    @GetMapping("/self/avatar")
    public ResponseEntity<ByteArrayResource> downloadUserOwnImage(final Authentication authentication) {

        return Optional
                .ofNullable(imageService.getFile(userRepository.findFirstByUsername(
                        authentication.getName()).getImage().getModelId()))
                .map(this::createImageModelInResponseEntity)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{modelId}/avatar")
    public ResponseEntity<ByteArrayResource> downloadUserImage(@PathVariable() final Long modelId) {

        if(userRepository.findById(modelId).isPresent()) {
            return Optional
                    .ofNullable(imageService.getFile(userRepository.findById(modelId).get().getImage().getModelId()))
                    .map(this::createImageModelInResponseEntity)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    private ResponseEntity<ByteArrayResource> createImageModelInResponseEntity(Image dbFile) {

        try {

            Image file = imageService.getFile(dbFile.getModelId());

            ByteArrayResource resource = new ByteArrayResource(file.getData());

            return ResponseEntity.ok().contentLength(dbFile.getData().length).contentType(MediaType.IMAGE_JPEG).body(resource);
        } catch (Exception e) {
            throw new InternalAuthenticationServiceException("Unable to generate image");
        }
    }
}
