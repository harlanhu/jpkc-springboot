package com.study.jpkc.common.component;

import cn.hutool.core.util.ObjectUtil;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * @Author Harlan
 * @Date 2020/12/30
 */
@Component
@Slf4j
@Setter
@ConfigurationProperties(prefix = "aliyun.oss")
public class OssComponent {

    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;


    /**
     * 字符串上传
     * @param objName 对象名
     * @param str 字符串
     * @return 上传结果
     */
    public PutObjectResult upload(String objName, String str) {
        OSS ossClient = new OSSClientBuilder()
                .build(endpoint, accessKeyId, accessKeySecret);
        // 如果需要上传时设置存储类型与访问权限，请参考以下示例代码。
        // ObjectMetadata metadata = new ObjectMetadata();
        // metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard.toString());
        // metadata.setObjectAcl(CannedAccessControlList.Private);
        // putObjectRequest.setMetadata(metadata);
        PutObjectResult result = ossClient.putObject(bucketName, objName, new ByteArrayInputStream(str.getBytes(StandardCharsets.UTF_8)));
        ossClient.shutdown();
        return result;
    }

    /**
     * Byte数组上传
     * @param objName 对象名
     * @param bytes byte数组
     * @return 上传结果
     */
    public PutObjectResult upload(String objName, byte[] bytes) {
        OSS ossClient = new OSSClientBuilder()
                .build(endpoint, accessKeyId, accessKeySecret);
        PutObjectResult result = ossClient.putObject(bucketName, objName, new ByteArrayInputStream(bytes));
        ossClient.shutdown();
        return result;
    }

    /**
     * 上传网络流
     * @param objName 对象名
     * @param url url
     * @return 上传结果
     */
    public PutObjectResult uploadByUrl(String objName, String url) throws IOException {
        OSS ossClient = new OSSClientBuilder()
                .build(endpoint, accessKeyId, accessKeySecret);
        InputStream is = new URL(url).openStream();
        PutObjectResult result = ossClient.putObject(bucketName, objName, is);
        ossClient.shutdown();
        return result;
    }

    /**
     * 上传文件流
     * @param objName 对象名
     * @param file 文件
     * @return 返回结果
     * @throws FileNotFoundException 文件异常
     */
    public PutObjectResult upload(String objName, File file) throws FileNotFoundException {
        OSS ossClient = new OSSClientBuilder()
                .build(endpoint, accessKeyId, accessKeySecret);
        InputStream is = new FileInputStream(file);
        PutObjectResult result = ossClient.putObject(bucketName, objName, is);
        ossClient.shutdown();
        return result;
    }

    /**
     * 本地文件上传
     * @param objName 对象名
     * @param filePath 文件路径
     * @return 上传结果
     */
    public PutObjectResult uploadByFilePtah(String objName, String filePath) {
        OSS ossClient = new OSSClientBuilder()
                .build(endpoint, accessKeyId, accessKeySecret);
        PutObjectRequest request = new PutObjectRequest(bucketName, objName, new File(filePath));
        // 如果需要上传时设置存储类型与访问权限，请参考以下示例代码。
        // ObjectMetadata metadata = new ObjectMetadata();
        // metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard.toString());
        // metadata.setObjectAcl(CannedAccessControlList.Private);
        // putObjectRequest.setMetadata(metadata);
        PutObjectResult result = ossClient.putObject(request);
        ossClient.shutdown();
        return result;
    }

    /**
     * 根据对象名下载数据
     * @param objName 对项目
     * @throws IOException IO异常
     */
    public void download(String objName) throws IOException {
        OSS ossClient = new OSSClientBuilder()
                .build(endpoint, accessKeyId, accessKeySecret);
        OSSObject ossObject = ossClient.getObject(bucketName, objName);
        InputStream content = ossObject.getObjectContent();
        if (!ObjectUtil.isNull(content)) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(content));
            while (true) {
                String line = reader.readLine();
                if (line == null){
                    break;
                }
                log.info(line);
            }
            content.close();
        }
        ossClient.shutdown();
    }
}
