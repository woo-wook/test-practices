package me.hanwook.testpractice.repository;

import me.hanwook.testpractice.entity.Manufacturer;
import me.hanwook.testpractice.entity.Model;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ModelRepository extends JpaRepository<Model, Long> {
    List<Model> findByManufacturer(Manufacturer manufacturer);

    boolean existsByName(String name);
}
