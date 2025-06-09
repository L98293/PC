package Samdi_PC.Seat.DTO;

import Samdi_PC.Seat.Domain.SeatStatus;

public record CreateSeatRequest(
        Integer seatNumber,
        SeatStatus status
) {
}
