package com.enhinck.db.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileUtil {

    public static String createFileName(String title, String ext) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd_HH_mm_ss");
        StringBuilder builder = new StringBuilder();
        builder.append(title).append(simpleDateFormat.format(new Date())).append(".").append(ext);
        return builder.toString();
    }


    public static void main(String[] args) {
        File file = new File("/Users/huenbin/work/shop");
        File[] files = file.listFiles();

        for (int i = 0; i < files.length; i++) {
            System.out.print(files[i].getName() + " ");
        }
    }

        //phantom-server-config phantom-server-eureka phantom-service-pay phantom-service-reservation phantom-service-subscribe phantom-service-open phantom-service-synchronization phantom-common-parent phantom-service-space phantom-service-message phantom-gateway-shop phantom-service-storage phantom-gateway-operation phantom-gateway-user phantom-service-authorization phantom-service-circle phantom-gateway-platform phantom-service-user phantom-gateway-portal phantom-service-operation phantom-service-portal phantom-service-auth phantom-service-town phantom-service-point phantom-service-company phantom-gateway-open phantom-gateway-enterprise
}
