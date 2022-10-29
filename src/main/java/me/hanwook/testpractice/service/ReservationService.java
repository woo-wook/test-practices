package me.hanwook.testpractice.service;

import lombok.RequiredArgsConstructor;
import me.hanwook.testpractice.entity.CarColor;
import me.hanwook.testpractice.entity.Model;
import me.hanwook.testpractice.entity.Reservation;
import me.hanwook.testpractice.exception.ModelNotFoundException;
import me.hanwook.testpractice.repository.ModelRepository;
import me.hanwook.testpractice.repository.ReservationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ModelRepository modelRepository;

    /**
     * 차량 예약
     * @return
     */
    @Transactional
    public Reservation reservation(Long modelId, CarColor color, String customerName, int carPrice, int optionPrice, int incentive) {
        Model model = modelRepository.findById(modelId)
                .orElseThrow(ModelNotFoundException::new);

        return reservationRepository.save(
                Reservation.builder()
                        .model(model)
                        .color(color)
                        .customerName(customerName)
                        .carPrice(carPrice)
                        .optionPrice(optionPrice)
                        .incentive(incentive)
                        .build()
        );
    }
}
