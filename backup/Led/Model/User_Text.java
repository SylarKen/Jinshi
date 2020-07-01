package cn.stylefeng.guns.Tools.Led.Model;


import com.sun.jna.Structure;

import java.util.List;

/**
 * 文本框
 */
public class User_Text  extends Structure{

	public String chContent; // 显示内容
	public User_PartInfo PartInfo = new User_PartInfo(); // 分区信息
	public int BkColor; // 背景颜色
	public User_FontSet FontInfo = new User_FontSet(); // 字体设置
	public User_MoveSet MoveSet = new User_MoveSet(); // 动作方式设置

	@Override
	protected List<String> getFieldOrder() {
		return null;
	}
}
