package me.hanwook.testpractice.service;

import lombok.RequiredArgsConstructor;
import me.hanwook.testpractice.entity.Manufacturer;
import me.hanwook.testpractice.exception.ManufacturerDuplicateException;
import me.hanwook.testpractice.repository.ManufacturerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ManufacturerService {

    private final ManufacturerRepository manufacturerRepository;

    @Transactional
    public Manufacturer create(String name) {
        validManufacturerName(name);

        return manufacturerRepository.save(
                Manufacturer.builder()
                        .name(name)
                        .build()
        );
    }

    private void validManufacturerName(String name) {
        if(manufacturerRepository.existsByName(name)) {
            throw new ManufacturerDuplicateException();
        }
    }
}
