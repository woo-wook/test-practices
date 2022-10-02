package me.hanwook.testpractice.entity;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class CarTest {

    @Test
    public void 차량_옵션_총액_조회() throws Exception {
        // given
        Model avante = Model.builder()
                .name("아반떼")
                .price(21000000)
                .build();

        Car car = Car.builder()
                .color(CarColor.RED)
                .model(avante)
                .build();

        addCarOption(car, makeOption("파노라마선루프", 100000));
        addCarOption(car, makeOption("내비게이션", 780000));

        // when
        int optionPrice = car.getOptionPrice();

        // then
        assertThat(optionPrice).isEqualTo(880000);
    }

    @Test
    public void 차량_총액_조회() throws Exception {
        // given
        Model sonata = Model.builder()
                .name("소나타")
                .price(3330000)
                .build();

        Car car = Car.builder()
                .color(CarColor.BLUE)
                .model(sonata)
                .build();

        addCarOption(car, makeOption("파노라마선루프", 300000));
        addCarOption(car, makeOption("내비게이션", 990000));
        addCarOption(car, makeOption("통풍시트", 2100000));

        // when
        int price = car.getPrice();

        // then
        assertThat(price).isEqualTo(6720000);
    }

    @Test
    public void 차량_옵션_총_수_조회() throws Exception {
        // given
        Model grandeur = Model.builder()
                .name("그랜저")
                .price(40000000)
                .build();

        Car car = Car.builder()
                .color(CarColor.BLACK)
                .model(grandeur)
                .build();

        addCarOption(car, makeOption("통풍시트", 2100000));
        addCarOption(car, makeOption("Head-up display", 1200000));

        // when
        int count = car.getOptionCount();

        // then
        assertThat(count).isEqualTo(2);
    }
    
    private Option makeOption(String name, int price) {
        return Option.builder()
                .name(name)
                .price(price)
                .build();
    }

    private void addCarOption(Car car, Option sunroof) {
        CarOption carOption = CarOption.builder()
                .car(car)
                .option(sunroof)
                .build();

        car.getOptions().add(carOption);
    }
}