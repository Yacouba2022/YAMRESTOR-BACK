package yamrestor.restor.service.impl.traiteur;

import yamrestor.restor.dto.request.traiteur.AffectationMaterielRequest;
import yamrestor.restor.dto.request.traiteur.AffectationPersonnelRequest;
import yamrestor.restor.dto.request.traiteur.AffectationVehiculeRequest;
import yamrestor.restor.dto.traiteur.AffectationsDTO;
import yamrestor.restor.entity.traiteur.*;
import yamrestor.restor.exception.BadRequestException;
import yamrestor.restor.exception.ResourceNotFoundException;
import yamrestor.restor.mapper.traiteur.AffectationMapper;
import yamrestor.restor.repository.traiteur.*;
import yamrestor.restor.service.traiteur.AffectationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AffectationServiceImpl implements AffectationService {

    private final AffectationPersonnelRepository personnelAffectRepository;
    private final AffectationVehiculeRepository vehiculeAffectRepository;
    private final AffectationMaterielRepository materielAffectRepository;
    private final PrestationRepository prestationRepository;
    private final PersonnelTraiteurRepository personnelRepository;
    private final VehiculeRepository vehiculeRepository;
    private final MaterielTraiteurRepository materielRepository;

    @Override
    @Transactional(readOnly = true)
    public AffectationsDTO affectations(String prestationGuid) {
        return AffectationsDTO.builder()
                .personnels(personnelAffectRepository.findByPrestationGuid(prestationGuid)
                        .stream().map(AffectationMapper::toDTO).toList())
                .vehicules(vehiculeAffectRepository.findByPrestationGuid(prestationGuid)
                        .stream().map(AffectationMapper::toDTO).toList())
                .materiels(materielAffectRepository.findByPrestationGuid(prestationGuid)
                        .stream().map(AffectationMapper::toDTO).toList())
                .build();
    }

    @Override
    @Transactional
    public AffectationPersonnelEntity affecterPersonnel(String prestationGuid, AffectationPersonnelRequest req) {
        AffectationPersonnelEntity a = new AffectationPersonnelEntity();
        a.setPrestation(resolvePrestation(prestationGuid));
        a.setPersonnel(personnelRepository.findByGuid(req.getPersonnelGuid())
                .orElseThrow(() -> new ResourceNotFoundException("PersonnelTraiteur", req.getPersonnelGuid())));
        a.setRole(req.getRole());
        return personnelAffectRepository.save(a);
    }

    @Override
    @Transactional
    public AffectationVehiculeEntity affecterVehicule(String prestationGuid, AffectationVehiculeRequest req) {
        AffectationVehiculeEntity a = new AffectationVehiculeEntity();
        a.setPrestation(resolvePrestation(prestationGuid));
        a.setVehicule(vehiculeRepository.findByGuid(req.getVehiculeGuid())
                .orElseThrow(() -> new ResourceNotFoundException("Vehicule", req.getVehiculeGuid())));
        a.setChauffeurNom(req.getChauffeurNom());
        a.setKmDepart(req.getKmDepart());
        a.setKmRetour(req.getKmRetour());
        a.setCarburant(req.getCarburant());
        return vehiculeAffectRepository.save(a);
    }

    @Override
    @Transactional
    public AffectationMaterielEntity affecterMateriel(String prestationGuid, AffectationMaterielRequest req) {
        MaterielTraiteurEntity m = materielRepository.findByGuid(req.getMaterielGuid())
                .orElseThrow(() -> new ResourceNotFoundException("MaterielTraiteur", req.getMaterielGuid()));
        int qte = req.getQuantite() != null ? req.getQuantite() : 1;
        int dispo = m.getQuantiteDisponible() != null ? m.getQuantiteDisponible() : 0;
        if (qte > dispo) {
            throw new BadRequestException("Matériel insuffisant pour « " + m.getNom()
                    + " » : demandé " + qte + ", disponible " + dispo + ".");
        }
        m.setQuantiteDisponible(dispo - qte);
        materielRepository.save(m);

        AffectationMaterielEntity a = new AffectationMaterielEntity();
        a.setPrestation(resolvePrestation(prestationGuid));
        a.setMateriel(m);
        a.setQuantite(qte);
        return materielAffectRepository.save(a);
    }

    @Override
    @Transactional
    public void retirerPersonnel(String affectationGuid) {
        AffectationPersonnelEntity a = personnelAffectRepository.findByGuid(affectationGuid)
                .orElseThrow(() -> new ResourceNotFoundException("AffectationPersonnel", affectationGuid));
        personnelAffectRepository.delete(a);
    }

    @Override
    @Transactional
    public void retirerVehicule(String affectationGuid) {
        AffectationVehiculeEntity a = vehiculeAffectRepository.findByGuid(affectationGuid)
                .orElseThrow(() -> new ResourceNotFoundException("AffectationVehicule", affectationGuid));
        vehiculeAffectRepository.delete(a);
    }

    @Override
    @Transactional
    public void retirerMateriel(String affectationGuid) {
        AffectationMaterielEntity a = materielAffectRepository.findByGuid(affectationGuid)
                .orElseThrow(() -> new ResourceNotFoundException("AffectationMateriel", affectationGuid));
        MaterielTraiteurEntity m = a.getMateriel();
        if (m != null && a.getQuantite() != null) {
            int dispo = m.getQuantiteDisponible() != null ? m.getQuantiteDisponible() : 0;
            m.setQuantiteDisponible(dispo + a.getQuantite());
            materielRepository.save(m);
        }
        materielAffectRepository.delete(a);
    }

    private PrestationEntity resolvePrestation(String guid) {
        return prestationRepository.findByGuid(guid)
                .orElseThrow(() -> new ResourceNotFoundException("Prestation", guid));
    }
}
