package yamrestor.restor.service.impl.location;

import yamrestor.restor.dto.request.location.ContratLocationRequest;
import yamrestor.restor.entity.location.ContratLocationEntity;
import yamrestor.restor.entity.location.ContratLocationLigneEntity;
import yamrestor.restor.entity.location.MaterielLocationEntity;
import yamrestor.restor.enums.StatutContratLocation;
import yamrestor.restor.exception.BadRequestException;
import yamrestor.restor.exception.ResourceNotFoundException;
import yamrestor.restor.repository.location.ContratLocationRepository;
import yamrestor.restor.repository.location.MaterielLocationRepository;
import yamrestor.restor.service.location.ContratLocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class ContratLocationServiceImpl implements ContratLocationService {

    private final ContratLocationRepository contratRepository;
    private final MaterielLocationRepository materielRepository;

    @Override
    public Page<ContratLocationEntity> search(StatutContratLocation statut, int page, int size) {
        return contratRepository.search(statut, PageRequest.of(page, size));
    }

    @Override
    public ContratLocationEntity findByGuid(String guid) {
        return contratRepository.findByGuid(guid)
                .orElseThrow(() -> new ResourceNotFoundException("ContratLocationEntity", guid));
    }

    @Override
    @Transactional
    public ContratLocationEntity creerDepuisRequest(ContratLocationRequest req) {
        ContratLocationEntity c = new ContratLocationEntity();
        c.setClientNom(req.getClientNom());
        c.setClientTelephone(req.getClientTelephone());
        c.setDateDebut(req.getDateDebut());
        c.setDateFin(req.getDateFin());
        c.setObservations(req.getObservations());
        c.setStatut(StatutContratLocation.RESERVE);

        BigDecimal total = BigDecimal.ZERO;
        BigDecimal cautionTotale = BigDecimal.ZERO;

        for (ContratLocationRequest.LigneRequest lr : req.getLignes()) {
            MaterielLocationEntity m = materielRepository.findByGuid(lr.getMaterielGuid())
                    .orElseThrow(() -> new ResourceNotFoundException("MaterielLocation", lr.getMaterielGuid()));
            int qte = lr.getQuantite() != null ? lr.getQuantite() : 1;
            int dispo = m.getQuantiteDisponible() != null ? m.getQuantiteDisponible() : 0;
            if (qte > dispo) {
                throw new BadRequestException("Stock de location insuffisant pour « " + m.getNom()
                        + " » : demandé " + qte + ", disponible " + dispo + ".");
            }
            // Réserve le matériel.
            m.setQuantiteDisponible(dispo - qte);
            materielRepository.save(m);

            BigDecimal prix = lr.getPrixUnitaire() != null ? lr.getPrixUnitaire()
                    : (m.getPrixLocation() != null ? m.getPrixLocation() : BigDecimal.ZERO);
            BigDecimal cautionUnit = m.getCaution() != null ? m.getCaution() : BigDecimal.ZERO;

            ContratLocationLigneEntity ligne = new ContratLocationLigneEntity();
            ligne.setContratLocation(c);
            ligne.setMateriel(m);
            ligne.setQuantite(qte);
            ligne.setPrixUnitaire(prix);
            ligne.setMontantLigne(prix.multiply(BigDecimal.valueOf(qte)));
            ligne.setCaution(cautionUnit.multiply(BigDecimal.valueOf(qte)));
            c.getLignes().add(ligne);

            total = total.add(ligne.getMontantLigne());
            cautionTotale = cautionTotale.add(ligne.getCaution());
        }
        c.setMontantTotal(total);
        c.setCautionTotale(cautionTotale);

        ContratLocationEntity saved = contratRepository.save(c);
        if (saved.getNumero() == null) {
            saved.setNumero("LOC-" + String.format("%05d", saved.getId()));
            saved = contratRepository.save(saved);
        }
        return saved;
    }

    @Override
    @Transactional
    public ContratLocationEntity retourner(String guid) {
        ContratLocationEntity c = findByGuid(guid);
        if (c.getStatut() == StatutContratLocation.RETOURNE) {
            throw new BadRequestException("Ce contrat a déjà été retourné.");
        }
        if (c.getStatut() == StatutContratLocation.ANNULE) {
            throw new BadRequestException("Un contrat annulé ne peut pas être retourné.");
        }
        libererMateriel(c);
        c.setStatut(StatutContratLocation.RETOURNE);
        return contratRepository.save(c);
    }

    @Override
    @Transactional
    public ContratLocationEntity annuler(String guid) {
        ContratLocationEntity c = findByGuid(guid);
        if (c.getStatut() == StatutContratLocation.RETOURNE) {
            throw new BadRequestException("Un contrat retourné ne peut pas être annulé.");
        }
        if (c.getStatut() != StatutContratLocation.ANNULE) {
            libererMateriel(c);
        }
        c.setStatut(StatutContratLocation.ANNULE);
        return contratRepository.save(c);
    }

    /** Remet en disponibilité les quantités réservées par le contrat. */
    private void libererMateriel(ContratLocationEntity c) {
        for (ContratLocationLigneEntity l : c.getLignes()) {
            MaterielLocationEntity m = l.getMateriel();
            if (m != null && l.getQuantite() != null) {
                int dispo = m.getQuantiteDisponible() != null ? m.getQuantiteDisponible() : 0;
                m.setQuantiteDisponible(dispo + l.getQuantite());
                materielRepository.save(m);
            }
        }
    }
}
