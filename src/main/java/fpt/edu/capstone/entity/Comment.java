package fpt.edu.capstone.entity;

public class Comment {
    private long id;
    private long userId;
    private long postId;
    private String commentText;
    private String historyComment; // co  can lay lich su comment ko ???
    private boolean isDelete;
    private boolean status;
    private long createAt;
    private long updateAt;

    public Comment(long id, long userId, long postId, String commentText, String historyComment, boolean isDelete, boolean status, long createAt, long updateAt) {
        this.id = id;
        this.userId = userId;
        this.postId = postId;
        this.commentText = commentText;
        this.historyComment = historyComment;
        this.isDelete = isDelete;
        this.status = status;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getPostId() {
        return postId;
    }

    public void setPostId(long postId) {
        this.postId = postId;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public String getHistoryComment() {
        return historyComment;
    }

    public void setHistoryComment(String historyComment) {
        this.historyComment = historyComment;
    }

    public boolean isDelete() {
        return isDelete;
    }

    public void setDelete(boolean delete) {
        isDelete = delete;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public long getCreateAt() {
        return createAt;
    }

    public void setCreateAt(long createAt) {
        this.createAt = createAt;
    }

    public long getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(long updateAt) {
        this.updateAt = updateAt;
    }
}
