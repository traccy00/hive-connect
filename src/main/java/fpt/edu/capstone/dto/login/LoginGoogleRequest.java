package fpt.edu.capstone.dto.login;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginGoogleRequest {
    private String name;
    private String email;
    private String picture;
}
