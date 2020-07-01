package cn.stylefeng.guns.Tools.Led.Model;

import com.sun.jna.Structure;

import java.util.List;

/**
 * 动画方式设置
 */
public class User_MoveSet extends Structure{

	public int iActionType; // 节目变换方式
	public int iActionSpeed; // 节目的播放速度
	public boolean bClear; // 是否需要清除背景
	public int iHoldTime; // 在屏幕上停留的时间
	public int iClearSpeed; // 清除显示屏的速度
	public int iClearActionType; // 节目清除的变换方式
	public int iFrameTime; // 每帧时间

	@Override
	protected List<String> getFieldOrder() {
		return null;
	}
}
