package yamrestor.restor.mapper.traiteur;

import yamrestor.restor.dto.traiteur.AffectationsDTO;
import yamrestor.restor.entity.traiteur.AffectationMaterielEntity;
import yamrestor.restor.entity.traiteur.AffectationPersonnelEntity;
import yamrestor.restor.entity.traiteur.AffectationVehiculeEntity;

public class AffectationMapper {

    public static AffectationsDTO.PersonnelDTO toDTO(AffectationPersonnelEntity a) {
        if (a == null) return null;
        return AffectationsDTO.PersonnelDTO.builder()
                .guid(a.getGuid())
                .personnelGuid(a.getPersonnel() != null ? a.getPersonnel().getGuid() : null)
                .personnelNom(a.getPersonnel() != null ? a.getPersonnel().getNom() : null)
                .fonction(a.getPersonnel() != null ? a.getPersonnel().getFonction() : null)
                .role(a.getRole())
                .build();
    }

    public static AffectationsDTO.VehiculeAffectDTO toDTO(AffectationVehiculeEntity a) {
        if (a == null) return null;
        return AffectationsDTO.VehiculeAffectDTO.builder()
                .guid(a.getGuid())
                .vehiculeGuid(a.getVehicule() != null ? a.getVehicule().getGuid() : null)
                .vehiculeNom(a.getVehicule() != null ? a.getVehicule().getNom() : null)
                .chauffeurNom(a.getChauffeurNom())
                .kmDepart(a.getKmDepart())
                .kmRetour(a.getKmRetour())
                .carburant(a.getCarburant())
                .build();
    }

    public static AffectationsDTO.MaterielAffectDTO toDTO(AffectationMaterielEntity a) {
        if (a == null) return null;
        return AffectationsDTO.MaterielAffectDTO.builder()
                .guid(a.getGuid())
                .materielGuid(a.getMateriel() != null ? a.getMateriel().getGuid() : null)
                .materielNom(a.getMateriel() != null ? a.getMateriel().getNom() : null)
                .quantite(a.getQuantite())
                .build();
    }

    private AffectationMapper() {}
}
