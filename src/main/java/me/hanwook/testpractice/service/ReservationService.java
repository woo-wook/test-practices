package me.hanwook.testpractice.service;

import lombok.RequiredArgsConstructor;
import me.hanwook.testpractice.entity.Car;
import me.hanwook.testpractice.entity.CarColor;
import me.hanwook.testpractice.entity.Model;
import me.hanwook.testpractice.entity.Reservation;
import me.hanwook.testpractice.exception.ModelNotFoundException;
import me.hanwook.testpractice.exception.ReservationNotFoundException;
import me.hanwook.testpractice.repository.ModelRepository;
import me.hanwook.testpractice.repository.ReservationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ModelRepository modelRepository;

    private final CarService carService;

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

    /**
     * 예약 취소
     * @param reservationId
     * @return
     */
    @Transactional
    public Reservation cancel(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(ReservationNotFoundException::new);

        reservation.cancel();

        return reservation;
    }

    /**
     * 차량 출고
     * @param reservationId
     * @return
     */
    @Transactional
    public Reservation delivery(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(ReservationNotFoundException::new);

        Car car = carService.create(
                reservation.getModel().getId(),
                reservation.getColor(),
                reservation.getOptions().stream()
                        .map(x -> x.getOption().getId())
                        .collect(Collectors.toList())
        );

        reservation.delivery(car);

        return reservation;
    }
}
