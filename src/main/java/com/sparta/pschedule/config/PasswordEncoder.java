package com.sparta.pschedule.config;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncoder {

    /**
     * encode
     BCrypt.MIN_COST: 암호화할 때 CPU 연산을 얼마나 복잡하게 할지 결정(비용)
     값이 클수록 해킹하기 어려워지지만, 암호화하는 데 시간이 오래 걸림(보통 10~12 정도 사용)
     rawPassword.toCharArray(): 보안상의 이유로 String 대신 char[] 배열로 변환하여 처리합니다. (메모리에 비밀번호 흔적이 남는 것을 최소화하기 위한 보안 관례입니다.)
     그리고 복호화 불가하다!!

     * matches
     내부 로직: 암호문 안에 숨겨진 'Salt(소금)' 값을 꺼내서 입력받은 평문에 똑같이 뿌린 뒤 암호화를 진행해 봅니다.
     */

    public String encode(String rawPassword) {
        return BCrypt.withDefaults().hashToString(BCrypt.MIN_COST, rawPassword.toCharArray());
    }

    public boolean matches(String rawPassword, String encodedPassword) {
        BCrypt.Result result = BCrypt.verifyer().verify(rawPassword.toCharArray(), encodedPassword);
        return result.verified;
    }
}