package yamrestor.restor.mapper.traiteur;

import yamrestor.restor.dto.traiteur.PersonnelTraiteurDTO;
import yamrestor.restor.entity.traiteur.PersonnelTraiteurEntity;

public class PersonnelTraiteurMapper {

    public static PersonnelTraiteurDTO toDTO(PersonnelTraiteurEntity p) {
        if (p == null) return null;
        return PersonnelTraiteurDTO.builder()
                .guid(p.getGuid())
                .nom(p.getNom())
                .fonction(p.getFonction())
                .telephone(p.getTelephone())
                .coutJournalier(p.getCoutJournalier())
                .actif(p.getActif())
                .build();
    }

    private PersonnelTraiteurMapper() {}
}
