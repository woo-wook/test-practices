package me.hanwook.testpractice.entity;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ManufacturerTest {

    @Test
    public void 제조사_모델_총수() throws Exception {
        // given
        Manufacturer hyundai = Manufacturer.builder()
                .name("현대")
                .build();

        hyundai.getModels().add(
            Model.builder()
                    .manufacturer(hyundai)
                    .name("아반떼")
                    .price(1500000)
                    .build()
        );

        hyundai.getModels().add(
                Model.builder()
                        .manufacturer(hyundai)
                        .name("소나타")
                        .price(2000000)
                        .build()
        );

        hyundai.getModels().add(
                Model.builder()
                        .manufacturer(hyundai)
                        .name("그랜져")
                        .price(3500000)
                        .build()
        );

        // when
        int count = hyundai.getTotalModelCount();
        
        // then
        assertThat(count).isEqualTo(3);
    }
}