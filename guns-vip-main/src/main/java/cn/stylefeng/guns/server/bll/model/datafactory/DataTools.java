package cn.stylefeng.guns.server.bll.model.datafactory;

import java.util.Iterator;
import java.util.List;

/**
 * @program: weightwebservice
 * @description: Tcp 数据处理类
 * @author: johnny
 * @create: 2018-07-20 17:16
 **/
public class DataTools {

    private void DataTools() throws Exception {

    }

    public static byte[] ByteListTobyteArray(List<Byte> list)
    {
        byte[] bytes = new byte[list.size()];

        int i = 0;
        Iterator<Byte> iterator = list.iterator();
        while (iterator.hasNext()) {
            bytes[i] = iterator.next();
            i++;
        }
        return bytes;

    }

    public static Package DataProcessIncome(String protocol, byte[] dataPackageBuff) throws Exception {

        Package dataPackage = null;
        //根据不同的协议号 protocal 进入不同的处理方法
        switch (protocol) {
            case "000D": {
//                dataPackage = new Package_State(dataPackageBuff);

            }
            break;
            case "2": {
//                dataPackage = new Package_Weigh(dataPackageBuff);
            }
            break;
            case "3": {

            }
            break;
            case "4": {

            }
            break;
            default: {

            }


        }

        return dataPackage;
    }
}
