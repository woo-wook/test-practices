package me.hanwook.testpractice.repository;

import me.hanwook.testpractice.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
