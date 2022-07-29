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
    String CHANGE_PASSWORD_SUCCESS = "Thay đổi mật khẩu thành công";
    String CHANGE_PASSWORD_FAILED = "Thay đổi mật khẩu thất bại";
    String RESEND_EMAIL_SUCCESS = "Gửi lại xác thực không thành công";
    //USER
    String USER_DOES_NOT_EXIST = "Người dùng không tồn tại";
    String USER_IS_INACTIVE = "Tài khoản người dùng không hoạt động";
    String USER_IS_DELETED = "Người dùng đã bị xóa";

    // COMMON
    String UPDATE_FAILED = "MSG_UPDATE_FAILED";
    String DELETE_FAILED = "MSG_DELETE_FAILED";
    String TOKEN_EXPIRED = "MSG_TOKEN_EXPIRED";
    String TOKEN_INVALID = "MSG_TOKEN_INVALID";

    //JOB
    String JOB_DOES_NOT_EXIST = "Tin tuyển dụng không tồn tại";
    String CREATE_JOB_SUCCESS = "Tạo tin tuyển dụng thành công.";
    String CREATE_JOB_FAILED = "Tạo tin tuyển dụng thất bại.";
    String UPDATE_JOB_SUCCESS = "Cập nhật tin tuyển dụng thành công.";
    String UPDATE_JOB_FAILED = "Cập nhật tin tuyển dụng thất bại.";

    //PAYMENT
    String PAYMENT_SUCCESS = "Thanh toán thành công";
    String PAYMENT_FAILED = "Thanh toán thất bại";
    String CHANGE_JOB_PAYMENT_ACTIVE_SUCCESS = "Thay đổi kích hoạt công việc thành công";

    //AMAZON
    String AMAZON_SAVE_URL = "https://hive-connect-images.s3.us-west-1.amazonaws.com/hiveconnect/";
    String UPLOAD_IMAGE_EXCEPTION = "MSG_UPLOAD_IMAGE_EXCEPTION";
    String REQUEST_EXCEPTION = "MSG_REQUEST_EXCEPTION";
    String UPLOAD_IMG_EXCEPTION = "MSG_UPLOAD_IMG_EXCEPTION";
    String MAX_IMAGE_SIZE = "MSG_MAX_IMAGE_SIZE_5MB";
    String UPLOAD_IMAGE_WRONG_TYPE = "Tệp tải lên không đúng định dạng";
    String UPLOAD_IMAGE_OVER_SIZE = "Hình ảnh tải lên bị quá dung lượng cho phép";
    String UPDATE_SUCCESSFULLY = "Cập nhật thành công";

    String HAVE_NOT_FOLLOWED_JOB = "Bạn chưa theo dõi công việc nào cả";
}
