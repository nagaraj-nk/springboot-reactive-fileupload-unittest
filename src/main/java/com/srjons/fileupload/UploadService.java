package com.srjons.fileupload;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

@Service
@Slf4j
public class UploadService {

    @Autowired
    private UploadRepo uploadRepo;

    public void process(String uploadDir, String fileName) {
        uploadRepo.process();
        File file = new File(uploadDir, fileName);
        try {
            String content = FileUtils.readFileToString(file, Charset.defaultCharset());
            log.info("content: " + content);
        } catch (IOException e) {
            e.printStackTrace();
        }

        log.info("some processing");
    }

    public void process() {
        log.info("some other svc process");
    }
}
