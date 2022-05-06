package fpt.edu.capstone.entity;

public class Image {
    private long id;
    private long jobId;
    private long companyId;
    private String imageName;
    private boolean isAvatar;
    private boolean isBanner;
    private boolean isDelete;
    private boolean status;
    private long createAt; // có thể check được xem nhà tuyển dụng thay đổi ảnh ko hợp lệ ???
    private long updateAt;

    public Image(long id, long jobId, long companyId, String imageName, boolean isAvatar, boolean isBanner, boolean isDelete, boolean status, long createAt, long updateAt) {
        this.id = id;
        this.jobId = jobId;
        this.companyId = companyId;
        this.imageName = imageName;
        this.isAvatar = isAvatar;
        this.isBanner = isBanner;
        this.isDelete = isDelete;
        this.status = status;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }

    public boolean isBanner() {
        return isBanner;
    }

    public void setBanner(boolean banner) {
        isBanner = banner;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getJobId() {
        return jobId;
    }

    public void setJobId(long jobId) {
        this.jobId = jobId;
    }

    public long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(long companyId) {
        this.companyId = companyId;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public boolean isAvatar() {
        return isAvatar;
    }

    public void setAvatar(boolean avatar) {
        isAvatar = avatar;
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
