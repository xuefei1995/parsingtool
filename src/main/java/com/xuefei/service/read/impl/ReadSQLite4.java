package com.xuefei.service.read.impl;

import com.xuefei.consts.VersionConst;
import com.xuefei.pojo.SQLiteData;
import com.xuefei.service.read.ReadSQLite;
import com.xuefei.util.FileUtil;
import com.xuefei.util.IOUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ReadSQLite4 implements ReadSQLite {
    @Override
    public String getVersion() {
        return VersionConst.VERSION4;
    }

    @Override
    public List<SQLiteData> readDateList(String readPath) {
        Set<SQLiteData> set = new HashSet<>();
        List<String> files = FileUtil.getFiles(readPath);
        for (String path : files) {
            Connection connection = null;
            PreparedStatement preparedStatement = null;
            ResultSet resultSet = null;
            try {
                connection = DriverManager.getConnection("jdbc:sqlite:" + path);
                preparedStatement = connection.prepareStatement("select name, examcardnum, examRoomId, idcardnum, scenephoto, faceResult, fingerprintResult, gmtVerifyTime from tl_upload_student");
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    SQLiteData SQLiteData = new SQLiteData();
                    SQLiteData.setName(resultSet.getString("name"));
                    SQLiteData.setIdcardnum(resultSet.getString("idcardnum"));
                    SQLiteData.setRoomnum(resultSet.getString("examRoomId"));
                    SQLiteData.setExamcardnum(resultSet.getString("examcardnum"));
                    SQLiteData.setPhotoPath(resultSet.getString("idcardnum") + ".jpg");
                    SQLiteData.setScenePhoto(resultSet.getString("scenephoto"));
                    SQLiteData.setFacecheckresult(resultSet.getInt("faceResult"));
                    SQLiteData.setFingercheckresult(resultSet.getInt("fingerprintResult"));
                    SQLiteData.setVerifytime(resultSet.getString("gmtVerifyTime"));

                    set.add(SQLiteData);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                IOUtil.closeResultSet(resultSet);
                IOUtil.closePreparedStatement(preparedStatement);
                IOUtil.closeConnection(connection);
            }
        }
        return new ArrayList<>(set);
    }
}
