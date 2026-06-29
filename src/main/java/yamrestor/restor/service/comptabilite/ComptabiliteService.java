package yamrestor.restor.service.comptabilite;

import yamrestor.restor.dto.comptabilite.CompteResultatDTO;

import java.time.LocalDate;

public interface ComptabiliteService {
    /** Compte de résultat : recettes encaissées − dépenses sur la période. */
    CompteResultatDTO compteResultat(LocalDate debut, LocalDate fin);
}
