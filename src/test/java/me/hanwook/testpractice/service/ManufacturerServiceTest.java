package me.hanwook.testpractice.service;

import me.hanwook.testpractice.entity.Manufacturer;
import me.hanwook.testpractice.exception.ManufacturerDuplicateException;
import me.hanwook.testpractice.repository.ManufacturerRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ManufacturerServiceTest {

    @Mock
    ManufacturerRepository manufacturerRepository;

    @InjectMocks
    ManufacturerService manufacturerService;

    @Test
    void 제조사_생성() {
        // given
        final String name = "현대";

        when(manufacturerRepository.existsByName(name))
                .thenReturn(false);

        when(manufacturerRepository.save(any(Manufacturer.class)))
                .then(AdditionalAnswers.returnsFirstArg());

        // when
        Manufacturer result = manufacturerService.create(name);

        // then
        assertThat(result.getName()).isEqualTo(name);
    }

    @Test
    void 제조사_생성_시_이름_중복_예외() {
        // given
        final String name = "현대";

        when(manufacturerRepository.existsByName(name))
                .thenReturn(true);

        // when & then
        assertThatThrownBy(() -> manufacturerService.create(name))
                .isInstanceOf(ManufacturerDuplicateException.class);
    }

}