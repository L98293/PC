package Samdi_PC.Pay.Domain;

import Samdi_PC.Users.Domain.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class Pay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Integer amount = 0;
    private LocalDateTime payTime;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
