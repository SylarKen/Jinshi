package cn.stylefeng.guns.modular.form.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.stylefeng.guns.modular.form.entity.*;
import cn.stylefeng.guns.modular.form.entity.xlsx.*;
import cn.stylefeng.guns.modular.form.mapper.VehicleMapper;
import cn.stylefeng.guns.modular.form.mapper.WeighRecordMapper;
import cn.stylefeng.guns.modular.form.mapper.WeighbridgeConfigMapper;
import cn.stylefeng.roses.kernel.model.response.ErrorResponseData;
import cn.stylefeng.roses.kernel.model.response.ResponseData;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author: rednoob
 * @Describe: 导出Xlsx(包括日报月报等等等等)
 * 导出使用easyPoi技术
 */
@Controller
@RequestMapping("/reports")
public class XlsxController {

    @Autowired
    private WeighRecordMapper dao;

    @Autowired
    private VehicleMapper carDao;

    @Autowired
    private WeighbridgeConfigMapper wcDao;

    private String PREFIX = "/report";

    @GetMapping("/index")
    public String index() {
        return PREFIX + "/index.html";
    }

    @GetMapping("/dayreports")
    public String excelByTime() {
        return PREFIX + "/excelByTime.html";
    }

    @GetMapping("/dayreport")
    public String excelByTimeAndAreaCode() {
        return PREFIX + "/excelByTimeAndAreaCode.html";
    }

    @GetMapping("/monthreport")
    public String monthExcelByTimeAndAreaCode() {
        return PREFIX + "/monthExcelByTimeAndAreaCode.html";
    }

    @GetMapping("/seasonreport")
    public String seasonExcelByTimeAndAreaCode() {
        return PREFIX + "/seasonExcelByTimeAndAreaCode.html";
    }

    @GetMapping("/halfreport")
    public String halfExcelByTimeAndAreaCode() {
        return PREFIX + "/halfExcelByTimeAndAreaCode.html";
    }

    @GetMapping("/yearreport")
    public String yearExcelByTimeAndAreaCode() {
        return PREFIX + "/yearExcelByTimeAndAreaCode.html";
    }

    @GetMapping("/dayreportbysix")
    public String daysExcelBySix() {
        return PREFIX + "/daysExcelBySix.html";
    }

    @GetMapping("/monthreportbysix")
    public String monthsExcelBySix() {
        return PREFIX + "/monthsExcelBySix.html";
    }

    @GetMapping("/seasonreportbysix")
    public String seasonExcelBySix() {
        return PREFIX + "/seasonExcelBySix.html";
    }

    @GetMapping("/halfreportbysix")
    public String halfExcelBySix() {
        return PREFIX + "/halfExcelBySix.html";
    }

    @GetMapping("/yearreportbysix")
    public String yearExcelBySix() {
        return PREFIX + "/yearExcelBySix.html";
    }

    @GetMapping("/carreportbydata")
    public String carExcel() {
        return PREFIX + "/carExcel.html";
    }

