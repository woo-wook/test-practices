package me.hanwook.testpractice.service;

import lombok.RequiredArgsConstructor;
import me.hanwook.testpractice.entity.*;
import me.hanwook.testpractice.exception.ModelNotAllowOptionException;
import me.hanwook.testpractice.exception.ModelNotFoundException;
import me.hanwook.testpractice.exception.OptionNotFoundException;
import me.hanwook.testpractice.repository.CarRepository;
import me.hanwook.testpractice.repository.ModelRepository;
import me.hanwook.testpractice.repository.OptionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 차량 서비스
 */
@Service
@RequiredArgsConstructor
public class CarService {

    private final ModelRepository modelRepository;
    private final CarRepository carRepository;
    private final OptionRepository optionRepository;

    /**
     * 차량 생성
     * @param modelId 생성할 차량의 모델명
     * @param color 차량의 색상
     * @param optionIds 옵션의 목록
     * @return 생성된 차량 정보
     */
    @Transactional
    public Car create(Long modelId, CarColor color, List<Long> optionIds) {
        Model model = modelRepository.findById(modelId)
                .orElseThrow(ModelNotFoundException::new);


        Car car = carRepository.save(
                Car.builder()
                        .model(model)
                        .color(color)
                        .build()
        );

        if(!CollectionUtils.isEmpty(optionIds)) {
            addOptions(optionIds, car);
        }

        return car;
    }

    private void addOptions(List<Long> optionIds, Car car) {
        for (Long optionId : optionIds) {
            Option option = optionRepository.findById(optionId).orElseThrow(OptionNotFoundException::new);

            if(!car.getModel().allowsOption(option)) {
                throw new ModelNotAllowOptionException();
            }

            CarOption.builder()
                    .car(car)
                    .option(option)
                    .build();
        }
    }

    /**
     * 모델로 차량 검색
     * @param modelId 모델아이디
     * @return 해당 모델의 전체 차량
     */
    @Transactional(readOnly = true)
    public List<Car> findByModel(Long modelId) {
        Model model = modelRepository.findById(modelId)
                .orElseThrow(ModelNotFoundException::new);

        return carRepository.findByModel(model);
    }
}
