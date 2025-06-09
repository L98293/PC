package Samdi_PC.USERS.Controller;

import Samdi_PC.USERS.DTO.CreateUserRequest;
import Samdi_PC.USERS.Domain.User;
import Samdi_PC.USERS.Repository.UserRepository;
import Samdi_PC.USERS.Service.UserService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.resource.ResourceUrlProvider;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    public UserController(UserService userService, ResourceUrlProvider resourceUrlProvider, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping
    public String deafultUser() {
        return "환영합니다.";
    }

    // 플레이어 생성
    @PostMapping
    public void createUser(@RequestBody CreateUserRequest request) {
        userService.createUser(request);
    }

    // 플레이어 조회
    @GetMapping("/{id}")
    public User getUserById(@PathVariable long id) {
        return userRepository.findById(id)
                .orElseThrow(()
                -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다."));
    }
}
