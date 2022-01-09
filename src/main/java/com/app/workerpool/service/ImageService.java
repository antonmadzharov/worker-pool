package com.app.workerpool.service;

import com.app.workerpool.models.Image;
import com.app.workerpool.models.User;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    void storeWorkerImage(MultipartFile file, long workerID);

    void storeUserImage(MultipartFile file, User user);

    Image getFile(long fileId);
}
