package yamrestor.restor.service.traiteur;

import yamrestor.restor.dto.traiteur.RapportTraiteurDTO;

import java.time.LocalDate;

public interface RapportTraiteurService {
    /** Synthèse des prestations sur une période : nombre, répartition par statut, CA, coûts, bénéfice. */
    RapportTraiteurDTO synthese(LocalDate debut, LocalDate fin);
}
