package me.hanwook.testpractice.repository;

import me.hanwook.testpractice.entity.Manufacturer;
import me.hanwook.testpractice.entity.Model;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ModelRepositoryTest {

    @Autowired
    private ModelRepository modelRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    @Transactional
    public void 제조사_검색() throws Exception {
        // given
        Manufacturer hyundai = Manufacturer.builder()
                .name("현대")
                .build();

        entityManager.persist(hyundai);

        Manufacturer kia = Manufacturer.builder()
                .name("기아")
                .build();

        entityManager.persist(kia);

        modelRepository.save(
                Model.builder()
                        .manufacturer(hyundai)
                        .name("SONATA")
                        .build()
        );

        modelRepository.save(
                Model.builder()
                        .manufacturer(hyundai)
                        .name("AVANTE")
                        .build()
        );

        modelRepository.save(
                Model.builder()
                        .manufacturer(kia)
                        .name("K5")
                        .build()
        );

        // when
        List<Model> hyundaiModels = modelRepository.findByManufacturer(hyundai);

        // then
        assertThat(hyundaiModels.size()).isEqualTo(2);
    }

    @Test
    void 모델명_일치_검색() {
        // given
        final String name = "SONATA";

        Manufacturer hyundai = Manufacturer.builder()
                .name("현대")
                .build();

        entityManager.persist(hyundai);

        modelRepository.save(
                Model.builder()
                        .manufacturer(hyundai)
                        .name(name)
                        .build()
        );

        // when
        boolean result = modelRepository.existsByName(name);

        // then
        assertThat(result).isTrue();
    }
    
    @Test
    void 모델명_불일치_검색(){
        // given
        final String name = "SONATA";

        // when
        boolean result = modelRepository.existsByName(name);

        // then
        assertThat(result).isFalse();
    }
}