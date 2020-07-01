package cn.stylefeng.guns.server.bll.model.datafactory;

/**
 * @program: weightwebservice
 * @description: 服务器返回的数据包，继承于DataPackage
 * @author: johnny
 * @create: 2018-07-19 11:48
 **/
public class Response {
    //数据包信息
    public boolean Allow;
    public String LedMessage;
    public String VoiceMessage;
    public String DocumentMessage;
    public byte[] Buff;

    private Response() throws Exception {


    }
    public Response(boolean allow, String ledMessage, String voiceMessage, String documentMessage) throws Exception {
        this.Allow = allow;
        this.LedMessage = ledMessage;
        this.VoiceMessage = voiceMessage;
        this.DocumentMessage = documentMessage;
        SetBuff();
    }

    private void SetBuff() throws Exception {
        this.Buff = new byte[100];

    }
}
