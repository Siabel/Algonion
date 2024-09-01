package com.e1i5.backend.domain.user.exception;

import com.e1i5.backend.global.error.GlobalBaseException;
import com.e1i5.backend.global.error.GlobalErrorCode;

public class UserNotFoundException extends GlobalBaseException {

    public UserNotFoundException(GlobalErrorCode errorCode) {
        super(errorCode);
    }
}
