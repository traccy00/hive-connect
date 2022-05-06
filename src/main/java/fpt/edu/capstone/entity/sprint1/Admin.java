package fpt.edu.capstone.entity.sprint1;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "admin")
//@Where(clause = "is_deleted=0")
@AllArgsConstructor
@NoArgsConstructor
public class Admin {
    /*
    username : hive-connect-system
    password : admin
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "user_id")
    private long userId;

    @Column(name = "full_name")
    private String fullName;

    @Transient
    private User user;
}
