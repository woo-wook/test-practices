package me.hanwook.testpractice.controller;

import lombok.RequiredArgsConstructor;
import me.hanwook.testpractice.dto.param.ManufacturerCreateParam;
import me.hanwook.testpractice.entity.Manufacturer;
import me.hanwook.testpractice.service.ManufacturerService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/apis/v1/manufacturers")
public class ManufacturerController {

    private final ManufacturerService manufacturerService;

    @PostMapping
    public ResponseEntity<Void> create(@Validated @RequestBody ManufacturerCreateParam param) {

        Manufacturer result = manufacturerService.create(param.getName());

        return ResponseEntity
                .created(URI.create("/apis/v1/manufacturers/" + result.getId()))
                .build();
    }
}
