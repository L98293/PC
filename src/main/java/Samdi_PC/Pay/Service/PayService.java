package Samdi_PC.Pay.Service;

import Samdi_PC.Pay.Domain.Pay;
import Samdi_PC.Pay.Repository.PayRepository;
import Samdi_PC.Users.Domain.User;
import Samdi_PC.Users.Repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PayService {

    private final UserRepository userRepository;
    private final PayRepository payRepository;

    @Transactional // 만약 if문 안에 있는 예외가 실행되면 예외를 감지해서 즉시 실행을 롤백함
    public void PaySystem(Long user_id, Integer amount) {
        User user = userRepository.findById(user_id)
                .orElseThrow(()
                -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        Pay pay = new Pay();
        pay.setAmount(amount);
        pay.setPayTime(LocalDateTime.now());
        pay.setUser(user);

        // 만약 여기 if문에서 예외가 발생하면
        if (user.getMoney() < amount) {
            throw new IllegalArgumentException("잔액이 부족해 결제를 진행할 수 없습니다.");
        }

        // 여기 아래코드는 실행되지 않음 만약 실행되어도 롤백됨
        user.setMoney(user.getMoney() - amount);
        payRepository.save(pay);
        userRepository.save(user);
    }
}
