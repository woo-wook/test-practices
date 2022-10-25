package me.hanwook.testpractice.service;

import me.hanwook.testpractice.entity.*;
import me.hanwook.testpractice.exception.ModelNotAllowOptionException;
import me.hanwook.testpractice.exception.ModelNotFoundException;
import me.hanwook.testpractice.repository.CarRepository;
import me.hanwook.testpractice.repository.ModelRepository;
import me.hanwook.testpractice.repository.OptionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CarServiceTest {

    @Mock
    ModelRepository modelRepository;

    @Mock
    CarRepository carRepository;

    @Mock
    OptionRepository optionRepository;

    @InjectMocks
    CarService carService;

    @Test
    void 차량_생성() {
        // given
        final Long modelId = 1L;
        final CarColor color = CarColor.RED;
        final List<Long> optionIds = List.of(1L, 2L);

        Model model = Model.builder()
                .manufacturer(
                        Manufacturer.builder()
                                .name("현대")
                                .build()
                )
                .name("SONATA")
                .price(100000000)
                .build();

        Option sunroof = Option.builder()
                .name("선루프")
                .price(880000)
                .build();

        Option navigation = Option.builder()
                .name("내비게이션")
                .price(12800000)
                .build();

        ModelAllowOption.builder()
                .model(model)
                .option(sunroof)
                .build();

        ModelAllowOption.builder()
                .model(model)
                .option(navigation)
                .build();

        when(modelRepository.findById(modelId))
                .thenReturn(Optional.of(model));

        when(carRepository.save(any(Car.class)))
                .then(AdditionalAnswers.returnsFirstArg());

        when(optionRepository.findById(1L))
                .thenReturn(Optional.of(sunroof));

        when(optionRepository.findById(2L))
                .thenReturn(Optional.of(navigation));

        // when
        Car result = carService.create(modelId, color, optionIds);

        // then
        assertThat(result.getColor()).isEqualTo(color);
        assertThat(result.getModel()).isEqualTo(model);
        assertThat(result.getOptions()).hasSize(2);
        assertThat(result.getOptions().stream().anyMatch(x -> x.getOption() == sunroof)).isTrue();
        assertThat(result.getOptions().stream().anyMatch(x -> x.getOption() == navigation)).isTrue();
    }

    @Test
    void 차량_생성_비허용_옵션_예외() {
        // given
        final Long modelId = 1L;
        final CarColor color = CarColor.RED;
        final List<Long> optionIds = List.of(1L);

        Model model = Model.builder()
                .manufacturer(
                        Manufacturer.builder()
                                .name("현대")
                                .build()
                )
                .name("SONATA")
                .price(100000000)
                .build();

        Option sunroof = Option.builder()
                .name("선루프")
                .price(880000)
                .build();

        when(modelRepository.findById(modelId))
                .thenReturn(Optional.of(model));

        when(carRepository.save(any(Car.class)))
                .then(AdditionalAnswers.returnsFirstArg());

        when(optionRepository.findById(1L))
                .thenReturn(Optional.of(sunroof));

        // when & then
        assertThatThrownBy(() -> carService.create(modelId, color, optionIds))
                .isInstanceOf(ModelNotAllowOptionException.class);
    }

    @Test
    void 차량_생성_모델_없음_예외() {
        // given
        final Long modelId = 1L;
        final CarColor color = CarColor.RED;

        // when & then
        assertThatThrownBy(() -> carService.create(modelId, color, null))
                .isInstanceOf(ModelNotFoundException.class);
    }

    @Test
    void 차량_모델_검색() {
        // given
        final Long modelId = 1L;

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

        when(carRepository.findByModel(any(Model.class)))
                .thenReturn(List.of(
                        Car.builder()
                                .color(CarColor.BLACK)
                                .model(model)
                                .build(),
                        Car.builder()
                                .color(CarColor.CYAN)
                                .model(model)
                                .build()
                ));

        // when
        List<Car> results = carService.findByModel(modelId);

        // then
        assertThat(results).hasSize(2);
        assertThat(results.stream().allMatch(car -> car.getModel() == model)).isTrue();
        assertThat(results.stream().anyMatch(car -> car.getColor() == CarColor.CYAN)).isTrue();
        assertThat(results.stream().anyMatch(car -> car.getColor() == CarColor.BLACK)).isTrue();
    }

    @Test
    void 차량_모델_검색_모델없음_예외() {
        // given
        final Long modelId = 1L;

        // when & then
        assertThatThrownBy(() -> carService.findByModel(modelId))
                .isInstanceOf(ModelNotFoundException.class);
    }

}