    /**
     * 流水日计
     *
     * @param Date
     * @param response
     * @throws IOException
     * @throws ParseException
     */
    @GetMapping("/getDailyReport")
    public void getDailyReport(String beginTime,String endTime, String plateNumber, HttpServletResponse response) throws IOException, ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        WeighRecordExample recordExample = new WeighRecordExample();
        WeighRecordExample.Criteria criteria = recordExample.createCriteria();
        Date startTimes = null;
        Date endTimes = null;
        if (StringUtils.isNotEmpty(beginTime) && StringUtils.isNotEmpty(endTime)) {
            startTimes = sdf.parse(beginTime);
            endTimes = sdf.parse(endTime);
            criteria.andTimeWeighBetween(startTimes, endTimes);
        }
        if (StringUtils.isNotEmpty(plateNumber)) {
            Vehicle vehicle = carDao.selectByPrimaryKey(Integer.parseInt(plateNumber));
            criteria.andPlateNumberEqualTo(vehicle.getPlateNumber());
        }
        List<WeighRecord> weighRecords = this.dao.selectByExample(recordExample);
        List<DailyData> dailyDataArrayList = new ArrayList<>();
        for (int i = 0; i < weighRecords.size(); i++) {
            WeighRecord weighRecord = weighRecords.get(i);
            DailyData dailyData = new DailyData();
            dailyData.setId(i+1);
            dailyData.setPlateNumber(weighRecord.getPlateNumber());
            dailyData.setGross(weighRecord.getGross());
            dailyData.setTare(weighRecord.getTare());
            dailyData.setTimeWeigh(weighRecord.getTimeWeigh());
            dailyData.setWeighbridgeCode(weighRecord.getWeighbridgeCode());
            dailyData.setWorkArea(weighRecord.getWorkArea());
            dailyDataArrayList.add(dailyData);
        }
        ExportParams params = new ExportParams();
        params.setTitle("金石废料统计表（流水日计）");
        params.setSheetName("流水日计");
        params.setSecondTitle("开始时间:" + beginTime + "           " +
                "               结束时间:" + endTime);
        params.setStyle(ExcelStyleUtil.class);
        Workbook workbook = ExcelExportUtil.exportExcel(params, DailyData.class, dailyDataArrayList);
        /*response.setContentType("application/vnd.ms-excel;charset=utf-8");
        ServletOutputStream os = response.getOutputStream();
        String filename = "日报";
        response.setHeader("Content-disposition", "attachment;filename="+new String(filename.getBytes("utf-8"), "ISO8859-1")+Date+".xls");*/
        sendResponse(workbook, "流水日报", beginTime + "   "+ endTime, response);//调用共同方法
    }

    /**
     * 各工作面日计
     *
     * @param Date
     * @param areaCode
     * @param response
     * @throws ParseException
     */
    @GetMapping("/getDailyReportByItem")
    public void getDailyReportByItem(String Date, String areaCode, String poundNumber, HttpServletResponse response) throws ParseException, IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        WeighRecordExample recordExample = new WeighRecordExample();
        WeighRecordExample.Criteria criteria = recordExample.createCriteria();
        Date startTime = null;
        Date endTime = null;
        if (StringUtils.isNotEmpty(Date)) {
            startTime = sdf.parse(Date + " 00:00:00");
            endTime = sdf.parse(Date + " 23:59:59");
            criteria.andTimeWeighBetween(startTime, endTime);
        }
        if (StringUtils.isNotEmpty(areaCode)) {
            criteria.andWorkAreaEqualTo(areaCode);
        }
        if (StringUtils.isNotEmpty(poundNumber)) {
            criteria.andWeighbridgeCodeEqualTo(poundNumber);
        }
        List<WeighRecord> weighRecords = this.dao.selectByExample(recordExample);
        List<DailyData2> dailyData2ArrayList = new ArrayList<>();
        for (int i = 0; i < weighRecords.size(); i++) {
            WeighRecord weighRecord = weighRecords.get(i);
            DailyData2 dailyData2 = new DailyData2();
            dailyData2.setId(i+1);
            dailyData2.setTimeWeigh(weighRecord.getTimeWeigh());
            dailyData2.setPlateNumber(weighRecord.getPlateNumber());
            dailyData2.setGross(weighRecord.getGross());
            dailyData2.setTare(weighRecord.getTare());
            dailyData2ArrayList.add(dailyData2);
        }
        ExportParams params = new ExportParams();
        params.setTitle("金石废料统计表（工作面日计）");
        params.setSheetName("各工作面日计");
        params.setSecondTitle("工作面:" + areaCode
                + "            地磅" + poundNumber
                + "            日期" + Date);
        params.setStyle(ExcelStyleUtil.class);
        Workbook workbook = ExcelExportUtil.exportExcel(params, DailyData2.class, dailyData2ArrayList);
        sendResponse(workbook, areaCode + "工作面日计", Date, response);
    }

    /**
     * 各工作面月计
     *
     * @param response
     * @throws IOException
     */
    @GetMapping("/getReportByMonth")
    public void getReportByMonth(String Date, String areaCode, HttpServletResponse response) throws IOException, ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startTime = null;
        Date endTime = null;
        if (StringUtils.isNotEmpty(Date)) {
            Date dates = sdf.parse(Date);
            startTime = getFirstDayDateOfMonth(dates);
            Date endDate = getLastDayOfMonth(dates);
            String format = sdf1.format(endDate) + " 23:59:59";
            endTime = sdf2.parse(format);
        }
        List<WeighbridgeConfig> wcList = this.wcDao.selectByExample(null);
        String weightbridgeCode1 = null;
        String weightbridgeCode2 = null;
        Iterator<WeighbridgeConfig> iterator = wcList.iterator();
        while (iterator.hasNext()) {
            WeighbridgeConfig weighbridgeConfig = iterator.next();
            String weightbridgeCode = weighbridgeConfig.getWeightbridgeCode();
            if (weightbridgeCode1 == null) {
                weightbridgeCode1 = weightbridgeCode;
                continue;
            }
            if (weightbridgeCode2 == null) {
                weightbridgeCode2 = weightbridgeCode;
                break;
            }
        }
        List<XlsxDataBean> list = this.dao.getReportByMonth(startTime, endTime, areaCode);
        ArrayList<DailyData3> dailyData3s = new ArrayList<>();
        DailyData3 dailyData3 = null;
        Date days = null;
        PoundData poundData1 = null;
        for (XlsxDataBean xlsxDataBean : list) {
            if (!xlsxDataBean.getShowTime().equals(days)) {
                days = xlsxDataBean.getShowTime();
                if (dailyData3 != null) {
                    ArrayList<PoundData> poundData = new ArrayList<>();
                    poundData.add(poundData1);
                    dailyData3.setPoundData3(poundData);
                    dailyData3 = checkDailyData3(dailyData3);
                    dailyData3s.add(dailyData3);
                }
                poundData1 = new PoundData(0, BigDecimal.ZERO, BigDecimal.ZERO);
                dailyData3 = new DailyData3();
            }
            ArrayList<PoundData> poundData1s = new ArrayList<>();
            dailyData3.setDays(xlsxDataBean.getShowTime());
            if (weightbridgeCode1.equals(xlsxDataBean.getWeighbridgeCode())) {
                PoundData poundData = new PoundData();
                poundData.setCountCars(xlsxDataBean.getVehiclenumber());
                poundData.setGross(xlsxDataBean.getGross());
                poundData.setTare(xlsxDataBean.getTare());
                poundData1s.add(poundData);
                dailyData3.setPoundData1(poundData1s);
            } else if (weightbridgeCode2.equals(xlsxDataBean.getWeighbridgeCode())) {
                PoundData poundData = new PoundData();
                poundData.setCountCars(xlsxDataBean.getVehiclenumber());
                poundData.setGross(xlsxDataBean.getGross());
                poundData.setTare(xlsxDataBean.getTare());
                poundData1s.add(poundData);
                dailyData3.setPoundData2(poundData1s);
            }
            poundData1.setCountCars(poundData1.getCountCars() + xlsxDataBean.getVehiclenumber());
            poundData1.setGross(poundData1.getGross().add(xlsxDataBean.getGross()));
            poundData1.setTare(poundData1.getTare().add(xlsxDataBean.getTare()));

        }
        if (dailyData3 != null) {
            ArrayList<PoundData> poundData = new ArrayList<>();
            poundData.add(poundData1);
            dailyData3.setPoundData3(poundData);
            dailyData3 = checkDailyData3(dailyData3);
            dailyData3s.add(dailyData3);
        }

        ExportParams params = new ExportParams();
        params.setTitle("金石废料统计表（工作面月计）");
        params.setSheetName("各工作面月计");
        params.setSecondTitle("工作面:" + areaCode + "月份" + Date);
        params.setStyle(ExcelStyleUtil.class);
        Workbook workbook = ExcelExportUtil.exportExcel(params, DailyData3.class, dailyData3s);
        sendResponse(workbook, areaCode + "工作面月计", Date, response);
    }

    /**
     * 各工作面季度
     *
     * @param Date
     * @param areaCode
     * @param response
     * @throws ParseException
     * @throws IOException
     */
    @GetMapping("/getReportBySeason")
    public void getReportBySeason(String Date, String areaCode, HttpServletResponse response) throws ParseException, IOException {
        List<WeighbridgeConfig> wcList = this.wcDao.selectByExample(null);
        String weightbridgeCode1 = null;
        String weightbridgeCode2 = null;
        Iterator<WeighbridgeConfig> iterator = wcList.iterator();
        while (iterator.hasNext()) {
            WeighbridgeConfig weighbridgeConfig = iterator.next();
            String weightbridgeCode = weighbridgeConfig.getWeightbridgeCode();
            if (weightbridgeCode1 == null) {
                weightbridgeCode1 = weightbridgeCode;
                continue;
            }
            if (weightbridgeCode2 == null) {
                weightbridgeCode2 = weightbridgeCode;
                break;
            }
        }
        List<XlsxDataBean> list = this.dao.getReportBySeason(Date, areaCode);
        ArrayList<DailyData4> dailyData3s = new ArrayList<>();
        DailyData4 dailyData3 = null;
        String days = null;
        PoundData poundData1 = null;
        for (XlsxDataBean xlsxDataBean : list) {
            if (!xlsxDataBean.getMonths().equals(days)) {
                days = xlsxDataBean.getMonths();
                if (dailyData3 != null) {
                    ArrayList<PoundData> poundData = new ArrayList<>();
                    poundData.add(poundData1);
                    dailyData3.setPoundData3(poundData);
                    dailyData3 = checkDailyData4(dailyData3);
                    dailyData3s.add(dailyData3);
                }
                poundData1 = new PoundData(0, BigDecimal.ZERO, BigDecimal.ZERO);
                dailyData3 = new DailyData4();
            }
            ArrayList<PoundData> poundData1s = new ArrayList<>();
            dailyData3.setDays(checkMonths(xlsxDataBean.getMonths()));
            if (weightbridgeCode1.equals(xlsxDataBean.getWeighbridgeCode())) {
                PoundData poundData = new PoundData();
                poundData.setCountCars(xlsxDataBean.getVehiclenumber());
                poundData.setGross(xlsxDataBean.getGross());
                poundData.setTare(xlsxDataBean.getTare());
                poundData1s.add(poundData);
                dailyData3.setPoundData1(poundData1s);
            } else if (weightbridgeCode2.equals(xlsxDataBean.getWeighbridgeCode())) {
                PoundData poundData = new PoundData();
                poundData.setCountCars(xlsxDataBean.getVehiclenumber());
                poundData.setGross(xlsxDataBean.getGross());
                poundData.setTare(xlsxDataBean.getTare());
                poundData1s.add(poundData);
                dailyData3.setPoundData2(poundData1s);
            }
            poundData1.setCountCars(poundData1.getCountCars() + xlsxDataBean.getVehiclenumber());
            poundData1.setGross(poundData1.getGross().add(xlsxDataBean.getGross()));
            poundData1.setTare(poundData1.getTare().add(xlsxDataBean.getTare()));

        }
        if (dailyData3 != null) {
            ArrayList<PoundData> poundData = new ArrayList<>();
            poundData.add(poundData1);
            dailyData3.setPoundData3(poundData);
            dailyData3 = checkDailyData4(dailyData3);
            dailyData3s.add(dailyData3);
        }
        ExportParams params = new ExportParams();
        params.setTitle("金石废料统计表（工作面季计）");
        params.setSheetName("各工作面季计");
        params.setSecondTitle("工作面:" + areaCode + Date + "季度");
        params.setStyle(ExcelStyleUtil.class);
        Workbook workbook = ExcelExportUtil.exportExcel(params, DailyData4.class, dailyData3s);
        sendResponse(workbook, areaCode + "工作面季度", Date, response);
    }

    /**
     * 各工作面半年
     *
     * @param Date
     * @param areaCode
     * @param response
     * @throws IOException
     */
    @GetMapping("/getReportByHalf")
    public void getReportByHalf(String Date, String areaCode, HttpServletResponse response) throws IOException {
        List<WeighbridgeConfig> wcList = this.wcDao.selectByExample(null);
        String weightbridgeCode1 = null;
        String weightbridgeCode2 = null;
        Iterator<WeighbridgeConfig> iterator = wcList.iterator();
        while (iterator.hasNext()) {
            WeighbridgeConfig weighbridgeConfig = iterator.next();
            String weightbridgeCode = weighbridgeConfig.getWeightbridgeCode();
            if (weightbridgeCode1 == null) {
                weightbridgeCode1 = weightbridgeCode;
                continue;
            }
            if (weightbridgeCode2 == null) {
                weightbridgeCode2 = weightbridgeCode;
                break;
            }
        }
        List<XlsxDataBean> list = this.dao.getReportByHalf(areaCode, Date);
        ArrayList<DailyData4> dailyData3s = new ArrayList<>();
        DailyData4 dailyData3 = null;
        String days = null;
        PoundData poundData1 = null;
        for (XlsxDataBean xlsxDataBean : list) {
            if (!xlsxDataBean.getMonths().equals(days)) {
                days = xlsxDataBean.getMonths();
                if (dailyData3 != null) {
                    ArrayList<PoundData> poundData = new ArrayList<>();
                    poundData.add(poundData1);
                    dailyData3.setPoundData3(poundData);
                    dailyData3 = checkDailyData4(dailyData3);
                    dailyData3s.add(dailyData3);
                }
                poundData1 = new PoundData(0, BigDecimal.ZERO, BigDecimal.ZERO);
                dailyData3 = new DailyData4();
            }
            ArrayList<PoundData> poundData1s = new ArrayList<>();
            dailyData3.setDays(checkMonths(xlsxDataBean.getMonths()));
            if (weightbridgeCode1.equals(xlsxDataBean.getWeighbridgeCode())) {
                PoundData poundData = new PoundData();
                poundData.setCountCars(xlsxDataBean.getVehiclenumber());
                poundData.setGross(xlsxDataBean.getGross());
                poundData.setTare(xlsxDataBean.getTare());
                poundData1s.add(poundData);
                dailyData3.setPoundData1(poundData1s);
            } else if (weightbridgeCode2.equals(xlsxDataBean.getWeighbridgeCode())) {
                PoundData poundData = new PoundData();
                poundData.setCountCars(xlsxDataBean.getVehiclenumber());
                poundData.setGross(xlsxDataBean.getGross());
                poundData.setTare(xlsxDataBean.getTare());
                poundData1s.add(poundData);
                dailyData3.setPoundData2(poundData1s);
            }
            poundData1.setCountCars(poundData1.getCountCars() + xlsxDataBean.getVehiclenumber());
            poundData1.setGross(poundData1.getGross().add(xlsxDataBean.getGross()));
            poundData1.setTare(poundData1.getTare().add(xlsxDataBean.getTare()));

        }
        if (dailyData3 != null) {
            ArrayList<PoundData> poundData = new ArrayList<>();
            poundData.add(poundData1);
            dailyData3.setPoundData3(poundData);
            dailyData3 = checkDailyData4(dailyData3);
            dailyData3s.add(dailyData3);
        }
        ExportParams params = new ExportParams();
        params.setTitle("金石废料统计表（工作面半年计）");
        params.setSheetName("各工作面半年计");
        params.setSecondTitle("工作面:" + areaCode + ("1".equals(Date) ? "上" : "下") + "半年");
        params.setStyle(ExcelStyleUtil.class);
        Workbook workbook = ExcelExportUtil.exportExcel(params, DailyData4.class, dailyData3s);
        sendResponse(workbook, areaCode + "工作面半年", Date, response);
    }

    /**
     * 各区年计
     *
     * @param Date
     * @param areaCode
     * @param response
     * @throws ParseException
     * @throws IOException
     */
    @GetMapping("/getReportByYear")
    public void getReportByYear(String Date, String areaCode, HttpServletResponse response) throws ParseException, IOException {
        List<WeighbridgeConfig> wcList = this.wcDao.selectByExample(null);
        String weightbridgeCode1 = null;
        String weightbridgeCode2 = null;
        Iterator<WeighbridgeConfig> iterator = wcList.iterator();
        while (iterator.hasNext()) {
            WeighbridgeConfig weighbridgeConfig = iterator.next();
            String weightbridgeCode = weighbridgeConfig.getWeightbridgeCode();
            if (weightbridgeCode1 == null) {
                weightbridgeCode1 = weightbridgeCode;
                continue;
            }
            if (weightbridgeCode2 == null) {
                weightbridgeCode2 = weightbridgeCode;
                break;
            }
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        List<XlsxDataBean> list = this.dao.getReportByYear(sdf.parse(Date), areaCode);
        ArrayList<DailyData4> dailyData3s = new ArrayList<>();
        DailyData4 dailyData3 = null;
        String days = null;
        PoundData poundData1 = null;
        for (XlsxDataBean xlsxDataBean : list) {
            if (!xlsxDataBean.getMonths().equals(days)) {
                days = xlsxDataBean.getMonths();
                if (dailyData3 != null) {
                    ArrayList<PoundData> poundData = new ArrayList<>();
                    poundData.add(poundData1);
                    dailyData3.setPoundData3(poundData);
                    dailyData3 = checkDailyData4(dailyData3);
                    dailyData3s.add(dailyData3);
                }
                poundData1 = new PoundData(0, BigDecimal.ZERO, BigDecimal.ZERO);
                dailyData3 = new DailyData4();
            }
            ArrayList<PoundData> poundData1s = new ArrayList<>();
            dailyData3.setDays(checkMonths(xlsxDataBean.getMonths()));
            if (weightbridgeCode1.equals(xlsxDataBean.getWeighbridgeCode())) {
                PoundData poundData = new PoundData();
                poundData.setCountCars(xlsxDataBean.getVehiclenumber());
                poundData.setGross(xlsxDataBean.getGross());
                poundData.setTare(xlsxDataBean.getTare());
                poundData1s.add(poundData);
                dailyData3.setPoundData1(poundData1s);
            } else if (weightbridgeCode2.equals(xlsxDataBean.getWeighbridgeCode())) {
                PoundData poundData = new PoundData();
                poundData.setCountCars(xlsxDataBean.getVehiclenumber());
                poundData.setGross(xlsxDataBean.getGross());
                poundData.setTare(xlsxDataBean.getTare());
                poundData1s.add(poundData);
                dailyData3.setPoundData2(poundData1s);
            }
            poundData1.setCountCars(poundData1.getCountCars() + xlsxDataBean.getVehiclenumber());
            poundData1.setGross(poundData1.getGross().add(xlsxDataBean.getGross()));
            poundData1.setTare(poundData1.getTare().add(xlsxDataBean.getTare()));

        }
        if (dailyData3 != null) {
            ArrayList<PoundData> poundData = new ArrayList<>();
            poundData.add(poundData1);
            dailyData3.setPoundData3(poundData);
            dailyData3 = checkDailyData4(dailyData3);
            dailyData3s.add(dailyData3);
        }

        ExportParams params = new ExportParams();
        params.setTitle("金石废料统计表（工作面年计）");
        params.setSheetName("各工作面年计");
        params.setSecondTitle("工作面:" + areaCode + Date + "年");
        params.setStyle(ExcelStyleUtil.class);
        Workbook workbook = ExcelExportUtil.exportExcel(params, DailyData4.class, dailyData3s);
        sendResponse(workbook, areaCode + "工作面年", Date, response);
    }

    /**
     * 六工作面日计
     *
     * @param Date
     * @param poundNumber
     * @param response
     */
    @GetMapping("/getDailyReportBySix")
    public void getDailyReportBySix(String Date, String poundNumber, HttpServletResponse response) throws IOException, ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startTime = null;
        Date endTime = null;
        if (StringUtils.isNotEmpty(Date)) {
            startTime = sdf.parse(Date + " 00:00:00");
            endTime = sdf.parse(Date + " 23:59:59");
        }
        List<WeighRecord> sixReportByDay = this.dao.getSixReportByDay(startTime, endTime, poundNumber);
        ArrayList<DailyData6> dailyData6s = new ArrayList<>();

        ArrayList<PoundData1> poundData1s1 = new ArrayList<>();
        ArrayList<PoundData1> poundData1s2 = new ArrayList<>();
        ArrayList<PoundData1> poundData1s3 = new ArrayList<>();

        ArrayList<PoundData1> poundData1s5 = new ArrayList<>();
        ArrayList<PoundData1> poundData1s6 = new ArrayList<>();
        ArrayList<PoundData1> poundData1s7 = new ArrayList<>();

        for (WeighRecord weighRecord : sixReportByDay) {
            String workArea = weighRecord.getWorkArea();
            PoundData1 poundData1 = new PoundData1();
            if ("第一工作面".equals(workArea)) {
                poundData1.setGross(weighRecord.getGross());
                poundData1.setTare(weighRecord.getTare());
                poundData1s1.add(poundData1);
            } else if ("第二工作面".equals(workArea)) {
                poundData1.setGross(weighRecord.getGross());
                poundData1.setTare(weighRecord.getTare());
                poundData1s2.add(poundData1);
            } else if ("第三工作面".equals(workArea)) {
                poundData1.setGross(weighRecord.getGross());
                poundData1.setTare(weighRecord.getTare());
                poundData1s3.add(poundData1);
            }  else if ("第五工作面".equals(workArea)) {
                poundData1.setGross(weighRecord.getGross());
                poundData1.setTare(weighRecord.getTare());
                poundData1s5.add(poundData1);
            } else if ("第六工作面".equals(workArea)) {
                poundData1.setGross(weighRecord.getGross());
                poundData1.setTare(weighRecord.getTare());
                poundData1s6.add(poundData1);
            } else if ("第七工作面".equals(workArea)) {
                poundData1.setGross(weighRecord.getGross());
                poundData1.setTare(weighRecord.getTare());
                poundData1s7.add(poundData1);
            }
        }
        int nums[] = new int[]{
                poundData1s1.size(),
                poundData1s2.size(),
                poundData1s3.size(),
                poundData1s5.size(),
                poundData1s6.size(),
                poundData1s7.size(),
        };
        Arrays.sort(nums);
        int num = nums[nums.length - 1];

        PoundData1 poundData = new PoundData1();
        poundData.setGross(BigDecimal.ZERO);
        poundData.setTare(BigDecimal.ZERO);
        for (int i = 0; i < num; i++) {
            DailyData6 dailyData6 = new DailyData6();
            if (poundData1s1.size() > i) {
                PoundData1 poundData1 = new PoundData1();
                poundData1.setTare(poundData1s1.get(i).getTare());
                poundData1.setGross(poundData1s1.get(i).getGross());
                ArrayList<PoundData1> poundData1s = new ArrayList<>();
                poundData1s.add(poundData1);
                dailyData6.setPoundData1(poundData1s);
            }else {
                ArrayList<PoundData1> poundData1s = new ArrayList<>();
                poundData1s.add(poundData);
                dailyData6.setPoundData1(poundData1s);
            }
            if (poundData1s2.size() > i) {
                PoundData1 poundData1 = new PoundData1();
                poundData1.setTare(poundData1s2.get(i).getTare());
                poundData1.setGross(poundData1s2.get(i).getGross());
                ArrayList<PoundData1> poundData1s = new ArrayList<>();
                poundData1s.add(poundData1);
                dailyData6.setPoundData2(poundData1s);
            }else {
                ArrayList<PoundData1> poundData1s = new ArrayList<>();
                poundData1s.add(poundData);
                dailyData6.setPoundData2(poundData1s);
            }
            if (poundData1s3.size() > i) {
                PoundData1 poundData1 = new PoundData1();
                poundData1.setTare(poundData1s3.get(i).getTare());
                poundData1.setGross(poundData1s3.get(i).getGross());
                ArrayList<PoundData1> poundData1s = new ArrayList<>();
                poundData1s.add(poundData1);
                dailyData6.setPoundData3(poundData1s);
            }else {
                ArrayList<PoundData1> poundData1s = new ArrayList<>();
                poundData1s.add(poundData);
                dailyData6.setPoundData3(poundData1s);
            }
            if (poundData1s5.size() > i) {
                PoundData1 poundData1 = new PoundData1();
                poundData1.setTare(poundData1s5.get(i).getTare());
                poundData1.setGross(poundData1s5.get(i).getGross());
                ArrayList<PoundData1> poundData1s = new ArrayList<>();
                poundData1s.add(poundData1);
                dailyData6.setPoundData5(poundData1s);
            }else {
                ArrayList<PoundData1> poundData1s = new ArrayList<>();
                poundData1s.add(poundData);
                dailyData6.setPoundData5(poundData1s);
            }
            if (poundData1s6.size() > i) {
                PoundData1 poundData1 = new PoundData1();
                poundData1.setTare(poundData1s6.get(i).getTare());
                poundData1.setGross(poundData1s6.get(i).getGross());
                ArrayList<PoundData1> poundData1s = new ArrayList<>();
                poundData1s.add(poundData1);
                dailyData6.setPoundData6(poundData1s);
            }else {
                ArrayList<PoundData1> poundData1s = new ArrayList<>();
                poundData1s.add(poundData);
                dailyData6.setPoundData6(poundData1s);
            }
            if (poundData1s7.size() > i) {
                PoundData1 poundData1 = new PoundData1();
                poundData1.setTare(poundData1s7.get(i).getTare());
                poundData1.setGross(poundData1s7.get(i).getGross());
                ArrayList<PoundData1> poundData1s = new ArrayList<>();
                poundData1s.add(poundData1);
                dailyData6.setPoundData7(poundData1s);
            }else {
                ArrayList<PoundData1> poundData1s = new ArrayList<>();
                poundData1s.add(poundData);
                dailyData6.setPoundData7(poundData1s);
            }
            dailyData6.setId(i + 1);
            dailyData6s.add(dailyData6);
        }
        ExportParams params = new ExportParams();
        params.setTitle("金石废料统计表（6工作面日计）");
        params.setSheetName("6工作面日计");
        params.setSecondTitle("日期:" + Date + "磅" + poundNumber);
        params.setStyle(ExcelStyleUtil.class);
        Workbook workbook = ExcelExportUtil.exportExcel(params, DailyData6.class, dailyData6s);
        sendResponse(workbook, "六工作面地磅" + poundNumber + "_", Date, response);
    }

    /**
     * 六工作面月计
     *
     * @param Date
     * @param poundNumber
     * @param response
     * @throws IOException
     * @throws ParseException
     */
    @GetMapping("/getMonthReportBySix")
    public void getMonthReportBySix(String Date, String poundNumber, HttpServletResponse response) throws IOException, ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startTime = null;
        Date endTime = null;
        if (StringUtils.isNotEmpty(Date)) {
            Date dates = sdf.parse(Date);
            startTime = getFirstDayDateOfMonth(dates);
            Date endDate = getLastDayOfMonth(dates);
            String format = sdf1.format(endDate) + " 23:59:59";
            endTime = sdf2.parse(format);
        }
        List<XlsxDataBean> list = this.dao.getSixReportByMonth(startTime, endTime, poundNumber);
        ArrayList<DailyData7> dailyData7s = new ArrayList<>();
        DailyData7 dailyData7 = null;
        Date days = null;
        PoundData poundData1 = null;
        for (XlsxDataBean xlsxDataBean : list) {
            if (!xlsxDataBean.getShowTime().equals(days)) {
                days = xlsxDataBean.getShowTime();
                if (dailyData7 != null) {
                    ArrayList<PoundData> poundData = new ArrayList<>();
                    poundData.add(poundData1);
                    dailyData7.setPoundData8(poundData);
                    dailyData7 = checkDailyData(dailyData7);
                    dailyData7s.add(dailyData7);
                }
                poundData1 = new PoundData(0, BigDecimal.ZERO, BigDecimal.ZERO);
                dailyData7 = new DailyData7();
            }
            ArrayList<PoundData> poundData1s = new ArrayList<>();
            dailyData7.setDays(xlsxDataBean.getShowTime());
            if ("第一工作面".equals(xlsxDataBean.getWorkArea())) {
                PoundData poundData = new PoundData();
                poundData.setCountCars(xlsxDataBean.getVehiclenumber());
                poundData.setGross(xlsxDataBean.getGross());
                poundData.setTare(xlsxDataBean.getTare());
                poundData1s.add(poundData);
                dailyData7.setPoundData1(poundData1s);
            } else if ("第二工作面".equals(xlsxDataBean.getWorkArea())) {
                PoundData poundData = new PoundData();
                poundData.setCountCars(xlsxDataBean.getVehiclenumber());
                poundData.setGross(xlsxDataBean.getGross());
                poundData.setTare(xlsxDataBean.getTare());
                poundData1s.add(poundData);
                dailyData7.setPoundData2(poundData1s);
            } else if ("第三工作面".equals(xlsxDataBean.getWorkArea())) {
                PoundData poundData = new PoundData();
                poundData.setCountCars(xlsxDataBean.getVehiclenumber());
                poundData.setGross(xlsxDataBean.getGross());
                poundData.setTare(xlsxDataBean.getTare());
                poundData1s.add(poundData);
                dailyData7.setPoundData3(poundData1s);
            } else if ("第五工作面".equals(xlsxDataBean.getWorkArea())) {
                PoundData poundData = new PoundData();
                poundData.setCountCars(xlsxDataBean.getVehiclenumber());
                poundData.setGross(xlsxDataBean.getGross());
                poundData.setTare(xlsxDataBean.getTare());
                poundData1s.add(poundData);
                dailyData7.setPoundData5(poundData1s);
            } else if ("第六工作面".equals(xlsxDataBean.getWorkArea())) {
                PoundData poundData = new PoundData();
                poundData.setCountCars(xlsxDataBean.getVehiclenumber());
                poundData.setGross(xlsxDataBean.getGross());
                poundData.setTare(xlsxDataBean.getTare());
                poundData1s.add(poundData);
                dailyData7.setPoundData6(poundData1s);
            } else if ("第七工作面".equals(xlsxDataBean.getWorkArea())) {
                PoundData poundData = new PoundData();
                poundData.setCountCars(xlsxDataBean.getVehiclenumber());
                poundData.setGross(xlsxDataBean.getGross());
                poundData.setTare(xlsxDataBean.getTare());
                poundData1s.add(poundData);
                dailyData7.setPoundData7(poundData1s);
            }
            poundData1.setCountCars(poundData1.getCountCars() + xlsxDataBean.getVehiclenumber());
            poundData1.setGross(poundData1.getGross().add(xlsxDataBean.getGross()));
            poundData1.setTare(poundData1.getTare().add(xlsxDataBean.getTare()));


        }
        if (dailyData7 != null) {
            ArrayList<PoundData> poundData = new ArrayList<>();
            poundData.add(poundData1);
            dailyData7.setPoundData8(poundData);
            dailyData7 = checkDailyData(dailyData7);
            dailyData7s.add(dailyData7);
        }
        ExportParams params = new ExportParams();
        params.setTitle("金石废料统计表（6工作面月计）");
        params.setSheetName("6工作面月计");
        params.setSecondTitle("月份:" + Date + "磅" + poundNumber);
        params.setStyle(ExcelStyleUtil.class);
        Workbook workbook = ExcelExportUtil.exportExcel(params, DailyData7.class, dailyData7s);
        sendResponse(workbook, "六工作面地磅" + poundNumber + "_月计", Date, response);
    }

    /**
     * 六工作面季计
     *
     * @param Date
     * @param poundNumber
     * @param response
     */
    @GetMapping("/getSeasonReportBySix")
    public void getSeasonReportBySix(String Date, String poundNumber, HttpServletResponse response) throws IOException {
        List<XlsxDataBean> list = this.dao.getSixReportBySeason(Date, poundNumber);
        ArrayList<DailyData8> dailyData7s = new ArrayList<>();
        DailyData8 dailyData7 = null;
        String days = null;
        PoundData poundData1 = null;
        for (XlsxDataBean xlsxDataBean : list) {
            if (!xlsxDataBean.getMonths().equals(days)) {
                days = xlsxDataBean.getMonths();
                if (dailyData7 != null) {
                    ArrayList<PoundData> poundData = new ArrayList<>();
                    poundData.add(poundData1);
                    dailyData7.setPoundData8(poundData);
                    dailyData7 = checkDailyData2(dailyData7);
                    dailyData7s.add(dailyData7);
                }
                poundData1 = new PoundData(0, BigDecimal.ZERO, BigDecimal.ZERO);
                dailyData7 = new DailyData8();
            }
            ArrayList<PoundData> poundData1s = new ArrayList<>();
            dailyData7.setDays(checkMonths((xlsxDataBean.getMonths()).toString()));
            if ("第一工作面".equals(xlsxDataBean.getWorkArea())) {
                PoundData poundData = new PoundData();
                poundData.setCountCars(xlsxDataBean.getVehiclenumber());
                poundData.setGross(xlsxDataBean.getGross());
                poundData.setTare(xlsxDataBean.getTare());
                poundData1s.add(poundData);
                dailyData7.setPoundData1(poundData1s);
            } else if ("第二工作面".equals(xlsxDataBean.getWorkArea())) {
                PoundData poundData = new PoundData();
                poundData.setCountCars(xlsxDataBean.getVehiclenumber());
                poundData.setGross(xlsxDataBean.getGross());
                poundData.setTare(xlsxDataBean.getTare());
                poundData1s.add(poundData);
                dailyData7.setPoundData2(poundData1s);
            } else if ("第三工作面".equals(xlsxDataBean.getWorkArea())) {
                PoundData poundData = new PoundData();
                poundData.setCountCars(xlsxDataBean.getVehiclenumber());
                poundData.setGross(xlsxDataBean.getGross());
                poundData.setTare(xlsxDataBean.getTare());
                poundData1s.add(poundData);
                dailyData7.setPoundData3(poundData1s);
            }  else if ("第五工作面".equals(xlsxDataBean.getWorkArea())) {
                PoundData poundData = new PoundData();
                poundData.setCountCars(xlsxDataBean.getVehiclenumber());
                poundData.setGross(xlsxDataBean.getGross());
                poundData.setTare(xlsxDataBean.getTare());
                poundData1s.add(poundData);
                dailyData7.setPoundData5(poundData1s);
            } else if ("第六工作面".equals(xlsxDataBean.getWorkArea())) {
                PoundData poundData = new PoundData();
                poundData.setCountCars(xlsxDataBean.getVehiclenumber());
                poundData.setGross(xlsxDataBean.getGross());
                poundData.setTare(xlsxDataBean.getTare());
                poundData1s.add(poundData);
                dailyData7.setPoundData6(poundData1s);
            } else if ("第七工作面".equals(xlsxDataBean.getWorkArea())) {
                PoundData poundData = new PoundData();
                poundData.setCountCars(xlsxDataBean.getVehiclenumber());
                poundData.setGross(xlsxDataBean.getGross());
                poundData.setTare(xlsxDataBean.getTare());
                poundData1s.add(poundData);
                dailyData7.setPoundData7(poundData1s);
            }
            poundData1.setCountCars(poundData1.getCountCars() + xlsxDataBean.getVehiclenumber());
            poundData1.setGross(poundData1.getGross().add(xlsxDataBean.getGross()));
            poundData1.setTare(poundData1.getTare().add(xlsxDataBean.getTare()));


        }
        if (dailyData7 != null) {
            ArrayList<PoundData> poundData = new ArrayList<>();
            poundData.add(poundData1);
            dailyData7.setPoundData8(poundData);
            dailyData7 = checkDailyData2(dailyData7);
            dailyData7s.add(dailyData7);
        }
        ExportParams params = new ExportParams();
        params.setTitle("金石废料统计表（6工作面季计）");
        params.setSheetName("6工作面季计");
        params.setSecondTitle("季度:" + Date + "地磅" + poundNumber);
        params.setStyle(ExcelStyleUtil.class);
        Workbook workbook = ExcelExportUtil.exportExcel(params, DailyData8.class, dailyData7s);
        sendResponse(workbook, "六工作面地磅" + poundNumber + "_季度", Date, response);
    }

    /**
     * 六工作面半年计
     *
     * @param Date
     * @param poundNumber
     * @param response
     * @throws IOException
     */
    @GetMapping("/getHalfReportBySix")
    public void getHalfReportBySix(String Date, String poundNumber, HttpServletResponse response) throws IOException {
        List<XlsxDataBean> list = this.dao.getSixReportByHalf(Date, poundNumber);
        ArrayList<DailyData8> dailyData7s = new ArrayList<>();
        DailyData8 dailyData7 = null;
        String days = null;
        PoundData poundData1 = null;
        for (XlsxDataBean xlsxDataBean : list) {
            if (!xlsxDataBean.getMonths().equals(days)) {
                days = xlsxDataBean.getMonths();
                if (dailyData7 != null) {
                    ArrayList<PoundData> poundData = new ArrayList<>();
                    poundData.add(poundData1);
                    dailyData7.setPoundData8(poundData);
                    dailyData7 = checkDailyData2(dailyData7);
                    dailyData7s.add(dailyData7);
                }
                poundData1 = new PoundData(0, BigDecimal.ZERO, BigDecimal.ZERO);
                dailyData7 = new DailyData8();
            }
            ArrayList<PoundData> poundData1s = new ArrayList<>();
            dailyData7.setDays(checkMonths((xlsxDataBean.getMonths()).toString()));
            if ("第一工作面".equals(xlsxDataBean.getWorkArea())) {
                PoundData poundData = new PoundData();
                poundData.setCountCars(xlsxDataBean.getVehiclenumber());
                poundData.setGross(xlsxDataBean.getGross());
                poundData.setTare(xlsxDataBean.getTare());
                poundData1s.add(poundData);
                dailyData7.setPoundData1(poundData1s);
            } else if ("第二工作面".equals(xlsxDataBean.getWorkArea())) {
                PoundData poundData = new PoundData();
                poundData.setCountCars(xlsxDataBean.getVehiclenumber());
                poundData.setGross(xlsxDataBean.getGross());
                poundData.setTare(xlsxDataBean.getTare());
                poundData1s.add(poundData);
                dailyData7.setPoundData2(poundData1s);
            } else if ("第三工作面".equals(xlsxDataBean.getWorkArea())) {
                PoundData poundData = new PoundData();
                poundData.setCountCars(xlsxDataBean.getVehiclenumber());
                poundData.setGross(xlsxDataBean.getGross());
                poundData.setTare(xlsxDataBean.getTare());
                poundData1s.add(poundData);
                dailyData7.setPoundData3(poundData1s);
            } else if ("第五工作面".equals(xlsxDataBean.getWorkArea())) {
                PoundData poundData = new PoundData();
                poundData.setCountCars(xlsxDataBean.getVehiclenumber());
                poundData.setGross(xlsxDataBean.getGross());
                poundData.setTare(xlsxDataBean.getTare());
                poundData1s.add(poundData);
                dailyData7.setPoundData5(poundData1s);
            } else if ("第六工作面".equals(xlsxDataBean.getWorkArea())) {
                PoundData poundData = new PoundData();
                poundData.setCountCars(xlsxDataBean.getVehiclenumber());
                poundData.setGross(xlsxDataBean.getGross());
                poundData.setTare(xlsxDataBean.getTare());
                poundData1s.add(poundData);
                dailyData7.setPoundData6(poundData1s);
            } else if ("第七工作面".equals(xlsxDataBean.getWorkArea())) {
                PoundData poundData = new PoundData();
                poundData.setCountCars(xlsxDataBean.getVehiclenumber());
                poundData.setGross(xlsxDataBean.getGross());
                poundData.setTare(xlsxDataBean.getTare());
                poundData1s.add(poundData);
                dailyData7.setPoundData7(poundData1s);
            }
            poundData1.setCountCars(poundData1.getCountCars() + xlsxDataBean.getVehiclenumber());
            poundData1.setGross(poundData1.getGross().add(xlsxDataBean.getGross()));
            poundData1.setTare(poundData1.getTare().add(xlsxDataBean.getTare()));


        }
        if (dailyData7 != null) {
            ArrayList<PoundData> poundData = new ArrayList<>();
            poundData.add(poundData1);
            dailyData7.setPoundData8(poundData);
            dailyData7 = checkDailyData2(dailyData7);
            dailyData7s.add(dailyData7);
        }
        ExportParams params = new ExportParams();
        params.setTitle("金石废料统计表（6工作面半年计）");
        params.setSheetName("6工作面半年计");
        params.setSecondTitle(("1".equals(Date) ? "上" : "下") + "半年" + "地磅" + poundNumber);
        params.setStyle(ExcelStyleUtil.class);
        Workbook workbook = ExcelExportUtil.exportExcel(params, DailyData8.class, dailyData7s);
        sendResponse(workbook, "六工作面地磅" + poundNumber + "_半年", Date, response);
    }

    /**
     * 六工作面年计
     *
     * @param Date
     * @param poundNumber
     * @param response
     */
    @GetMapping("/getYearReportBySix")
    public void getYearReportBySix(String Date, String poundNumber, HttpServletResponse response) throws IOException {
        List<XlsxDataBean> list = this.dao.getSixReportByYear(Date, poundNumber);
        ArrayList<DailyData8> dailyData7s = new ArrayList<>();
        DailyData8 dailyData7 = null;
        String days = null;
        PoundData poundData1 = null;
        for (XlsxDataBean xlsxDataBean : list) {
            if (!xlsxDataBean.getMonths().equals(days)) {
                days = xlsxDataBean.getMonths();
                if (dailyData7 != null) {
                    ArrayList<PoundData> poundData = new ArrayList<>();
                    poundData.add(poundData1);
                    dailyData7.setPoundData8(poundData);
                    dailyData7 = checkDailyData2(dailyData7);
                    dailyData7s.add(dailyData7);
                }
                poundData1 = new PoundData(0, BigDecimal.ZERO, BigDecimal.ZERO);
                dailyData7 = new DailyData8();
            }
            ArrayList<PoundData> poundData1s = new ArrayList<>();
            dailyData7.setDays(checkMonths((xlsxDataBean.getMonths()).toString()));
            if ("第一工作面".equals(xlsxDataBean.getWorkArea())) {
                PoundData poundData = new PoundData();
                poundData.setCountCars(xlsxDataBean.getVehiclenumber());
                poundData.setGross(xlsxDataBean.getGross());
                poundData.setTare(xlsxDataBean.getTare());
                poundData1s.add(poundData);
                dailyData7.setPoundData1(poundData1s);
            } else if ("第二工作面".equals(xlsxDataBean.getWorkArea())) {
                PoundData poundData = new PoundData();
                poundData.setCountCars(xlsxDataBean.getVehiclenumber());
                poundData.setGross(xlsxDataBean.getGross());
                poundData.setTare(xlsxDataBean.getTare());
                poundData1s.add(poundData);
                dailyData7.setPoundData2(poundData1s);
            } else if ("第三工作面".equals(xlsxDataBean.getWorkArea())) {
                PoundData poundData = new PoundData();
                poundData.setCountCars(xlsxDataBean.getVehiclenumber());
                poundData.setGross(xlsxDataBean.getGross());
                poundData.setTare(xlsxDataBean.getTare());
                poundData1s.add(poundData);
                dailyData7.setPoundData3(poundData1s);
            }else if ("第五工作面".equals(xlsxDataBean.getWorkArea())) {
                PoundData poundData = new PoundData();
                poundData.setCountCars(xlsxDataBean.getVehiclenumber());
                poundData.setGross(xlsxDataBean.getGross());
                poundData.setTare(xlsxDataBean.getTare());
                poundData1s.add(poundData);
                dailyData7.setPoundData5(poundData1s);
            } else if ("第六工作面".equals(xlsxDataBean.getWorkArea())) {
                PoundData poundData = new PoundData();
                poundData.setCountCars(xlsxDataBean.getVehiclenumber());
                poundData.setGross(xlsxDataBean.getGross());
                poundData.setTare(xlsxDataBean.getTare());
                poundData1s.add(poundData);
                dailyData7.setPoundData6(poundData1s);
            } else if ("第七工作面".equals(xlsxDataBean.getWorkArea())) {
                PoundData poundData = new PoundData();
                poundData.setCountCars(xlsxDataBean.getVehiclenumber());
                poundData.setGross(xlsxDataBean.getGross());
                poundData.setTare(xlsxDataBean.getTare());
                poundData1s.add(poundData);
                dailyData7.setPoundData7(poundData1s);
            }
            poundData1.setCountCars(poundData1.getCountCars() + xlsxDataBean.getVehiclenumber());
            poundData1.setGross(poundData1.getGross().add(xlsxDataBean.getGross()));
            poundData1.setTare(poundData1.getTare().add(xlsxDataBean.getTare()));


        }
        if (dailyData7 != null) {
            ArrayList<PoundData> poundData = new ArrayList<>();
            poundData.add(poundData1);
            dailyData7.setPoundData8(poundData);
            dailyData7 = checkDailyData2(dailyData7);
            dailyData7s.add(dailyData7);
        }
        ExportParams params = new ExportParams();
        params.setTitle("金石废料统计表（6工作面年计）");
        params.setSheetName("6工作面年计");
        params.setSecondTitle(Date + "年" + "地磅" + poundNumber);
        params.setStyle(ExcelStyleUtil.class);
        Workbook workbook = ExcelExportUtil.exportExcel(params, DailyData8.class, dailyData7s);
        sendResponse(workbook, "六工作面地磅" + poundNumber + "_年", Date, response);
    }

    /**
     * 车辆信息xlsx
     *
     * @param response
     * @throws IOException
     */
    @GetMapping("/allCarsExcel")
    public void allCarsExcel(HttpServletResponse response) throws IOException {
        List<Vehicle> vehicles = this.carDao.selectByExample(null);
        List<CarsXlsxData> cars = new ArrayList<>();
        for (Vehicle vehicle : vehicles) {
            CarsXlsxData car = new CarsXlsxData();
            car.setPlateNumber(vehicle.getPlateNumber());
            car.setTare(vehicle.getTare());
            car.setVehicleType(checkVehicleType(vehicle.getVehicleType()));
            car.setWorkArea(vehicle.getWorkArea());
            cars.add(car);
        }
        ExportParams params = new ExportParams();
        String filename = "金石废料运输车辆统计";
        params.setTitle(filename);
        params.setSheetName("车辆目录");
        params.setStyle(ExcelStyleUtil.class);
        Workbook workbook = ExcelExportUtil.exportExcel(params, CarsXlsxData.class, cars);
        response.setHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes("utf-8"), "ISO8859-1") + ".xlsx");
        response.setContentType("application/application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        ServletOutputStream os = response.getOutputStream();
        workbook.write(os);
        os.flush();
        os.close();
    }

    /**
     * 当月地区无工作为0
     *
     * @param dailyData7
     * @return
     */
    private DailyData8 checkDailyData2(DailyData8 dailyData7) {
        PoundData poundData = new PoundData();
        poundData.setCountCars(0);
        poundData.setGross(BigDecimal.ZERO);
        poundData.setTare(BigDecimal.ZERO);
        ArrayList<PoundData> poundDatas = new ArrayList<>();
        poundDatas.add(poundData);
        if (CollectionUtils.isEmpty(dailyData7.getPoundData1())) {
            dailyData7.setPoundData1(poundDatas);
        }
        if (CollectionUtils.isEmpty(dailyData7.getPoundData2())) {
            dailyData7.setPoundData2(poundDatas);
        }
        if (CollectionUtils.isEmpty(dailyData7.getPoundData3())) {
            dailyData7.setPoundData3(poundDatas);
        }
        if (CollectionUtils.isEmpty(dailyData7.getPoundData5())) {
            dailyData7.setPoundData5(poundDatas);
        }
        if (CollectionUtils.isEmpty(dailyData7.getPoundData6())) {
            dailyData7.setPoundData6(poundDatas);
        }
        if (CollectionUtils.isEmpty(dailyData7.getPoundData7())) {
            dailyData7.setPoundData7(poundDatas);
        }

        return dailyData7;
    }

    /**
     * 当月地磅无工作为0
     *
     * @param dailyData7
     * @return
     */
    private DailyData3 checkDailyData3(DailyData3 dailyData7) {
        PoundData poundData = new PoundData();
        poundData.setCountCars(0);
        poundData.setGross(BigDecimal.ZERO);
        poundData.setTare(BigDecimal.ZERO);
        ArrayList<PoundData> poundDatas = new ArrayList<>();
        poundDatas.add(poundData);
        if (CollectionUtils.isEmpty(dailyData7.getPoundData1())) {
            dailyData7.setPoundData1(poundDatas);
        }
        if (CollectionUtils.isEmpty(dailyData7.getPoundData2())) {
            dailyData7.setPoundData2(poundDatas);
        }
        if (CollectionUtils.isEmpty(dailyData7.getPoundData3())) {
            dailyData7.setPoundData3(poundDatas);
        }
        return dailyData7;
    }

    /**
     * 当季地磅无工作为0
     *
     * @param dailyData7
     * @return
     */
    private DailyData4 checkDailyData4(DailyData4 dailyData7) {
        PoundData poundData = new PoundData();
        poundData.setCountCars(0);
        poundData.setGross(BigDecimal.ZERO);
        poundData.setTare(BigDecimal.ZERO);
        ArrayList<PoundData> poundDatas = new ArrayList<>();
        poundDatas.add(poundData);
        if (CollectionUtils.isEmpty(dailyData7.getPoundData1())) {
            dailyData7.setPoundData1(poundDatas);
        }
        if (CollectionUtils.isEmpty(dailyData7.getPoundData2())) {
            dailyData7.setPoundData2(poundDatas);
        }
        if (CollectionUtils.isEmpty(dailyData7.getPoundData3())) {
            dailyData7.setPoundData3(poundDatas);
        }
        return dailyData7;
    }


    /**
     * 当天那个地区无工作为0
     *
     * @param dailyData7
     * @return
     */
    private DailyData7 checkDailyData(DailyData7 dailyData7) {
        PoundData poundData = new PoundData();
        poundData.setCountCars(0);
        poundData.setGross(BigDecimal.ZERO);
        poundData.setTare(BigDecimal.ZERO);
        ArrayList<PoundData> poundDatas = new ArrayList<>();
        poundDatas.add(poundData);
        if (CollectionUtils.isEmpty(dailyData7.getPoundData1())) {
            dailyData7.setPoundData1(poundDatas);
        }
        if (CollectionUtils.isEmpty(dailyData7.getPoundData2())) {
            dailyData7.setPoundData2(poundDatas);
        }
        if (CollectionUtils.isEmpty(dailyData7.getPoundData3())) {
            dailyData7.setPoundData3(poundDatas);
        }
        if (CollectionUtils.isEmpty(dailyData7.getPoundData5())) {
            dailyData7.setPoundData5(poundDatas);
        }
        if (CollectionUtils.isEmpty(dailyData7.getPoundData6())) {
            dailyData7.setPoundData6(poundDatas);
        }
        if (CollectionUtils.isEmpty(dailyData7.getPoundData7())) {
            dailyData7.setPoundData7(poundDatas);
        }

        return dailyData7;
    }

    /**
     * 判断月份,转成合适的字符串
     *
     * @param months
     * @return
     */
    private String checkMonths(String months) {
        switch (months) {
            case "1":
                return "一月";
            case "2":
                return "二月";
            case "3":
                return "三月";
            case "4":
                return "四月";
            case "5":
                return "五月";
            case "6":
                return "六月";
            case "7":
                return "七月";
            case "8":
                return "八月";
            case "9":
                return "九月";
            case "10":
                return "十月";
            case "11":
                return "十一月";
            default:
                return "十二月";
        }
    }

    /**
     * 返回xlsx公用方法
     *
     * @param workbook
     * @param response
     * @throws IOException
     */
    private void sendResponse(Workbook workbook, String filename, String Date, HttpServletResponse response) throws IOException {
        Sheet sheet = workbook.getSheetAt(0);
        Row row = sheet.getRow(1);
        Cell cell = row.getCell(0);
        CellStyle cellStyle = workbook.createCellStyle();
        short index = IndexedColors.YELLOW.getIndex();
        cellStyle.setFillForegroundColor(index);
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setAlignment(HorizontalAlignment.LEFT);
        cell.setCellStyle(cellStyle);
        sheet.protectSheet(UUID.randomUUID().toString());
        response.setHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes("utf-8"), "ISO8859-1") + Date + ".xlsx");
        response.setContentType("application/application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        ServletOutputStream os = response.getOutputStream();
        workbook.write(os);
        os.flush();
        os.close();
    }

    /**
     * 获取传入日期所在月的第一天
     *
     * @param date
     */
    public static Date getFirstDayDateOfMonth(final Date date) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        final int last = cal.getActualMinimum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DAY_OF_MONTH, last);
        return cal.getTime();
    }

    /**
     * 获取传入日期所在月的最后一天
     *
     * @param date
     */
    public static Date getLastDayOfMonth(final Date date) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        final int last = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DAY_OF_MONTH, last);
        return cal.getTime();
    }

    /**
     * 获取全部车辆信息填充下拉框
     *
     * @return
     */
    @GetMapping("/getCars")
    @ResponseBody
    public ResponseData getCars() {
        List<Vehicle> vehicles = this.carDao.selectByExample(null);
        if (CollectionUtils.isNotEmpty(vehicles)) {
            return ResponseData.success(vehicles);
        }
        return ErrorResponseData.error("查询失败");
    }

    @GetMapping("/getWcs")
    @ResponseBody
    public ResponseData getWcs() {
        List<WeighbridgeConfig> weighbridgeConfigs = this.wcDao.selectByExample(null);
        if (CollectionUtils.isNotEmpty(weighbridgeConfigs)) {
            return ResponseData.success(weighbridgeConfigs);
        }
        return ErrorResponseData.error("查询失败");
    }

    /**
     * 各个车辆xlsx
     *
     * @param plateNumber
     * @param response
     */
    @GetMapping("/getCarsXlsx")
    public void getCarsXlsx(Integer plateNumber, HttpServletResponse response) throws IOException {
        Vehicle vehicle = this.carDao.selectByPrimaryKey(plateNumber);
        String plateNumber1 = vehicle.getPlateNumber();
        ArrayList<EveryCarsXlsxData> list = new ArrayList<>();
        WeighRecordExample e = new WeighRecordExample();
        e.createCriteria().andPlateNumberEqualTo(plateNumber1);
        List<WeighRecord> weighRecords = this.dao.selectByExample(e);
        for (int i = 0; i < weighRecords.size(); i++) {
            EveryCarsXlsxData xlsxData = new EveryCarsXlsxData();
            xlsxData.setId(i+1);
            WeighRecord weighRecord = weighRecords.get(i);
            xlsxData.setMonth(weighRecord.getTimeWeigh());
            xlsxData.setDay(weighRecord.getTimeWeigh());
            xlsxData.setTime(weighRecord.getTimeWeigh());
            xlsxData.setWeighbridgeCode(weighRecord.getWeighbridgeCode());
            xlsxData.setTare(weighRecord.getTare());
            xlsxData.setGross(weighRecord.getGross());
            list.add(xlsxData);
        }
        ExportParams params = new ExportParams();
        params.setTitle("金石废料统计表（各车辆）");
        params.setSheetName("各车辆总计");
        params.setSecondTitle("车牌号:" + plateNumber1
                + "          所属工作面:" + vehicle.getWorkArea()
                + "          型号:" + vehicle.getVehicleType()
                + "          皮重:" + vehicle.getTare()
        );
        params.setStyle(ExcelStyleUtil.class);
        Workbook workbook = ExcelExportUtil.exportExcel(params, EveryCarsXlsxData.class, list);
        Sheet sheet = workbook.getSheetAt(0);
        Row row = sheet.getRow(1);
        Cell cell = row.getCell(0);
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.LEFT);
        cell.setCellStyle(cellStyle);
        sheet.protectSheet(UUID.randomUUID().toString());
        String filename = "金石废料统计表（各车辆）";
        response.setHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes("utf-8"), "ISO8859-1") + ".xlsx");
        response.setContentType("application/application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        ServletOutputStream os = response.getOutputStream();
        workbook.write(os);
        os.flush();
        os.close();
    }

    @RequestMapping("/getCarsExcel")
    public void getCarsExcel(String plateNumber,String vehicleType,String workArea, HttpServletResponse response) throws IOException {
        List<Vehicle> vehicles = this.carDao.getCarsExcel(plateNumber, vehicleType, workArea);
        List<CarsXlsxData> cars = new ArrayList<>();
        for (Vehicle vehicle : vehicles) {
            CarsXlsxData car = new CarsXlsxData();
            car.setPlateNumber(vehicle.getPlateNumber());
            car.setTare(vehicle.getTare());
            car.setVehicleType(checkVehicleType(vehicle.getVehicleType()));
            car.setWorkArea(vehicle.getWorkArea());
            cars.add(car);
        }
        ExportParams params = new ExportParams();
        String filename = "金石废料运输车辆统计";
        params.setTitle(filename);
        params.setSheetName("车辆目录");
        params.setStyle(ExcelStyleUtil.class);
        Workbook workbook = ExcelExportUtil.exportExcel(params, CarsXlsxData.class, cars);
        response.setHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes("utf-8"), "ISO8859-1") + ".xlsx");
        response.setContentType("application/application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        ServletOutputStream os = response.getOutputStream();
        workbook.write(os);
        os.flush();
        os.close();
    }

    private String checkVehicleType(Integer VehicleType) {
        switch (VehicleType) {
            case 1:
                return "卡车";
            case 2:
                return "叉装车辆";
            case 3:
                return "挖掘机";
            case 4:
                return "洒水车";
            case 5:
                return "维修车辆";
            case 6:
                return "管理车辆";
            default:
                return "其他车";
        }
    }

}
