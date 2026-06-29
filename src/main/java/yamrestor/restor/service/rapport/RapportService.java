package yamrestor.restor.service.rapport;

import yamrestor.restor.dto.rapport.ProduitVenduDTO;
import yamrestor.restor.dto.rapport.VenteCategorieDTO;
import yamrestor.restor.dto.rapport.VentesRapportDTO;

import java.time.LocalDate;
import java.util.List;

public interface RapportService {
    VentesRapportDTO ventes(LocalDate debut, LocalDate fin);
    List<ProduitVenduDTO> topProduits(LocalDate debut, LocalDate fin, int limit);
    List<VenteCategorieDTO> ventesParCategorie(LocalDate debut, LocalDate fin);
}
