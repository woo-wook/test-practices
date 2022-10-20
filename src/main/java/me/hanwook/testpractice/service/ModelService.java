package me.hanwook.testpractice.service;

import lombok.RequiredArgsConstructor;
import me.hanwook.testpractice.entity.Manufacturer;
import me.hanwook.testpractice.entity.Model;
import me.hanwook.testpractice.exception.ManufacturerNotFoundException;
import me.hanwook.testpractice.exception.ModelDuplicateException;
import me.hanwook.testpractice.exception.UnusablePriceException;
import me.hanwook.testpractice.repository.ManufacturerRepository;
import me.hanwook.testpractice.repository.ModelRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ModelService {

    private final ModelRepository modelRepository;
    private final ManufacturerRepository manufacturerRepository;

    /**
     * 모델 생성
     * @param manufacturerId
     * @param name
     * @param price
     * @return
     */
    @Transactional
    public Model create(Long manufacturerId, String name, int price) {
        Manufacturer manufacturer = manufacturerRepository.findById(manufacturerId)
                .orElseThrow(ManufacturerNotFoundException::new);

        validatePrice(price);
        validateModelName(name, manufacturer);

        return modelRepository.save(
            Model.builder()
                    .manufacturer(manufacturer)
                    .name(name)
                    .price(price)
                    .build()
        );
    }

    private void validateModelName(String name, Manufacturer manufacturer) {
        if(modelRepository.existsByManufacturerAndName(manufacturer, name)) {
            throw new ModelDuplicateException();
        }
    }

    /**
     * 모델 금액 검증
     * @param price
     */
    private void validatePrice(int price) {
        if(price < 10000000 || price > 1000000000) {
            throw new UnusablePriceException();
        }
    }
}
