package com.hwamok.api;

import com.hwamok.core.response.ApiResult;
import com.hwamok.core.response.Result;
import com.hwamok.file.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;

    @PostMapping
    public ResponseEntity<ApiResult<?>> create(@RequestPart MultipartFile imageProfile) {
        fileService.upload(imageProfile);
        return Result.created();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResult<?>> delete(@PathVariable long id) {
        fileService.delete(id);
        return Result.ok();
    }
}
