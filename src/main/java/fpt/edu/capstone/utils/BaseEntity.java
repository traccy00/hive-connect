package fpt.edu.capstone.utils;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@MappedSuperclass
public class BaseEntity {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY, value = "createdAt")
    @Column(name = "created_at")
    private Date createdAt;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY, value = "updatedAt")
    @Column(name = "updated_at")
    private Date updatedAt;

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}