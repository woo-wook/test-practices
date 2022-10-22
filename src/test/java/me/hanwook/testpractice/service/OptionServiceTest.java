package me.hanwook.testpractice.service;

import me.hanwook.testpractice.entity.Option;
import me.hanwook.testpractice.exception.OptionDuplicateException;
import me.hanwook.testpractice.repository.OptionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OptionServiceTest {

    @Mock
    OptionRepository optionRepository;

    @InjectMocks
    OptionService optionService;

    @Test
    void 옵션_생성() {
        // given
        final String name = "선루프";
        final int price = 100000;

        when(optionRepository.existsByName(name))
                .thenReturn(false);

        when(optionRepository.save(any(Option.class)))
                .then(AdditionalAnswers.returnsFirstArg());

        // when
        Option result = optionService.create(name, price);

        // then
        assertThat(result.getName()).isEqualTo(name);
        assertThat(result.getPrice()).isEqualTo(price);
    }

    @Test
    void 옵션_생성_중_중복_이름_존재_예외() {
        // given
        final String name = "선루프";
        final int price = 100000;

        when(optionRepository.existsByName(name))
                .thenReturn(true);

        // when & then
        assertThatThrownBy(() -> optionService.create(name, price))
                .isInstanceOf(OptionDuplicateException.class);
    }
}