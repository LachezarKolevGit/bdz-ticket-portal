package bg.tusofia.vvps.ticketsystem.home;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String getHomePage(){
        return "home_page";
    }

}
