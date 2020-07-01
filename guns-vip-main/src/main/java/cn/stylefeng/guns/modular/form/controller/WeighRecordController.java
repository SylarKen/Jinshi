package cn.stylefeng.guns.modular.form.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.export.styler.ExcelExportStylerBorderImpl;
import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.config.aop.WbcLogRecord;
import cn.stylefeng.guns.modular.form.entity.DemoData;
import cn.stylefeng.guns.modular.form.entity.WeighRecord;
import cn.stylefeng.guns.modular.form.entity.xlsx.Conditions;
import cn.stylefeng.guns.modular.form.service.WeighRecordService;
import cn.stylefeng.guns.modular.form.wrapper.WeighbridgeConfigWrapper;
import cn.stylefeng.guns.sys.core.log.LogObjectHolder;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.kernel.model.response.ResponseData;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author: rednoob
 * @Describe: 数据管理Controller层
 */
@Controller
@RequestMapping("/weighRecord")
public class WeighRecordController extends BaseController {

    @Autowired
    private WeighRecordService service;

    private String PREFIX = "/weighRecord";

    /**
     * 跳转主页面
     */
    @GetMapping("/index")
    public String toIndex() {
        return PREFIX + "/index.html";
    }
    /**
     * 新增页面
     */
    @GetMapping("/add")
    public String add() {
        return PREFIX + "/add.html";
    }

    /**
     *编辑页面
     */
    @GetMapping("/edit")
    public String edit() {
        return PREFIX + "/edit.html";
    }

    /**
     * 跳转日期下载xlsx页面
     */
    @GetMapping("/excelByTime")
    public String excelByTime() {
        return PREFIX + "/excelByTime.html";
    }

    /**
     * 查询数据管理
     * http://localhost:8080/weighRecord/list?page=1&limit=10&condition=
     */
    @GetMapping("/list")
    @ResponseBody
    public Object queryList(@RequestParam(required = false) String condition) {
        Page<Map<String, Object>> page = this.service.queryWeighRecord(condition);
        Page wrap = new WeighbridgeConfigWrapper(page).wrap();
        return LayuiPageFactory.createPageInfo(wrap);
    }
    /**
     * 获取信息
     */
    @PostMapping("/getInfo")
    @ResponseBody
    public WeighRecord getInfoById(@RequestParam Long id) {
        WeighRecord weighRecord = this.service.getInfo(id);
        LogObjectHolder.me().set(weighRecord);
        return weighRecord;
    }
    /**
     * 更新信息
     */
    @PostMapping("/update")
    @ResponseBody
    @WbcLogRecord(value = "修改数据")
    public ResponseData updateByPid(WeighRecord weighRecord) {
        Boolean b = this.service.update(weighRecord);
        if (b) {
            return SUCCESS_TIP;
        }
        return ResponseData.error("更新失败");
    }
    /**
     * 删除信息
     */
    @PostMapping("/delete")
    @ResponseBody
    @WbcLogRecord(value = "删除数据")
    public ResponseData deleteById(@RequestParam Long id) {
        Boolean b = this.service.delete(id);
        if (b) {
            return SUCCESS_TIP;
        }
        return ResponseData.error("删除失败");
    }

    /**
     * 导出日报
     * 例子未做功能
     */
    @RequestMapping("getXlsx")
    public void getXlsx(String Date, HttpServletResponse response) throws IOException {

      // Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("金实石业数据日报","日报"), DemoData.class, list);
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        ServletOutputStream os = response.getOutputStream();
        response.setHeader("Content-disposition", "attachment;filename=日报"+Date+".xls");
       //workbook.write(os);
        ExportParams params = new ExportParams();
        os.flush();
        os.close();
    }

    /**
     * 查询xlsx所需
     * @param condition
     * @return
     */
    @GetMapping("/listByCondition")
    @ResponseBody
    public Object queryListByCondition(Conditions condition) {
        Page<Map<String, Object>> page = this.service.queryListByCondition(condition);
        Page wrap = new WeighbridgeConfigWrapper(page).wrap();
        return LayuiPageFactory.createPageInfo(wrap);
    }



    /*
    *  //创建工作簿
        XSSFWorkbook wb = new XSSFWorkbook();
        //创建一个sheet
        XSSFSheet sheet = wb.createSheet();

        // 创建单元格样式
        XSSFCellStyle style = wb.createCellStyle();
        style.setFillBackgroundColor((short)4);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND); //solid 填充
        style.setAlignment(HorizontalAlignment.CENTER); //文字水平居中
        style.setVerticalAlignment(VerticalAlignment.CENTER);//文字垂直居中
        style.setBorderBottom(BorderStyle.THIN); //底边框加黑
        style.setBorderLeft(BorderStyle.THIN);  //左边框加黑
        style.setBorderRight(BorderStyle.THIN); // 有边框加黑
        style.setBorderTop(BorderStyle.THIN); //上边框加黑
        //为单元格添加背景样式
        for (int i = 0; i < 6; i++) { //需要6行表格
            Row row = sheet.createRow(i); //创建行
            for (int j = 0; j < 6; j++) {//需要6列
                row.createCell(j).setCellStyle(style);
            }
        }


        //合并单元格
        sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 0));//合并单元格，cellRangAddress四个参数，第一个起始行，第二终止行，第三个起始列，第四个终止列
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 1, 5));

        //tian入数据
        XSSFRow row = sheet.getRow(0); //获取第一行
        row.getCell(1).setCellValue("2018期末考试"); //在第一行中创建一个单元格并赋值
        XSSFRow row1 = sheet.getRow(1); //获取第二行，为每一列添加字段
        row1.getCell(1).setCellValue("语文");
        row1.getCell(2).setCellValue("数学");
        row1.getCell(3).setCellValue("英语");
        row1.getCell(4).setCellValue("物理");
        row1.getCell(5).setCellValue("化学");
        XSSFRow row2 = sheet.getRow(2); //获取第三行
        row2.getCell(0).setCellValue("张三");
        XSSFRow row3 = sheet.getRow(3); //获取第四行
        row3.getCell(0).setCellValue("张三");
        XSSFRow row4 = sheet.getRow(4); //获取第五行
        row4.getCell(0).setCellValue("张三");
        XSSFRow row5 = sheet.getRow(5); //获取第五行
        row5.getCell(0).setCellValue("张三");

        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        ServletOutputStream os = response.getOutputStream();
        response.setHeader("Content-disposition", "attachment;filename=user.xls");
        wb.write(os);
        os.flush();
        wb.close();
        os.close();*/

}
