package Samdi_PC.USERS.Service;


import Samdi_PC.Seat.Domain.SeatStatus;
import Samdi_PC.Seat.Repository.SeatRepository;
import Samdi_PC.USERS.DTO.CreateUserRequest;
import Samdi_PC.USERS.Domain.User;
import Samdi_PC.USERS.Domain.UserType;
import Samdi_PC.USERS.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final SeatRepository seatRepository;

    // 유저 만들고 저장하는 코드
    public User createUser(CreateUserRequest request) {
        User user = User.builder()
                .username(request.username())
                .money(request.money())
                .userType(request.userType())
                .build();
        userRepository.save(user);

        if (user.getUserType() == UserType.NPC) {
            seatRepository.findAll().stream()
                    .filter(seats -> seats.getStatus() == SeatStatus.AVAILABLE)
                    .findFirst()
                    .ifPresentOrElse(seats -> {
                        seats.assignUser(user);
                        seats.setStatus(SeatStatus.USED);
                        seatRepository.save(seats);
                    }, () -> {
                        throw new IllegalArgumentException("사용 가능한 좌석이 없습니다.");
                    });
        }
        return user;
    }
}
