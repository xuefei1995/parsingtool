package com.xuefei.service.deal;

import com.xuefei.pojo.HNDownloadData;
import com.xuefei.pojo.Param;
import com.xuefei.service.read.impl.ReadHNExcel;
import com.xuefei.util.DbUtil;
import com.xuefei.util.IDUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HNDownloadDealData {

    public void createData(Param param) {

        //读取Excel文件内容
        List<HNDownloadData> hnDownloadData = new ReadHNExcel().readDBF(param.getSrcPath());

        //生成db文件
        buildDB(param.getOutPath(), hnDownloadData);

    }

    private void buildDB(String outPath, List<HNDownloadData> hnDownloadData) {

        List<Map<String, Object>> studentInfos = new ArrayList<>();
        List<Map<String, Object>> exam = new ArrayList<>();
        int i = 1;
        for (HNDownloadData data : hnDownloadData) {
            Map<String, Object> studentInfo = new HashMap<String, Object>();
            studentInfo.put("collectfacephoto", data.getFacePhoto());
            String studentId = IDUtil.uuid();
            studentInfo.put("studentid", studentId);
            studentInfo.put("examcardnum", data.getIdCardNum());
            studentInfo.put("name", data.getName());
            studentInfo.put("sex", data.getSex());
            studentInfo.put("idcardnum", data.getIdCardNum());
            studentInfo.put("facefeature", null);
            studentInfo.put("physicalid", null);
            studentInfo.put("firstfingerfeature", null);
            studentInfo.put("secondfingerfeature", null);
            studentInfo.put("firstfingerimg", null);
            studentInfo.put("secondfingerimg", null);
            studentInfos.add(studentInfo);

            Map<String, Object> examData = new HashMap<String, Object>();
            examData.put("studentid", studentId);
            examData.put("idcardnum", data.getIdCardNum());
            examData.put("roomnum", "1");
            examData.put("roomcode", "1");
            examData.put("roomsession", "1");
            examData.put("roomsessioncode", "11");
            examData.put("subjectcode", "1");
            examData.put("subject", "公务员考试");
            examData.put("seatnum", i);
            examData.put("starttime", "2021-11-25 09:00:00");
            examData.put("endtime", "2021-11-26 18:00:00");
            exam.add(examData);

            i++;
        }
        if (hnDownloadData.size() == 0) {
            return;
        }

        DbUtil.createDB(hnDownloadData.get(0).getOrgCode(), "20211125", outPath, studentInfos, exam);

    }
}
