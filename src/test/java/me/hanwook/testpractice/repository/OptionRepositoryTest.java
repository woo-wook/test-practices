package me.hanwook.testpractice.repository;

import me.hanwook.testpractice.entity.Option;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class OptionRepositoryTest {

    @Autowired
    OptionRepository optionRepository;

    @Test
    void 옵션_검색() throws Exception {
        // given
        optionRepository.save(
                Option.builder()
                        .name("내비게이션")
                        .price(100000)
                        .build()
        );

        optionRepository.save(
                Option.builder()
                        .name("선루프")
                        .price(300000)
                        .build()
        );

        optionRepository.save(
                Option.builder()
                        .name("파노라마선루프")
                        .price(200000)
                        .build()
        );

        String name = "선루프";


        // when
        List<Option> result = optionRepository.findByNameContains(name);

        // then
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.stream().allMatch(x -> x.getName().contains(name))).isTrue();
    }

    @Test
    void 동일한_이름의_옵션_존재하는지_확인() {
        // given
        final String name = "내비게이션";

        optionRepository.save(
                Option.builder()
                        .name(name)
                        .price(100000)
                        .build()
        );

        // when
        boolean navigation = optionRepository.existsByName(name);
        boolean sunroof = optionRepository.existsByName("선루프");

        // then
        assertThat(navigation).isTrue();
        assertThat(sunroof).isFalse();
    }
}