package com.course.server.util;

import java.util.UUID;

public class UuidUtil {

    public static String[] chars = new String[] { "a", "b", "c", "d", "e", "f",
            "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
            "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z" };

    /**
     * 获取短的uuid
     */
    public static String getShortUuid(){
        StringBuilder shortBuffer = new StringBuilder();
        String uuid = UuidUtil.getUuid();
        for (int i = 0; i < 8; i++) {
            String uuid_four = uuid.substring(i * 4, i * 4 + 4);
            int x = Integer.parseInt(uuid_four, 16);
            shortBuffer.append(chars[x % 0x3E]); // 对62取余
        }
        return shortBuffer.toString();
    }

    /**
     * 获取32位的uuid
     */
    public static String getUuid(){
        String uuid = UUID.randomUUID().toString().replace("-", "");
        return uuid;
    }

    public static void main(String[] args) {
        System.out.println(getUuid());
        System.out.println(getShortUuid());
    }
}
