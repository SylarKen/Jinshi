package cn.stylefeng.guns.Tools.Led.Model;

import com.sun.jna.Structure;

import java.util.List;

/**
 * 节目区域参数
 */
public class User_PartInfo extends Structure{

	public int iX; // 窗口的起点X
	public int iY; // 窗口的起点Y
	public int iWidth; // 窗体的宽度
	public int iHeight; // 窗体的高度
	public int iFrameMode; // 边框的样式
	public int FrameColor; // 边框颜色

	@Override
	protected List<String> getFieldOrder() {
		return null;
	}
}
