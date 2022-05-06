//package fpt.edu.capstone.service.impl;
//
//import fpt.edu.capstone.entity.sprint1.Role;
//import fpt.edu.capstone.entity.sprint1.User;
//import fpt.edu.capstone.repository.UserRepository;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//import org.springframework.util.CollectionUtils;
//
//import java.util.HashSet;
//import java.util.List;
//import java.util.Optional;
//import java.util.Set;
//
//@Service
//public class SecurityUserServiceImpl implements UserDetailsService {
//    private final UserRepository userRepository;
//
//    public SecurityUserServiceImpl(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Optional<User> user = userRepository.findByUsername(username);
//        if (!user.isPresent()) {
//            throw new UsernameNotFoundException("User not found with username: " + username);
//        }
//
//        long role = user.get().getRoleId();
//        Set<SimpleGrantedAuthority> sga = new HashSet<>();
//
//        if (!CollectionUtils.isEmpty(rolePermissions)){
//            rolePermissions.forEach(rp -> {
//                sga.add(new SimpleGrantedAuthority(rp.getPermission().getPermissionCode()));
//            });
//        }
//
//        return org.springframework.security.core.userdetails.User.withUsername(cmcUser.getAccount())
//                .password(cmcUser.getPassword())
//                .authorities(sga)
//                .accountExpired(false).accountLocked(false)
//                .credentialsExpired(false).disabled(false).build();
//    }
//}