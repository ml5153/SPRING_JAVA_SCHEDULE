package com.sparta.pschedule.controller;

import com.sparta.pschedule.dto.user.*;
import com.sparta.pschedule.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    @PostMapping("/signup")
    public ResponseEntity<UserSignUpResponse> signUp(
            @Valid @RequestBody UserSignUpRequest request
    ) {
        UserSignUpResponse response = service.signUp(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponse> login(
            @Valid @RequestBody UserLoginRequest request,
            HttpServletRequest httpRequest
    ) {
        // 이메일, 비밀번호 유효검사
        UserLoginResponse response = service.login(request);

        // 세션(통행증발급)
        HttpSession session = httpRequest.getSession();
        session.setAttribute("LOGIN_USER", response.getId());

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest httpRequest) {
        HttpSession session = httpRequest.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetUserResponse> getUser(
            @PathVariable Long id
    ) {
        GetUserResponse response = service.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping()
    public ResponseEntity<List<GetUserResponse>> getUserList() {
        List<GetUserResponse> responses = service.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }

    @RequestMapping(value = "/{id}", method = {RequestMethod.PUT, RequestMethod.PATCH})
    public ResponseEntity<UpdateUserResponse> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UpdateUserRequest request
    ) {
        UpdateUserResponse response = service.update(id, request);
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }


}
