package com.medical.utils;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @author Luo.X
 * @Description 随机数
 * @CreateTime 2022-09-08 15:30
 * @Version 1.0
 */
public class RandomUtils {

    /**
     * UUID随机字符串
     * @return
     */
    public static String getUUID(){
        return UUID.randomUUID().toString().replace("-", "'");
    }

    /**
     * 当前时间随机字符串
     * @return
     */
    public static String getDate(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String s = simpleDateFormat.format(new Date());
        return s;
    }
}
