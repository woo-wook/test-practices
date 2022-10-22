package me.hanwook.testpractice.service;

import lombok.RequiredArgsConstructor;
import me.hanwook.testpractice.entity.Option;
import me.hanwook.testpractice.exception.OptionDuplicateException;
import me.hanwook.testpractice.repository.OptionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OptionService {

    private final OptionRepository optionRepository;

    @Transactional
    public Option create(String name, int price) {
        validateOptionName(name);

        return optionRepository.save(
                Option.builder()
                        .name(name)
                        .price(price)
                        .build()
        );
    }

    private void validateOptionName(String name) {
        if(optionRepository.existsByName(name)) {
            throw new OptionDuplicateException();
        }
    }

}
