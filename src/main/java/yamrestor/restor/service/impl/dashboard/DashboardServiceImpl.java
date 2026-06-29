package yamrestor.restor.service.impl.dashboard;

import yamrestor.restor.dto.dashboard.DashboardDTO;
import yamrestor.restor.dto.rapport.ProduitVenduDTO;
import yamrestor.restor.enums.StatutCommande;
import yamrestor.restor.enums.StatutReservation;
import yamrestor.restor.repository.catalogue.MatierePremiereRepository;
import yamrestor.restor.repository.salle.CommandeLigneRepository;
import yamrestor.restor.repository.salle.CommandeRepository;
import yamrestor.restor.repository.salle.ReservationRepository;
import yamrestor.restor.service.dashboard.DashboardService;
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
public class DashboardServiceImpl implements DashboardService {

    private final CommandeRepository commandeRepository;
    private final CommandeLigneRepository ligneRepository;
    private final ReservationRepository reservationRepository;
    private final MatierePremiereRepository matiereRepository;

    @Override
    @Transactional(readOnly = true)
    public DashboardDTO synthese() {
        LocalDate today = LocalDate.now();
        LocalDateTime debut = today.atStartOfDay();
        LocalDateTime fin = today.atTime(LocalTime.MAX);

        List<ProduitVenduDTO> top = ligneRepository.topProduits(debut, fin, PageRequest.of(0, 5)).stream()
                .map(r -> ProduitVenduDTO.builder()
                        .produitNom((String) r[0])
                        .quantite(r[1] != null ? ((Number) r[1]).longValue() : 0)
                        .montant(r[2] != null ? (BigDecimal) r[2] : BigDecimal.ZERO)
                        .build())
                .toList();

        return DashboardDTO.builder()
                .chiffreAffairesJour(nz(commandeRepository.chiffreAffairesEntre(debut, fin)))
                .nbCommandesJour(commandeRepository.countEntre(debut, fin))
                .commandesEnAttente(commandeRepository.countByStatut(StatutCommande.ENVOYEE))
                .reservationsJour(reservationRepository.countByDateReservationAndStatutNot(today, StatutReservation.ANNULEE))
                .alertesStock(matiereRepository.countEnAlerte())
                .valorisationStock(nz(matiereRepository.valorisationTotale()))
                .topProduitsJour(top)
                .build();
    }

    private BigDecimal nz(BigDecimal v) {
        return v != null ? v : BigDecimal.ZERO;
    }
}
