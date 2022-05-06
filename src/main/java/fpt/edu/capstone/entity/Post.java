package fpt.edu.capstone.entity;
//Mạng xã hội thu nhỏ, yser có thể post bài về những chủ đề như review công ty , hỏi về những câu hỏi hay phỏng vấn....
// cần bảng like comment , vote ???
public class Post {
    private long id;
    private long userId;
    private String hashtag;
    private String title;
    private String description;
    private long createAt;
    private long updateAt;

    public Post(long id, long userId, String hashtag, String title, String description, long createAt, long updateAt) {
        this.id = id;
        this.userId = userId;
        this.hashtag = hashtag;
        this.title = title;
        this.description = description;
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

    public String getHashtag() {
        return hashtag;
    }

    public void setHashtag(String hashtag) {
        this.hashtag = hashtag;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
