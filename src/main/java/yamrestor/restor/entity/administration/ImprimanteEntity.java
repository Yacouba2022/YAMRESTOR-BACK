package yamrestor.restor.entity.administration;

import yamrestor.restor.entity.BaseEntity;
import yamrestor.restor.enums.TypeImprimante;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(name = "imprimantes")
@Getter
@Setter
@NoArgsConstructor
@SQLDelete(sql = "UPDATE imprimantes SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class ImprimanteEntity extends BaseEntity {

    @Column(nullable = false)
    private String nom;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TypeImprimante type;

    /** Adresse réseau (IP) ou nom partagé de l'imprimante. */
    private String adresse;

    private String modele;

    private Boolean actif = true;
}
