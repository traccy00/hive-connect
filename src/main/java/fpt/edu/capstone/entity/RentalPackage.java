package fpt.edu.capstone.entity;

import fpt.edu.capstone.utils.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Table(name = "rental_package")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class RentalPackage extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "package_name")
    private String packageName;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private boolean status;
}
