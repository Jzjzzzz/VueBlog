package com.jzj.blog.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.CannedAccessControlList;
import com.jzj.blog.oss.service.FileService;
import com.jzj.blog.oss.util.OssProperties;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    @Override
    public String upload(InputStream inputStream, String module, String fileName) {
        String endpoint = OssProperties.END_POINT;
        String accessKeyId = OssProperties.KEY_ID;
        String accessKeySecret = OssProperties.KEY_SECRET;

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        //判断BUCKET_NAME是否存在
        if(!ossClient.doesBucketExist(OssProperties.BUCKET_NAME)){
            ossClient.createBucket(OssProperties.BUCKET_NAME);
            ossClient.setBucketAcl(OssProperties.BUCKET_NAME, CannedAccessControlList.PublicRead);
        }
        //上传文件
        //构建日期路径
        String timeFolder = new DateTime().toString("/yyyy/MM/dd/");
        //文件名生成
        fileName=UUID.randomUUID().toString()+ fileName.substring(fileName.lastIndexOf("."));
        String key=module+timeFolder+fileName;
        ossClient.putObject(OssProperties.BUCKET_NAME, key, inputStream);

        // 关闭OSSClient。
        ossClient.shutdown();

        //文件的URL地址
        return "https://" + OssProperties.BUCKET_NAME + "." + OssProperties.END_POINT + "/" + key;
    }

    @Override
    public void removeFile(String url) {

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(OssProperties.END_POINT, OssProperties.KEY_ID, OssProperties.KEY_SECRET);

        //文件名（服务器上的文件路径）
        String host = "https://" + OssProperties.BUCKET_NAME + "." + OssProperties.END_POINT + "/";
        String objectName = url.substring(host.length());

        // 删除文件。如需删除文件夹，请将ObjectName设置为对应的文件夹名称。如果文件夹非空，则需要将文件夹下的所有object删除后才能删除该文件夹。
        ossClient.deleteObject(OssProperties.BUCKET_NAME, objectName);

        // 关闭OSSClient。
        ossClient.shutdown();

    }
}
