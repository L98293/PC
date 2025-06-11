package Samdi_PC.Pay.DTO;

public record PayRequest(
        Long userId,
        Integer amount
) {
}
