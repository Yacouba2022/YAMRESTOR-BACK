package yamrestor.restor.controller.crm;

import yamrestor.restor.dto.SelectOptionDTO;
import yamrestor.restor.dto.crm.ClientDTO;
import yamrestor.restor.dto.request.crm.ClientRequest;
import yamrestor.restor.entity.crm.ClientEntity;
import yamrestor.restor.mapper.crm.ClientMapper;
import yamrestor.restor.service.crm.ClientService;
import yamrestor.restor.util.Permissions;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/clients")
@RequiredArgsConstructor
@Tag(name = "Clients")
@SecurityRequirement(name = "bearerAuth")
public class ClientController {

    private final ClientService clientService;

    @GetMapping
    @PreAuthorize("hasAuthority('" + Permissions.CLIENT_CONSULTER + "')")
    public ResponseEntity<Page<ClientDTO>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false, defaultValue = "") String q) {
        return ResponseEntity.ok(clientService.search(q, page, size).map(ClientMapper::toDTO));
    }

    @GetMapping("/{guid}")
    @PreAuthorize("hasAuthority('" + Permissions.CLIENT_CONSULTER + "')")
    public ResponseEntity<ClientDTO> findByGuid(@PathVariable String guid) {
        return ResponseEntity.ok(ClientMapper.toDTO(clientService.findByGuid(guid)));
    }

    @GetMapping("/autocomplete")
    @PreAuthorize("hasAuthority('" + Permissions.CLIENT_CONSULTER + "')")
    public ResponseEntity<List<SelectOptionDTO>> autocomplete(
            @RequestParam(required = false, defaultValue = "") String q,
            @RequestParam(defaultValue = "7") int limit) {
        return ResponseEntity.ok(clientService.autocomplete(q, limit));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('" + Permissions.CLIENT_CREER + "')")
    public ResponseEntity<ClientDTO> creer(@Valid @RequestBody ClientRequest req) {
        ClientEntity c = new ClientEntity();
        apply(c, req);
        return ResponseEntity.status(HttpStatus.CREATED).body(ClientMapper.toDTO(clientService.save(c)));
    }

    @PutMapping("/{guid}")
    @PreAuthorize("hasAuthority('" + Permissions.CLIENT_MODIFIER + "')")
    public ResponseEntity<ClientDTO> modifier(@PathVariable String guid, @Valid @RequestBody ClientRequest req) {
        ClientEntity c = clientService.findByGuid(guid);
        apply(c, req);
        return ResponseEntity.ok(ClientMapper.toDTO(clientService.save(c)));
    }

    @DeleteMapping("/{guid}")
    @PreAuthorize("hasAuthority('" + Permissions.CLIENT_SUPPRIMER + "')")
    public ResponseEntity<Void> supprimer(@PathVariable String guid) {
        clientService.delete(guid);
        return ResponseEntity.noContent().build();
    }

    private void apply(ClientEntity c, ClientRequest req) {
        c.setNom(req.getNom());
        c.setTelephone(req.getTelephone());
        c.setEmail(req.getEmail());
        c.setAdresse(req.getAdresse());
        c.setDateNaissance(req.getDateNaissance());
        c.setPreferences(req.getPreferences());
        c.setRemisePourcentage(req.getRemisePourcentage() != null ? req.getRemisePourcentage() : BigDecimal.ZERO);
        c.setActif(req.getActif() != null ? req.getActif() : Boolean.TRUE);
    }
}
