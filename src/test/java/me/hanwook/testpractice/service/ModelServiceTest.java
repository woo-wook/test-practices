package me.hanwook.testpractice.service;

import me.hanwook.testpractice.entity.Manufacturer;
import me.hanwook.testpractice.entity.Model;
import me.hanwook.testpractice.entity.Option;
import me.hanwook.testpractice.exception.ManufacturerNotFoundException;
import me.hanwook.testpractice.exception.ModelDuplicateException;
import me.hanwook.testpractice.exception.UnusablePriceException;
import me.hanwook.testpractice.repository.ManufacturerRepository;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ModelServiceTest {
    @Mock
    ManufacturerRepository manufacturerRepository;

    @Mock
    ModelRepository modelRepository;

    @Mock
    OptionRepository optionRepository;

    @InjectMocks
    ModelService modelService;

    @Test
    void 모델_생성_제조사_없음() {
        // given
        Long manufacturerId = 1L;
        String name = "SONATA";
        int price = 100000000;

        // when & then
        assertThatThrownBy(() -> modelService.create(manufacturerId, name, price, null))
                .isInstanceOf(ManufacturerNotFoundException.class);
    }

    @Test
    void 모델_생성_금액_경계값() {
        // given
        Long manufacturerId = 1L;
        String name = "SONATA";

        when(manufacturerRepository.findById(manufacturerId))
                .thenReturn(
                        Optional.of(
                                Manufacturer.builder()
                                        .name("HYUNDAI")
                                        .build()
                        )
                );

        // when & then
        assertThatThrownBy(() -> modelService.create(manufacturerId, name, 9999999, null))
                .isInstanceOf(UnusablePriceException.class);

        assertThatThrownBy(() -> modelService.create(manufacturerId, name, 1000000001, null))
                .isInstanceOf(UnusablePriceException.class);
    }
    
    @Test
    void 모델_생성_중복_테스트() {
        // given
        Long manufacturerId = 1L;
        String name = "SONATA";
        int price = 200000000;

        when(manufacturerRepository.findById(manufacturerId))
                .thenReturn(
                        Optional.of(
                                Manufacturer.builder()
                                        .name("HYUNDAI")
                                        .build()
                        )
                );

        when(modelRepository.existsByManufacturerAndName(any(Manufacturer.class), eq(name)))
                .thenReturn(true);
        
        // when & then
        assertThatThrownBy(() -> modelService.create(manufacturerId, name, price, null))
                .isInstanceOf(ModelDuplicateException.class);
    }
    
    @Test
    void 모델_등록() {
        // given
        Long manufacturerId = 1L;
        String name = "SONATA";
        int price = 200000000;
        List<Long> optionIds = List.of(1L, 2L);

        when(manufacturerRepository.findById(manufacturerId))
                .thenReturn(
                        Optional.of(
                                Manufacturer.builder()
                                        .name("HYUNDAI")
                                        .build()
                        )
                );

        when(modelRepository.existsByManufacturerAndName(any(Manufacturer.class), eq(name)))
                .thenReturn(false);

        when(modelRepository.save(any(Model.class)))
                .then(AdditionalAnswers.returnsFirstArg());

        optionIds.forEach(optionId ->
                when(optionRepository.findById(optionId))
                        .thenReturn(Optional.of(
                                Option.builder()
                                        .name("옵션 " + optionId)
                                        .price(1000000)
                                        .build()
                        ))
        );

        // when
        Model model = modelService.create(manufacturerId, name, price, optionIds);

        // then
        assertThat(model.getName()).isEqualTo(name);
        assertThat(model.getPrice()).isEqualTo(price);
        assertThat(model.getAllowOptions()).hasSize(2);
    }

}