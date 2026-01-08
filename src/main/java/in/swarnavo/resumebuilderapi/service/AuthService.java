package in.swarnavo.resumebuilderapi.service;

import in.swarnavo.resumebuilderapi.dto.AuthResponse;
import in.swarnavo.resumebuilderapi.dto.RegisterRequest;
import in.swarnavo.resumebuilderapi.exception.ResourceExistsException;
import in.swarnavo.resumebuilderapi.models.User;
import in.swarnavo.resumebuilderapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepository;

    public AuthResponse register(RegisterRequest request) {
        log.info("Inside AuthService: register() {} ", request);

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ResourceExistsException("User already exists with this email");
        }

        User newUser = toDocument(request);

        newUser = userRepository.save(newUser);

        // TODO: send verification email

        return toResponse(newUser);
    }

    private AuthResponse toResponse(User newUser) {
        return AuthResponse.builder()
                .id(newUser.getId())
                .name(newUser.getName())
                .email(newUser.getEmail())
                .profileImageUrl(newUser.getProfileImageUrl())
                .subscriptionPlan(newUser.getSubscriptionPlan())
                .emailVerified(newUser.isEmailVerified())
                .createdAt(newUser.getCreatedAt())
                .updatedAt(newUser.getUpdatedAt())
                .build();
    }

    private User toDocument(RegisterRequest request) {
        return User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(request.getPassword())
                .profileImageUrl(request.getProfileImageUrl())
                .subscriptionPlan("Basic")
                .emailVerified(false)
                .verificationToken(UUID.randomUUID().toString())
                .verificationExpires(LocalDateTime.now().plusHours(24))
                .build();
    }
}
