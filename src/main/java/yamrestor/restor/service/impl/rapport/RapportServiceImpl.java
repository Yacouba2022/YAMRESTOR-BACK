package yamrestor.restor.service.impl.rapport;

import yamrestor.restor.dto.rapport.ProduitVenduDTO;
import yamrestor.restor.dto.rapport.VenteCategorieDTO;
import yamrestor.restor.dto.rapport.VentesRapportDTO;
import yamrestor.restor.repository.salle.CommandeLigneRepository;
import yamrestor.restor.repository.salle.CommandeRepository;
import yamrestor.restor.service.rapport.RapportService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RapportServiceImpl implements RapportService {

    private final CommandeRepository commandeRepository;
    private final CommandeLigneRepository ligneRepository;

    @Override
    @Transactional(readOnly = true)
    public VentesRapportDTO ventes(LocalDate debut, LocalDate fin) {
        LocalDateTime[] p = periode(debut, fin);
        return VentesRapportDTO.builder()
                .debut(p[0].toLocalDate())
                .fin(p[1].toLocalDate())
                .nbCommandes(commandeRepository.countEntre(p[0], p[1]))
                .chiffreAffaires(nz(commandeRepository.chiffreAffairesEntre(p[0], p[1])))
                .tvaCollectee(nz(ligneRepository.tvaCollectee(p[0], p[1])))
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProduitVenduDTO> topProduits(LocalDate debut, LocalDate fin, int limit) {
        LocalDateTime[] p = periode(debut, fin);
        int n = Math.min(Math.max(limit, 1), 100);
        return ligneRepository.topProduits(p[0], p[1], PageRequest.of(0, n)).stream()
                .map(r -> ProduitVenduDTO.builder()
                        .produitNom((String) r[0])
                        .quantite(r[1] != null ? ((Number) r[1]).longValue() : 0)
                        .montant(r[2] != null ? (BigDecimal) r[2] : BigDecimal.ZERO)
                        .build())
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<VenteCategorieDTO> ventesParCategorie(LocalDate debut, LocalDate fin) {
        LocalDateTime[] p = periode(debut, fin);
        return ligneRepository.ventesParCategorie(p[0], p[1]).stream()
                .map(r -> VenteCategorieDTO.builder()
                        .categorie(r[0] != null ? (String) r[0] : "(sans catégorie)")
                        .montant(r[1] != null ? (BigDecimal) r[1] : BigDecimal.ZERO)
                        .build())
                .toList();
    }

    /** Normalise la période (par défaut : mois en cours). */
    private LocalDateTime[] periode(LocalDate debut, LocalDate fin) {
        LocalDate d = debut != null ? debut : LocalDate.now().withDayOfMonth(1);
        LocalDate f = fin != null ? fin : LocalDate.now();
        return new LocalDateTime[]{ d.atStartOfDay(), f.atTime(LocalTime.MAX) };
    }

    private BigDecimal nz(BigDecimal v) {
        return v != null ? v : BigDecimal.ZERO;
    }
}
