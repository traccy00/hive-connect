package fpt.edu.capstone.dto.common;

public interface ResponseMessageConstants {
    String SUCCESS = "MSG_SUCCESS";
    String ERROR = "MSG_ERROR";
    String ACCESS_DENIED = "MSG_ACCESS_DENIED";
    String DATA_INVALID = "DATA_INVALID";
    String DATA_IS_NULL_OR_EMPTY = "DATA_IS_NULL_OR_EMPTY";

    // LOGIN
    String LOGIN_ACCESS_DENIED = "MSG_LOGIN_ACCESS_DENIED";
    String LOGIN_EXCEPTION = "MSG_LOGIN_EXCEPTION";
    String LOGIN_SUCCESS = "MSG_LOGIN_SUCCESS";
    String LOGIN_FAILED = "MSG_LOGIN_FAILED";
    String LOGIN_PROVIDER_INVALID = "MSG_LOGIN_PROVIDER_INVALID";
    String LOGIN_REFRESH_EXCEPTION = "MSG_LOGIN_REFRESH_EXCEPTION";
    String USERNAME_OR_PASSWORD_MUST_NOT_CONTAIN_ANY_SPACE_CHARACTERS = "MSG_USERNAME_OR_PASSWORD_MUST_NOT_CONTAIN_ANY_SPACE_CHARACTERS";
    String AUTHENTICATION_IS_NOT_SUPPORT_BY_SERVER = "MSG_AUTHENTICATION_IS_NOT_SUPPORT_BY_SERVER";
    String USERNAME_OR_PASSWORD_INCORRECT = "MSG_USERNAME_OR_PASSWORD_INCORRECT";

    //REGISTER
    String REGISTER_SUCCESS = "REGISTER_SUCCESS";
    String REGISTER_FAILED = "REGISTER_FAILED";
    String CHANGE_PASSWORD_SUCCESS = "CHANGE_PASSWORD_SUCCESS";
    String CHANGE_PASSWORD_FAILED = "CHANGE_PASSWORD_FAILED";
    String RESEND_EMAIL_SUCCESS = "RESEND_EMAIL_SUCCESS";
    //USER
    String USER_DOES_NOT_EXIST = "USER_DOES_NOT_EXIST";
    String USER_IS_INACTIVE = "USER_IS_INACTIVE";
    String USER_IS_DELETED = "USER_IS_DELETED";

    // COMMON
    String UPDATE_FAILED = "MSG_UPDATE_FAILED";
    String DELETE_FAILED = "MSG_DELETE_FAILED";
    String TOKEN_EXPIRED = "MSG_TOKEN_EXPIRED";
    String TOKEN_INVALID = "MSG_TOKEN_INVALID";

    //JOB
    String CREATE_JOB_SUCCESS = "CREATE_JOB_SUCCESS";
    String CREATE_JOB_FAILED = "CREATE_JOB_FAILED";
    String UPDATE_JOB_SUCCESS = "UPDATE_JOB_SUCCESS";
    String UPDATE_JOB_FAILED = "UPDATE_JOB_FAILED";

    //CATEGORY
    String CREATE_CATEGORY_SUCCESS = "CREATE_CATEGORY_SUCCESS";
    String UPDATE_CATEGORY_SUCCESS = "UPDATE_CATEGORY_SUCCESS";
    String DELETE_CATEGORY_SUCCESS = "DELETE_CATEGORY_SUCCESS";

    //PAYMENT
    String PAYMENT_SUCCESS = "PAYMENT_SUCCESS";
    String PAYMENT_FAILED = "PAYMENT_FAILED";

    //JOB
    String JOB_DOES_NOT_EXIST = "Tin tuyển dụng không tồn tại";

    //AMAZON
    String AMAZON_SAVE_URL = "https://hive-connect-images.s3.us-west-1.amazonaws.com/hiveconnect/";
    String UPLOAD_IMAGE_EXCEPTION = "MSG_UPLOAD_IMAGE_EXCEPTION";
    String REQUEST_EXCEPTION = "MSG_REQUEST_EXCEPTION";
    String UPLOAD_IMG_EXCEPTION = "MSG_UPLOAD_IMG_EXCEPTION";
    String MAX_IMAGE_SIZE = "MSG_MAX_IMAGE_SIZE_5MB";
    String UPLOAD_IMAGE_WRONG_TYPE = "Tệp tải lên không đúng định dạng";
    String UPLOAD_IMAGE_OVER_SIZE = "Hình ảnh tải lên bị quá dung lượng cho phép";
    String UPDATE_SUCCESSFULLY = "Cập nhật thành công";
}
