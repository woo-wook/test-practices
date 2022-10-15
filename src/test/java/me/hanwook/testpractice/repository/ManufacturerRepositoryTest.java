package me.hanwook.testpractice.repository;

import me.hanwook.testpractice.entity.Manufacturer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ManufacturerRepositoryTest {

    @Autowired
    ManufacturerRepository manufacturerRepository;

    @Test
    public void 제조사_이름_포함_검색() throws Exception {
        // given
        manufacturerRepository.save(
                Manufacturer.builder()
                        .name("현대")
                        .build()
        );

        manufacturerRepository.save(
                Manufacturer.builder()
                        .name("기아")
                        .build()
        );

        manufacturerRepository.save(
                Manufacturer.builder()
                        .name("현대차")
                        .build()
        );

        manufacturerRepository.save(
                Manufacturer.builder()
                        .name("기알")
                        .build()
        );

        manufacturerRepository.save(
                Manufacturer.builder()
                        .name("현기차")
                        .build()
        );

        // when
        List<Manufacturer> manufacturers1 = manufacturerRepository.findByNameContains("차");
        List<Manufacturer> manufacturers2 = manufacturerRepository.findByNameContains("현대");
        List<Manufacturer> manufacturers3 = manufacturerRepository.findByNameContains("기");
        List<Manufacturer> manufacturers4 = manufacturerRepository.findByNameContains("현");

        // then
        assertThat(manufacturers1).hasSize(2);
        assertThat(manufacturers2).hasSize(2);
        assertThat(manufacturers3).hasSize(3);
        assertThat(manufacturers4).hasSize(3);
    }
}