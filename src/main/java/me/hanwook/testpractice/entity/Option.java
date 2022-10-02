package me.hanwook.testpractice.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

/**
 * 차량 옵션
 */
@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Option {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "option_id")
    private Long id;

    @Column(length = 100)
    private String name;

    private int price;

    @OneToMany(cascade = ALL, mappedBy = "option", orphanRemoval = true)
    private List<CarOption> cars = new ArrayList<>();

    @Builder
    public Option(String name, int price) {
        this.name = name;
        this.price = price;
    }

    /**
     * 해당 옵션을 사용하는 차의 대수를 조회합니다.
     * @return
     */
    public int getTotalCarCount() {
        return cars.size();
    }

    /**
     * 해당 옵션의 전체 판매 금액을 조회합니다.
     * @return
     */
    public int getTotalPrice() {
        return price * getTotalCarCount();
    }
}
