package yamrestor.restor.service.administration;

import yamrestor.restor.entity.administration.ImprimanteEntity;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ImprimanteService {
    Page<ImprimanteEntity> findAll(int page, int size);
    ImprimanteEntity findByGuid(String guid);
    ImprimanteEntity save(ImprimanteEntity imprimante);
    void delete(String guid);
    List<ImprimanteEntity> findActives();
}
