package com.StreamBox.backend.User;

import java.time.LocalDateTime;

public record UserResponse(
        Long id,
        String email,
        String username,
        LocalDateTime createdAt
) {
    public static UserResponse from(User user) {
        return new UserResponse(
                user.getId(),
                user.getEmail(),
                user.getUsername(),
                user.getCreatedAt()
        );
    }
}