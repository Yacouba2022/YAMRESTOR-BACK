package yamrestor.restor.entity.salle;

import yamrestor.restor.entity.BaseEntity;
import yamrestor.restor.enums.StatutReservation;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "reservations")
@Getter
@Setter
@NoArgsConstructor
@SQLDelete(sql = "UPDATE reservations SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class ReservationEntity extends BaseEntity {

    @Column(nullable = false)
    private String clientNom;

    private String clientTelephone;

    @Column(nullable = false)
    private LocalDate dateReservation;

    private LocalTime heure;

    private Integer nombrePersonnes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "table_id")
    private TableEntity table;

    @Column(columnDefinition = "TEXT")
    private String observations;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutReservation statut = StatutReservation.EN_ATTENTE;
}
