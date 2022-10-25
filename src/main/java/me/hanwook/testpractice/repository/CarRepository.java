package me.hanwook.testpractice.repository;

import me.hanwook.testpractice.entity.Car;
import me.hanwook.testpractice.entity.CarColor;
import me.hanwook.testpractice.entity.Model;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {

    /**
     * 모델과 컬러로 카운트 조회
     * @param model
     * @param color
     * @return
     */
    long countByModelAndColor(Model model, CarColor color);

    /**
     * 모델별 차량 전체 조회
     * @param model
     * @return
     */
    List<Car> findByModel(Model model);
}
