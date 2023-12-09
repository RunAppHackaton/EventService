package com.runapp.eventservice.feignClient;

import com.runapp.eventservice.dto.response.StorageServiceResponse;
import com.runapp.eventservice.dto.request.DeleteStorageRequest;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name = "storage-service")
public interface StorageServiceClient {

    @PostMapping(value = "/storage/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    StorageServiceResponse uploadFile(@Parameter(description = "file", required = true) @RequestPart("file") MultipartFile file,
                                      @Parameter(description = "directory", required = true) @RequestPart("directory") String directory);

    @DeleteMapping(value = "/storage/delete")
    ResponseEntity<Object> deleteFile(@RequestBody DeleteStorageRequest deleteStorageRequest);
}
