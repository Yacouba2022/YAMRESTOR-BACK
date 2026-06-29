package yamrestor.restor.service.impl.traiteur;

import yamrestor.restor.dto.traiteur.RapportTraiteurDTO;
import yamrestor.restor.entity.traiteur.PrestationEntity;
import yamrestor.restor.enums.StatutPrestation;
import yamrestor.restor.repository.traiteur.PrestationRepository;
import yamrestor.restor.service.traiteur.RapportTraiteurService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RapportTraiteurServiceImpl implements RapportTraiteurService {

    private final PrestationRepository prestationRepository;

    @Override
    @Transactional(readOnly = true)
    public RapportTraiteurDTO synthese(LocalDate debut, LocalDate fin) {
        LocalDate d = debut != null ? debut : LocalDate.now().withDayOfMonth(1);
        LocalDate f = fin != null ? fin : d.plusMonths(1);

        List<PrestationEntity> prestations = prestationRepository.toutesEntre(d, f);

        Map<String, Long> parStatut = new LinkedHashMap<>();
        for (StatutPrestation s : StatutPrestation.values()) parStatut.put(s.name(), 0L);

        BigDecimal ca = BigDecimal.ZERO;
        BigDecimal coutTotal = BigDecimal.ZERO;
        for (PrestationEntity p : prestations) {
            parStatut.merge(p.getStatut().name(), 1L, Long::sum);
            // Le CA / coûts ne comptent que les prestations non annulées.
            if (p.getStatut() != StatutPrestation.ANNULEE) {
                ca = ca.add(nz(p.getMontantTotal()));
                coutTotal = coutTotal
                        .add(nz(p.getCoutMatieresPremieres()))
                        .add(nz(p.getCoutPersonnel()))
                        .add(nz(p.getCoutTransport()))
                        .add(nz(p.getCoutMateriel()))
                        .add(nz(p.getCoutDivers()));
            }
        }

        return RapportTraiteurDTO.builder()
                .debut(d).fin(f)
                .nbPrestations(prestations.size())
                .parStatut(parStatut)
                .chiffreAffaires(ca)
                .coutTotal(coutTotal)
                .benefice(ca.subtract(coutTotal))
                .build();
    }

    private BigDecimal nz(BigDecimal v) {
        return v != null ? v : BigDecimal.ZERO;
    }
}
