package Samdi_PC.Users.DTO;

import Samdi_PC.Users.Domain.UserType;

public record CreateUserRequest(
        String username,
        Integer money,
        UserType userType
) {
}
