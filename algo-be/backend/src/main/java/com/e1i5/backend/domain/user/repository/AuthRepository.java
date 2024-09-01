package com.e1i5.backend.domain.user.repository;

import com.e1i5.backend.domain.user.dto.response.FriendListInterfaceResponse;
import com.e1i5.backend.domain.user.dto.response.NicknameResponse;
import com.e1i5.backend.domain.user.dto.response.UserIdResponse;
import com.e1i5.backend.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AuthRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
    boolean existsByNickname(String nickname);
    Optional<User> findByNickname(String nickname);
    Optional<UserIdResponse> findUserIdByNickname(String nickname);
    Optional<NicknameResponse> findByUserId(int userId);
    Optional<User> findUserByUserId(int userId);
    List<FriendListInterfaceResponse> findByNicknameContaining(String nickname);
    LocalDate findLastSolvedDateByUserId(int userId);

}
