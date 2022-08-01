package fpt.edu.capstone.dto.common;

public interface ResponseMessageConstants {
    String SUCCESS = "MSG_SUCCESS";
    String ERROR = "MSG_ERROR";
    String ACCESS_DENIED = "Truy cập bị từ chối.";
    String DATA_INVALID = "Dữ liệu không hợp lệ.";
    String DATA_IS_NULL_OR_EMPTY = "Không có dữ liệu.";
    String REQUEST_NOT_EXIST = "Không tìm thấy người dùng hoặc yêu cầu không tồn tại.";
    String CREATE_FAIL = "Tạo thất bại";
    String ROLE_NAME_EXISTS = "Loại người dùng này đã tồn tại";
    String PLEASE_TRY_TO_CONTACT_ADMIN = "Có lỗi xảy ra, vui lòng liên hệ quản trị hệ thống.";

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
    String REGISTER_SUCCESS = "Đăng ký thành công.";
    String REGISTER_FAILED = "Đăng ký thất bại.";
    String CHANGE_PASSWORD_SUCCESS = "Thay đổi mật khẩu thành công";
    String CHANGE_PASSWORD_FAILED = "Thay đổi mật khẩu thất bại";
    String RESEND_EMAIL_SUCCESS = "Gửi lại xác thực không thành công";

    //USER
    String USER_DOES_NOT_EXIST = "Người dùng không tồn tại";
    String USER_IS_INACTIVE = "Tài khoản người dùng không hoạt động";
    String USER_IS_DELETED = "Người dùng đã bị xóa";

    // COMMON
    String UPDATE_FAILED = "Cập nhật thất bại.";
    String DELETE_FAILED = "Xóa thất bại.";
    String TOKEN_EXPIRED = "Mã token hết hạn.";
    String TOKEN_INVALID = "Mã token không hợp lệ.";
    String REQUIRE_INPUT_MANDATORY_FIELD = "Vui lòng điền vào thông tin bắt buộc.";

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

    //COMPANY
    String SEND_OTP_SUCCESS = "Gửi OTP thành công.";
    String NO_REQUEST_JOIN_COMPANY_SENT = "Chưa tạo yêu cầu tham gia công ty nào.";
    String NO_REQUEST_JOIN_COMPANY_RECEIVED = "Không có yêu cầu tham gia công ty của bạn.";

    //BANNER
    String CREATE_BANNER_FAIL = "Tạo banner thất bại";

    //CV
    String CV_NOT_EXIST = "CV không tồn tại";
    String YOUR_CV_EXISTED = "CV không tồn tại";

    //PAYMENT
    String PRICE_EQUAL_GREATER_THAN_ZERO = "Giá của gói lớn hơn hoặc bằng 0.";
    String BANNER_IMAGE_INVALID = "Ảnh banner không hợp lệ";
    String BANNER_POSITION_INVALID = "Chọn một hoặc nhiều vị trí hiển thị banner.";
    String DISCOUNT_PRICE_INVALID = "Giá khuyến mãi không hợp lệ";
    String RENTAL_PACKAGE_DOES_NOT_EXIST = "Nhóm gói không tồn tại";
    String DETAIL_PACKAGE_DOES_NOT_EXIST = "Gói không tồn tại";
    String BANNER_TITLE_HAVE_ALREADY_EXISTED = "Tên gói đã tồn tại";
    String PAYMENT_DOES_NOT_EXIST = "Giao dịch không tồn tại";
    String SEND_RESET_PASSWORD_MAIL_SUCCESS = "Chúng tôi đã gửi mail làm mới mật khẩu tới email của bạn. Vui lòng kiểm tra email.";
    String SEND_EMAIL_FAIL = "Có lỗi xảy ra. Gửi email không thành công!";
    String DETAIL_PAYMENT_NOT_FOUND = "Không tìm thấy gói dịch vụ";
}
