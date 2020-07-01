package cn.stylefeng.guns.Tools.Led.Model;

import com.sun.jna.Structure;

import java.util.List;

/**
 * 计时窗口
 */
public class User_Timer extends Structure {

	public User_PartInfo PartInfo = new User_PartInfo(); // 分区信息
	public int BkColor; // 背景颜色
	public User_FontSet FontInfo = new User_FontSet(); // 字体设置
	public int ReachTimeYear; // 到达年
	public int ReachTimeMonth; // 到达月
	public int ReachTimeDay; // 到达日
	public int ReachTimeHour; // 到达时
	public int ReachTimeMinute; // 到达分
	public int ReachTimeSecond; // 到达秒
	public boolean bDay; // 是否显示天 0－不显示 1－显示
	public boolean bHour; // 是否显示小时
	public boolean bMin; // 是否显示分钟
	public boolean bSec; // 是否显示秒
	public boolean bMulOrSingleLine; // 单行还是多行
	public String chTitle; // 添加显示文字

	@Override
	protected List<String> getFieldOrder() {
		return null;
	}
}
