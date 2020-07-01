package cn.stylefeng.guns;

import java.io.Console;
import java.math.BigDecimal;

/**
 * @author ：Sylar
 * @date ：Created in 2020/5/28 9:16
 * @description：測試
 * @modified By：
 * @version: $
 */
public class Test {
    public static void main() {
        BigDecimal a = new BigDecimal(234);
        System.out.println(a);
        BigDecimal b = a;
        System.out.println(b);
        BigDecimal c = new BigDecimal(123);
        System.out.println(c);
        a = c;
        System.out.println(a);

    }
}
