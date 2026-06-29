package yamrestor.restor.service.impl.administration;

import yamrestor.restor.dto.administration.ParametreDTO;
import yamrestor.restor.dto.request.administration.ParametreRequest;
import yamrestor.restor.entity.administration.ParametreEntity;
import yamrestor.restor.mapper.administration.ParametreMapper;
import yamrestor.restor.repository.administration.ParametreRepository;
import yamrestor.restor.service.administration.ParametreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ParametreServiceImpl implements ParametreService {

    private final ParametreRepository repository;

    @Override
    @Transactional
    public ParametreDTO get() {
        return ParametreMapper.toDTO(getOrCreate());
    }

    @Override
    @Transactional
    public ParametreDTO mettreAJour(ParametreRequest req) {
        ParametreEntity p = getOrCreate();
        p.setNomRestaurant(req.getNomRestaurant());
        p.setSlogan(req.getSlogan());
        p.setEmail(req.getEmail());
        p.setTelephone(req.getTelephone());
        p.setTelephone2(req.getTelephone2());
        p.setAdresse(req.getAdresse());
        p.setVille(req.getVille());
        p.setPays(req.getPays());
        p.setSiteWeb(req.getSiteWeb());
        if (req.getDevise() != null) p.setDevise(req.getDevise());
        if (req.getSymboleDevise() != null) p.setSymboleDevise(req.getSymboleDevise());
        if (req.getTauxTvaDefaut() != null) p.setTauxTvaDefaut(req.getTauxTvaDefaut());
        if (req.getNumerotationAuto() != null) p.setNumerotationAuto(req.getNumerotationAuto());
        if (req.getPrefixeCommande() != null) p.setPrefixeCommande(req.getPrefixeCommande());
        if (req.getPrefixeTicket() != null) p.setPrefixeTicket(req.getPrefixeTicket());
        if (req.getPrefixeFacture() != null) p.setPrefixeFacture(req.getPrefixeFacture());
        if (req.getPrefixeReservation() != null) p.setPrefixeReservation(req.getPrefixeReservation());
        p.setPiedPageTicket(req.getPiedPageTicket());
        p.setConditionsGenerales(req.getConditionsGenerales());
        return ParametreMapper.toDTO(repository.save(p));
    }

    /** Charge la ligne unique de paramètres, en créant une ligne par défaut si nécessaire. */
    private ParametreEntity getOrCreate() {
        return repository.findTopByOrderByIdAsc()
                .orElseGet(() -> repository.save(new ParametreEntity()));
    }
}
