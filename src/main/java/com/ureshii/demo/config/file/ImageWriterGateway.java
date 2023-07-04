package com.ureshii.demo.config.file;

import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.file.FileHeaders;
import org.springframework.messaging.handler.annotation.Header;

@MessagingGateway(defaultRequestChannel = "imageChanel")
public interface ImageWriterGateway {
    void saveFile(@Header(FileHeaders.FILENAME) String fileName, byte[] image);
}
