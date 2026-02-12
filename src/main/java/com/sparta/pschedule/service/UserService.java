package com.sparta.pschedule.service;

import com.sparta.pschedule.config.PasswordEncoder;
import com.sparta.pschedule.dto.user.*;
import com.sparta.pschedule.entity.User;
import com.sparta.pschedule.exception.CommonError;
import com.sparta.pschedule.exception.CommonException;
import com.sparta.pschedule.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;


    @Transactional
    public UserSignUpResponse signUp(UserSignUpRequest request) {
        String encodePassword = passwordEncoder.encode(request.getPassword());

        User user = repository.save(new User(
                request.getName(),
                request.getEmail(),
                encodePassword
        ));
        return UserSignUpResponse.from(user);
    }


    @Transactional
    public UserLoginResponse login(UserLoginRequest request) {
        User user = repository.findByEmail(request.getEmail()).orElseThrow(
                () -> new CommonException(CommonError.NOT_FOUND_USER)
        );

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new CommonException(CommonError.INVALID_PASSWORD);
        }
        return UserLoginResponse.from(user);
    }

    @Transactional(readOnly = true)
    public GetUserResponse findById(Long id) {
        User user = repository.findById(id).orElseThrow(
                () -> new CommonException(CommonError.NOT_FOUND_USER)
        );
        return GetUserResponse.from(user);
    }

    @Transactional(readOnly = true)
    public List<GetUserResponse> findAll() {
        List<User> users = repository.findAll();
        return users.stream()
                .map(user -> GetUserResponse.from(user))
                .toList();
    }

    @Transactional
    public UpdateUserResponse update(Long id, UpdateUserRequest request) {
        User user = repository.findById(id).orElseThrow(
                () -> new CommonException(CommonError.NOT_UPDATE_USER)
        );
        user.update(request.getName(), request.getName());
        return UpdateUserResponse.from(user);
    }

    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new CommonException(CommonError.NOT_DELETE_USER);
        }
        repository.deleteById(id);
    }


}
