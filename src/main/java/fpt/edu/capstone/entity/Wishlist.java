package fpt.edu.capstone.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

// thêm các job vào mục yêu thích

public class Wishlist {
    private long id;
    private long postId; // lưu bài viết yêu thích
    private long jobId; // lưu  job yêu thích
    private long userId;
    private boolean status;
    private boolean isDelete;

    public Wishlist(long id, long postId, long userId, boolean status, boolean isDelete) {
        this.id = id;
        this.postId = postId;
        this.userId = userId;
        this.status = status;
        this.isDelete = isDelete;
    }

    public long getJobId() {
        return jobId;
    }

    public void setJobId(long jobId) {
        this.jobId = jobId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPostId() {
        return postId;
    }

    public void setPostId(long postId) {
        this.postId = postId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isDelete() {
        return isDelete;
    }

    public void setDelete(boolean delete) {
        isDelete = delete;
    }
}
