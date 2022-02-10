package com.app.workerpool.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;

import com.app.workerpool.models.Image;
import com.app.workerpool.repositories.UserRepository;
import com.app.workerpool.security.AuthenticationService;
import com.app.workerpool.service.ImageService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/image")
@CrossOrigin(value = {"*"}, exposedHeaders = {"Content-Disposition"})
public class ImageController {

    private final ImageService imageService;
    private final UserRepository userRepository;
    private final AuthenticationService authenticationService;


    @PostMapping("/uploadWorkerImage")
    public ResponseEntity<Void> uploadFile(@RequestParam(value = "file")
                                           final MultipartFile file, @RequestParam("workerID")final long workerID) {
        imageService.storeWorkerImage(file, workerID);
        URI fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUri();

        return ResponseEntity.created(fileDownloadUri).build();
    }

    @PostMapping("/uploadUserImage")
    public ResponseEntity<Void> uploadFile(@RequestParam("file") final MultipartFile file,
                                           final HttpServletRequest req) {
        imageService.storeUserImage(file, userRepository.findFirstByUsername(authenticationService.getUsername(req)));
        URI fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUri();

        return ResponseEntity.created(fileDownloadUri).build();
    }


//    @GetMapping("/downloadImage")
//    public ResponseEntity<byte[]> downloadFile(final HttpServletRequest req) {
//        Image dbFile = imageService.getFile(userRepository.findFirstByUsername(
//                authenticationService.getUsername(req)).getImage().getModelId());
//        HttpHeaders header = new HttpHeaders();
//        header.setContentType(MediaType.valueOf(dbFile.getContentType()));
//        header.setContentLength(dbFile.getData().length);
//        header.set("Content-Disposition", "attachment; filename=" + dbFile.getFileName());
//        return new ResponseEntity<>(dbFile.getData(), header, HttpStatus.OK);
//    }
}
