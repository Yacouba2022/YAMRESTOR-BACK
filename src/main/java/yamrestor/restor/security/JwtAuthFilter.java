package yamrestor.restor.security;

import yamrestor.restor.entity.administration.UserEntity;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.ZoneId;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }
        String token = header.substring(7);
        try {
            String username = jwtUtil.extractUsername(token);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails user = userDetailsService.loadUserByUsername(username);
                if (jwtUtil.isValid(token, user)) {

                    if (user instanceof UserEntity userEntity) {
                        // Compte désactivé entre-temps : on vérifie l'état actif à CHAQUE requête.
                        if (!userEntity.isEnabled()) {
                            ecrireNonAutorise(response, "Votre compte a été désactivé.");
                            return;
                        }
                        // Déconnexion forcée par un admin : tout jeton émis avant tokensValidAfter est rejeté.
                        if (jetonRevoque(token, userEntity)) {
                            ecrireNonAutorise(response, "Votre session a été fermée par un administrateur.");
                            return;
                        }
                    }

                    var auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                    auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }
        } catch (JwtException | IllegalArgumentException e) {
            // Token invalide ou expiré : on n'authentifie pas. Les endpoints publics passent,
            // les endpoints protégés renverront un 401 propre.
            SecurityContextHolder.clearContext();
        }
        chain.doFilter(request, response);
    }

    /** Vrai si le jeton a été émis avant la date de déconnexion forcée de l'utilisateur. */
    private boolean jetonRevoque(String token, UserEntity user) {
        if (user.getTokensValidAfter() == null) {
            return false;
        }
        Date issuedAt = jwtUtil.extractIssuedAt(token);
        if (issuedAt == null) {
            return false;
        }
        Date seuil = Date.from(user.getTokensValidAfter().atZone(ZoneId.systemDefault()).toInstant());
        return issuedAt.before(seuil);
    }

    /** Écrit une réponse 401 propre pour que le front (intercepteur) déconnecte l'utilisateur. */
    private void ecrireNonAutorise(HttpServletResponse response, String message) throws IOException {
        SecurityContextHolder.clearContext();
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        String safe = message == null ? "Non autorisé" : message.replace("\"", "'");
        response.getWriter().write("{\"status\":401,\"message\":\"" + safe + "\"}");
    }
}
