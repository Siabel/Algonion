package com.e1i5.backend.domain.user.dto.response;


import lombok.ToString;

public interface StreakResponseInterface {
    String getSubmissionTime();
    long getCount();

}
