package me.hanwook.testpractice.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

/**
 * 제조사
 */
@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Manufacturer {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "manufacturer_id")
    private Long id;

    @Column(length = 50)
    private String name;

    @OneToMany(cascade = ALL, mappedBy = "manufacturer", orphanRemoval = true)
    private List<Model> models = new ArrayList<>();
}
