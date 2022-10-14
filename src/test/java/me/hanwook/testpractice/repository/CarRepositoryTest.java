package me.hanwook.testpractice.repository;

import me.hanwook.testpractice.entity.Car;
import me.hanwook.testpractice.entity.Manufacturer;
import me.hanwook.testpractice.entity.Model;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;

import static me.hanwook.testpractice.entity.CarColor.*;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CarRepositoryTest {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    public void 차량_모델_색상_수_조회() throws Exception {
        // given
        Manufacturer hyundai = Manufacturer.builder()
                .name("현대")
                .build();

        entityManager.persist(hyundai);

        Model sonata = Model.builder()
                .manufacturer(hyundai)
                .name("SONATA")
                .build();

        entityManager.persist(sonata);

        Model avante = Model.builder()
                .manufacturer(hyundai)
                .name("AVANTE")
                .build();

        entityManager.persist(avante);

        carRepository.save(
                Car.builder()
                        .model(sonata)
                        .color(RED)
                        .build()
        );

        carRepository.save(
                Car.builder()
                        .model(sonata)
                        .color(RED)
                        .build()
        );

        carRepository.save(
                Car.builder()
                        .model(sonata)
                        .color(BLACK)
                        .build()
        );

        carRepository.save(
                Car.builder()
                        .model(sonata)
                        .color(BLUE)
                        .build()
        );

        carRepository.save(
                Car.builder()
                        .model(avante)
                        .color(RED)
                        .build()
        );

        // when
        long sonataRedCount = carRepository.countByModelAndColor(sonata, RED);
        long sonataBlueCount = carRepository.countByModelAndColor(sonata, BLUE);
        long sonataBlackCount = carRepository.countByModelAndColor(sonata, BLACK);
        long avanteRedCount = carRepository.countByModelAndColor(avante, RED);
        long avanteBlackCount = carRepository.countByModelAndColor(avante, BLACK);

        // then
        assertThat(sonataRedCount).isEqualTo(2);
        assertThat(sonataBlueCount).isEqualTo(1);
        assertThat(sonataBlackCount).isEqualTo(1);
        assertThat(avanteRedCount).isEqualTo(1);
        assertThat(avanteBlackCount).isEqualTo(0);
    }
}