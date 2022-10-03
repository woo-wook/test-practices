package me.hanwook.testpractice.entity;

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
    
    @Test
    public void 모델_허용_옵션_여부() throws Exception {
        // given
        Model sonata = Model.builder()
                .name("소나타")
                .price(30000000)
                .build();

        Option sunroof = Option.builder()
                .name("선루프")
                .price(200000)
                .build();

        Option navigation = Option.builder()
                .name("내비게이션")
                .price(1800000)
                .build();

        sonata.getAllowOptions().add(
                ModelAllowOption.builder()
                        .model(sonata)
                        .option(sunroof)
                        .build()
        );

        // when
        boolean allowSunroof = sonata.allowsOption(sunroof);
        boolean allowNavigation = sonata.allowsOption(navigation);

        // then
        assertThat(allowSunroof).isTrue();
        assertThat(allowNavigation).isFalse();
    }
}