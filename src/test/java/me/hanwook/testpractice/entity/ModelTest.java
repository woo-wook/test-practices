package me.hanwook.testpractice.entity;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class ModelTest {

    @Test
    public void 모델의_차량_총_대수_조회() throws Exception {
        // given
        Model avante = Model.builder()
                .name("아반떼")
                .price(100000)
                .build();

        avante.getCars().add(
            Car.builder()
                    .model(avante)
                    .color(CarColor.BLACK)
                    .build()
        );

        avante.getCars().add(
                Car.builder()
                        .model(avante)
                        .color(CarColor.CYAN)
                        .build()
        );

        avante.getCars().add(
                Car.builder()
                        .model(avante)
                        .color(CarColor.RED)
                        .build()
        );

        avante.getCars().add(
                Car.builder()
                        .model(avante)
                        .color(CarColor.BLUE)
                        .build()
        );

        // when
        int count = avante.getTotalCarCount();

        // then
        assertThat(count).isEqualTo(4);
    }
}