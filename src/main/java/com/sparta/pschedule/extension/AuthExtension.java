package com.sparta.pschedule.extension;

import com.sparta.pschedule.exception.CommonError;
import com.sparta.pschedule.exception.CommonException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class AuthExtension {

    public static Long checkSession(HttpServletRequest httpRequest) {
        HttpSession session = httpRequest.getSession(false);

        if (session == null || session.getAttribute("LOGIN_USER") == null) {
            throw new CommonException(CommonError.UNAUTHORIZED_ACCESS);
        }
        return (Long) session.getAttribute("LOGIN_USER");
    }
}
