package com.xuefei.pojo;

import lombok.Data;

import java.util.Objects;

@Data
public class SQLiteData {

    // 考生姓名
    private String name;
    // 身份证号
    private String idcardnum;
    // 考场号
    private String roomnum;
    // 考生号
    private String examcardnum;
    // 照片路径
    private String photoPath;
    // base64人脸照片
    private String scenePhoto;
    // 人脸验证结果
    private int facecheckresult;
    // 指纹验证结果
    private int fingercheckresult;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SQLiteData SQLiteData = (SQLiteData) o;
        return Objects.equals(idcardnum, SQLiteData.idcardnum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idcardnum);
    }
}
