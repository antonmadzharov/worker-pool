package com.app.workerpool.controllers;

import com.app.workerpool.security.AuthenticationServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;

import com.app.workerpool.models.Image;
import com.app.workerpool.repositories.UserRepository;
import com.app.workerpool.service.ImageService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/images")
@CrossOrigin(value = {"*"}, exposedHeaders = {"Content-Disposition"})
public class ImageController {

    private final ImageService imageService;
    private final UserRepository userRepository;


    @PostMapping("//{workerId}")
    public ResponseEntity<Void> uploadFile(@RequestParam(value = "file")
                                           final MultipartFile file, @PathVariable() final Long workerId) {
        imageService.storeWorkerImage(file, workerId);
        URI fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUri();

        return ResponseEntity.created(fileDownloadUri).build();
    }

    @PostMapping("/uploadUserImage/{modelId}")
    public ResponseEntity<Void> uploadFile(@RequestParam("file") final MultipartFile file,
                                           Authentication authentication) {
        imageService.storeUserImage(file, userRepository.findFirstByUsername(authentication.getName()));
        URI fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUri();

        return ResponseEntity.created(fileDownloadUri).build();
    }

}
