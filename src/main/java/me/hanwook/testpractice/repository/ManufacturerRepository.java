package me.hanwook.testpractice.repository;

import me.hanwook.testpractice.entity.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ManufacturerRepository extends JpaRepository<Manufacturer, Long> {

    /**
     * 이름에 포함되어 있는 제조사명 검색
     * @param name 제조사명
     * @return 해당 제조사명이 포함되어 있는 제조사 목록
     */
    List<Manufacturer> findByNameContains(String name);
}
