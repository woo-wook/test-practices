package me.hanwook.testpractice.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

/**
 * 차량
 */
@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Car {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "car_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "model_id", foreignKey = @ForeignKey(name = "fk_car_01"))
    private Model model;

    @Column(length = 10)
    @Enumerated(STRING)
    private CarColor color;

    @OneToMany(cascade = ALL, mappedBy = "car", orphanRemoval = true)
    private List<CarOption> options = new ArrayList<>();

    @Builder
    public Car(Model model, CarColor color) {
        this.model = model;
        this.color = color;
    }

    /**
     * 차량의 총 가격을 조회합니다.
     * @return
     */
    public int getPrice() {
        return 0;
    }

    /**
     * 옵션의 가격을 조회합니다.
     * @return
     */
    public int getOptionPrice() {
        return options.stream()
                .mapToInt(option -> option.getOption().getPrice())
                .sum();
    }
}
