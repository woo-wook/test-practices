package me.hanwook.testpractice.service;

import me.hanwook.testpractice.entity.Manufacturer;
import me.hanwook.testpractice.exception.UnusablePriceException;
import me.hanwook.testpractice.repository.ManufacturerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ModelServiceTest {
    @Mock
    ManufacturerRepository manufacturerRepository;
    @InjectMocks
    ModelService modelService;

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
        assertThatThrownBy(() -> modelService.create(manufacturerId, name, 9999999))
                .isInstanceOf(UnusablePriceException.class);

        assertThatThrownBy(() -> modelService.create(manufacturerId, name, 1000000001))
                .isInstanceOf(UnusablePriceException.class);
    }

}