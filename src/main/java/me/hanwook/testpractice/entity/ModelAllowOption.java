package me.hanwook.testpractice.entity;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

/**
 * 모델별 허용 옵션
 */
@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class ModelAllowOption {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "model_allow_option_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "model_id", foreignKey = @ForeignKey(name = "fk_model_allow_option_01"))
    private Model model;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "option_id", foreignKey = @ForeignKey(name = "fk_model_allow_option_02"))
    private Option option;

    @Builder
    public ModelAllowOption(Model model, Option option) {
        this.model = model;
        this.option = option;

        this.model.getAllowOptions().add(this);
    }
}
