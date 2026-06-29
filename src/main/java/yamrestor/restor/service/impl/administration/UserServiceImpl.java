package yamrestor.restor.service.impl.administration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import yamrestor.restor.dto.request.administration.UserCreateRequest;
import yamrestor.restor.dto.request.administration.UserUpdateRequest;
import yamrestor.restor.entity.administration.ProfilEntity;
import yamrestor.restor.entity.administration.UserEntity;
import yamrestor.restor.exception.BadRequestException;
import yamrestor.restor.exception.ResourceNotFoundException;
import yamrestor.restor.repository.administration.ProfilRepository;
import yamrestor.restor.repository.administration.UserRepository;
import yamrestor.restor.service.administration.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ObjectMapper objectMapper;
    private final ProfilRepository profilRepository;

    @Override
    public Page<UserEntity> findAll(int page, int size) {
        return userRepository.findAll(PageRequest.of(page, size, Sort.by("name")));
    }

    @Override
    public UserEntity findByGuid(String guid) {
        return userRepository.findByGuid(guid)
                .orElseThrow(() -> new ResourceNotFoundException("UserEntity", guid));
    }

    @Override
    @Transactional
    public UserEntity creerDepuisRequest(UserCreateRequest req) {
        if (userRepository.existsByEmail(req.getEmail())) {
            throw new IllegalArgumentException("Email déjà utilisé : " + req.getEmail());
        }
        UserEntity user = new UserEntity();
        user.setName(req.getName());
        user.setEmail(req.getEmail());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setFonction(req.getFonction());
        user.setTelephone(req.getTelephone());
        user.setAdresse(req.getAdresse());
        user.setCompte(req.getCompte());
        user.setEtat(req.getEtat() != null ? req.getEtat() : "actif");
        user.setProfil(resolveProfil(req.getProfilGuid()));
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public UserEntity modifierDepuisRequest(String guid, UserUpdateRequest req) {
        UserEntity user = findByGuid(guid);
        user.setName(req.getName());
        user.setFonction(req.getFonction());
        user.setTelephone(req.getTelephone());
        user.setAdresse(req.getAdresse());
        user.setCompte(req.getCompte());
        if (req.getEtat() != null) user.setEtat(req.getEtat());
        if (req.getProfilGuid() != null) user.setProfil(resolveProfil(req.getProfilGuid()));
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void changerMotDePasse(String guid, String nouveauMotDePasse) {
        UserEntity user = findByGuid(guid);
        user.setPassword(passwordEncoder.encode(nouveauMotDePasse));
        userRepository.save(user);
    }

    @Override
    @Transactional
    public UserEntity sauvegarderPreferences(String guid, Map<String, Object> prefs) {
        UserEntity user = findByGuid(guid);
        try {
            user.setPreferences(objectMapper.writeValueAsString(prefs));
        } catch (JsonProcessingException e) {
            throw new BadRequestException("Préférences JSON invalides.");
        }
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void deconnecter(String guid) {
        UserEntity user = findByGuid(guid);
        user.setTokensValidAfter(LocalDateTime.now());
        user.setEtat("inactif");
        userRepository.save(user);
    }

    @Override
    @Transactional
    public UserEntity toggleEtat(String guid, String etat) {
        if (!List.of("actif", "inactif").contains(etat))
            throw new BadRequestException("Valeur d'état invalide : " + etat);
        UserEntity user = findByGuid(guid);
        user.setEtat(etat);
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void supprimer(String guid) {
        UserEntity user = findByGuid(guid);
        userRepository.delete(user);
    }

    // ─── Helpers privés ───────────────────────────────────────────────────────

    private ProfilEntity resolveProfil(String guid) {
        if (guid == null) return null;
        return profilRepository.findByGuid(guid)
                .orElseThrow(() -> new ResourceNotFoundException("Profil", guid));
    }
}
