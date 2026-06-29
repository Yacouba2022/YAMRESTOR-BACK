package yamrestor.restor.repository.administration;

import yamrestor.restor.entity.administration.ProfilEntity;
import yamrestor.restor.entity.administration.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Override
    @EntityGraph(attributePaths = {"profil", "profil.roles", "profil.roles.permissions", "profil.permissionsSupplementaires"})
    Page<UserEntity> findAll(Pageable pageable);

    @EntityGraph(attributePaths = {"profil", "profil.roles", "profil.roles.permissions", "profil.permissionsSupplementaires"})
    Optional<UserEntity> findByGuid(String guid);

    @EntityGraph(attributePaths = {"profil", "profil.roles", "profil.roles.permissions", "profil.permissionsSupplementaires"})
    Optional<UserEntity> findByEmail(String email);

    boolean existsByEmail(String email);

    /** Nombre d'utilisateurs (non supprimés) assignés à un profil donné. */
    long countByProfil(ProfilEntity profil);

    /** Utilisateurs actifs possédant un rôle au code donné (ex. {@code super_admin}). */
    @Query("SELECT DISTINCT u FROM UserEntity u JOIN u.profil p JOIN p.roles r "
         + "WHERE r.code = :roleCode AND u.etat = 'actif'")
    List<UserEntity> findActifsByRoleCode(@Param("roleCode") String roleCode);
}
