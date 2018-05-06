package cn.edu.bistu.cs.xiong.wordsbook;

import java.util.UUID;

/**
 * 生成唯一标识符UUID
 */
public class GUID {
    public static String createGUID(){

        String uuid = UUID.randomUUID().toString();
        // 转换为大写,没必要
        uuid = uuid.toUpperCase().replaceAll("-", "");
        return uuid;
    }
}
