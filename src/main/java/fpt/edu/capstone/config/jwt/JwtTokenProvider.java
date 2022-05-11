//package fpt.edu.capstone.config.jwt;
//
//import fpt.edu.capstone.common.JWTConstants;
//import fpt.edu.capstone.common.ResponseMessageConstants;
//import fpt.edu.capstone.config.UserConfig;
//import fpt.edu.capstone.entity.sprint1.Role;
//import fpt.edu.capstone.entity.sprint1.User;
//import fpt.edu.capstone.service.RoleService;
//import fpt.edu.capstone.utils.enums.Enums;
//import io.jsonwebtoken.*;
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.http.HttpServletRequest;
//import java.time.Instant;
//import java.util.*;
//
//@Component
//public class JwtTokenProvider {
//  private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);
//
//  @Value(value = "${jwt.expiration.access.token}")
//  private int jwtExpirationAccessToken;
//
//  @Value(value = "${jwt.expiration.refresh.token}")
//  private int jwtExpirationRefreshToken;
//
//  @Value(value = "${jwt.secret}")
//  private String jwtSecret;
//
//  @Autowired
//  private RoleService roleService;
//
//  @Autowired
//  private UserConfig userConfig;
//
////  public UserTokenModel getUserByToken(String token) throws Exception {
////    try {
////      Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
////      UserTokenModel userTokenModel = new UserTokenModel();
////      userTokenModel.setUserId(claims.get(JWTConstants.CLAIM_USER_ID, Long.class));
////      userTokenModel.setEmail(claims.getSubject().toLowerCase());
////      userTokenModel.setActiveMode(claims.get("active_mode", String.class));
////      return userTokenModel;
////    } catch (ExpiredJwtException ex) {
////      throw new Exception(ResponseMessageConstants.LOGIN_REFRESH_EXCEPTION);
////    }
////  }
//
////  public Authentication getAuthentication(UserTokenModel userTokenModel, HttpServletRequest request) throws Exception {
////    // CHECK USER REFRESH TOKEN IS EXPIRED ?
////    if (userTokenModel.getActiveMode().equalsIgnoreCase(adminConfig.getActiveMode())) {
////      Admin admin = adminUserService.getAdminByIdAndEmail(userTokenModel.getUserId(), userTokenModel.getEmail());
////      if (admin != null) {
////        AuthModel authModel = AuthModel.transform(admin);
////        Role role = roleService.getRoleById(admin.getRoleId());
////        Set<Role> roles = new HashSet<>(Arrays.asList(role));
////        authModel.setRoles(roles);
////        List<GrantedAuthority> authorities = getUserAuthority(roles);
////        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(authModel,
////          null, authorities);
////        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
////        return authenticationToken;
////      }
////    }
////
////    throw new Exception(ResponseMessageConstants.LOGIN_EXCEPTION);
////  }
//
//  private List<GrantedAuthority> getUserAuthority(Set<Role> userRoles) {
//    Set<GrantedAuthority> roles = new HashSet<>();
//    userRoles.forEach((role) -> {
//      roles.add(new SimpleGrantedAuthority(role.getName()));
//    });
//    List<GrantedAuthority> grantedAuthorities = new ArrayList<>(roles);
//    return grantedAuthorities;
//  }
//
//  public boolean validateToken(String authToken) {
//    try {
//      Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
//      return true;
//    } catch (SignatureException ex) {
//      logger.error("Invalid JWT signature");
//    } catch (MalformedJwtException ex) {
//      logger.error("Invalid JWT token");
//    } catch (ExpiredJwtException ex) {
//      logger.error("Expired JWT token");
//      throw new IllegalArgumentException(ResponseMessageConstants.TOKEN_EXPIRED);
//    } catch (UnsupportedJwtException ex) {
//      logger.error("Unsupported JWT token");
//    } catch (IllegalArgumentException ex) {
//      logger.error("JWT claims string is empty");
//    }
//    throw new IllegalArgumentException(ResponseMessageConstants.TOKEN_INVALID);
//  }
//
////  public String resolveToken(HttpServletRequest req) {
////    String bearerToken = req.getHeader(JWTConstants.TOKEN_HEADER);
////    List<String> pathList = new ArrayList<String>(Arrays.asList(SecurityConfig.pathArray));
////    List<String> paths = getPath(pathList);
////    if (checkPath(req.getRequestURI().toLowerCase(), paths))
////      return null;
////    if (pathList.contains(req.getRequestURI().toLowerCase()))
////      return null;
////    if (StringUtils.isNotEmpty(bearerToken) && bearerToken.startsWith(JWTConstants.TOKEN_PREFIX))
////      return bearerToken.replace(JWTConstants.TOKEN_PREFIX, "");
////    return null;
////  }
//
//  private List<String> getPath(List<String> pathList) {
//    List<String> path = new ArrayList<>();
//    for (String p : pathList)
//      if (p.contains("/**"))
//        path.add(p.replace("/**", ""));
//    return path;
//  }
//
//  private boolean checkPath(String uri, List<String> pathList) {
//    for (String p : pathList)
//      if (uri.contains(p))
//        return true;
//    return false;
//  }
//
//  public String generateToken(User user) {
//    Instant instant = Instant.now();
//    return Jwts.builder().setSubject(user.getEmail()).claim(JWTConstants.CLAIM_USER_ID, user.getId())
//      .claim("active_mode", userConfig.getActiveMode())
//      .claim("scopes", Arrays.asList(Enums.Scopes.ACCESS_TOKEN.authority())).setIssuedAt(Date.from(instant))
//      .setExpiration(Date.from(instant.plusSeconds(jwtExpirationAccessToken)))
//      .signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
//  }
//}
