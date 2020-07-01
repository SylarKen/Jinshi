package cn.stylefeng.guns.server.utils;

import java.util.List;

/**
 * @author ：Sylar
 * @date ：Created in 2020/1/3 15:28
 * @description：二进制、十进制、十六进制转换
 * @modified By：
 * @version: $
 */
public class ByteUtils {
    // Byte[] 转换为字符串，数组高位在前  —— 如：{00, 59} - 22784
    static public Integer bytesToInt_R(Byte a, Byte... b) {
        int dec = a;
        for (int i = 0; i < b.length; i++) {
            dec = dec | b[i] << (8 * (i + 1));
        }
        return dec;

    }

    // Byte[] 转换为Int，数组低位在前 —— 如：{00, 59} - 89
    static public Integer bytesToInt_L(Byte a, Byte... b) {
        int dec = 0;
        for (int i = b.length - 1; i >= 0; i--) {
            dec = dec | (b[i] & 0xff) << (8 * (b.length - 1 - i));
        }
        dec = dec | (a & 0xff) << (8 * (b.length));
//        int dec = a;
//        for (int i = 0; i < b.length; i++) {
//            dec = dec | b[i] << (8 * (i + 1));
//        }
        return dec;

    }

    // Byte[] 转换为Int，数组低位在前 —— 如：{00, 59} - 89
    static public Integer bytesToInt_L(List<Byte> a) {
        int dec = 0;
        if (a.size() > 0) {
            for (int i = a.size() - 1; i >= 0; i--) {
                dec = dec | (a.get(i) & 0xff) << (8 * (a.size() - 1 - i));
            }
        }
        return dec;
    }

    static public Integer bytesToInt_R(List<Byte> a) {
        int dec = 0;
        if (a.size() > 0) {
            dec = a.get(0);
            for (int i = 1; i < a.size(); i++) {
                dec = dec | a.get(i) << (8 * (i + 1));
            }
        }
        return dec;
    }

    public static String asciiToString(String value)
    {
        StringBuffer sbu = new StringBuffer();
        String[] chars = value.split(",");
        for (int i = 0; i < chars.length; i++) {
            sbu.append((char) Integer.parseInt(chars[i]));
        }
        return sbu.toString();
    }
}
