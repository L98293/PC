package Samdi_PC.Seat.Repository;

import Samdi_PC.Seat.Domain.Seats;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SeatRepository extends JpaRepository<Seats, Long> {

    // 특정유저가 앉은 좌석 조회
    Optional<Seats> findById(Long id);
}
