package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.service.LoginService;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {
    @Override
    public boolean login(Object... objects) throws Exception {
        String email = (String) objects[0];
        String password = (String) objects[1];

        return false;
    }
}
