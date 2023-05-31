package bg.tusofia.vvps.ticketsystem.user;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    @Email
    private String email;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @Length(min = 3)
    private String password;

}

