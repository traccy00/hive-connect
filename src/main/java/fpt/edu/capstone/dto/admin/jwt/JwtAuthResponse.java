package fpt.edu.capstone.dto.admin.jwt;

public class JwtAuthResponse {

  private String accessToken;

  private String refreshToken;

  public JwtAuthResponse(String accessToken) {
    this.accessToken = accessToken;
  }

  public JwtAuthResponse(String accessToken, String refreshToken) {
    this.accessToken = accessToken;
    this.refreshToken = refreshToken;
  }

  public String getAccessToken() {
    return accessToken;
  }

  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }

  public String getRefreshToken() {
    return refreshToken;
  }

  public void setRefreshToken(String refreshToken) {
    this.refreshToken = refreshToken;
  }
}
