package com.e1i5.backend.domain.user.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProfileServiceImplTest {
    static class WeekData {
        private String x;
        private String y;
        private List<DataPoint> data;

        public WeekData() {
            this.data = new ArrayList<>();
        }

        public String getX() {
            return x;
        }

        public void setX(String x) {
            this.x = x;
        }

        public String getY() {
            return y;
        }

        public void setY(String y) {
            this.y = y;
        }

        public List<DataPoint> getData() {
            return data;
        }

        public void addData(DataPoint dataPoint) {
            this.data.add(dataPoint);
        }

    }

    static class DataPoint {
        private String x;
        private int y;

        public DataPoint(String x, int y) {
            this.x = x;
            this.y = y;
        }

        public String getX() {
            return x;
        }

        public int getY() {
            return y;
        }
    }

    public static List<WeekData> generateStatisticsData() {
        List<WeekData> statisticsData = new ArrayList<>();

        // 오늘부터 364일전까지의 날짜를 생성
        LocalDate currentDate = LocalDate.now();
        LocalDate startDate = currentDate.minusDays(364);

        // DateTimeFormatter를 사용하여 월을 포맷팅
        DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("MM");

        // 요일 배열
        String[] daysOfWeek = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};

        int value = 1;
        while (startDate.isBefore(currentDate)) {
            WeekData weekData = new WeekData();

            // 월을 x축으로 추가
            weekData.setX(startDate.format(monthFormatter));

            // 요일을 y축으로 추가
            weekData.setY(daysOfWeek[startDate.getDayOfWeek().getValue()]);

            // 데이터를 추가
            for (int i = 0; i < 7; i++) {
                // 임의의 데이터를 생성 (여기에서는 랜덤으로 10부터 50까지의 값)
                int randomValue = value++;
                weekData.addData(new DataPoint(startDate.toString(), randomValue));
            }

            // 생성된 주간 데이터를 리스트에 추가
            statisticsData.add(weekData);

            // 다음 날짜로 이동
            startDate = startDate.plusDays(1);
        }

        return statisticsData;
    }

    @Test
    public void test() {
        List<WeekData> weekData = generateStatisticsData();
        for (WeekData w: weekData) {
            System.out.println(w);
        }
    }
}