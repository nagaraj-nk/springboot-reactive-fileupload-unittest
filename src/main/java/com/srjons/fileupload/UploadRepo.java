package com.srjons.fileupload;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public class UploadRepo {
    public void process() {
        log.info("some repo processing");
    }
}
