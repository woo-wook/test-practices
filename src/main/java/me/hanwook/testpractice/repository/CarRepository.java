package me.hanwook.testpractice.repository;

import me.hanwook.testpractice.entity.Car;
import me.hanwook.testpractice.entity.CarColor;
import me.hanwook.testpractice.entity.Model;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {

    long countByModelAndColor(Model model, CarColor color);

    List<Car> findByModel(Model model);
}
