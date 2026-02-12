package com.sparta.pschedule.service;

import com.sparta.pschedule.dto.user.*;
import com.sparta.pschedule.entity.User;
import com.sparta.pschedule.exception.CommonError;
import com.sparta.pschedule.exception.CommonException;
import com.sparta.pschedule.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;


    @Transactional
    public UserSignUpResponse signUp(UserSignUpRequest request) {
        User user = repository.save(new User(
                request.getName(),
                request.getEmail(),
                request.getPassword()
        ));
        return UserSignUpResponse.from(user);
    }


    @Transactional
    public UserLoginResponse login(UserLoginRequest request) {
        User user = repository.findByEmail(request.getEmail()).orElseThrow(
                () -> new CommonException(CommonError.NO_FIND_USER)
        );

        if (!Objects.equals(request.getPassword(), user.getPassword())) {
            throw new CommonException(CommonError.INVALID_PASSWORD);
        }
        return UserLoginResponse.from(user);
    }

    @Transactional(readOnly = true)
    public GetUserResponse findById(Long id) {
        User user = repository.findById(id).orElseThrow(
                () -> new CommonException(CommonError.NO_FIND_USER)
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
                () -> new CommonException(CommonError.NO_UPDATE_USER)
        );
        user.update(request.getName(), request.getName());
        return UpdateUserResponse.from(user);
    }

    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new CommonException(CommonError.NO_DELETE_USER);
        }
        repository.deleteById(id);
    }


}
