package fpt.edu.capstone.entity;
// like và comment post. có thể like và comment bình luận phía dưới bài viết
public class Like {
    private long id;
    private long postId;
    private long userId;
    private boolean status; // status =0 : không có tương tác gì
    private boolean isLike; //status sẽ  = 1 . khi đó isLike =0 là dislike, = 1 là like
/*
status =0 ; isLike null
status =1; islike = 0 ==> dislike
status =1; islike =1 ==> like
 */
    public Like(long id, long postId, long userId, boolean status, boolean isLike) {
        this.id = id;
        this.postId = postId;
        this.userId = userId;
        this.status = status;
        this.isLike = isLike;
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

    public boolean isLike() {
        return isLike;
    }

    public void setLike(boolean like) {
        isLike = like;
    }
}
