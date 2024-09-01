package com.e1i5.backend.domain.user.service;

import com.e1i5.backend.domain.user.dto.response.FriendListInterfaceResponse;
import com.e1i5.backend.domain.user.dto.response.FriendListResponse;
import com.e1i5.backend.domain.user.entity.Friend;
import com.e1i5.backend.domain.user.entity.User;
import com.e1i5.backend.domain.user.repository.AuthRepository;
import com.e1i5.backend.domain.user.repository.FriendRepository;
import com.e1i5.backend.global.error.GlobalBaseException;
import com.e1i5.backend.global.error.GlobalErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FriendServiceImpl implements FriendService{

    @Autowired
    private FriendRepository friendRepo;
    @Autowired
    private AuthRepository authRepo;

    @Override
    public int makeFriendship(int userId, String friendNickname) {
        User user = getUserByUserId(userId);
        User friend = getUserByNickname(friendNickname);

        Optional<Friend> friendship = friendRepo.findByUserAndFriend(user, friend);

        if (friendship.isPresent()) {
//            friendRepo.deleteByUserAndFriend(user, friend); //401 why?
            friendRepo.delete(friendship.get());
        } else {
            Friend friendEntity = Friend.builder()
                    .user(user)
                    .friend(friend)
                    .build();

            friendRepo.save(friendEntity);
        }
        return checkFriendship(user, friend);
    }

    /**
     * 사용자간의 친구 여부 확인
     * @param user 로그인한 사용자
     * @param friend 친구 요청 대상
     * @return 0: 로그인한 사용자와 친구 사용자가 같음
     *         1: 두 사용자가 친구
     *         2: 두 사용자가 친구 아님
     */
    @Override
    public int checkFriendship(User user, User friend) {
        if (user.getUserId() == friend.getUserId()) return 0;

        boolean friendship = friendRepo.existsByUserAndFriend(user, friend);
        if (friendship) return 1;
        else return 2;
    }

    @Override
    public int checkFriendship(int userId, String friendNickname) {
        User user = getUserByUserId(userId);
        User friend = getUserByNickname(friendNickname);

        if (user.getUserId() == friend.getUserId()) return 0;

        boolean friendship = friendRepo.existsByUserAndFriend(user, friend);
        if (friendship) return 1;
        else return 2;
    }

    @Override
    public List<FriendListInterfaceResponse> listFriendship(int userId) {

        return friendRepo.getFriendInfoByFriendId(userId);
    }

    @Override
    public List<FriendListInterfaceResponse> searchNickname(String nickname) {

        return authRepo.findByNicknameContaining(nickname);
    }

    private User getUserByNickname(String nickname) {
        return authRepo.findByNickname(nickname)
                .orElseThrow(() -> new GlobalBaseException(GlobalErrorCode.USER_NOT_FOUND));
    }

    private User getUserByUserId(int userId) {
        return authRepo.findById(userId)
                .orElseThrow(() -> new GlobalBaseException(GlobalErrorCode.USER_NOT_FOUND));
    }
}
