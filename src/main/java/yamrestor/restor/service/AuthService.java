package yamrestor.restor.service;

import yamrestor.restor.dto.request.LoginRequest;
import yamrestor.restor.dto.response.AuthResponse;

public interface AuthService {
    /** Authentifie l'utilisateur et renvoie le jeton JWT ainsi que ses permissions effectives. */
    AuthResponse login(LoginRequest request);
}
