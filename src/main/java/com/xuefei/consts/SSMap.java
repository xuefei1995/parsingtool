package com.xuefei.consts;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

public class SSMap {

    private static String time = "2022-10-29";

    private SSMap() {
    }

    ;
    //key-科目码
    private static Map<String, SSData> map = new HashMap<>();

    static {
        map.put("101", new SSData("11", "场次一", time + " 09:00:00", time + " 11:00:00"));
        map.put("102", new SSData("12", "场次二", time + " 13:00:00", time + " 15:00:00"));
        map.put("201", new SSData("11", "场次一", time + " 09:00:00", time + " 11:00:00"));
        map.put("201A", new SSData("11", "场次一", time + " 09:00:00", time + " 11:00:00"));
        map.put("202", new SSData("12", "场次二", time + " 13:00:00", time + " 15:00:00"));
        map.put("202A", new SSData("12", "场次二", time + " 13:00:00", time + " 15:00:00"));
        map.put("301", new SSData("11", "场次一", time + " 09:00:00", time + " 11:00:00"));
        map.put("301A", new SSData("11", "场次一", time + " 09:00:00", time + " 11:00:00"));
        map.put("302", new SSData("12", "场次二", time + " 13:00:00", time + " 15:00:00"));
        map.put("302A", new SSData("12", "场次二", time + " 13:00:00", time + " 15:00:00"));
        for (int i = 303; i < 318; i++) {
            map.put(String.valueOf(i), new SSData("13", "场次三", time + " 16:00:00", time + " 18:00:00"));
        }
        for (int i = 403; i < 416; i++) {
            map.put(String.valueOf(i), new SSData("13", "场次三", time + " 16:00:00", time + " 18:00:00"));
        }
        map.put("418", new SSData("13", "场次三", time + " 16:00:00", time + " 18:00:00"));
    }

    public static Map<String, SSData> getSSMap() {
        return map;
    }


    @Data
    @AllArgsConstructor
    public static class SSData {
        private String sceneCode;
        private String sceneName;
        private String startTime;
        private String endTime;
    }

}
