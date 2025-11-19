package com.example.userservice.service;

import com.example.commonlib.exception.AppException;
import com.example.commonlib.exception.ErrorCode;
import com.example.userservice.dto.user.UserCreationRequest;
import com.example.userservice.dto.user.UserResponse;
import com.example.userservice.dto.user.UserUpdateRequest;
import com.example.userservice.mapper.UserMapper;
import com.example.userservice.model.User;
import com.example.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.AccessLevel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserService {

    final UserRepository userRepository;
    final UserMapper userMapper;

    public UserResponse createUser(UserCreationRequest userCreationRequest) {
        if (userRepository.existsUserByAccId(String.valueOf(userCreationRequest.getAccId()))) {
            throw new AppException(ErrorCode.EXISTED, "User with this accId already exists");
        }

        User user = userMapper.toModel(userCreationRequest);
        User saved = userRepository.save(user);
        return userMapper.toResponse(saved);
    }

    public UserResponse updateUser(String id, UserUpdateRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        userMapper.updateUserFromRequest(request, user);
        return userMapper.toResponse(userRepository.save(user));
    }

    public UserResponse getUserById(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return userMapper.toResponse(user);
    }
	
		public UserResponse getUserByAccId(String accId) {
			User user = userRepository.findUserByAccId(accId)
					.orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND, "User not found"));
			return userMapper.toResponse(user);
		}

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toResponse)
                .toList();
    }
}
