package me.hanwook.testpractice.service;

import me.hanwook.testpractice.entity.CarColor;
import me.hanwook.testpractice.entity.Model;
import me.hanwook.testpractice.entity.Reservation;
import me.hanwook.testpractice.exception.ModelNotFoundException;
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

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    @Mock
    ModelRepository modelRepository;

    @Mock
    ReservationRepository reservationRepository;

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
}