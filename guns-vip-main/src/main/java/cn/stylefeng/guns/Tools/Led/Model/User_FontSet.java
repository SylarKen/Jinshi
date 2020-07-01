package cn.stylefeng.guns.Tools.Led.Model;

import com.sun.jna.Structure;

import java.util.List;

/**
 * 字体参数
 */
public class User_FontSet extends Structure{

	public String strFontName; // 字体的名称
	public int iFontSize; // 字体的大小
	public boolean bFontBold; // 字体是否加粗
	public boolean bFontItaic; // 字体是否是斜体
	public boolean bFontUnderline; // 字体是否带下划线
	public int colorFont; // 字体的颜色
	public int iAlignStyle; // 对齐方式
	// 0－ 左对齐
	// 1－居中
	// 2－右对齐
	public int iVAlignerStyle; // 上下对齐方式
	// 0-顶对齐
	// 1-上下居中
	// 2-底对齐
	public int iRowSpace; // 行间距

	@Override
	protected List<String> getFieldOrder() {
		return null;
	}
}
