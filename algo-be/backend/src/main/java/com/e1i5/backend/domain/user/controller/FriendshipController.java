package com.e1i5.backend.domain.user.controller;

import com.e1i5.backend.domain.user.dto.response.FriendListInterfaceResponse;
import com.e1i5.backend.domain.user.service.FriendService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/v1/friendship")
public class FriendshipController {

    @Autowired
    private FriendService friendService;

    /**
     * 친구 추가
     * @param nickname
     * @return 0: 로그인한 사용자와 친구 사용자가 같음
     *         1: 두 사용자가 친구
     *         2: 두 사용자가 친구 아님
     */
    @Operation(summary = "친구 추가와 삭제")
    @GetMapping()
    public ResponseEntity<Integer> makeFriendship(@RequestParam String nickname, Principal principal) {
        int userId = Integer.parseInt(principal.getName());
        int checkFriendship = friendService.makeFriendship(userId, nickname);

        return new ResponseEntity<>(checkFriendship, HttpStatus.OK);
    }

    /**
     * 친구 목록 불러오기
     * @return 닉네임, 티어, 점수
     */
    @Operation(summary = "친구 목록 불러오기")
    @GetMapping("/friends")
    public ResponseEntity<List<FriendListInterfaceResponse>> getFriendsList(Principal principal) {
        // 친구 목록 불러오기는 로그인한 사용자만 볼 수 있는 건지
        int userId = Integer.parseInt(principal.getName());
        List<FriendListInterfaceResponse> testFriends = friendService.listFriendship(userId);

        return new ResponseEntity<>(testFriends, HttpStatus.OK);
    }

    /**
     * 친구 검색
     * @param nickname
     * @return 닉네임, 티어, 점수
     */
    @Operation(summary = "친구 검색")
    @GetMapping("/search")
    public ResponseEntity<List<FriendListInterfaceResponse>> searchUser(@RequestParam("nickname") String nickname) {
        List<FriendListInterfaceResponse> friends = friendService.searchNickname(nickname);
        return new ResponseEntity<>(friends, HttpStatus.OK);
    }

    @Operation(summary = "친구 여부 확인")
    @GetMapping("/{nickname}")
    public ResponseEntity<Integer> checkFriendship(@PathVariable("nickname") String nickname, Principal principal) {
        int userId = Integer.parseInt(principal.getName());
        int checkFriendship = friendService.checkFriendship(userId, nickname);
        return new ResponseEntity<>(checkFriendship, HttpStatus.OK);
    }
}
