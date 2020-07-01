package cn.stylefeng.guns.Tools.Led;

import cn.stylefeng.guns.Tools.Led.Model.*;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.win32.StdCallLibrary;
import com.sun.jna.win32.W32APIOptions;
import lombok.Synchronized;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author ：Sylar
 * @date ：Created in 2020/5/20 9:19
 * @description：
 * @modified By：
 * @version: $
 */

//JNA 方法调用动态库
@Component
public class LedHandler implements InitializingBean {
    // 全局参数定义
    static int m_iCardNum = 1;
    static int m_iProgramIndex = -1;
    static int m_iProgramCount = 0;
    static DllLibrary m_DllLibrary = null;
    //    static String m_strUserPath = System.getProperty("user.dir");
    static String m_strUserPath = "";
    private static final User32 USER = User32.INSTANCE;
    private static final GDI32 GDI = GDI32.INSTANCE;
    private static ReentrantLock lock = new ReentrantLock();

    @Override
    public void afterPropertiesSet() throws Exception {
        Init();
    }

//    @Override
//    public void run() {
//        try {
//            if (m_DllLibrary == null) {
//
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

//    // 主函数
//    public void LedHandler(String[] args) {
//        if (m_DllLibrary == null) {
//            Init();
//        }
//
////        // 2、节目操作函数演示
////        ////(1)、删除所有节目
////        OnDelAllProgram();
////        ////(2)、添加节目(可以添加多个节目)
////        for(int i=0;i<1;i++)
////        {
////            OnAddProgram();//添加节目
////            ////(3)、添加分区窗口到节目,每个节目可以添加多个分区，但分区位置不能重叠
////            OnAddText(); 		//文本窗操作演示
////            //OnAddSingleText();//单行文本窗操作演示
////            //OnAddbmp(); 		//图片窗操作演示
////            //OnAddTime(); 		//时间窗操作演示
////            //OnAddTimeCount(); //计时窗操作演示
////            //OnAddTemperature();//温度窗操作演示
////        }
////        ////(3)、发送节目到显示屏
////        OnSendToScreen();
////
////		/*
////		// 3、实时更新数据
////		////(0)、清空控制卡原有节目，只需要清空一次
////		//OnRealtimeScreenClear();
////		////(1)、建立连接
////		if(OnRealtimeConnect())
////		{
////			////(2)、发送数据
////			//OnRealtimeSendData(); 	//图片句柄
////			//OnRealtimeSendBmpData();//图片路径
////			OnRealtimeSendText();	//文本信息
////			////(3)、断开连接
////			OnRealtimeDisConnect();
////		}
////		*/
////
////        // 4、显示屏控制函数
////        //OnAjusttime();  //校准显示屏时间
////        //OnOpenScreen(); //打开显示屏
////        //OnCloseScreen();//关闭显示屏
//    }

    private static void Init() {
        if(m_DllLibrary==null) {
            m_strUserPath = ClassUtils.getDefaultClassLoader().getResource("").getPath();
            if (m_strUserPath.charAt(0) == '/') {
                m_strUserPath = m_strUserPath.substring(1);
            }
            // 1、加载动态库EQ2008_Dll.dll
            String strDllFileName = m_strUserPath + "res-led/EQ2008_Dll";
            String strEQ2008_Dll_Set_Path = m_strUserPath + "res-led/EQ2008_Dll_Set.ini";
//            System.setProperty("jna.encoding", "GBK");
            m_DllLibrary = (DllLibrary) Native.loadLibrary(strDllFileName, DllLibrary.class);
            m_DllLibrary.User_ReloadIniFile(strEQ2008_Dll_Set_Path);
        }
    }

