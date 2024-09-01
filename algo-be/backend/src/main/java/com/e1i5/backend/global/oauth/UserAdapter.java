package com.e1i5.backend.global.oauth;

import com.e1i5.backend.domain.user.entity.CustomUserDetail;
import com.e1i5.backend.domain.user.entity.User;
import lombok.Getter;

/**
 * @AuthenticationPrincipal 위한 파일
 * 사용 안함
 */

@Getter
public class UserAdapter extends CustomUserDetail {

    private User user;
//    private Map<String, Object> attributes;

    public UserAdapter(User user){
        super(user);
        this.user = user;
    }

//    public UserAdapter(User user, Map<String, Object> attributes){
//        super(user, attributes);
//        this.user = user;
//        this.attributes = attributes;
//    }

//    private User account;
//    public UserAccount(User account) {
//        super(account.getEmail(), List.of(new SimpleGrantedAuthority("ROLE_USER")));
//        this.account = account;
//    }
}
