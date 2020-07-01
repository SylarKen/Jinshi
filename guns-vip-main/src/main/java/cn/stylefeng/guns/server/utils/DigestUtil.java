package cn.stylefeng.guns.server.utils;

import sun.misc.BASE64Encoder;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author ：Sylar
 * @date ：Created in 2019/10/16 9:29
 * @description：
 * @modified By：
 * @version: $
 */
@SuppressWarnings("restriction")
public class DigestUtil {

    public static String md5(String str) {
        try {
            // 将密码编程字节再加密
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] bys = md.digest(str.getBytes());
            // 将加密后的字节数组使用Base64算法变成字符串
            BASE64Encoder encode = new BASE64Encoder();
            return encode.encode(bys);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String SHA1(String decript) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.update(decript.getBytes());
            byte messageDigest[] = digest.digest();
            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            // 字节数组转换为 十六进制 数
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
