package com.xuefei.service.read;

import com.xuefei.pojo.SQLiteData;
import com.xuefei.pojo.Param;
import com.xuefei.service.read.impl.ReadSQLite3;
import com.xuefei.service.read.impl.ReadSQLite4;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReadSQLiteFactory {

    private Map<String, ReadSQLite> map = new HashMap<>();

    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ReadSQLiteFactory() {
        ReadSQLite3 readDB3 = new ReadSQLite3();
        ReadSQLite4 readDB4 = new ReadSQLite4();
        map.put(readDB3.getVersion(), readDB3);
        map.put(readDB4.getVersion(), readDB4);
    }

    public List<SQLiteData> readDb(Param param) {
        ReadSQLite readSQLite = map.get(param.getVersion());
        return readSQLite.readDateList(param.getSrcPath());
    }


}
