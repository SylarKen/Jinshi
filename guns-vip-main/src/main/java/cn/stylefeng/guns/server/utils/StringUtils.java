package cn.stylefeng.guns.server.utils;

/**
 * @author ：Sylar
 * @date ：Created in 2020/1/8 16:56
 * @description：字符串工具类
 * @modified By：
 * @version: $
 */
public class StringUtils {
    static public String Reverse(String s){
        char[] array = s.toCharArray();
        StringBuilder reverse = new StringBuilder();
        for (int i = array.length - 1; i >= 0; i--)
            reverse.append(array[i]);
        return reverse.toString();

    }
}
