package Samdi_PC.Users.Repository;

import Samdi_PC.Users.Domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
