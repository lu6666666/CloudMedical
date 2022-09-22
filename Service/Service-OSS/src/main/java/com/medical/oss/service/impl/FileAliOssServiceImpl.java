package com.medical.oss.service.impl;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.PutObjectRequest;
import com.medical.exception.customizeException;
import com.medical.oss.service.FileAliOssService;
import com.medical.oss.utils.AliUtils;
import com.medical.result.ResultCodeEnum;
import com.medical.utils.RandomUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Luo.X
 * @Description 文件上传接口实现类
 * @CreateTime 2022-09-20 14:34
 * @Version 1.0
 */
@Service
public class FileAliOssServiceImpl implements FileAliOssService {

    /**
     * 文件上传
     *
     * @param file
     * @return
     */
    @Override
    public Map<String, Object> fileUploadService(MultipartFile file) {
        if (file == null) {
            throw new customizeException(ResultCodeEnum.FILEUPLOAD_ERROR);
        }
        Map<String, Object> map = new HashMap<>();

        // Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。
        String endpoint = AliUtils.ENDPOINT;
        // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
        String accessKeyId = AliUtils.ACCESSKEY_ID;
        String accessKeySecret = AliUtils.ACCESSKEY_SECRET;
        // 填写Bucket名称，例如examplebucket。
        String bucketName = AliUtils.BUCKET_NAME;
        // 填写Object完整路径，完整路径中不能包含Bucket名称，例如exampledir/exampleobject.txt。
        //String objectName = "exampledir/exampleobject.txt";

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        try {
            // 上传文件流
            InputStream inputStream = file.getInputStream();

            // 按照当前日期，创建文件夹，上传到创建文件夹里面
            String timeUrl = new DateTime().toString("yyyy/MM/dd");
            // 生成文件名
            String fileName = timeUrl + "/" + RandomUtils.getUUID() + file.getOriginalFilename();

            // 创建PutObjectRequest对象。
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName, inputStream);

            // 如果需要上传时设置存储类型和访问权限，请参考以下示例代码。
            // ObjectMetadata metadata = new ObjectMetadata();
            // metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard.toString());
            // metadata.setObjectAcl(CannedAccessControlList.Private);
            // putObjectRequest.setMetadata(metadata);

            // 上传文件。
            ossClient.putObject(putObjectRequest);

            // 上传后文件的地址 https://cloudmedical.oss-cn-hangzhou.aliyuncs.com/%E5%88%98%E8%AF%86%E6%B8%8A%E7%8E%A9%E6%89%8B%E6%9C%BA.jpg
            String url = "https://" + bucketName + "." + endpoint + "/" + fileName;
            map.put("url", url);
            map.put("fileName", fileName);
            map.put("size", file.getSize());
            map.put("type", file.getContentType());
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
            throw new customizeException(ResultCodeEnum.FILEUPLOAD_ERROR);
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
            throw new customizeException(ResultCodeEnum.FILEUPLOAD_ERROR);
        } catch (IOException e) {
            e.printStackTrace();
            throw new customizeException(ResultCodeEnum.FILEUPLOAD_ERROR);
        } finally {
            if (ossClient != null) {
                // 关闭OSSClient
                ossClient.shutdown();
            }
        }
        return map;
    }
}
