package fpt.edu.capstone.common;

/**
 * @author hungnd on 05-Feb-2021
 */
public interface JWTConstants {

  String TOKEN_HEADER = "Authorization";

  String TOKEN_PREFIX = "Bearer ";

  String CLAIM_USER_ID = "userId";

  String CLAIM_ROLES = "roles";

  String CLAIM_EMAIL = "email";

  String CLAIM_SOCIAL_ID = "socialId";

  String CLAIM_PROVIDER = "provider";

  String ACTIVE_MODE = "prod";
}
