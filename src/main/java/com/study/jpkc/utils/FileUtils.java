package com.study.jpkc.utils;

import org.springframework.web.multipart.MultipartFile;

/**
 * @Author Harlan
 * @Date 2021/3/3
 */
public class FileUtils {

    private static final String[] PICTURE_TYPE = {"image/jpeg", "image/jpg", "image/png"};

    private static final String[] VIDEO_TYPE = {"video/mp4", "video/mp3", "video/avi", "video/mpg", "video/mpeg", "video/wmv", "video/rmvb", "video/rm", "video/flv"};

    public static boolean isTypeOfPicture(MultipartFile file) {
        String fileType = file.getContentType();
        for (String s : PICTURE_TYPE) {
            if (s.equals(fileType)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isTypeOfVideo(MultipartFile file) {
        String fileType = file.getContentType();
        for (String s : VIDEO_TYPE) {
            if (s.equals(fileType)) {
                return true;
            }
        }
        return false;
    }

    public static String getFileSuffix(String originalFileName) {
        int lastIndex = originalFileName.lastIndexOf(".");
        return originalFileName.substring(lastIndex);
    }
}
