package Samdi_PC.Seat.Domain;

import Samdi_PC.USERS.Domain.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@Data
@NoArgsConstructor
@Table(name = "Seats")
public class Seats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 좌석 번호
    private Integer seatNumber;

    // 해당 좌석을 사용중인 플레이어
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    // ENUM(열거형)으로 만들어 두었던 seatStatus를 문자열로 저장
    @Enumerated(EnumType.STRING)
    private SeatStatus status;


    // ChatGPT
    public void assignUser(User user) {
        if (this.user != null) {
            this.user.setSeat(null); // 기존 관계 제거
        }
        this.user = user;
        if (user != null && user.getSeat() != this) {
            user.setSeat(this); // 양방향 관계 설정
        }
    }
}
