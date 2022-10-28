package me.hanwook.testpractice.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class ReservationOption {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "reservation_option_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "car_id", foreignKey = @ForeignKey(name = "fk_reservation_option_01"))
    private Reservation reservation;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "option_id", foreignKey = @ForeignKey(name = "fk_reservation_option_02"))
    private Option option;

    @Builder
    public ReservationOption(Reservation reservation, Option option) {
        this.reservation = reservation;
        this.option = option;

        this.reservation.getOptions().add(this);
    }
}
