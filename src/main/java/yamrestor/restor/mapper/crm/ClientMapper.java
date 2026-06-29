package yamrestor.restor.mapper.crm;

import yamrestor.restor.dto.crm.ClientDTO;
import yamrestor.restor.entity.crm.ClientEntity;

public class ClientMapper {

    public static ClientDTO toDTO(ClientEntity c) {
        if (c == null) return null;
        return ClientDTO.builder()
                .guid(c.getGuid())
                .nom(c.getNom())
                .telephone(c.getTelephone())
                .email(c.getEmail())
                .adresse(c.getAdresse())
                .dateNaissance(c.getDateNaissance())
                .preferences(c.getPreferences())
                .pointsFidelite(c.getPointsFidelite())
                .remisePourcentage(c.getRemisePourcentage())
                .actif(c.getActif())
                .build();
    }

    private ClientMapper() {}
}
