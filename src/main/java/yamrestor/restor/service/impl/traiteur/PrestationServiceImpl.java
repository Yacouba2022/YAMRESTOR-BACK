package yamrestor.restor.service.impl.traiteur;

import yamrestor.restor.dto.request.traiteur.PrestationRequest;
import yamrestor.restor.entity.administration.UserEntity;
import yamrestor.restor.entity.traiteur.PrestationEntity;
import yamrestor.restor.enums.StatutPrestation;
import yamrestor.restor.exception.ResourceNotFoundException;
import yamrestor.restor.repository.administration.UserRepository;
import yamrestor.restor.repository.traiteur.PrestationRepository;
import yamrestor.restor.service.traiteur.PrestationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class PrestationServiceImpl implements PrestationService {

    private final PrestationRepository prestationRepository;
    private final UserRepository userRepository;

    @Override
    public Page<PrestationEntity> search(StatutPrestation statut, LocalDate date, int page, int size) {
        return prestationRepository.search(statut, date, PageRequest.of(page, size));
    }

    @Override
    public PrestationEntity findByGuid(String guid) {
        return prestationRepository.findByGuid(guid)
                .orElseThrow(() -> new ResourceNotFoundException("PrestationEntity", guid));
    }

    @Override
    @Transactional
    public PrestationEntity creerDepuisRequest(PrestationRequest req) {
        PrestationEntity p = new PrestationEntity();
        p.setStatut(StatutPrestation.DEVIS);
        apply(p, req);

        PrestationEntity saved = prestationRepository.save(p);
        if (saved.getNumero() == null) {
            saved.setNumero("PRE-" + String.format("%05d", saved.getId()));
            saved = prestationRepository.save(saved);
        }
        return saved;
    }

    @Override
    @Transactional
    public PrestationEntity modifierDepuisRequest(String guid, PrestationRequest req) {
        PrestationEntity p = findByGuid(guid);
        apply(p, req);
        return prestationRepository.save(p);
    }

    @Override
    @Transactional
    public PrestationEntity changerStatut(String guid, StatutPrestation statut) {
        PrestationEntity p = findByGuid(guid);
        p.setStatut(statut);
        return prestationRepository.save(p);
    }

    @Override
    @Transactional
    public void delete(String guid) {
        prestationRepository.delete(findByGuid(guid));
    }

    private void apply(PrestationEntity p, PrestationRequest req) {
        p.setTypeEvenement(req.getTypeEvenement());
        p.setDateEvenement(req.getDateEvenement());
        p.setHeure(req.getHeure());
        p.setLieu(req.getLieu());
        p.setClientNom(req.getClientNom());
        p.setClientTelephone(req.getClientTelephone());
        p.setResponsable(resolveResponsable(req.getResponsableGuid()));
        p.setNombreConvivesPrevu(req.getNombreConvivesPrevu());
        p.setNombreAdultes(req.getNombreAdultes());
        p.setNombreEnfants(req.getNombreEnfants());
        p.setObservations(req.getObservations());
        p.setCoutMatieresPremieres(nz(req.getCoutMatieresPremieres()));
        p.setCoutPersonnel(nz(req.getCoutPersonnel()));
        p.setCoutTransport(nz(req.getCoutTransport()));
        p.setCoutMateriel(nz(req.getCoutMateriel()));
        p.setCoutDivers(nz(req.getCoutDivers()));
    }

    private UserEntity resolveResponsable(String guid) {
        if (guid == null || guid.isBlank()) return null;
        return userRepository.findByGuid(guid)
                .orElseThrow(() -> new ResourceNotFoundException("Responsable", guid));
    }

    private BigDecimal nz(BigDecimal v) {
        return v != null ? v : BigDecimal.ZERO;
    }
}
