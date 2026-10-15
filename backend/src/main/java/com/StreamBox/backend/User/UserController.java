package com.StreamBox.backend.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.Authentication;
import java.util.List;

@SuppressWarnings("unused")
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

   @PostMapping
public ResponseEntity<UserResponse> create(@Valid @RequestBody CreateUserRequest request) {
    if (userRepository.existsByEmail(request.email())) {
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    User user = new User();
    user.setEmail(request.email());
    user.setUsername(request.username());
    user.setPassword(passwordEncoder.encode(request.password()));

    User saved = userRepository.save(user);
    return ResponseEntity.status(HttpStatus.CREATED).body(UserResponse.from(saved));
}

@GetMapping
public List<UserResponse> findAll() {
    return userRepository.findAll().stream()
            .map(UserResponse::from)
            .toList();
}

@GetMapping("/me")
public ResponseEntity<UserResponse> me(Authentication authentication) {
    String email = authentication.getName();
    return userRepository.findByEmail(email)
            .map(user -> ResponseEntity.ok(UserResponse.from(user)))
            .orElse(ResponseEntity.notFound().build());
}
}