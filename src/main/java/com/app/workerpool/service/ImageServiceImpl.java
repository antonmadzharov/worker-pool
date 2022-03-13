package com.app.workerpool.service;

import com.app.workerpool.exceptions.FileStorageException;
import com.app.workerpool.exceptions.MyFileNotFoundException;
import com.app.workerpool.models.Worker;
import com.app.workerpool.models.Image;
import com.app.workerpool.models.User;
import com.app.workerpool.repositories.WorkerRepository;
import com.app.workerpool.repositories.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@Service
public class ImageServiceImpl implements ImageService {

    private final ImageRepository userImageRepository;
    private final WorkerRepository workerRepository;

    public void storeWorkerImage(final MultipartFile file , final long workerID) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }
            Image image = new Image(file.getOriginalFilename(),
                    file.getContentType(),
                    file.getBytes(), workerRepository.getById(workerID));

            userImageRepository.save(image);
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }
    public void storeUserImage(final MultipartFile file,final User user) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }
            Image image = new Image(file.getOriginalFilename(),
                    file.getContentType(),
                    file.getBytes(),user);
            userImageRepository.save(image);
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public Image getFile(final long fileId) {
        return userImageRepository.findById(fileId)
                .orElseThrow(() -> new MyFileNotFoundException("File not found with userImageId " + fileId));
    }
}
