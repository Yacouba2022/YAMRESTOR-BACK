package yamrestor.restor.service.impl.comptabilite;

import yamrestor.restor.dto.comptabilite.CompteResultatDTO;
import yamrestor.restor.repository.caisse.PaiementRepository;
import yamrestor.restor.repository.comptabilite.DepenseRepository;
import yamrestor.restor.service.comptabilite.ComptabiliteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class ComptabiliteServiceImpl implements ComptabiliteService {

    private final PaiementRepository paiementRepository;
    private final DepenseRepository depenseRepository;

    @Override
    @Transactional(readOnly = true)
    public CompteResultatDTO compteResultat(LocalDate debut, LocalDate fin) {
        LocalDate d = debut != null ? debut : LocalDate.now().withDayOfMonth(1);
        LocalDate f = fin != null ? fin : LocalDate.now();

        BigDecimal recettes = nz(paiementRepository.recettesEntre(d.atStartOfDay(), f.atTime(LocalTime.MAX)));
        BigDecimal depenses = nz(depenseRepository.sommeEntre(d, f));

        return CompteResultatDTO.builder()
                .debut(d)
                .fin(f)
                .totalRecettes(recettes)
                .totalDepenses(depenses)
                .benefice(recettes.subtract(depenses))
                .build();
    }

    private BigDecimal nz(BigDecimal v) {
        return v != null ? v : BigDecimal.ZERO;
    }
}
