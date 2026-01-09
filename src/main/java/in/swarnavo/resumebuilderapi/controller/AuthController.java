package in.swarnavo.resumebuilderapi.controller;

import in.swarnavo.resumebuilderapi.dto.AuthResponse;
import in.swarnavo.resumebuilderapi.dto.LoginRequest;
import in.swarnavo.resumebuilderapi.dto.RegisterRequest;
import in.swarnavo.resumebuilderapi.service.AuthService;
import in.swarnavo.resumebuilderapi.service.FileUploadService;
import in.swarnavo.resumebuilderapi.util.AppConstants;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping(AppConstants.AUTH_CONTROLLER)
public class AuthController {

    private final AuthService authService;

    private final FileUploadService fileUploadService;

    @PostMapping(AppConstants.REGISTER)
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        log.info("Inside AuthController - register(): {}", request);
        AuthResponse response = authService.register(request);
        log.info("Response from service: {}", response);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping(AppConstants.VERIFY_EMAIL)
    public ResponseEntity<?> verifyEmail(@RequestParam String token) {
        log.info("Inside AuthController - verifyEmail(): {}", token);
        authService.verifyEmail(token);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Email verified successfully!"));
    }

    @PostMapping(AppConstants.UPLOAD_IMAGE)
    public ResponseEntity<?> uploadImage(@RequestPart("image")MultipartFile file) throws IOException {
        log.info("Inside AuthController - uploadImage()");
        Map<String, String> response =  fileUploadService.uploadSingleImage(file);
        return ResponseEntity.ok(response);
    }

    @PostMapping(AppConstants.LOGIN)
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        log.info("Inside AuthController - login(): {}", request);
        AuthResponse response = authService.login(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
