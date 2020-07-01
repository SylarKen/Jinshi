package cn.stylefeng.guns.base;

import org.apache.poi.hssf.record.DVALRecord;

import java.util.ArrayList;

public class TestRodom {
    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<Integer>();
        list.add(-20);
        list.add(-76);
        list.add(-81);
        list.add(-75);
        list.add(-77);
        list.add(-76);
        list.add(-76);
        list.add(-76);
        list.add(-76);
        list.add(-76);
        list.add(-76);
        list.add(-76);
        list.add(-76);
        list.add(-76);
        list.add(-76);
        list.add(-76);
        list.add(-76);
        list.add(-75);
        list.add(-76);
        list.add(-76);
        int j = 0;
        int k = 0;
        for (Integer i : list) {
            if (i.equals(k)) {
                j++;
                k = i;
            }else {
                k = i;
                j = 0;
            }
            if (j == 10) break;
        }
        System.out.println(k);
    }
}
