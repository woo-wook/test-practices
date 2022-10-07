package me.hanwook.testpractice.repository;

import me.hanwook.testpractice.entity.Option;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OptionRepository extends JpaRepository<Option, Long> {

    /**
     * 이름이 포함되어 있는 옵션 명 검색
     * @param name
     * @return
     */
    List<Option> findByNameContains(String name);
}
