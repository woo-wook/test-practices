package me.hanwook.testpractice.service;

import me.hanwook.testpractice.entity.Car;
import me.hanwook.testpractice.entity.CarColor;
import me.hanwook.testpractice.entity.Manufacturer;
import me.hanwook.testpractice.entity.Model;
import me.hanwook.testpractice.repository.CarRepository;
import me.hanwook.testpractice.repository.ModelRepository;
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
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CarServiceTest {

    @Mock
    ModelRepository modelRepository;

    @Mock
    CarRepository carRepository;

    @InjectMocks
    CarService carService;

    @Test
    void 차량_생성() {
        // given
        final Long modelId = 1L;
        final CarColor color = CarColor.RED;

        Model model = Model.builder()
                .manufacturer(
                        Manufacturer.builder()
                                .name("현대")
                                .build()
                )
                .name("SONATA")
                .price(100000000)
                .build();

        when(modelRepository.findById(modelId))
                .thenReturn(Optional.of(model));

        when(carRepository.save(any(Car.class)))
                .then(AdditionalAnswers.returnsFirstArg());

        // when
        Car result = carService.create(modelId, color);

        // then
        assertThat(result.getColor()).isEqualTo(color);
        assertThat(result.getModel()).isEqualTo(model);
    }

}