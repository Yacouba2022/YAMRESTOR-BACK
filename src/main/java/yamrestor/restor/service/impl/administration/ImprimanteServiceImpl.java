package yamrestor.restor.service.impl.administration;

import yamrestor.restor.entity.administration.ImprimanteEntity;
import yamrestor.restor.exception.ResourceNotFoundException;
import yamrestor.restor.repository.administration.ImprimanteRepository;
import yamrestor.restor.service.administration.ImprimanteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ImprimanteServiceImpl implements ImprimanteService {

    private final ImprimanteRepository imprimanteRepository;

    @Override
    public Page<ImprimanteEntity> findAll(int page, int size) {
        return imprimanteRepository.findAll(PageRequest.of(page, size, Sort.by("nom")));
    }

    @Override
    public ImprimanteEntity findByGuid(String guid) {
        return imprimanteRepository.findByGuid(guid)
                .orElseThrow(() -> new ResourceNotFoundException("ImprimanteEntity", guid));
    }

    @Override
    @Transactional
    public ImprimanteEntity save(ImprimanteEntity imprimante) {
        return imprimanteRepository.save(imprimante);
    }

    @Override
    @Transactional
    public void delete(String guid) {
        imprimanteRepository.delete(findByGuid(guid));
    }

    @Override
    public List<ImprimanteEntity> findActives() {
        return imprimanteRepository.findByActifTrue();
    }
}
