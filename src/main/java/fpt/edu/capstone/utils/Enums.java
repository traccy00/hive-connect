package fpt.edu.capstone.utils;

public class Enums {
    public enum ResponseStatus {
        SUCCESS("Success"), ERROR("Error");

        ResponseStatus(String status) {
            this.status = status;
        }

        public String getStatus() {
            return status;
        }

        private String status;

    }

    public enum ApprovalStatus {
        APPROVED("Approved"), REJECT("Reject"), PENDING("Pending");

        ApprovalStatus(String status) {this.status = status;}

        public String getStatus() {return status;}

        private String status;
    }
}