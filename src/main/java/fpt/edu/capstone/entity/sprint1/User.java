package fpt.edu.capstone.entity.sprint1;

import fpt.edu.capstone.utils.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "user")
@Where(clause = "is_deleted=0")
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "role_id")
    private long roleId;
    //role =1 là customer
    //role =2 là recruiter
    //role =3 là admin

    @Column(name = "is_deleted")
    private int isDeleted = 0;
}
