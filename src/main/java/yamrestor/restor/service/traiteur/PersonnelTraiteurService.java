package yamrestor.restor.service.traiteur;

import yamrestor.restor.dto.SelectOptionDTO;
import yamrestor.restor.entity.traiteur.PersonnelTraiteurEntity;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PersonnelTraiteurService {
    Page<PersonnelTraiteurEntity> findAll(int page, int size);
    PersonnelTraiteurEntity findByGuid(String guid);
    PersonnelTraiteurEntity save(PersonnelTraiteurEntity personnel);
    void delete(String guid);
    List<PersonnelTraiteurEntity> findActifs();
    List<SelectOptionDTO> autocomplete(String q, int limit);
}
