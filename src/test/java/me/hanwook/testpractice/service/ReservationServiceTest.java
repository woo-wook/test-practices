package me.hanwook.testpractice.service;

import me.hanwook.testpractice.entity.*;
import me.hanwook.testpractice.exception.ModelNotFoundException;
import me.hanwook.testpractice.exception.ReservationNotFoundException;
import me.hanwook.testpractice.repository.ModelRepository;
import me.hanwook.testpractice.repository.ReservationRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    @Mock
    ModelRepository modelRepository;

    @Mock
    ReservationRepository reservationRepository;

    @Mock
    CarService carService;

    @InjectMocks
    ReservationService reservationService;

    @Test
    void 예약_생성() {
        // given
        Long modelId = 1L;
        CarColor color = CarColor.RED;
        String customerName = "Ryu Hanwook";
        int carPrice = 200000;
        int optionPrice = 30000;
        int incentive = 10000;

        when(modelRepository.findById(modelId))
                .thenReturn(Optional.of(
                        Model.builder()
                                .name("모델1")
                                .price(10000)
                                .build()
                ));

        when(reservationRepository.save(any(Reservation.class)))
                .then(AdditionalAnswers.returnsFirstArg());


        // when
        Reservation result = reservationService.reservation(modelId, color, customerName, carPrice, optionPrice, incentive);

        // then
        assertThat(result.getColor()).isEqualTo(color);
        assertThat(result.getCustomerName()).isEqualTo(customerName);
        assertThat(result.getCarPrice()).isEqualTo(carPrice);
        assertThat(result.getOptionPrice()).isEqualTo(optionPrice);
        assertThat(result.getIncentive()).isEqualTo(incentive);
        assertThat(result.getTotalPrice()).isEqualTo(carPrice + optionPrice + incentive);
    }

    @Test
    void 예약_생성_모델없음_예외() {
        // given
        Long modelId = 1L;
        CarColor color = CarColor.RED;
        String customerName = "Ryu Hanwook";
        int carPrice = 200000;
        int optionPrice = 30000;
        int incentive = 10000;

        // when & then
        assertThatThrownBy(() -> reservationService.reservation(modelId, color, customerName, carPrice, optionPrice, incentive))
                .isInstanceOf(ModelNotFoundException.class);
    }

    @Test
    void 예약_취소() {
        // given
        Long reservationId = 1L;

        when(reservationRepository.findById(reservationId))
                .thenReturn(
                        Optional.ofNullable(
                                Reservation.builder()
                                        .incentive(10000)
                                        .optionPrice(10000)
                                        .carPrice(10000)
                                        .build()
                        )
                );

        // when
        Reservation result = reservationService.cancel(reservationId);

        // then
        assertThat(result.getStatus()).isEqualTo(ReservationStatus.CANCEL);
    }
    
    @Test
    void 예약_취소_예약없음_예외() {
        // given
        Long reservationId = 1L;
        
        // when & then
        assertThatThrownBy(() -> reservationService.cancel(reservationId))
                .isInstanceOf(ReservationNotFoundException.class);
    }

    @Test
    void 예약_출고() {
        // given
        Long reservationId = 1L;

        Model model = Model.builder()
                .price(1000000)
                .name("SONATA")
                .build();

        Reservation reservation = Reservation.builder()
                .model(model)
                .color(CarColor.BLACK)
                .build();

        when(reservationRepository.findById(reservationId))
                .thenReturn(Optional.ofNullable(reservation));

        when(carService.create(eq(null), any(CarColor.class), any(List.class)))
                .thenReturn(
                        Car.builder()
                                .model(model)
                                .color(reservation.getColor())
                                .build()
                );

        // when
        Reservation result = reservationService.delivery(reservationId);
        Car car = result.getCar();

        // then
        assertThat(result.getStatus()).isEqualTo(ReservationStatus.DELIVERY);
        assertThat(car.getColor()).isEqualTo(reservation.getColor());
    }
    
    @Test
    void 예약_출고_예약없음_예외() {
        // given
        Long reservationId = 1L;

        // when & then
        assertThatThrownBy(() -> reservationService.delivery(reservationId))
                .isInstanceOf(ReservationNotFoundException.class);
    }
}