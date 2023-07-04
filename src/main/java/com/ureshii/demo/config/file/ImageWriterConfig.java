package com.ureshii.demo.config.file;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.file.dsl.Files;
import org.springframework.integration.file.support.FileExistsMode;

import java.io.File;

@Configuration
public class ImageWriterConfig {

    @Value("${app.baseDirectory}")
    private String baseDirectory;

    @Bean
    public IntegrationFlow imageWriterFlow() {
        return IntegrationFlow
                .from(MessageChannels.direct("imageChanel"))
                .handle(Files.outboundAdapter(new File(baseDirectory))
                        .fileExistsMode(FileExistsMode.REPLACE).autoCreateDirectory(true)).get();
    }
}
