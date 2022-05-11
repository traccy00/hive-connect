package fpt.edu.capstone.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UserConfig {
  @Value("${spring.profiles.active}")
  private String activeMode;


  @Value("${admin.vn.time-zone}")
  private String adminVNTimezone;

  public String getActiveMode() {
    return activeMode;
  }

  public String getAdminVNTimezone() {
    return adminVNTimezone;
  }
}
