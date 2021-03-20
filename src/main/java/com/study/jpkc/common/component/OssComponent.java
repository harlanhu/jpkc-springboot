package com.study.jpkc.common.component;

import cn.hutool.core.util.ObjectUtil;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.*;
import io.jsonwebtoken.lang.Strings;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.URL;
import java.util.Date;

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
    private static final long EXPIRES_DATE = 3600L * 1000 * 24 * 365;


    /**
     * 字符串上传
     * @param objName 对象名
     * @param str 字符串
     * @return 上传结果
     */
    public URL upload(String objName, String str) {
        OSS ossClient = new OSSClientBuilder()
                .build(endpoint, accessKeyId, accessKeySecret);
        PutObjectRequest request = new PutObjectRequest(bucketName, objName, new ByteArrayInputStream(str.getBytes(Strings.UTF_8)));
        return upload(objName, ossClient, request);
    }

    /**
     * Byte数组上传
     * @param objName 对象名
     * @param bytes byte数组
     * @return 上传结果
     */
    public URL upload(String objName, byte[] bytes) {
        OSS ossClient = new OSSClientBuilder()
                .build(endpoint, accessKeyId, accessKeySecret);
        PutObjectRequest request = new PutObjectRequest(bucketName, objName, new ByteArrayInputStream(bytes));
        return upload(objName, ossClient, request);
    }

    /**
     * 上传
     * @param objName 对象名
     * @param ossClient 客户端
     * @param request 请求参数
     * @return 上传地址
     */
    private URL upload(String objName, OSS ossClient, PutObjectRequest request) {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setObjectAcl(CannedAccessControlList.PublicRead);
        request.setMetadata(metadata);
        ossClient.putObject(request);
        Date date = new Date(System.currentTimeMillis() + EXPIRES_DATE);
        URL url = ossClient.generatePresignedUrl(bucketName, objName, date);
        ossClient.shutdown();
        return url;
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
    public URL upload(String objName, File file) throws FileNotFoundException {
        OSS ossClient = new OSSClientBuilder()
                .build(endpoint, accessKeyId, accessKeySecret);
        InputStream is = new FileInputStream(file);
        PutObjectRequest request = new PutObjectRequest(bucketName, objName, is);
        return upload(objName, ossClient, request);
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
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setObjectAcl(CannedAccessControlList.PublicRead);
        request.setMetadata(metadata);
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
