package bg.tusofia.vvps.ticketsystem.user;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private String email;
    private String firstName;
    private String lastName;
    private String password;

}

