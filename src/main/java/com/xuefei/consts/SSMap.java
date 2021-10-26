package com.xuefei.consts;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

public class SSMap {

    private SSMap() {};
    //key-科目码
    private static Map<String, SSData> map = new HashMap<>();

    static {
        map.put("101", new SSData("11", "场次一", "2021-10-30 09:00:00", "2021-10-30 11:00:00"));
        map.put("102", new SSData("12", "场次二", "2021-10-30 13:00:00", "2021-10-30 15:00:00"));
        map.put("201", new SSData("11", "场次一", "2021-10-30 09:00:00", "2021-10-30 11:00:00"));
        map.put("201A", new SSData("11", "场次一", "2021-10-30 09:00:00", "2021-10-30 11:00:00"));
        map.put("202", new SSData("12", "场次二", "2021-10-30 13:00:00", "2021-10-30 15:00:00"));
        map.put("202A", new SSData("12", "场次二", "2021-10-30 13:00:00", "2021-10-30 15:00:00"));
        map.put("301", new SSData("11", "场次一", "2021-10-30 09:00:00", "2021-10-30 11:00:00"));
        map.put("301A", new SSData("11", "场次一", "2021-10-30 09:00:00", "2021-10-30 11:00:00"));
        map.put("302", new SSData("12", "场次二", "2021-10-30 13:00:00", "2021-10-30 15:00:00"));
        map.put("302A", new SSData("12", "场次二", "2021-10-30 13:00:00", "2021-10-30 15:00:00"));
        for (int i = 303; i < 317; i++) {
            map.put(String.valueOf(i), new SSData("13", "场次三", "2021-10-30 16:00:00", "2021-10-30 18:00:00"));
        }
        for (int i = 403; i < 416; i++) {
            map.put(String.valueOf(i), new SSData("13", "场次三", "2021-10-30 16:00:00", "2021-10-30 18:00:00"));
        }
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
