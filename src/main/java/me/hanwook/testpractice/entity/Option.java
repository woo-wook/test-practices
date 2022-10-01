package me.hanwook.testpractice.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
}
