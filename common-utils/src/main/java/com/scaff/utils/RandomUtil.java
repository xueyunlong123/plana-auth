package com.scaff.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * Created by huangpin on 16/10/21.
 */
public class RandomUtil {
    private static final String formatStr = "yyyyMMddHHmmssSSS";

    private static final SimpleDateFormat format = new SimpleDateFormat(formatStr);


    public static String getRandomId(){
        String str = UUID.randomUUID().toString().toUpperCase().replace("-","") + UUID.randomUUID().toString().toUpperCase().replace("-","");
        if (str.length() > 64){
            str = str.substring(0,64);
        }
        return str;
    }

    public static String getRandomNumberString(int length) {
        int random = (int) (Math.random() * Math.pow(10, 4));
        if (random < Math.pow(10, length - 1)) {
            random += (int) Math.pow(10, length - 1);
        }
        return String.valueOf(random);
    }

    public static String getRandomLong(int length){
        int first = new Random().nextInt(9) + 1;
        StringBuilder stringBuilder = new StringBuilder(first);
        for(int i = 1 ; i < length ; ++i){
            stringBuilder.append(new Random().nextInt(10));
        }
        return stringBuilder.toString();
    }

    public static void main(String[] args) {
        System.out.println(generateSpecialString("mc"));
    }

    public static String generateSpecialString(String suffix) {
        return UUID.randomUUID().toString().toUpperCase() + "-" + suffix;
    }

    public static String generateStringWithoutGang() {
        return UUID.randomUUID().toString().toUpperCase().replace("-","");
    }

    public static String generateRandomString() {
        return UUID.randomUUID().toString().replace("-","").toUpperCase();
    }

    public static String generateRandomAccount(){
        return String.format("%s%s",format.format(new Date()),getRandomLong(16));
    }

    public static String generateRandomResourceId(){
        return String.format("%s%s",format.format(new Date()),getRandomLong(16));
    }

    public static String generateUUIDWithTime(){
        return String.format("%s%s",format.format(new Date()),getRandomLong(16));
    }
}
