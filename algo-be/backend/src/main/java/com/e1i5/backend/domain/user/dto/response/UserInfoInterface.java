package com.e1i5.backend.domain.user.dto.response;

import com.e1i5.backend.domain.user.entity.ProfileFileInfo;

/**
 *  사용자 정보 불러오는 인터페이스 -> UserInfoResponse으로 변환
 */
public interface UserInfoInterface {
    String getTier();
    Integer getUserId();
    Integer getUserScore();
    ProfileFileInfo getUserProfile();
    Long getProblemCount();
}
