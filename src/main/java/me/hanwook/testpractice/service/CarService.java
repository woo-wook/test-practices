package me.hanwook.testpractice.service;

import lombok.RequiredArgsConstructor;
import me.hanwook.testpractice.entity.Car;
import me.hanwook.testpractice.entity.CarColor;
import me.hanwook.testpractice.entity.Model;
import me.hanwook.testpractice.exception.ModelNotFoundException;
import me.hanwook.testpractice.repository.CarRepository;
import me.hanwook.testpractice.repository.ModelRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 차량 서비스
 */
@Service
@RequiredArgsConstructor
public class CarService {

    private final ModelRepository modelRepository;
    private final CarRepository carRepository;

    /**
     * 차량 생성
     * @param modelId 생성할 차량의 모델명
     * @param color 차량의 색상
     * @return 생성된 차량 정보
     */
    @Transactional
    public Car create(Long modelId, CarColor color) {
        Model model = modelRepository.findById(modelId)
                .orElseThrow(ModelNotFoundException::new);

        return carRepository.save(
                Car.builder()
                        .model(model)
                        .color(color)
                        .build()
        );
    }
}
