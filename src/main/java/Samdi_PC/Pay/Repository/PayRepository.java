package Samdi_PC.Pay.Repository;

import Samdi_PC.Pay.Domain.Pay;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PayRepository extends JpaRepository<Pay, Long> {

}
