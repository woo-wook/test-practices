package me.hanwook.testpractice.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Reservation {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "reservation_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "model_id", foreignKey = @ForeignKey(name = "fk_reservation_01"))
    private Model model;

    /**
     * 예약색상
     */
    @Column(length = 10)
    @Enumerated(EnumType.STRING)
    private CarColor color;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "car_id", foreignKey = @ForeignKey(name = "fk_reservation_02"))
    private Car car;

    /**
     * 고객명
     */
    @Column(length = 100)
    private String customerName;

    /**
     * 차량총액
     */
    private int carPrice;

    /**
     * 옵션총액
     */
    private int optionPrice;

    /**
     * 차량 남겨먹는 금액..
     */
    private int incentive;

    @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReservationOption> options = new ArrayList<>();

    /**
     * 계약 총액
     * @return
     */
    public int getTotalPrice() {
        return this.carPrice + this.optionPrice + this.incentive;
    }

    @Builder
    public Reservation(Model model, CarColor color, Car car, String customerName, int carPrice, int optionPrice, int incentive) {
        this.model = model;
        this.color = color;
        this.car = car;
        this.customerName = customerName;
        this.carPrice = carPrice;
        this.optionPrice = optionPrice;
        this.incentive = incentive;
    }
}
