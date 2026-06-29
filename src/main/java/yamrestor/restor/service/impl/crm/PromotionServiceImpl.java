package yamrestor.restor.service.impl.crm;

import yamrestor.restor.dto.request.crm.PromotionRequest;
import yamrestor.restor.entity.crm.PromotionEntity;
import yamrestor.restor.exception.BadRequestException;
import yamrestor.restor.exception.ResourceNotFoundException;
import yamrestor.restor.repository.crm.PromotionRepository;
import yamrestor.restor.service.crm.PromotionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PromotionServiceImpl implements PromotionService {

    private final PromotionRepository promotionRepository;

    @Override
    public Page<PromotionEntity> findAll(int page, int size) {
        return promotionRepository.findAll(PageRequest.of(page, size, Sort.by("nom")));
    }

    @Override
    public PromotionEntity findByGuid(String guid) {
        return promotionRepository.findByGuid(guid)
                .orElseThrow(() -> new ResourceNotFoundException("PromotionEntity", guid));
    }

    @Override
    @Transactional
    public PromotionEntity creerDepuisRequest(PromotionRequest req) {
        if (req.getCode() != null && !req.getCode().isBlank() && promotionRepository.existsByCode(req.getCode())) {
            throw new BadRequestException("Code de promotion déjà utilisé : " + req.getCode());
        }
        PromotionEntity p = new PromotionEntity();
        apply(p, req);
        return promotionRepository.save(p);
    }

    @Override
    @Transactional
    public PromotionEntity modifierDepuisRequest(String guid, PromotionRequest req) {
        PromotionEntity p = findByGuid(guid);
        if (req.getCode() != null && !req.getCode().isBlank()) {
            promotionRepository.findByCode(req.getCode())
                    .filter(other -> !other.getGuid().equals(guid))
                    .ifPresent(other -> { throw new BadRequestException("Code de promotion déjà utilisé : " + req.getCode()); });
        }
        apply(p, req);
        return promotionRepository.save(p);
    }

    @Override
    @Transactional
    public void delete(String guid) {
        promotionRepository.delete(findByGuid(guid));
    }

    @Override
    public List<PromotionEntity> actives() {
        LocalDate jour = LocalDate.now();
        LocalTime heure = LocalTime.now();
        return promotionRepository.findByActifTrue().stream()
                .filter(p -> p.estActive(jour, heure))
                .toList();
    }

    @Override
    public PromotionEntity findByCode(String code) {
        return promotionRepository.findByCode(code)
                .orElseThrow(() -> new ResourceNotFoundException("Promotion (code)", code));
    }

    private void apply(PromotionEntity p, PromotionRequest req) {
        p.setNom(req.getNom());
        p.setType(req.getType());
        p.setValeur(req.getValeur() != null ? req.getValeur() : BigDecimal.ZERO);
        p.setCode(req.getCode() != null && !req.getCode().isBlank() ? req.getCode() : null);
        p.setDateDebut(req.getDateDebut());
        p.setDateFin(req.getDateFin());
        p.setHeureDebut(req.getHeureDebut());
        p.setHeureFin(req.getHeureFin());
        p.setActif(req.getActif() != null ? req.getActif() : Boolean.TRUE);
    }
}
