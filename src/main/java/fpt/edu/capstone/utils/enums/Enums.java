package fpt.edu.capstone.utils.enums;

/**
 * @author hungnd on 05-Feb-2021
 */
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

  public enum RevenueStatus {
    Today, Week, Month
  }

  public enum ProductStatus {
    Stock(1, "Available"), OutStocking(2, "Out of Stock");

    private long id;
    private String status;

    ProductStatus(long id, String status) {
      this.id = id;
      this.status = status;
    }

    public String getStatus() {
      return status;
    }

    public long getId() {
      return id;
    }
  }

  public enum UserStatus {
    Deleted(-1), Locked(0), Activated(1), Inactive(2);

    UserStatus(int status) {
      this.status = status;
    }

    private int status;

    public int status() {
      return status;
    }
  }

  public enum UserRefreshTokenStatus {
    NotExpire(0), Expired(1);

    UserRefreshTokenStatus(int userRefreshTokenStatus) {
      this.userRefreshTokenStatus = userRefreshTokenStatus;
    }

    private int userRefreshTokenStatus;

    public int value() {
      return userRefreshTokenStatus;
    }
  }

  public enum Scopes {
    ACCESS_TOKEN, REFRESH_TOKEN;

    public String authority() {
      return "ROLE_" + this.name();
    }
  }

  public enum TransactionType {
    Withdraw, Deposit, Transfer, Advances, Allowance
  }

  public enum JobName {
    SUBSIDIZE("SUBSIDIZE"), SYNC_USER_LDAP("SYNC_USER_LDAP"), TRANSACTION_APPROVED("TRANSACTION_APPROVED");

    JobName(String status) {
      this.status = status;
    }

    public String job() {
      return status;
    }

    private String status;

  }

  public enum TransactionStatus {
    Completed, Pending, Reject, Approved, Canceled, Created, Refund, Receive, Bonus, AllowanceTransfer, Pending_Order
  }

  public enum SocialProvider {
    TIME_BIRD, GOOGLE, FACEBOOK, ZALO, APPLE
  }

  public enum OrderStatus {
    Completed, Pending, Accepted, Rejected, Cancel, Shipping, Exported, Created
  }

  public enum OrderType {
    Give("Give"), Delivery("Delivery"), TakeAway("Take Away");

    OrderType(String status) {
      this.status = status;
    }

    public String getStatus() {
      return status;
    }

    private String status;

  }

  public enum DetailMaterialTable {
    Size, Size_Material, Default_Material, Optional_Material
  }

  public enum NotificationType {
    Wallet, Order, Promotion
  }

  public enum PromotionStatus {
    Pending, Active, Inactive, Finished
  }
}
