package cn.stylefeng.guns.Tools.Led.Model;

import com.sun.jna.Structure;

import java.util.List;

/**
 * 日期时间窗口
 */
public class User_DateTime extends Structure {

	public User_PartInfo PartInfo = new User_PartInfo(); // 分区信息
	public int BkColor; // 背景颜色
	public User_FontSet FontInfo = new User_FontSet(); // 字体设置
	public int iDisplayType; // 显示风格
	public String chTitle; // 添加显示文字
	public int bYearDisType; // 年份位数0 －4；1－2位
	public int bMulOrSingleLine; // 单行还是多行
	public int bYear; // 是否显示年
	public int bMouth; // 是否显示月
	public int bDay; // 是否显示日
	public int bWeek; // 是否显示星期
	public int bHour; // 是否显示小时
	public int bMin; // 是否显示分钟
	public int bSec; // 是否显示秒钟

	@Override
	protected List<String> getFieldOrder() {
		return null;
	}
}
