package Samdi_PC.Seat.Service;

import Samdi_PC.Seat.DTO.CreateSeatRequest;
import Samdi_PC.Seat.Domain.SeatStatus;
import Samdi_PC.Seat.Domain.Seats;
import Samdi_PC.Seat.Repository.SeatRepository;
import Samdi_PC.Users.Domain.User;
import Samdi_PC.Users.Domain.UserType;
import Samdi_PC.Users.Repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class SeatService {

    private final SeatRepository seatRepository;
    private final UserRepository userRepository;

    public SeatService(SeatRepository seatRepository, UserRepository userRepository) {
        this.seatRepository = seatRepository;
        this.userRepository = userRepository;
    }

    // 좌석 만들고 저장하는 코드
    public Seats createSeats(CreateSeatRequest request) {
        Seats seats = Seats.builder()
                .seatNumber(request.seatNumber())
                .status(request.status())
                .build();

        return seatRepository.save(seats);
    }

    // 특정 좌석에 사용자 할당
    public void sitUser(Long userId,Long seatId) {
        User user = userRepository.findById(userId)
                .orElseThrow(()
                        -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다."));
        // Seats타입의 seat이라는 이름을 가진 변수 생성
        Seats seat;
        // NPC좌석 자동 지정(Chat GPT 다량 사용)
        if (user.getUserType() == UserType.NPC) {
            seat = seatRepository.findAll().stream()
                    // 상태가 AVAILABLE인 좌석만 남김
                    // 여기서 s는 stream을 의미함
                    // filter는 steam API의 메서드로 스트림에서 특정조건을 만족하는걸 제외하고 전부 제거
                    .filter(s -> s.getStatus() == SeatStatus.AVAILABLE)
                    // 상태가 AVAILABLE인 좌석중 가장 먼저나오는걸 가져옴
                    .findFirst()
                    // 만약 AVAILABLE인 좌석이 없다면 아래 코드 실행
                    .orElseThrow(()
                            -> new IllegalArgumentException("빈 좌석이 없습니다."));
            // 사용자 좌석 지정

            // 만약 유저타입이 NPC가 아닌 PLAYER라면 아래 코드 실행
        } else {
            if (seatId == null) {
                throw new IllegalArgumentException("사용자는 좌석을 지정해야합니다.");
            }
            seat = seatRepository.findById(seatId)
                    .orElseThrow(()
                            -> new IllegalArgumentException("해당 좌석을 찾을 수 없습니다."));
            if (seat.getStatus() == SeatStatus.USED) {
                throw new IllegalArgumentException("해당 좌석은 이미 사용중입니다.");
            }
        }

        seat.assignUser(user);
        seat.setStatus(SeatStatus.USED);

        seatRepository.save(seat);
        userRepository.save(user);
    }

    // 특정 좌석에 사용자 할당 취소(?)
    public void LeaveSeat(Long userId, Long seatId) {
        User user = userRepository.findById(userId)
                .orElseThrow(()
                        -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다."));
        Seats seat = seatRepository.findById(seatId)
                .orElseThrow(()
                        -> new IllegalArgumentException("해당 좌석을 찾을 수 없습니다."));
        if (!seat.getUser().equals(user)) {
            throw new IllegalArgumentException("해당 좌석에 앉아있는 사용자가 아닙니다.");
        }

        seat.assignUser(null);
        seat.setStatus(SeatStatus.AVAILABLE);

        seatRepository.save(seat);
        userRepository.save(user);
    }

    // 특정 유저가 앉아있는 좌석 조회
    public Seats getSeats(Long userId) {
        User  user = userRepository.findById(userId)
                .orElseThrow(()
                        -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다."));
        return user.getSeat();
    }
}
