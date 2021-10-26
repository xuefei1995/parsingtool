package com.xuefei.service.read.impl;

import com.linuxense.javadbf.DBFReader;
import com.xuefei.pojo.DBFData;
import com.xuefei.util.FileUtil;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class ReadDBF {

    public List<DBFData> readDBF(String readPath) {
        List<DBFData> list = new ArrayList<>();
        FileInputStream fileInputStream = FileUtil.getFileInputStream(readPath);

        DBFReader reader = new DBFReader(fileInputStream);

        Object[] rowValues;
        while ((rowValues = reader.nextRecord()) != null) {
            DBFData data = new DBFData();
            data.setZkzh(String.valueOf(rowValues[6]));
            data.setKsxm(String.valueOf(rowValues[7]));
            data.setZjhm(String.valueOf(rowValues[8]));
            data.setKmdm(String.valueOf(rowValues[9]));
            data.setKmmc(String.valueOf(rowValues[10]));
            data.setKcdm(String.valueOf(rowValues[11]));
            data.setKczwh(String.valueOf(rowValues[12]));
            list.add(data);
        }

        FileUtil.closeInputStream(fileInputStream);

        return list;
    }
}
