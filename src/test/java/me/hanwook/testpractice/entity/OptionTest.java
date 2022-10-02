package me.hanwook.testpractice.entity;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class OptionTest {

    @Test
    public void 옵션_총_대수_조회() throws Exception {
        // given
        Option option = Option.builder()
                .name("선루프")
                .price(20000000)
                .build();

        addCar(option);
        addCar(option);
        addCar(option);

        // when
        int count = option.getTotalCarCount();

        // then
        assertThat(count).isEqualTo(3);
    }

    private void addCar(Option option) {
        Car car = Car.builder()
                .color(CarColor.RED)
                .build();

        option.getCars().add(
                CarOption.builder()
                    .option(option)
                    .car(car)
                    .build()
        );
    }
}