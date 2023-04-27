package bg.tusofia.vvps.ticketsystem.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public String register(@RequestBody User user) { //???
        userService.registerUser(user);
        return "user/user_registered_successfully";
    }

    @PostMapping("/login")
    public void login(@RequestBody UserDTO userDTO) {

    }

}
