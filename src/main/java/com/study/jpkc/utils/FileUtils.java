package com.study.jpkc.utils;

import org.springframework.web.multipart.MultipartFile;

/**
 * @Author Harlan
 * @Date 2021/3/3
 */
public class FileUtils {

    private static final String[] PICTURE_TYPE = {"image/jpeg", "image/jpg", "image/png"};

    public static boolean isTypeOfPicture(MultipartFile file) {
        String fileType = file.getContentType();
        for (String s : PICTURE_TYPE) {
            if (s.equals(fileType)) {
                return true;
            }
        }
        return false;
    }
}
