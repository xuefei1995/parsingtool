package com.xuefei.util;

import java.io.File;
import java.sql.*;
import java.util.List;
import java.util.Map;

public class DbUtil {

    public static void createDB(String orgId, String taskId, String path, List<Map<String, Object>> collection, List<Map<String, Object>> exam) {
        String tableName1 = "students";   //身份信息采集表名
        String tableName2 = "examinfo";   //考试信息数据表名
        String tableName3 = "taskinfo";   //考试任务信息数据表名
        File dbfile = new File(path + File.separator + "download.db");
        //如果本地存在改文件，则删除掉，因为需要生成文件的前提是服务器上没有，所以需要生成最新的，在此之前必须将原有的旧文件删除掉，防止数据还是以前的版本
        if (dbfile.exists()) {
            dbfile.delete();
        }
        Connection conn = null;
        Statement stat = null;
        PreparedStatement prest1 = null;
        PreparedStatement prest2 = null;
        PreparedStatement prest3 = null;
        try {
            dbfile.createNewFile();
            conn = DriverManager.getConnection("jdbc:sqlite:" + dbfile.getAbsolutePath());
            stat = conn.createStatement();
            //首先清空数据表
            stat.executeUpdate("drop table if exists " + tableName1);
            stat.executeUpdate("drop table if exists " + tableName2);
            stat.executeUpdate("drop table if exists " + tableName3);
            stat.executeUpdate("CREATE TABLE " + tableName1 + " (examcardnum text,name text,sex text,idcardnum text,collectfacephoto text,physicalid text,firstfingerfeature text,secondfingerfeature text,facefeature text,firstfingerimg text,secondfingerimg text,studentid text)");//学生基础信息表-编排表关联采集表查询
            stat.executeUpdate("CREATE TABLE " + tableName2 + " (idcardnum text,roomnum text,roomsession text,subject text,subjectcode text,seatnum text,starttime text,endtime text,studentid text,roomsessioncode text,roomcode text)");//考试信息表(考生编排表)
            stat.executeUpdate("CREATE TABLE " + tableName3 + " (orgid text,taskid text,orgcode text,orgname text,studentdataversion int)");//任务信息表
            // 创建索引
            stat.executeUpdate("CREATE INDEX idcardNum ON " + tableName1 + "(idcardnum)");

            //批量插入数据
            prest1 = conn.prepareStatement("INSERT INTO " + tableName1 + " VALUES(?,?,?,?,?,?,?,?,?,?,?,?)");
            for (Map<String, Object> po : collection) {
                prest1.setString(1, po.get("examcardnum") + "");
                prest1.setString(2, po.get("name") + "");
                prest1.setString(3, po.get("sex") + "");
                prest1.setString(4, po.get("idcardnum") + "");
                prest1.setString(5, po.get("collectfacephoto") + "");
                prest1.setString(6, po.get("physicalid") + "");
                prest1.setString(7, po.get("firstfingerfeature") + "");
                prest1.setString(8, po.get("secondfingerfeature") + "");
                prest1.setString(9, po.get("facefeature") + "");
                prest1.setString(10, po.get("firstfingerimg") + "");
                prest1.setString(11, po.get("secondfingerimg") + "");
                prest1.setString(12, po.get("studentid") + "");
                prest1.addBatch();
            }
            conn.setAutoCommit(false);
            prest1.executeBatch();
            conn.setAutoCommit(true);

            //编排表
            prest2 = conn.prepareStatement("INSERT INTO " + tableName2 + " VALUES(?,?,?,?,?,?,?,?,?,?,?)");
            for (Map<String, Object> po : exam) {
                prest2.setString(1, po.get("idcardnum") + "");
                prest2.setString(2, po.get("roomnum") + "");
                prest2.setString(3, po.get("roomsession") + "");
                prest2.setString(4, po.get("subject") + "");
                prest2.setString(5, po.get("subjectcode") + "");
                prest2.setString(6, po.get("seatnum") + "");
                prest2.setString(7, po.get("starttime") + "");
                prest2.setString(8, po.get("endtime") + "");
                prest2.setString(9, po.get("studentid") + "");
                prest2.setString(10, po.get("roomsessioncode") + "");
                prest2.setString(11, po.get("roomcode") + "");
                prest2.addBatch();
            }
            conn.setAutoCommit(false);
            prest2.executeBatch();
            conn.setAutoCommit(true);

            prest3 = conn.prepareStatement("INSERT INTO " + tableName3 + " VALUES(?,?,?,?,?)");
            prest3.setString(1, orgId + "");
            prest3.setString(2, taskId + "");
            prest3.setString(3, orgId + "");
            prest3.setString(4, orgId + "");
            prest3.setString(5, "1");
            conn.setAutoCommit(false);
            prest3.execute();
            conn.setAutoCommit(true);

        } catch (Exception e) {
            dbfile.delete();
            e.printStackTrace();
        } finally {
            IOUtil.closePreparedStatement(prest1);
            IOUtil.closePreparedStatement(prest2);
            IOUtil.closePreparedStatement(prest3);
            IOUtil.closeConnection(conn);
        }
    }
}
