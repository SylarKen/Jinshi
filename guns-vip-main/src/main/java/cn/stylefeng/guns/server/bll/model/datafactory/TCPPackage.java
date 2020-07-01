package cn.stylefeng.guns.server.bll.model.datafactory;

import cn.stylefeng.guns.server.utils.ByteUtils;
import org.apache.poi.hpsf.Decimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @program: weightwebservice
 * @description: 地磅上传的数据包类
 * @author: johnny
 * @create: 2018-07-18 16:45
 **/
@Component
public class TCPPackage {
//    @Autowired
//    RecordService service_record;
//
//    @Autowired
//    SenderService service_sender;
//
//    @Autowired
//    SenderConfigService service_sender_conig;

    public static TCPPackage tcpPackage;

    @PostConstruct
    public void init() {
        tcpPackage = this;
//        tcpPackage.service_record = this.service_record;
//        tcpPackage.service_sender = this.service_sender;
//        tcpPackage.service_sender_conig = this.service_sender_conig;
    }

    public String Header;
    public String CIMI;
    public int PackageSize;
    public BigDecimal Data;
    public String Ender;
    public byte[] Buff;
    public byte[] Response;


    private TCPPackage() throws Exception {


    }

    public TCPPackage(byte[] buff) throws Exception {
//        service = (RecordService) SpringContextUtil.getBean("service");
        if (Init(buff)) {
//            this.Save();
        }

    }

    private boolean Init(byte[] buff) throws Exception {
        int buffLength = buff.length;
        if (buffLength >= 12) {
            //数据包头有效
            if (true) {
                StringBuilder data = new StringBuilder();
                for (int i = 2; i < 2 + 7; i++) {
//                    data.append(ByteUtils.asciiToString(String.format("%1$02x", buff[i] & 0xff).toUpperCase()));
                    data.append((char) buff[i]);
                }
//                this.Data = Double.parseDouble(data.toString()) / 1000;

//                int data_int = ByteUtils.bytesToInt_L(data);
                BigDecimal data_decimal = new BigDecimal(data.toString());
                this.Data = data_decimal.divide(new BigDecimal(10));
                String ender = "";
                for (int i = buffLength - 2; i < buffLength; i++) {
                    ender += String.format("%1$02x", buff[i] & 0xff).toUpperCase();
                }
                this.Ender = ender;
                return true;
            }
            // 测试台秤数据协议
            else {
                StringBuilder data = new StringBuilder();
                for (int i = 0; i < 0 + 5; i++) {
                    if (String.format("%1$02x", buff[i] & 0xff).toUpperCase().equals("20")) {
                        continue;
                    }
//                    data.append(ByteUtils.asciiToString(String.format("%1$02x", buff[i] & 0xff).toUpperCase()));
                    data.append((char) buff[i]);
                }
//                this.Data = Double.parseDouble(data.toString()) / 1000;

//                int data_int = ByteUtils.bytesToInt_L(data);
                BigDecimal data_decimal = new BigDecimal(data.toString());
                this.Data = data_decimal.divide(new BigDecimal(1000));
                String ender = "";
                for (int i = buffLength - 2; i < buffLength; i++) {
                    ender += String.format("%1$02x", buff[i] & 0xff).toUpperCase();
                }
                this.Ender = ender;
                return true;
            }
//            else{
//                return false;
//            }
        } else {
            return false;
        }
    }

    private byte[] GetResponse(Byte[] dataResponse) throws Exception {
        //调用业务逻辑
        if (dataResponse.length > 0) {
            ArrayList<Byte> resArr = new ArrayList();
//        List<Byte> resArr = new ArrayList();
            if (this.Buff != null && this.Buff.length >= 8) {
                for (int i = 0; i < 25; i++) {
                    resArr.add(new Byte(this.Buff[i]));
                }
            }
            for (int i = 0; i < 2; i++) {
                resArr.add((byte) 0);
            }
            if (dataResponse != null && dataResponse.length > 0) {
                for (int i = 0; i < dataResponse.length; i++) {
                    resArr.add(new Byte(dataResponse[i]));
                }
            }

            if (this.Buff != null) {
                if (this.Buff.length >= 8) {
                    for (int i = this.Buff.length - 2 - 8; i < this.Buff.length; i++) {
                        resArr.add(new Byte(this.Buff[i]));
                    }
                }
            }
//        System.arraycopy(this.Buff, 0, resArr, 0, 8);
//        System.arraycopy(dataResponse, 0, resArr, resArr.size(), dataResponse.length);
//        byteArr.addAll(Arrays.asList(new Byte(dataResponse.toString())));
            //根据业务逻辑反馈的结果生成响应数据
            byte[] bytes = DataTools.ByteListTobyteArray(resArr);
//        byte[] bytes = new byte[resArr.size()];
//
//        int i = 0;
//        Iterator<Byte> iterator = resArr.iterator();
//        while (iterator.hasNext()) {
//            bytes[i] = iterator.next();
//            i++;
//        }
            return bytes;
        } else {
            return new byte[0];
        }
//        res = new Response();
//        res.Allow = true;
    }

    private boolean Save() {
        try {
            String str = "";
            for (int i = 0; i < this.Buff.length; i++) {
                String s = String.format("%1$02x", this.Buff[i] & 0xff).toUpperCase();
                str += s + " ";
            }
//            if (tcpPackage.service_sender.CheckSender(this.ICCID)) {
            if (true) {

                return true;
            } else {
                return false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }


    }
}
