package me.hanwook.testpractice.entity;

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
}