    @Synchronized
    public static void Test() {
//        System.setProperty("jna.encoding", "GBK");
        Init();
        // 2、节目操作函数演示
        ////(1)、删除所有节目
//        OnDelAllProgram();
//        ////(2)、添加节目(可以添加多个节目)
//        for (int i = 0; i < 1; i++) {
//            OnAddProgram();//添加节目
//            ////(3)、添加分区窗口到节目,每个节目可以添加多个分区，但分区位置不能重叠
//            OnAddText();        //文本窗操作演示
//            //OnAddSingleText();//单行文本窗操作演示
//            //OnAddbmp(); 		//图片窗操作演示
//            //OnAddTime(); 		//时间窗操作演示
//            //OnAddTimeCount(); //计时窗操作演示
//            //OnAddTemperature();//温度窗操作演示
//        }
//        ////(3)、发送节目到显示屏
//        OnSendToScreen();


//		// 3、实时更新数据
//		////(0)、清空控制卡原有节目，只需要清空一次
//		OnRealtimeScreenClear();
//		////(1)、建立连接
//		if(OnRealtimeConnect())
//		{
//			////(2)、发送数据
//			//OnRealtimeSendData(); 	//图片句柄
//			//OnRealtimeSendBmpData();//图片路径
//			OnRealtimeSendText();	//文本信息
//			////(3)、断开连接
//			OnRealtimeDisConnect();
//		}


        // 4、显示屏控制函数
        OnAjusttime();  //校准显示屏时间
        //OnOpenScreen(); //打开显示屏
        //OnCloseScreen();//关闭显示屏
    }

