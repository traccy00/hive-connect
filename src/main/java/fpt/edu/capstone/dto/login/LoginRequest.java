package fpt.edu.capstone.dto.login;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class LoginRequest implements Serializable {
    private static final long serialVersionUID = 5926468583005150707L;

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
