package com.app.workerpool.controllers;

import com.app.workerpool.models.Image;
import com.app.workerpool.models.User;
import com.app.workerpool.models.Worker;
import com.app.workerpool.repositories.UserRepository;
import com.app.workerpool.repositories.WorkerRepository;
import com.app.workerpool.service.ImageService;
import com.app.workerpool.service.UserService;
import com.app.workerpool.service.WorkerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.IOException;
import java.net.URI;
import java.util.Optional;

@CrossOrigin(maxAge = 3600)
@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping(value = "workers")
@Validated
public class WorkerController {

    private final WorkerRepository workerRepository;
    private final WorkerService workerService;
    private final ImageService imageService;


    @PostMapping(value = "/create")
    public ResponseEntity<Worker> save(@Valid @RequestBody final Worker worker) {
        System.out.println(worker.toString());
        return new ResponseEntity<>(workerService.save(worker), HttpStatus.OK);
    }

    @PostMapping("/{workerId}/avatar")
    public ResponseEntity<Void> uploadFile(@RequestParam(value = "file")
                                           final MultipartFile file, @PathVariable() final Long workerId) {
        imageService.storeWorkerImage(file, workerId);
        URI fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUri();

        return ResponseEntity.created(fileDownloadUri).build();
    }

    @GetMapping (value = "/{modelId}")
    public ResponseEntity<?> getWorker(@PathVariable() final long modelId){
        return Optional
                .of( workerRepository.findById(modelId) )
                .map( user -> ResponseEntity.ok().body(user) )
                .orElseGet( () -> ResponseEntity.notFound().build() );
    }

    @GetMapping("/{modelId}/avatar")
    public ResponseEntity<ByteArrayResource> downloadWorkerOwnImage(@PathVariable() final long modelId) {

        return Optional
                .ofNullable(imageService.getFile(workerRepository.findById(modelId).get().getImage().getModelId()))
                .map(this::createImageModelInResponseEntity)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @PostMapping(value = "/{modelId}/rate")
    public ResponseEntity<?> rateWorker(@PathVariable() final long modelId,
                                            @RequestParam(value = "speed") @Min(1) @Max(10)
                                                    int speed,
                                            @RequestParam(value = "quality") @Min(1) @Max(10)
                                                    int quality,
                                            @RequestParam(value = "communication") @Min(1) @Max(10)
                                                    int communication,
                                            @RequestParam(value = "price") @Min(1) @Max(10)
                                                    int price) {

        return new ResponseEntity<>(workerService.updateWorkerRating(speed + quality + communication + price, workerRepository.findById(modelId).get()), HttpStatus.OK);
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
