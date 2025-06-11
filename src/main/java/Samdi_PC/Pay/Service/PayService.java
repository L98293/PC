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
    // 리턴값 없음
    // PaySystem메서드에 user_id와 amount값들을 받음
    public void PaySystem(Long user_id, Integer amount) {
        // User 클래스의 객체를 user로 선언해서 다룸
        // userRepositoy는 UserRepositorry의 객체이며 UserRepository는 데이터 베이스에 접근해서 User를 조작할 수 있음
        User user = userRepository.findById(user_id) // 이걸 호출하면 반환타입은 Optional<User>이고 .orElseThrow도 Optional에서 사용됨
            // .oElseThrow는 예외 커스텀이 가능
            // ()는 매게변수가 없지만 반환값은 있음
            // 람다식은 익명함수에서 쓰이지만 매개변수가 없는 람다식이 익명함수 이므로 이 코드자체가 익명함수 (Github Copilot - Claude Sonnet 4 참고)
                .orElseThrow(()
                -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        Pay pay = new Pay();
        pay.setAmount(amount);
        pay.setPayTime(LocalDateTime.now());
        pay.setUser(user);

        // 만약 여기 if문에서 예외가 발생하면 - (다음 주석)
        if (user.getMoney() < amount) {
            throw new IllegalArgumentException("잔액이 부족해 결제를 진행할 수 없습니다.");
        }

        // 여기 아래코드는 실행되지 않음 만약 실행되어도 롤백됨
        user.setMoney(user.getMoney() - amount);
        payRepository.save(pay);
        userRepository.save(user);
    }
}
