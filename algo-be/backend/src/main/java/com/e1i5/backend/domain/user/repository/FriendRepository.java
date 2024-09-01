package com.e1i5.backend.domain.user.repository;

import com.e1i5.backend.domain.user.dto.response.FriendListInterfaceResponse;
import com.e1i5.backend.domain.user.entity.Friend;
import com.e1i5.backend.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FriendRepository extends JpaRepository<Friend, Integer> {

//    @Query("SELECT u.nickname AS nickname, u.tier AS tier, u.userScore AS userScore, " +
//            "(SELECT COUNT(sp) FROM SolvedProblem sp WHERE sp.user = u) AS count " +
//            "FROM Friend fr " +
//            "JOIN fr.friend u " +
//            "WHERE fr.user.userId = :userId")
    @Query("SELECT u.nickname AS nickname, u.tier AS tier, u.userScore AS userScore " +
            "FROM Friend fr " +
            "JOIN fr.friend u " +
            "WHERE fr.user.userId = :userId")
    List<FriendListInterfaceResponse> getFriendInfoByFriendId(@Param("userId") int userId);

    boolean existsByUserAndFriend(User user, User friend);

    Optional<Friend> findByUserAndFriend(User user, User friend);

    void deleteByUserAndFriend(User user, User friend);
}
