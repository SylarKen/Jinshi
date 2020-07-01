package cn.stylefeng.guns.Tools.Led;

import lombok.Synchronized;
import onbon.bx05.*;
import onbon.bx05.area.TextCaptionBxArea;
import onbon.bx05.area.page.TextBxPage;
import onbon.bx05.file.BxFileWriterListener;
import onbon.bx05.file.ProgramBxFile;
import onbon.bx05.utils.DisplayStyleFactory;
import onbon.bx05.utils.TextBinary;

import java.awt.*;
import java.util.concurrent.locks.ReentrantLock;

public class Led implements BxFileWriterListener<Bx5GScreen> {
    int posX = 0;
    int posY = 0;
    private String ip = "";
    private Bx5GScreenProfile profile;
    private Bx5GScreenClient screen;
    private DisplayStyleFactory.DisplayStyle[] styles;
    private static ReentrantLock lock = new ReentrantLock();

    public Led(String ip) throws Exception {
        Init(ip);
    }

    private void Init(String ip) {
        this.ip = ip;
        screen = new Bx5GScreenClient("MyScreen");
        //
        // 连接控制器
        // 其中, 192.168.88.199 为控制器的实际 IP，请根据实际情况填写。
        // 如你不知道控制器的 IP 是多少，请先使用 LEDSHOW TW 软件对控制器进行 IP 设置
        // 端口号默认为 5005
//        if (!screen.connect("192.168.1.101", 5005)) {
//            System.out.println("connect failed");
//            return;
//        }
        if (!screen.connect(ip, 5005)) {
            System.out.println("connect failed");
            return;
        }
        profile = screen.getProfile();
        styles = DisplayStyleFactory.getStyles().toArray(new DisplayStyleFactory.DisplayStyle[0]);
    }

    @Synchronized
    public void SendText(String text) throws Bx5GException {
        lock.lock();
        if (!this.ip.equals(screen.getAddress())) {
            Init(this.ip);
        }
        try {
            // 创建节目文件
            ProgramBxFile p000 = new ProgramBxFile("P000", profile);
            // 是否显示节目边框
            p000.setFrameShow(false);

            // 创建一个文本区
            // 分别输入其X,Y,W,H
            // 屏幕左上角坐标为 (0, 0)
            // 注意区域坐标和宽高，不要越界
            TextCaptionBxArea tArea = new TextCaptionBxArea(posX, posY, 64, 32, screen.getProfile());
            // 使能区域边框
            tArea.setFrameShow(false);
            // 使用内置边框3
//        tArea.loadFrameImage(3);

            // 创建一个数据页，并希望显示 “P009” 这几个文字
            TextBxPage page = new TextBxPage(text);
            // 对文本的处理是否自动换行
            page.setLineBreak(true);
            // 设置文本水平对齐方式
            page.setHorizontalAlignment(TextBinary.Alignment.NEAR);
            // 设置文本垂直居中方式
            page.setVerticalAlignment(TextBinary.Alignment.CENTER);
            // 设置文本字体
            page.setFont(new Font("宋体", Font.PLAIN, 12));         // 字体
            // 设置文本颜色
            page.setForeground(Color.red);
            // 设置区域背景色，默认为黑色
            page.setBackground(Color.darkGray);
            // 调整特技方式
            page.setDisplayStyle(styles[1]);

            // 调整特技速度
            page.setSpeed(10);
            //
            page.setHeadTailInterval(-2);
            // 调整停留时间, 单位 10ms
            page.setStayTime(0);

            // 将前面创建的 page 添加到 area 中
            tArea.addPage(page);

            // 再创建一个数据页，用于显示图片
            /**
             ImageFileBxPage iPage = new ImageFileBxPage("d:/1.png");
             // 调整特技方式
             iPage.setDisplayStyle(styles[1]);
             // 调整特技速度
             iPage.setSpeed(1);
             // 调整停留时间, 单位 10ms
             iPage.setStayTime(100);

             // 将前面创建的 iPage 添加到 area 中
             tArea.addPage(iPage);
             */

            // 将 area 添加到节目中
            p000.addArea(tArea);
            screen.writeProgramQuickly(p000);
        } catch (Exception ex) {

        } finally {
            lock.unlock();
        }
    }

    @Override
    public void fileWriting(Bx5GScreen bx5GScreen, String s, int i) {

    }

    @Override
    public void fileFinish(Bx5GScreen bx5GScreen, String s, int i) {

    }

    @Override
    public void progressChanged(Bx5GScreen bx5GScreen, String s, int i, int i1) {

    }

    @Override
    public void cancel(Bx5GScreen bx5GScreen, String s, Bx5GException e) {

    }

    @Override
    public void done(Bx5GScreen bx5GScreen) {

    }
}

