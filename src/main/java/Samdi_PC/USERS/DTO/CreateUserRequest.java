package Samdi_PC.USERS.DTO;

import Samdi_PC.USERS.Domain.UserType;

public record CreateUserRequest(
        String username,
        Integer money,
        UserType userType
) {
}
