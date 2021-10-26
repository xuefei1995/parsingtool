package com.xuefei.service.read;

import com.xuefei.pojo.SQLiteData;

import java.util.List;

public interface ReadSQLite {

    String getVersion();

    List<SQLiteData> readDateList(String readPath);

}
