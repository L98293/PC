package Samdi_PC.Seat.Controller;

import Samdi_PC.Seat.DTO.SitRequest;
import Samdi_PC.Seat.Domain.Seats;
import Samdi_PC.Seat.Repository.SeatRepository;
import Samdi_PC.Seat.Service.SeatService;
import Samdi_PC.Seat.DTO.CreateSeatRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/seats")
public class SeatController {

    private final SeatService seatService;
    private final SeatRepository seatRepository;

    public SeatController(SeatService seatService, SeatRepository seatRepository) {
        this.seatService = seatService;
        this.seatRepository = seatRepository;
    }

    @GetMapping
    public String deafultSeats() {
        return "좌석 만들기: Post요청, 좌석 상태 조회: /{id}경로로 Post요청";
    }

    // 좌석 만들기
    @PostMapping
    public void createSeat(@RequestBody CreateSeatRequest request) {
        seatService.createSeats(request);
    }

    @GetMapping("/{id}")
    public Seats getSeatsById(@PathVariable Long id) {
        return seatRepository.findById(id)
                .orElseThrow(()
                -> new IllegalArgumentException("해당 사용자명을 찾을 수 없습니다."));
    }

    @PostMapping("/sit")
    public ResponseEntity<String> sitSeats(@RequestBody SitRequest request) {
        seatService.sitUser(request.userId(), request.seatId());
        return ResponseEntity.ok("좌석이 지정되었습니다.");
    }
}
