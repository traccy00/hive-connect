package fpt.edu.capstone.dto.login;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginGoogleRequest {
    //FE trả
    private String name;
    private String email;
    private String picture;
    private String username;
    private String password;
    private String confirmPassword;
    private long roleId;
}
