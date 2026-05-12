package kz.bitlab.lms.service;

import kz.bitlab.lms.dto.auth.AuthRequest;
import kz.bitlab.lms.dto.auth.AuthResponse;
import kz.bitlab.lms.dto.auth.RefreshTokenRequest;

public interface AuthService {
    AuthResponse login(AuthRequest request);
    AuthResponse refresh(RefreshTokenRequest request);
}
