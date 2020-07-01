package cn.stylefeng.guns.Tools.Led.Model;

import com.sun.jna.Structure;

import java.util.List;

/**
 * 图文框
 */
public class User_Bmp extends Structure {
	public User_PartInfo PartInfo = new User_PartInfo(); // 分区信息

	@Override
	protected List<String> getFieldOrder() {
		return null;
	}
}
