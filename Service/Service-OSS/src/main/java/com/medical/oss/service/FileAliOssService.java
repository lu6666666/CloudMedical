package com.medical.oss.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @author Luo.X
 * @Description 文件上传接口
 * @CreateTime 2022-09-20 14:34
 * @Version 1.0
 */
public interface FileAliOssService {

    /**
     * 文件上传
     * @param file
     * @return
     */
    Map<String, Object> fileUploadService(MultipartFile file);
}
