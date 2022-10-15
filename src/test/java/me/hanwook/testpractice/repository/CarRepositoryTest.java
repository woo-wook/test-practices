package me.hanwook.testpractice.repository;

import me.hanwook.testpractice.entity.Car;
import me.hanwook.testpractice.entity.Manufacturer;
import me.hanwook.testpractice.entity.Model;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import java.util.List;

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
    
    @Test
    public void 차량_모델별_목록_조회() throws Exception {
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
                        .color(BLUE)
                        .build()
        );

        carRepository.save(
                Car.builder()
                        .model(sonata)
                        .color(RED)
                        .build()
        );

        // when
        List<Car> sonatas = carRepository.findByModel(sonata);
        List<Car> avantes = carRepository.findByModel(avante);

        // then
        assertThat(sonatas).hasSize(2);
        assertThat(avantes).hasSize(0);
        assertThat(sonatas.stream().filter(x -> x.getColor() == BLUE).count()).isEqualTo(1);
        assertThat(sonatas.stream().filter(x -> x.getColor() == RED).count()).isEqualTo(1);
    }
}