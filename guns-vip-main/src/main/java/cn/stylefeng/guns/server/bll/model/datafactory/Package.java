package cn.stylefeng.guns.server.bll.model.datafactory;

/**
 * @program: weightwebservice
 * @description: TCP 数据中的数据部分
 * @author: johnny
 * @create: 2018-07-21 09:39
 **/
public abstract class Package {
    public byte[] Response;


    public Package(byte[] buff) {


    }

    protected Package() {
    }

    abstract public byte[] GetResponse() throws Exception;
}
