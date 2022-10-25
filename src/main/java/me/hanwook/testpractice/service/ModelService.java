package me.hanwook.testpractice.service;

import lombok.RequiredArgsConstructor;
import me.hanwook.testpractice.entity.Manufacturer;
import me.hanwook.testpractice.entity.Model;
import me.hanwook.testpractice.entity.ModelAllowOption;
import me.hanwook.testpractice.exception.*;
import me.hanwook.testpractice.repository.ManufacturerRepository;
import me.hanwook.testpractice.repository.ModelRepository;
import me.hanwook.testpractice.repository.OptionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ModelService {

    private final ModelRepository modelRepository;
    private final ManufacturerRepository manufacturerRepository;
    private final OptionRepository optionRepository;

    /**
     * 모델 생성
     * @param manufacturerId
     * @param name
     * @param price
     * @return
     */
    @Transactional
    public Model create(Long manufacturerId, String name, int price, List<Long> optionIds) {
        Manufacturer manufacturer = manufacturerRepository.findById(manufacturerId)
                .orElseThrow(ManufacturerNotFoundException::new);

        validatePrice(price);
        validateModelName(name, manufacturer);

        Model model = modelRepository.save(
                Model.builder()
                        .manufacturer(manufacturer)
                        .name(name)
                        .price(price)
                        .build()
        );

        if(!CollectionUtils.isEmpty(optionIds)) {
            addOptions(model, optionIds);
        }

        return model;
    }

    private void addOptions(Model model, List<Long> optionIds) {
        optionIds.forEach(optionId ->
                ModelAllowOption.builder()
                        .model(model)
                        .option(optionRepository.findById(optionId).orElseThrow(OptionNotFoundException::new))
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
