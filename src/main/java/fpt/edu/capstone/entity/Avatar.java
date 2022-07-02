package fpt.edu.capstone.entity;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "avatar")
@Getter
@Setter
public class Avatar {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String name;

    private String contentType;

    private long size;

    private long userId;

    @Type(type="org.hibernate.type.BinaryType")
    @Column(name = "data")
    private byte[] data;


}