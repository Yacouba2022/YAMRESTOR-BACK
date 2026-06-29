package yamrestor.restor.mapper.caisse;

import yamrestor.restor.dto.caisse.SessionCaisseDTO;
import yamrestor.restor.entity.caisse.SessionCaisseEntity;

public class SessionCaisseMapper {

    public static SessionCaisseDTO toDTO(SessionCaisseEntity s) {
        if (s == null) return null;
        return SessionCaisseDTO.builder()
                .guid(s.getGuid())
                .numero(s.getNumero())
                .caissierGuid(s.getCaissier() != null ? s.getCaissier().getGuid() : null)
                .caissierNom(s.getCaissier() != null ? s.getCaissier().getName() : null)
                .fondInitial(s.getFondInitial())
                .dateOuverture(s.getDateOuverture())
                .dateFermeture(s.getDateFermeture())
                .totalEncaisse(s.getTotalEncaisse())
                .totalEspeces(s.getTotalEspeces())
                .montantTheorique(s.getMontantTheorique())
                .fondFinalReel(s.getFondFinalReel())
                .ecart(s.getEcart())
                .commentaire(s.getCommentaire())
                .statut(s.getStatut())
                .build();
    }

    private SessionCaisseMapper() {}
}
