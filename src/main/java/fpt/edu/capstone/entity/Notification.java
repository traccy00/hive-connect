package fpt.edu.capstone.entity;
//thông báo của Fwork cho user hoặc company về chính sách, thay đổi,....
public class Notification {
    private long id;
    private String text;
    private long companyId;// thông báo cho cá nhân công ty hoặc người dùng, có thể thông báo người dùng ABC đang được công ty CMC quan tâm đánh giá CV
    private long userId;
    private boolean status;
    private boolean isBanner; // thông báo trên banner của web

    public Notification(long id, String text, long companyId, long userId, boolean status, boolean isBanner) {
        this.id = id;
        this.text = text;
        this.companyId = companyId;
        this.userId = userId;
        this.status = status;
        this.isBanner = isBanner;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(long companyId) {
        this.companyId = companyId;
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

    public boolean isBanner() {
        return isBanner;
    }

    public void setBanner(boolean banner) {
        isBanner = banner;
    }
}
