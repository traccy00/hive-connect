package fpt.edu.capstone.entity;
//Report những post có tính chất vi phạm điều khoản website
//khi người dùng report 1 bài, sẽ hiện lên các radio button cho người dùng chọn lí do report
public class Report {
    private long id;
    private long postId;
    private String reportType;
    private boolean status;

    public Report(long id, long postId, String reportType, boolean status) {
        this.id = id;
        this.postId = postId;
        this.reportType = reportType;
        this.status = status;
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

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
