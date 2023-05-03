package com.sky.service.impl;

import com.sky.dto.GoodsSalesDTO;
import com.sky.entity.Orders;
import com.sky.mapper.OrderDetailMapper;
import com.sky.mapper.OrderMapper;
import com.sky.mapper.UserMapper;
import com.sky.service.ReportService;
import com.sky.service.WorkSpaceService;
import com.sky.vo.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.WatchService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    OrderDetailMapper orderDetailMapper;
    @Autowired
    WorkSpaceService workSpaceService;

    @Override
    public TurnoverReportVO getTurnover(LocalDate begin, LocalDate end) {
//        ArrayList<LocalDate> dateList = new ArrayList<>();
//        dateList.add(begin);
//
//        while (!begin.equals(end)){
//            begin=begin.plusDays(1);//日期计算，获得指定日期后1天的日期
//            dateList.add(begin);
//        }
        ArrayList<LocalDate> dateList = dataInsert(begin, end);
        ArrayList<Double> turnoverList = new ArrayList<>();
        for (LocalDate localDate : dateList) {
            //设置每天开始的时间
            LocalDateTime beginTime = LocalDateTime.of(localDate, LocalTime.MIN);
            //设置每天结束的时间
            LocalDateTime endTime = LocalDateTime.of(localDate, LocalTime.MAX);
            //封装要查询的对象
            Map map = new HashMap<>();
            map.put("status", Orders.COMPLETED);
            map.put("begin", beginTime);
            map.put("end", endTime);
            Double turnover = orderMapper.sumByMap(map);
            turnover = turnover == null ? 0.0 : turnover;
            turnoverList.add(turnover);
        }
//返回查询到的对象并进行封装
        return TurnoverReportVO.builder()
                .dateList(StringUtils.join(dateList, ","))
                .turnoverList(StringUtils.join(turnoverList, ","))
                .build();
    }

    @Override
    public UserReportVO getUserStatistics(LocalDate begin, LocalDate end) {
        ArrayList<LocalDate> dateList = dataInsert(begin, end);
        ArrayList<Integer> newCustomers = new ArrayList<>();
        for (LocalDate localDate : dateList) {
            LocalDateTime beginTime = LocalDateTime.of(localDate, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(localDate, LocalTime.MAX);
            Map map=new HashMap<>();
            map.put("beginTime",beginTime);
            map.put("endTime",endTime);
            Integer newCustom=userMapper.queryNewCustomers(map);
            newCustom = newCustom==null?0:newCustom;
            newCustomers.add(newCustom);
        }

        return UserReportVO.builder()
                .dateList(StringUtils.join(dateList,","))
                .newUserList(StringUtils.join(newCustomers,","))
                .totalUserList(StringUtils.join(userMapper.queryAll(),","))
                .build();
    }

    @Override
    public OrderReportVO getOrdersStatistics(LocalDate begin, LocalDate end) {


        ArrayList<LocalDate> localDates = dataInsert(begin, end);
        ArrayList<Integer> validOrderCountList = new ArrayList<>();//今日有效订单
        ArrayList<Integer> todayOrderCountList = new ArrayList<>();//今日总订单
        Double orderCompletionRate;
        for (LocalDate localDate : localDates) {
            LocalDateTime beginTime = LocalDateTime.of(localDate, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(localDate, LocalTime.MAX);
            Map map=new HashMap<>();
            map.put("begin",beginTime);
            map.put("end",endTime);
            map.put("status",Orders.COMPLETED);
            Integer validOrderCount = orderMapper.todayValidOrderCount(map);
            validOrderCount= validOrderCount==null?0:validOrderCount;
            validOrderCountList.add(validOrderCount);
            Integer todayOrderCount=orderMapper.todayOrderCount(beginTime,endTime);
            todayOrderCount= todayOrderCount==null?0:todayOrderCount;
            todayOrderCountList.add(todayOrderCount);
        }
        Integer totalOrderCount=orderMapper.queryTotalOrderCount();//总订单
        totalOrderCount= totalOrderCount==null?0:totalOrderCount;

        Integer totalValidOrderCount=orderMapper.queryTotalValidOrderCount();//总有效订单
        totalValidOrderCount= totalValidOrderCount==null?0:totalValidOrderCount;

        if (totalOrderCount==0) {
            orderCompletionRate=0.0;
        }else
         orderCompletionRate=totalValidOrderCount/1.0/totalOrderCount;
        return OrderReportVO.builder()
                .totalOrderCount(totalOrderCount)//订单总数
                .orderCountList(StringUtils.join(todayOrderCountList,","))
                .validOrderCount(totalValidOrderCount)//有效订单数
                .dateList(StringUtils.join(localDates,","))
                .validOrderCountList(StringUtils.join(validOrderCountList,","))
                .orderCompletionRate(orderCompletionRate).build();
    }
    //        //日期，以逗号分隔，例如：2022-10-01,2022-10-02,2022-10-03
//        private String dateList;
//        //每日订单数，以逗号分隔，例如：260,210,215
//        private String orderCountList;
//        //每日有效订单数，以逗号分隔，例如：20,21,10
//        private String validOrderCountList;
//        //订单总数
//        private Integer totalOrderCount;
//        //有效订单数   Orders.COMPLETED
//        private Integer validOrderCount;
//        //订单完成率
//        private Double orderCompletionRate;

    @Override
    public SalesTop10ReportVO getTop10(LocalDate begin, LocalDate end) {
        LocalDateTime beginTime = LocalDateTime.of(begin, LocalTime.MIN);
        LocalDateTime endTime = LocalDateTime.of(end, LocalTime.MAX);
        List<GoodsSalesDTO> goodsSalesDTOList = orderMapper.getSalesTop10(beginTime, endTime);
        System.out.println(goodsSalesDTOList);
//---------------------------------
        String nameList = StringUtils.join(goodsSalesDTOList.stream().map(GoodsSalesDTO::getName).collect(Collectors.toList()),",");
        String numberList = StringUtils.join(goodsSalesDTOList.stream().map(GoodsSalesDTO::getNumber).collect(Collectors.toList()),",");

        return SalesTop10ReportVO.builder()
                .nameList(nameList)
                .numberList(numberList)
                .build();
    }

    @Override
    public void exportBusinessData(HttpServletResponse response) {
        LocalDate begin = LocalDate.now().minusDays(30);
        LocalDate end = LocalDate.now().minusDays(1);
        //查询概览运营数据，提供给Excel模板文件
        BusinessDataVO businessData = workSpaceService.getBusinessData(LocalDateTime.of(begin,LocalTime.MIN), LocalDateTime.of(end, LocalTime.MAX));
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("template/运营数据报表模板.xlsx");
        try {
            //基于提供好的模板文件创建一个新的Excel表格对象
            XSSFWorkbook excel = new XSSFWorkbook(inputStream);
            //获得Excel文件中的一个Sheet页
            XSSFSheet sheet = excel.getSheet("Sheet1");

            sheet.getRow(1).getCell(1).setCellValue(begin + "至" + end);
            //获得第4行
            XSSFRow row = sheet.getRow(3);
            //获取单元格
            row.getCell(2).setCellValue(businessData.getTurnover());
            row.getCell(4).setCellValue(businessData.getOrderCompletionRate());
            row.getCell(6).setCellValue(businessData.getNewUsers());
            row = sheet.getRow(4);
            row.getCell(2).setCellValue(businessData.getValidOrderCount());
            row.getCell(4).setCellValue(businessData.getUnitPrice());
            for (int i = 0; i < 30; i++) {
                LocalDate date = begin.plusDays(i);
                //准备明细数据
                businessData = workSpaceService.getBusinessData(LocalDateTime.of(date,LocalTime.MIN), LocalDateTime.of(date, LocalTime.MAX));
                row = sheet.getRow(7 + i);
                row.getCell(1).setCellValue(date.toString());
                row.getCell(2).setCellValue(businessData.getTurnover());
                row.getCell(3).setCellValue(businessData.getValidOrderCount());
                row.getCell(4).setCellValue(businessData.getOrderCompletionRate());
                row.getCell(5).setCellValue(businessData.getUnitPrice());
                row.getCell(6).setCellValue(businessData.getNewUsers());
            }
            //通过输出流将文件下载到客户端浏览器中
            ServletOutputStream out = response.getOutputStream();
            excel.write(out);
            //关闭资源
            out.flush();
            out.close();
            excel.close();

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private ArrayList<LocalDate> dataInsert(LocalDate begin, LocalDate end) {
        ArrayList<LocalDate> dateList = new ArrayList<>();
        dateList.add(begin);

        while (!begin.equals(end)) {
            begin = begin.plusDays(1);//日期计算，获得指定日期后1天的日期
            dateList.add(begin);
        }
        return dateList;
    }
}
