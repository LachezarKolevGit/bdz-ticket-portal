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
    public String registerUser(@ModelAttribute("user") User user) {
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

    @GetMapping("/register/admin")
    public String registerAdminPage(Model model) {
        UserDTO admin = new UserDTO();
        model.addAttribute("admin", admin);
        return "admin/admin_register_page";
    }

    @PostMapping("/register/admin")
    public String registerAdmin(@ModelAttribute("user") UserDTO userDTO) {
        userService.registerAdmin(userDTO);

        return "admin/admin_registered_successfully";
    }

    //give it some thought for regarding admin
    @GetMapping("/add")
    public String addUserPage(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "user/user_register";
    }

    @PostMapping("/add")
    public String addUser(@ModelAttribute("user") User user) {
        userService.registerUser(user);
        return "user/user_registered_successfully";
    }

    @GetMapping("/profile/{userId}")
    public String getUserProfile(Model model, @PathVariable(name = "userId") String userId) {
        model.addAttribute("user", userService.findUserById(userId));
        return "user/user_details";
    }

    @GetMapping("/profile")
    public String getUserProfile(Model model) {
        User user = userService.getLoggedInUser();
        System.out.println(user);
        model.addAttribute("user", user);
        return "user/user_details";
    }

    @PostMapping("/profile")
    public String editUserProfile(@ModelAttribute(name = "user") User user, Model model) {
        System.out.println("User in edit " + user);
        user = userService.editProfile(user);
        model.addAttribute("user", user);
        return "user/user_details";
    }

   /* @GetMapping("/edit")
    public String editUserProfilePage(@ModelAttribute(name = "user") User user, Model model) {
        System.out.println(user);
        model.addAttribute("user", user);
        return "user/user_edit_details"; //change
    }
*/


}
