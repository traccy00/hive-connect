package fpt.edu.capstone.service.impl;

import fpt.edu.capstone.entity.sprint1.Role;
import fpt.edu.capstone.entity.sprint1.Users;
import fpt.edu.capstone.repository.UserRepository;
import fpt.edu.capstone.service.RoleService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class SecurityUserServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    private final RoleService roleService;

    public SecurityUserServiceImpl(UserRepository userRepository, RoleService roleService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Users> optionUser = userRepository.findByUsername(username);
        if (!optionUser.isPresent()) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        Users user = optionUser.get();
        Role role = roleService.getRoleById(user.getRoleId());

        return org.springframework.security.core.userdetails.User.withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(role.getName())
                .accountExpired(false).accountLocked(false)
                .credentialsExpired(false).disabled(false)
                .build();
    }
}