    @Synchronized
    public static void SendMessage(int cardNum, String message) {
        lock.lock();

        try {
            m_DllLibrary.User_RealtimeScreenClear(cardNum);
            if (m_DllLibrary.User_RealtimeConnect(cardNum)) {
                String   strText = "实时发送文本测试!";
                User_FontSet FontInfo = new  User_FontSet();
                FontInfo.bFontBold = false;
                FontInfo.bFontItaic= false;
                FontInfo.bFontUnderline = false;
                FontInfo.colorFont = 0xFFFF;
                FontInfo.iFontSize = 12;
                FontInfo.strFontName = "宋体";
                FontInfo.iAlignStyle = 0;
                FontInfo.iVAlignerStyle = 0;
                FontInfo.iRowSpace = 0;
                if(!m_DllLibrary.User_RealtimeSendText(cardNum,0,0,32,32,strText,FontInfo))
                {
                    System.out.println("发送实时文本失败！");
                }
                else
                {
                    System.out.println("发送实时文本成功！");
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            m_DllLibrary.User_RealtimeDisConnect(cardNum);
            lock.unlock();
        }
    }

    //函数：添加节目索引
    public static void OnAddProgram()
    {
        m_iProgramIndex= m_DllLibrary.User_AddProgram(m_iCardNum,false,10);
        m_iProgramCount++;
        System.out.println("添加节目"+m_iProgramCount);
    }

    //函数：添加文本窗
    public static void OnAddText()
    {
        User_Text Text = new User_Text();

        Text.BkColor = 0;
        Text.chContent = "欢迎使用";

        Text.PartInfo.FrameColor = 0;
        Text.PartInfo.iFrameMode = 0;
        Text.PartInfo.iHeight = 32;
        Text.PartInfo.iWidth = 32;
        Text.PartInfo.iX = 0;
        Text.PartInfo.iY = 0;

        Text.FontInfo.bFontBold = false;
        Text.FontInfo.bFontItaic= false;
        Text.FontInfo.bFontUnderline = false;
        Text.FontInfo.colorFont = 0xFFFF;
        Text.FontInfo.iFontSize = 12;
        Text.FontInfo.strFontName = "";
        Text.FontInfo.iAlignStyle = 1;
        Text.FontInfo.iVAlignerStyle = 1;
        Text.FontInfo.iRowSpace = 0;

        Text.MoveSet.bClear = false;
        Text.MoveSet.iActionSpeed = 1;
        Text.MoveSet.iActionType = 0;
        Text.MoveSet.iHoldTime = 10;
        Text.MoveSet.iClearActionType	= 0;
        Text.MoveSet.iClearSpeed		= 4;
        Text.MoveSet.iFrameTime			= 10;

        if(-1 == m_DllLibrary.User_AddText(m_iCardNum,Text,m_iProgramIndex))
        {
            System.out.println("添加文本失败！");
        }
        else
        {
            System.out.println("添加文本成功！");
        }
    }

    //函数：添加单行文本
    public static void OnAddSingleText()
    {
        User_SingleText SingleText = new User_SingleText();

        SingleText.BkColor		= 0;
        SingleText.chContent	= "欢迎使用EQ异步控制卡！";
        SingleText.PartInfo.iFrameMode	= 0;
        SingleText.PartInfo.iHeight		= 32;
        SingleText.PartInfo.iWidth		= 64;
        SingleText.PartInfo.iX = 0;
        SingleText.PartInfo.iY = 0;
        SingleText.FontInfo.bFontBold		= false;
        SingleText.FontInfo.bFontItaic		= false;
        SingleText.FontInfo.bFontUnderline	= false;
        SingleText.FontInfo.colorFont		= 0xFF;
        SingleText.FontInfo.iFontSize		= 12;
        SingleText.PartInfo.FrameColor		= 0;
        SingleText.FontInfo.strFontName		= "";
        SingleText.MoveSet.bClear			= false;
        SingleText.MoveSet.iActionSpeed		= 8;
        SingleText.MoveSet.iActionType		= 2;
        SingleText.MoveSet.iHoldTime		= 0;
        SingleText.MoveSet.iClearActionType	= 0;
        SingleText.MoveSet.iClearSpeed		= 4;
        SingleText.MoveSet.iFrameTime		= 20;
        if(-1 == m_DllLibrary.User_AddSingleText(m_iCardNum,SingleText,m_iProgramIndex))
        {
            System.out.println("添加单行文本失败！");
        }
        else
        {
            System.out.println("添加单行文本成功！");
        }
    }

    //函数：添加图片
    public static void OnAddbmp()
    {
        User_Bmp BmpZone = new User_Bmp();
        User_MoveSet MoveSet = new User_MoveSet();
        int iBMPZoneNum;

        BmpZone.PartInfo.iX		 = 0;
        BmpZone.PartInfo.iY		 = 0;
        BmpZone.PartInfo.iHeight = 32;
        BmpZone.PartInfo.iWidth  = 64;
        BmpZone.PartInfo.FrameColor = 0xFF00;
        BmpZone.PartInfo.iFrameMode = 1;

        MoveSet.bClear			= true;
        MoveSet.iActionSpeed	= 4;
        MoveSet.iActionType		= 0;
        MoveSet.iHoldTime		= 50;
        MoveSet.iClearActionType= 0;
        MoveSet.iClearSpeed		= 4;
        MoveSet.iFrameTime		= 10;

        iBMPZoneNum = m_DllLibrary.User_AddBmpZone(m_iCardNum,BmpZone,m_iProgramIndex);

        //通过图片路径添加两张图片
        if(m_DllLibrary.User_AddBmpFile(m_iCardNum,iBMPZoneNum,m_strUserPath +"\\res\\BMP1.bmp",MoveSet,m_iProgramIndex))
        {
            System.out.println("添加图片路径1成功！");
        }
        else
        {
            System.out.println("添加图片路径1失败！");
        }

        // 通过图片句柄添加图片
        WinNT.HANDLE hBitmap = USER.LoadImage(null,           // 模块实例句柄
                m_strUserPath + "\\res\\BMP1.bmp",   // 位图路径
                USER.IMAGE_BITMAP,  // 位图类型
                64,   				// 指定图片宽
                32,  				// 指定图片高
                USER.LR_LOADFROMFILE );
        if(hBitmap != null)
        {
            if(m_DllLibrary.User_AddBmp(m_iCardNum,iBMPZoneNum,hBitmap,MoveSet,m_iProgramIndex))
            {
                System.out.println("添加图片句柄2成功！");
            }
            else
            {
                System.out.println("添加图片句柄2失败！");
            }
            GDI.DeleteObject(hBitmap);
        }
        else
        {
            System.out.println("添加图片句柄2失败！");
        }
    }

    //函数：添加时间
    public static void OnAddTime()
    {
        User_DateTime  DateTime = new User_DateTime();

        DateTime.bDay = 1;
        DateTime.bHour = 1;
        DateTime.BkColor = 1;
        DateTime.bMin = 1;
        DateTime.bMouth = 1;
        DateTime.bMulOrSingleLine = 1;
        DateTime.bSec =1;
        DateTime.bWeek = 1;
        DateTime.bYear = 1;
        DateTime.bYearDisType = 1;
        DateTime.chTitle = "北京";

        DateTime.PartInfo.iFrameMode = 1;
        DateTime.iDisplayType = 4;
        DateTime.PartInfo.FrameColor =0xFFFF;
        DateTime.PartInfo.iHeight = 64;
        DateTime.PartInfo.iWidth = 64;
        DateTime.PartInfo.iX=0;
        DateTime.PartInfo.iY=0;
        DateTime.FontInfo.bFontBold=false;
        DateTime.FontInfo.bFontItaic=false;
        DateTime.FontInfo.bFontUnderline=false;
        DateTime.FontInfo.colorFont = 0xFFFF;
        DateTime.FontInfo.iAlignStyle = 1;
        DateTime.FontInfo.iFontSize = 12;
        DateTime.FontInfo.strFontName = "";

        if(-1 == m_DllLibrary.User_AddTime(m_iCardNum,DateTime,m_iProgramIndex))
        {
            System.out.println("添加时间失败！");
        }
        else
        {
            System.out.println("添加时间成功！");
        }
    }

    //函数：添加计时
    public static void OnAddTimeCount()
    {
        User_Timer  TimeCount = new User_Timer();

        TimeCount.bDay = true;
        TimeCount.bHour = false;
        TimeCount.BkColor =0;
        TimeCount.bMin = false;
        TimeCount.bMulOrSingleLine =true;
        TimeCount.bSec =false;
        TimeCount.chTitle = "距离五一";
        TimeCount.FontInfo.bFontBold=false;
        TimeCount.FontInfo.bFontItaic=false;
        TimeCount.FontInfo.bFontUnderline=false;
        TimeCount.FontInfo.colorFont=0xFFFF;
        TimeCount.FontInfo.iAlignStyle=2;
        TimeCount.FontInfo.iFontSize=12;
        TimeCount.FontInfo.strFontName="";
        TimeCount.PartInfo.FrameColor = 0xFF00;
        TimeCount.PartInfo.iFrameMode =0;
        TimeCount.PartInfo.iHeight = 32;
        TimeCount.PartInfo.iWidth = 64;
        TimeCount.PartInfo.iX =0;
        TimeCount.PartInfo.iY=0;
        TimeCount.ReachTimeYear=2015;
        TimeCount.ReachTimeMonth=5;
        TimeCount.ReachTimeDay= 1;
        TimeCount.ReachTimeHour=10;
        TimeCount.ReachTimeMinute=0;
        TimeCount.ReachTimeSecond=0;

        if(-1 == m_DllLibrary.User_AddTimeCount(m_iCardNum,TimeCount,m_iProgramIndex))
        {
            System.out.println("添加计时失败！");
        }
        else
        {
            System.out.println("添加计时成功！");
        }
    }

    //函数：添加温度
    public static void OnAddTemperature()
    {
        User_Temperature  Temperature = new User_Temperature();

        Temperature.BkColor=0;
        Temperature.chTitle="";
        Temperature.DisplayType=0;
        Temperature.PartInfo.FrameColor=0xFF00;
        Temperature.PartInfo.iFrameMode=1;
        Temperature.PartInfo.iHeight=32;
        Temperature.PartInfo.iWidth=64;
        Temperature.PartInfo.iX=0;
        Temperature.PartInfo.iY=0;
        Temperature.FontInfo.bFontBold=false;
        Temperature.FontInfo.bFontItaic=false;
        Temperature.FontInfo.bFontUnderline=false;
        Temperature.FontInfo.colorFont=0xFFFF;
        Temperature.FontInfo.iAlignStyle=0;
        Temperature.FontInfo.iFontSize=12;
        Temperature.FontInfo.strFontName="宋体";

        if(-1 == m_DllLibrary.User_AddTemperature(m_iCardNum,Temperature,m_iProgramIndex))
        {
            System.out.println("添加温度失败！");
        }
        else
        {
            System.out.println("添加温度成功！");
        }
    }

    //函数：删除所有节目
    public static void OnDelAllProgram()
    {
        if(!m_DllLibrary.User_DelAllProgram(m_iCardNum))
        {
            System.out.println("删除节目失败！");
        }
        else
        {
            //提示信息
            m_iProgramCount=0;
            System.out.println("请首先添加节目！");
        }
    }

    //函数：发送数据到显示屏
    public static void OnSendToScreen()
    {
        if(!m_DllLibrary.User_SendToScreen(m_iCardNum))
        {
            System.out.println("数据发送失败！");
        }
        else
        {
            System.out.println("数据发送成功！");
        }

    }

    //函数：校正时间
    public static void OnAjusttime()
    {
        if(!m_DllLibrary.User_AdjustTime(m_iCardNum))
        {
            System.out.println("校准时间失败！");
        }
        else
        {
            System.out.println("校准时间成功！");
        }
    }

    //函数：打开显示屏
    public static void OnOpenScreen()
    {
        if(!m_DllLibrary.User_OpenScreen(m_iCardNum))
        {
            System.out.println("打开显示屏失败！");
        }
        else
        {
            System.out.println("打开显示屏成功！");
        }
    }

    //函数：关闭显示屏
    public static void OnCloseScreen()
    {
        if(!m_DllLibrary.User_CloseScreen(m_iCardNum))
        {
            System.out.println("关闭显示屏失败！");
        }
        else
        {
            System.out.println("关闭显示屏成功！");
        }
    }

    //函数：实时发送数据，建立连接
    //日期：2008-04-30
    public static boolean OnRealtimeConnect()
    {
        if(!m_DllLibrary.User_RealtimeConnect(m_iCardNum))
        {
            System.out.println("实时发送数据建立连接失败！");
            return false;
        }
        else
        {
            System.out.println("实时发送数据建立连接成功！");
            return true;
        }
    }

    //函数：实时发送数据，发送数据
    //日期：2008-04-30
    public static void OnRealtimeSendData()
    {
        // 通过图片句柄添加图片
        WinNT.HANDLE hBitmap = USER.LoadImage(null,           	// 模块实例句柄
                m_strUserPath + "\\res\\BMP1.bmp",// 位图路径
                USER.IMAGE_BITMAP,	// 位图类型
                64,   				// 指定图片宽
                32,  				// 指定图片高
                USER.LR_LOADFROMFILE);// 从路径处加载图片
        if(hBitmap != null)
        {
            if(false == m_DllLibrary.User_RealtimeSendData(m_iCardNum,0,0,64,32,hBitmap))
            {
                System.out.println("发送实时图片句柄失败！");
            }
            else
            {
                System.out.println("发送实时图片句柄成功！");
            }
            GDI.DeleteObject(hBitmap);
        }
        else
        {
            System.out.println("发送实时图片句柄失败！");
        }
    }

    //函数：实时发送图片路径
    //日期：2015-09-21
    public static void OnRealtimeSendBmpData()
    {
        if(false == m_DllLibrary.User_RealtimeSendBmpData(m_iCardNum,0,0,64,32,m_strUserPath + "\\res\\BMP2.bmp"))
        {
            System.out.println("发送实时图片路径失败！");
        }
        else
        {
            System.out.println("发送实时图片路径成功！");
        }
    }

    //函数：实时发送文本内容
    //日期：2015-09-21
    public static void OnRealtimeSendText()
    {
        String   strText = "实时发送文本测试!";
        User_FontSet FontInfo = new User_FontSet();

        FontInfo.bFontBold = false;
        FontInfo.bFontItaic= false;
        FontInfo.bFontUnderline = false;
        FontInfo.colorFont = 0xFFFF;
        FontInfo.iFontSize = 12;
        FontInfo.strFontName = "宋体";
        FontInfo.iAlignStyle = 0;
        FontInfo.iVAlignerStyle = 0;
        FontInfo.iRowSpace = 0;

        if(!m_DllLibrary.User_RealtimeSendText(m_iCardNum,0,0,32,32,strText,FontInfo))
        {
            System.out.println("发送实时文本失败！");
        }
        else
        {
            System.out.println("发送实时文本成功！");
        }
    }

    //函数：实时发送数据，断开连接
    //日期：2008-04-30
    public static void OnRealtimeDisConnect()
    {
        if(!m_DllLibrary.User_RealtimeDisConnect(m_iCardNum))
        {
            System.out.println("实时发送数据断开连接失败！");
        }
        else
        {
            System.out.println("实时发送数据断开连接成功！");
        }
    }


    //函数：清空控制卡节目
    //日期：2015-09-21
    public static void OnRealtimeScreenClear()
    {
        if(!m_DllLibrary.User_RealtimeScreenClear(m_iCardNum))
        {
            System.out.println("清空控制卡节目失败！");
        }
        else
        {
            System.out.println("清空控制卡节目成功！");
        }
    }
}

