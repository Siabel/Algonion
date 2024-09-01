package com.e1i5.backend.domain.user.service;

import com.e1i5.backend.domain.user.dto.response.FriendListInterfaceResponse;
import com.e1i5.backend.domain.user.dto.response.FriendListResponse;
import com.e1i5.backend.domain.user.entity.User;

import java.util.List;

public interface FriendService {
    /**
     * 2. 친구 취소
     */
    int makeFriendship(int userId, String friendNickname);
    List<FriendListInterfaceResponse> listFriendship(int userId);
    List<FriendListInterfaceResponse> searchNickname(String nickname);
//    void cancelFriendship(int userId, String friendNickname);
    int checkFriendship(User user, User friend);
    int checkFriendship(int userId, String friendNickname);
}
