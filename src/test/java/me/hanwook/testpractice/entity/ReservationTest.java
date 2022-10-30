package me.hanwook.testpractice.entity;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ReservationTest {

    @Test
    void 예약_총액_조회() {
        // given
        Reservation reservation = Reservation.builder()
                .carPrice(1000000)
                .optionPrice(200000000)
                .incentive(30000000)
                .build();

        // when
        int result = reservation.getTotalPrice();

        // then
        assertThat(result).isEqualTo(231000000);
    }

    @Test
    void 예약_취소() {
        // given
        Reservation reservation = Reservation.builder()
                .build();

        // when
        reservation.cancel();

        // then
        assertThat(reservation.getStatus()).isEqualTo(ReservationStatus.CANCEL);
    }

    @Test
    void 차량_출고() {
        // given
        Reservation reservation = Reservation.builder()
                .build();

        Car car = Car.builder()
                .color(CarColor.BLACK)
                .build();

        // when
        reservation.delivery(car);

        // then
        assertThat(reservation.getCar()).isEqualTo(car);
        assertThat(reservation.getStatus()).isEqualTo(ReservationStatus.DELIVERY);
    }
}