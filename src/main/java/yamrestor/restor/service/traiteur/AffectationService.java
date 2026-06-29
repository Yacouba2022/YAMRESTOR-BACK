package yamrestor.restor.service.traiteur;

import yamrestor.restor.dto.request.traiteur.AffectationMaterielRequest;
import yamrestor.restor.dto.request.traiteur.AffectationPersonnelRequest;
import yamrestor.restor.dto.request.traiteur.AffectationVehiculeRequest;
import yamrestor.restor.dto.traiteur.AffectationsDTO;
import yamrestor.restor.entity.traiteur.AffectationMaterielEntity;
import yamrestor.restor.entity.traiteur.AffectationPersonnelEntity;
import yamrestor.restor.entity.traiteur.AffectationVehiculeEntity;

public interface AffectationService {
    AffectationsDTO affectations(String prestationGuid);

    AffectationPersonnelEntity affecterPersonnel(String prestationGuid, AffectationPersonnelRequest req);
    AffectationVehiculeEntity affecterVehicule(String prestationGuid, AffectationVehiculeRequest req);
    /** Affecte du matériel : réserve la quantité (décrémente la disponibilité). */
    AffectationMaterielEntity affecterMateriel(String prestationGuid, AffectationMaterielRequest req);

    void retirerPersonnel(String affectationGuid);
    void retirerVehicule(String affectationGuid);
    /** Retire le matériel et le remet en disponibilité. */
    void retirerMateriel(String affectationGuid);
}
