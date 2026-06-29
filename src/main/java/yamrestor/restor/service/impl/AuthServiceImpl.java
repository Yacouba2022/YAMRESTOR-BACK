package yamrestor.restor.service.impl;

import yamrestor.restor.dto.request.LoginRequest;
import yamrestor.restor.dto.response.AuthResponse;
import yamrestor.restor.entity.administration.UserEntity;
import yamrestor.restor.security.JwtUtil;
import yamrestor.restor.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    /** Délai d'inactivité (minutes) renvoyé au front pour la déconnexion automatique. */
    @Value("${app.security.inactivity-timeout-minutes:15}")
    private int inactivityTimeoutMinutes;

    @Override
    @Transactional(readOnly = true)
    public AuthResponse login(LoginRequest request) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        UserEntity user = (UserEntity) auth.getPrincipal();

        String token = jwtUtil.generateToken(user);

        AuthResponse response = new AuthResponse();
        response.setToken(token);
        response.setGuid(user.getGuid());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setPermissions(user.getProfil() != null
                ? List.copyOf(user.getProfil().getPermissions()) : List.of());
        response.setSuperAdmin(user.isSuperAdmin());
        response.setInactivityTimeoutMinutes(inactivityTimeoutMinutes);
        return response;
    }
}
