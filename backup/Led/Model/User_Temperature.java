package cn.stylefeng.guns.Tools.Led.Model;

import com.sun.jna.Structure;

import java.util.List;

/**
 * 温度窗口
 */
public class User_Temperature extends Structure {

	public User_PartInfo PartInfo = new User_PartInfo(); // 分区信息
	public int BkColor; // 背景颜色
	public User_FontSet FontInfo = new User_FontSet(); // 字体设置
	public String chTitle; // 标题
	public int DisplayType; // 显示格式：0－度 1－C

	@Override
	protected List<String> getFieldOrder() {
		return null;
	}
}
