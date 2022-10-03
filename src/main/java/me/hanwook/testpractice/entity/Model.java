package me.hanwook.testpractice.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Model {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "model_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "manufacturer_id", foreignKey = @ForeignKey(name = "fk_model_01"))
    private Manufacturer manufacturer;

    @Column(length = 200)
    private String name;

    private int price;

    @OneToMany(cascade = ALL, mappedBy = "model", orphanRemoval = true)
    private List<Car> cars = new ArrayList<>();

    @OneToMany(cascade = ALL, mappedBy = "model", orphanRemoval = true)
    private List<ModelAllowOption> allowOptions = new ArrayList<>();

    @Builder
    public Model(Manufacturer manufacturer, String name, int price) {
        this.manufacturer = manufacturer;
        this.name = name;
        this.price = price;
    }

    /**
     * 차량을 총 대수를 조회합니다.
     * @return
     */
    public int getTotalCarCount() {
        return cars.size();
    }

    /**
     * 옵션의 허용 여부를 조회합니다.
     * @param option
     * @return
     */
    public boolean allowsOption(Option option) {
        return allowOptions.stream()
                .anyMatch(x -> x.getOption().equals(option));
    }
}
