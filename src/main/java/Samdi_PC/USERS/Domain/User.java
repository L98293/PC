package Samdi_PC.USERS.Domain;

import Samdi_PC.Seat.Domain.Seats;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@NoArgsConstructor
@Data
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private Integer money;

    @OneToOne(mappedBy = "user")
    private Seats seat;

    @Enumerated(EnumType.STRING)
    private UserType userType;
}
