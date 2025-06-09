package Samdi_PC.USERS.Repository;

import Samdi_PC.USERS.Domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
