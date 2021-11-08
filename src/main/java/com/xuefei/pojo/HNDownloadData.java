package com.xuefei.pojo;

import lombok.Data;

@Data
public class HNDownloadData {

    //机构代码
    private String orgCode;
    //身份证号
    private String idCardNum;
    //姓名
    private String name;
    //性别
    private String sex;
    //人脸照片base64
    private String facePhoto;

}
