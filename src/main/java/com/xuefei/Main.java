package com.xuefei;

import com.xuefei.consts.ModelConst;
import com.xuefei.gui.impl.DBFWindow;
import com.xuefei.gui.Window;
import com.xuefei.gui.impl.HNDownloadWindow;
import com.xuefei.gui.impl.SQLiteWindow;

import java.util.HashMap;
import java.util.Map;

public class Main {

    //设置当前模式
    private static final String model = ModelConst.SQLITE_MODEL;

    private static final Map<String, Window> map = new HashMap<>();

    static {
        map.put(ModelConst.SQLITE_MODEL, new SQLiteWindow());
        map.put(ModelConst.DBF_MODEL, new DBFWindow());
        map.put(ModelConst.HN_MODEL, new HNDownloadWindow());
    }

    public static void main(String[] args) {
        map.get(model).createWindow();
    }
}



