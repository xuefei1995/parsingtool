package com.xuefei.service.read.impl;

import com.xuefei.pojo.HNDownloadData;
import com.xuefei.util.Base64Util;
import com.xuefei.util.FileUtil;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReadHNExcel {

    public List<HNDownloadData> readDBF(String readPath) {
        List<HNDownloadData> list = new ArrayList<>();
        try {
            Workbook workbook = new XSSFWorkbook(FileUtil.getFileInputStream(readPath + File.separator + "1.xlsx"));
            Sheet sheetAt = workbook.getSheetAt(0);
            //得到总行数
            int lastRowNum = sheetAt.getLastRowNum();
            for (int i = 1; i <= lastRowNum; i++) {
                Row row = sheetAt.getRow(i); //获得该行数据
                HNDownloadData data = new HNDownloadData();
                data.setOrgCode(String.valueOf(getCellFormatValue(row.getCell(0))));
                data.setIdCardNum(String.valueOf(getCellFormatValue(row.getCell(1))));
                data.setName(String.valueOf(getCellFormatValue(row.getCell(2))));
                int sexCode = 0;
                if (String.valueOf(getCellFormatValue(row.getCell(1))).length() == 18) {
                    sexCode = Integer.parseInt(String.valueOf(getCellFormatValue(row.getCell(1))).substring(16, 17));
                }
                data.setSex(sexCode % 2 == 0 ? "女" : "男");
                data.setFacePhoto(Base64Util.inputStreamToBase64(FileUtil.getFileInputStream(readPath + File.separator + data.getIdCardNum() + ".jpg")));
                list.add(data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    private Object getCellFormatValue(Cell cell) {
        Object cellvalue = "";
        if (cell != null) {
            // 判断当前Cell的Type
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_NUMERIC:// 如果当前Cell的Type为NUMERIC
                case Cell.CELL_TYPE_FORMULA: {
                    // 判断当前的cell是否为Date
                    if (DateUtil.isCellDateFormatted(cell)) {
                        // 如果是Date类型则，转化为Data格式
                        // data格式是带时分秒的：2013-7-10 0:00:00
                        // cellvalue = cell.getDateCellValue().toLocaleString();
                        // data格式是不带带时分秒的：2013-7-10
                        Date date = cell.getDateCellValue();
                        cellvalue = date;
                    } else {// 如果是纯数字
                        // 取得当前Cell的数值
                        cellvalue = String.valueOf(cell.getNumericCellValue());
                    }
                    break;
                }
                case Cell.CELL_TYPE_STRING:// 如果当前Cell的Type为STRING
                    // 取得当前的Cell字符串
                    cellvalue = cell.getRichStringCellValue().getString();
                    break;
                default:// 默认的Cell值
                    cellvalue = "";
            }
        } else {
            cellvalue = "";
        }
        return cellvalue;
    }
}
