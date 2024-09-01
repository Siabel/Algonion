package com.e1i5.backend.global.util;

import java.util.HashMap;
import java.util.Map;

public class AlgoScoreUtil {

    private static final Map<Integer, Integer> BOJ_SCORE_MAP = initBojScoreMap();
    private static final Map<Integer, Integer> PROGRAMMERS_SCORE_MAP = initProgrammersScoreMap();
    private static final Map<String, Integer> SWEA_SCORE_MAP = initSweaScoreMap();

    private static Map<Integer, Integer> initBojScoreMap() {
        System.out.println("////////////////////////////////////////////들어옴////////////////////////");
        Map<Integer, Integer> bojScoreMap = new HashMap<>();
        bojScoreMap.put(0, 0);
        bojScoreMap.put(1, 1);
        bojScoreMap.put(2, 1);
        bojScoreMap.put(3, 1);
        bojScoreMap.put(4, 1);
        bojScoreMap.put(5, 1);
        bojScoreMap.put(6, 8);
        bojScoreMap.put(7, 8);
        bojScoreMap.put(8, 8);
        bojScoreMap.put(9, 8);
        bojScoreMap.put(10, 8);
        bojScoreMap.put(11, 40);
        bojScoreMap.put(12, 40);
        bojScoreMap.put(13, 40);
        bojScoreMap.put(14, 40);
        bojScoreMap.put(15, 40);
        bojScoreMap.put(16, 200);
        bojScoreMap.put(17, 200);
        bojScoreMap.put(18, 200);
        bojScoreMap.put(19, 200);
        bojScoreMap.put(20, 200);
        bojScoreMap.put(21, 1000);
        bojScoreMap.put(22, 1000);
        bojScoreMap.put(23, 1000);
        bojScoreMap.put(24, 1000);
        bojScoreMap.put(25, 1000);
        bojScoreMap.put(26, 3000);
        bojScoreMap.put(27, 3000);
        bojScoreMap.put(28, 3000);
        bojScoreMap.put(29, 3000);
        bojScoreMap.put(30, 3000);
        return bojScoreMap;
    }

    private static Map<Integer, Integer> initProgrammersScoreMap() {
        System.out.println("//////////////////////programmers////////////////////////////////");
        Map<Integer, Integer> programmersScoreMap = new HashMap<>();
        programmersScoreMap.put(0, 1);
        programmersScoreMap.put(1, 8);
        programmersScoreMap.put(2, 40);
        programmersScoreMap.put(3, 200);
        programmersScoreMap.put(4, 1000);
        programmersScoreMap.put(5, 3000);
        return programmersScoreMap;
    }

    private static Map<String, Integer> initSweaScoreMap() {
        System.out.println("/////////////////////////////////SWEA////////////////////////");
        Map<String, Integer> sweaScoreMap = new HashMap<>();
        sweaScoreMap.put("D1", 1);
        sweaScoreMap.put("D2", 8);
        sweaScoreMap.put("D3", 40);
        sweaScoreMap.put("D4", 40);
        sweaScoreMap.put("D5", 200);
        sweaScoreMap.put("D6", 200);
        sweaScoreMap.put("D7", 1000);
        sweaScoreMap.put("D8", 3000);
        return sweaScoreMap;
    }

    //TODO : 우선 계산을 위해 없는 값이 들어왔을 시 0값 제공
    //TODO : 나중에는 에러 발생하여 아예 못 저장하는 로직 필요
    public static int getBojScore(int problemLevel) {
        return BOJ_SCORE_MAP.getOrDefault(problemLevel, 0);
    }

    public static int getProgrammersScore(int problemLevel) {
        return PROGRAMMERS_SCORE_MAP.getOrDefault(problemLevel, 0);
    }

    public static int getSweaScore(String problemLevel) {
        return SWEA_SCORE_MAP.getOrDefault(problemLevel, 0);
    }
}
