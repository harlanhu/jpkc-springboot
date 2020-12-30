package com.study.jpkc.common.component;

import cn.hutool.core.util.ObjectUtil;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.PutObjectResult;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.*;
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


    public PutObjectResult upload(String objName, String obj) {
        OSS ossClient = new OSSClientBuilder()
                .build(endpoint, accessKeyId, accessKeySecret);

        PutObjectResult result = ossClient.putObject(bucketName, objName, new ByteArrayInputStream(obj.getBytes(StandardCharsets.UTF_8)));
        ossClient.shutdown();
        return result;
    }

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
