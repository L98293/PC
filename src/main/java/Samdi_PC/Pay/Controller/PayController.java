package Samdi_PC.Pay.Controller;

import Samdi_PC.Pay.DTO.PayRequest;
import Samdi_PC.Pay.Service.PayService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pay")
@RequiredArgsConstructor
public class PayController {

    private final PayService payService;

    @PostMapping
    public ResponseEntity<String> pay(@RequestBody PayRequest request) {
        payService.PaySystem(request.userId(), request.amount());
        return ResponseEntity.ok("결제가 완료되었습니다.");
    }
}
