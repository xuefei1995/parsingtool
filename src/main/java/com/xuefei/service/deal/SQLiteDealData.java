package com.xuefei.service.deal;

import com.xuefei.pojo.SQLiteData;
import com.xuefei.pojo.Param;
import com.xuefei.service.read.ReadSQLiteFactory;
import com.xuefei.util.FileUtil;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.util.List;

public class SQLiteDealData {

    public void createData(Param param) throws Exception {

        //读取全部upload.db文件内容
        List<SQLiteData> SQLiteDataList = new ReadSQLiteFactory().readDb(param);

        //生成excel表格
        buildExcel(param.getOutPath(), SQLiteDataList);
    }

    private void buildExcel(String outPath, List<SQLiteData> SQLiteDataList) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        //创建表格
        XSSFSheet sheet = workbook.createSheet();
        //定义头标题
        String[] title = {"姓名", "身份证号", "考场号", "考生号", "验证照片", "总验证结果", "人脸验证结果", "指纹验证结果"};
        //创建标题
        XSSFRow titleRow = sheet.createRow(0);
        for (int i = 0; i < title.length; i++) {
            titleRow.createCell(i).setCellValue(title[i]);
        }
        //构造数据
        for (int i = 0; i < SQLiteDataList.size(); i++) {
            SQLiteData SQLiteData = SQLiteDataList.get(i);
            XSSFRow row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue(SQLiteData.getName());
            row.createCell(1).setCellValue(SQLiteData.getIdcardnum());
            row.createCell(2).setCellValue(SQLiteData.getRoomnum());
            row.createCell(3).setCellValue(SQLiteData.getExamcardnum());
            row.createCell(4).setCellValue(SQLiteData.getIdcardnum() + ".jpg");
            row.createCell(5).setCellValue((SQLiteData.getFacecheckresult() != 1 && SQLiteData.getFingercheckresult() != 1) ? "失败" : "成功");
            row.createCell(6).setCellValue(SQLiteData.getFacecheckresult() == 1 ? "成功" : "失败");
            row.createCell(7).setCellValue(SQLiteData.getFingercheckresult() == 1 ? "成功" : "失败");
            //存储照片
            FileUtil.savePhoto(outPath, SQLiteData.getIdcardnum(), SQLiteData.getScenePhoto(), SQLiteData.getRoomnum());

        }
        //生成excel
        workbook.write(FileUtil.getOutputStream(outPath + "/验证结果读取.xlsx"));
        workbook.close();
    }
}
