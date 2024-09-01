package com.e1i5.backend.domain.user.dto.response;

import java.util.List;

public class StreakResponse {

    private String chartName;
    private String seriesName;
    private List<String> categories;
    private List<String> series;

    // Getters and setters

    @Override
    public String toString() {
        return "streakDate: {" +
                "options: {" +
                "   chart: {" +
                "        id: " + chartName +
                "    }, " +
                "    xaxis" + categories +
                "}," +
                "series: " + series +
                '}';
    }
}
//                "   {" +
//                "       name: " +  seriesName +
//                "       data: " + series +
