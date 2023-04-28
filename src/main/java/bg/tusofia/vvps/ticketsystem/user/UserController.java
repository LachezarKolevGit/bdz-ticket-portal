package bg.tusofia.vvps.ticketsystem.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String welcomePage() {
        return "home_page";
    }

    @GetMapping("/register")
    public String registerUser(Model model) {
        User user = new User();
        model.addAttribute("user", user);

        return "user/user_register";
    }

    @PostMapping("/register")
    public String registerUser(User user) {
        System.out.println("Submitted user is" + user);
        userService.registerUser(user);
        return "user/user_registered_successfully";
    }

    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error) {
        ModelAndView model = new ModelAndView();
        model.setViewName("loginPage");
        if (error != null) {
            model.addObject("error", "Invalid Credentials provided.");
        }

        return "user/user_login";
    }

    @PostMapping("/register/admin")
    public String registerAdmin(@RequestBody User user) {
        userService.registerAdmin(user);

        return "admin/admin_registered_successfully";
    }

    @PostMapping("/login/admin")  //add authentication for admin
    public String loginAdmin(Model model, @RequestBody UserDTO userDTO) {
        String username = userService.tryToAuthenticateUser(userDTO);
        model.addAttribute("username", username);

        return "admin/admin_login_successfully";
    }

}
