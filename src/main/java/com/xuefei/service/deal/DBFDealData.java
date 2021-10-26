package com.xuefei.service.deal;

import com.xuefei.consts.SSMap;
import com.xuefei.pojo.DBFData;
import com.xuefei.pojo.Param;
import com.xuefei.service.read.impl.ReadDBF;
import com.xuefei.util.FileUtil;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class DBFDealData {

    public void createData(Param param) throws Exception {

        //读取DBF文件内容
        List<DBFData> dbfData = new ReadDBF().readDBF(param.getSrcPath());

        //生成excel表格
        buildExcel(param.getOutPath(), dbfData);
    }

    private void buildExcel(String outPath, List<DBFData> dbfData) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();

        buildTask(workbook);

        buildScene(workbook, dbfData);

        buildSubject(workbook, dbfData);

        buildStudent(workbook, dbfData);

        buildStudentArrange(workbook, dbfData);

        //生成excel
        workbook.write(FileUtil.getOutputStream(outPath + "/校级平台初始化数据.xlsx"));
        workbook.close();
    }

    private void buildStudentArrange(XSSFWorkbook workbook, List<DBFData> dbfData) {
        //创建表格
        XSSFSheet sheet = workbook.createSheet("学生编排");
        //定义头标题
        String[] title = {"任务编号", "身份证号", "姓名", "准考证号", "考场号", "座位号", "科目编号"};
        //创建标题
        XSSFRow titleRow = sheet.createRow(0);
        for (int i = 0; i < title.length; i++) {
            titleRow.createCell(i).setCellValue(title[i]);
        }

        for (int i = 0; i < dbfData.size(); i++) {
            DBFData data = dbfData.get(i);
            XSSFRow row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue("20211030");
            row.createCell(1).setCellValue(data.getZjhm());
            row.createCell(2).setCellValue(data.getKsxm());
            row.createCell(3).setCellValue(data.getZkzh());
            row.createCell(4).setCellValue(data.getKcdm());
            row.createCell(5).setCellValue(data.getKczwh());
            row.createCell(6).setCellValue(data.getKmdm());
        }
    }

    private void buildStudent(XSSFWorkbook workbook, List<DBFData> dbfData) {
        //创建表格
        XSSFSheet sheet = workbook.createSheet("学生");
        //定义头标题
        String[] title = {"任务编号", "姓名", "性别", "身份证号", "人脸照片名称", "身份证照片名称"
                , "右手指纹照片名称", "右手指纹特征值", "左手指纹照片名称", "左手指纹特征值"};
        //创建标题
        XSSFRow titleRow = sheet.createRow(0);
        for (int i = 0; i < title.length; i++) {
            titleRow.createCell(i).setCellValue(title[i]);
        }

        List<DBFData> collect = dbfData.stream().filter(distinctByName(DBFData::getZjhm)).collect(Collectors.toList());
        for (int i = 0; i < collect.size(); i++) {
            DBFData data = collect.get(i);
            XSSFRow row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue("20211030");
            row.createCell(1).setCellValue(data.getKsxm());

            int sexCode = 0;
            if (data.getZjhm().length() == 18) {
                sexCode = Integer.parseInt(data.getZjhm().substring(16, 17));
            }
            row.createCell(2).setCellValue(sexCode % 2 == 0 ? "女" : "男");

            row.createCell(3).setCellValue(data.getZjhm());
            row.createCell(4).setCellValue(data.getZjhm() + ".jpg");
            row.createCell(5).setCellValue("");
            row.createCell(5).setCellValue("");
            row.createCell(6).setCellValue("");
            row.createCell(7).setCellValue("");
            row.createCell(8).setCellValue("");
        }

    }

    private void buildSubject(XSSFWorkbook workbook, List<DBFData> dbfData) {
        //创建表格
        XSSFSheet sheet = workbook.createSheet("科目");
        //定义头标题
        String[] title = {"任务编号", "科目编号", "科目名称", "开始时间", "结束时间", "关联场次编号"};
        //创建标题
        XSSFRow titleRow = sheet.createRow(0);
        for (int i = 0; i < title.length; i++) {
            titleRow.createCell(i).setCellValue(title[i]);
        }
        Map<String, DBFData> collect = dbfData.stream().filter(distinctByName(DBFData::getKmdm)).collect(Collectors.toMap(DBFData::getKmdm, DBFData -> DBFData));
        Set<String> keys = collect.keySet();
        int i = 1;
        for (String kmdm : keys) {
            DBFData data = collect.get(kmdm);
            XSSFRow row = sheet.createRow(i);
            row.createCell(0).setCellValue("20211030");
            row.createCell(1).setCellValue(kmdm);
            row.createCell(2).setCellValue(data.getKmmc());

            SSMap.SSData ssData = SSMap.getSSMap().get(kmdm);
            row.createCell(3).setCellValue(ssData.getStartTime());
            row.createCell(4).setCellValue(ssData.getEndTime());
            row.createCell(5).setCellValue(ssData.getSceneCode());
            i++;
        }
    }

    private void buildScene(XSSFWorkbook workbook, List<DBFData> dbfData) {
        //创建表格
        XSSFSheet sheet = workbook.createSheet("场次");
        //定义头标题
        String[] title = {"任务编号", "场次编号", "场次名称", "开始时间", "结束时间"};
        //创建标题
        XSSFRow titleRow = sheet.createRow(0);
        for (int i = 0; i < title.length; i++) {
            titleRow.createCell(i).setCellValue(title[i]);
        }
        List<String> collect = dbfData.stream().map(DBFData::getKmdm).distinct().collect(Collectors.toList());
        Set<String> sceneSet = new HashSet<>();

        for (int i = 0; i < collect.size(); i++) {
            SSMap.SSData ssData = SSMap.getSSMap().get(collect.get(i));
            if (sceneSet.contains(ssData.getSceneCode())) {
                continue;
            }
            XSSFRow row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue("20211030");
            row.createCell(1).setCellValue(ssData.getSceneCode());
            row.createCell(2).setCellValue(ssData.getSceneName());
            row.createCell(3).setCellValue(ssData.getStartTime());
            row.createCell(4).setCellValue(ssData.getEndTime());
            sceneSet.add(ssData.getSceneCode());
        }
    }

    private void buildTask(XSSFWorkbook workbook) {
        //创建表格
        XSSFSheet sheet = workbook.createSheet("任务");
        //定义头标题
        String[] title = {"任务编号", "任务名称", "开始时间", "结束时间", "验证类型", "描述"};
        //创建标题
        XSSFRow titleRow = sheet.createRow(0);
        for (int i = 0; i < title.length; i++) {
            titleRow.createCell(i).setCellValue(title[i]);
        }
        for (int i = 0; i < 1; i++) {
            XSSFRow row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue("20211030");
            row.createCell(1).setCellValue("教师资格考试");
            row.createCell(2).setCellValue("2021-10-30 00:00:00");
            row.createCell(3).setCellValue("2021-10-30 23:59:59");
            row.createCell(4).setCellValue("1");
            row.createCell(5).setCellValue("教师资格考试");
        }
    }

    private <T> Predicate<T> distinctByName(Function<? super T , Object> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

}
