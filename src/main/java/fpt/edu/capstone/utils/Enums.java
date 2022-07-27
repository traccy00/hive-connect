package fpt.edu.capstone.utils;

import java.util.HashMap;
import java.util.Map;

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

    public enum FileUploadType {
        Image("image"), CV("cv");

        private String status;

        public String getStatus() {return status;}

        FileUploadType(String status) {
            this.status = status;
        }

        private  static final Map<String, FileUploadType> map = new HashMap<>();
        static {
            for (FileUploadType value:values()) {
                map.put(value.getStatus(),value);
            }
        }
        public static FileUploadType parse(String type) {
            return  map.get((type));
        }
    }
}