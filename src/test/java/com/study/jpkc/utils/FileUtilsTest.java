package com.study.jpkc.utils;

import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * @Author Harlan
 * @Date 2021/3/10
 */
class FileUtilsTest {

    @Test
    void getFileUrlPathTest() throws MalformedURLException {
        URL url = new URL("http://web-applications.oss-cn-chengdu.aliyuncs.com/jpck/course/5a22571cd942475aae7fe24aa596b451/logo/courseLogo.png?Expires=1615271192&OSSAccessKeyId=LTAI4GHwUUA7g2o474KgGesL&Signature=56sQcQuWo44zmzKGeBePzCBhUEU%3D");
        String urlPath = FileUtils.getFileUrlPath(url);
        assertThat(urlPath).isNotBlank();
        System.out.println(urlPath);
    }
}
