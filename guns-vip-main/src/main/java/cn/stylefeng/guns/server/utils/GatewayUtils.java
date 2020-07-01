package cn.stylefeng.guns.server.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author ：Sylar
 * @date ：Created in 2020/1/8 10:23
 * @description：加庆网关工具类
 * @modified By：
 * @version: $
 */
public class GatewayUtils {
    public static Date Bytes2Date(Byte[] b) throws ParseException {
        String[] label = new String[]{"-", "-", " ", ":", ":", ""};
        StringBuilder date = new StringBuilder();
        date.append("20");
        for (int i = 0, j = 0; i < b.length; i++, j++) {
            String s = String.format("%02d", b[i] & 0xff).toUpperCase();
            date.append(s).append(label[j]);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.parse(date.toString());
    }
}
