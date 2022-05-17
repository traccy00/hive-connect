package fpt.edu.capstone.dto.admin;

import fpt.edu.capstone.entity.sprint1.Admin;
import fpt.edu.capstone.entity.sprint1.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class AdminResponse {
    private long id;
    private long userId;
    private String fullName;
    private String username;
    private String email;
    private long roleId;
    private LocalDateTime createdAt;
    private LocalDateTime lastLoginTime;

    public static AdminResponse fromEntity(Admin entity, User user){
        if(entity == null){
            return null;
        }
        return AdminResponse.builder()
                .id(entity.getId())
                .userId(entity.getUserId())
                .fullName(entity.getFullName())
                .username(user.getUsername())
                .email(user.getEmail())
                .roleId(user.getRoleId())
                .createdAt(user.getCreatedAt())
                .lastLoginTime(user.getLastLoginTime())
                .build();
    }

}
