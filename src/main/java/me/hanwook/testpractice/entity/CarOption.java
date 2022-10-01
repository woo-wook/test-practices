package me.hanwook.testpractice.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class CarOption {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "car_option_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "car_id", foreignKey = @ForeignKey(name = "fk_car_option_01"))
    private Car car;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "option_id", foreignKey = @ForeignKey(name = "fk_car_option_02"))
    private Option option;
}
