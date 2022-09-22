package com.medical.oss.controller;

import com.medical.oss.service.FileAliOssService;
import com.medical.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @author Luo.X
 * @Description 阿里OSS文件上传 controller
 * @CreateTime 2022-09-20 14:31
 * @Version 1.0
 */
@Api(tags = "阿里OSS文件上传")
@RestController
@RequestMapping("/site/FileAliOss")
public class FileAliOssApplication {

    @Autowired
    private FileAliOssService fileAliOssService;
    /**
     * 文件上传
     * @param file
     * @return
     */
    @ApiOperation(value = "文件上传")
    @PostMapping("/fileUpload")
    public Result fileUpload(MultipartFile file){
        Map<String,Object> map = fileAliOssService.fileUploadService(file);
        return Result.ok(map);
    }
}
