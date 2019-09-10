package com.enhinck.db.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FileUtil {

    public static String createFileName(String title, String ext) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd_HH_mm_ss");
        StringBuilder builder = new StringBuilder();
        builder.append(title).append(simpleDateFormat.format(new Date())).append(".").append(ext);
        return builder.toString();
    }
}
