//package fpt.edu.capstone.security;
//
//import fpt.edu.capstone.entity.sprint1.Role;
//import fpt.edu.capstone.entity.sprint1.User;
//import fpt.edu.capstone.utils.enums.Enums;
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import lombok.extern.log4j.Log4j2;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Component;
//
//import java.io.Serializable;
//import java.time.Instant;
//import java.util.Arrays;
//import java.util.Date;
//import java.util.function.Function;
//import java.util.stream.Collectors;
//
//@Component
//@Log4j2
//public class TokenUtils implements Serializable {
//    private static final long serialVersionUID = -2550185165626007488L;
//
//    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;
//
//    @Value("${jwt.secret}")
//    private String secret;
//
//    //retrieve username from jwt token
//    public String getUsernameFromToken(String token) {
//        return getClaimFromToken(token, Claims::getSubject);
//    }
//
//    //retrieve expiration date from jwt token
//    public Date getExpirationDateFromToken(String token) {
//        return getClaimFromToken(token, Claims::getExpiration);
//    }
//
//    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
//        final Claims claims = getAllClaimsFromToken(token);
//        return claimsResolver.apply(claims);
//    }
//
//    private Claims getAllClaimsFromToken(String token) {
//        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
//    }
//
//    //check if the token has expired
//    private Boolean isTokenExpired(String token) {
//        final Date expiration = getExpirationDateFromToken(token);
//        return expiration.before(new Date());
//    }
//
//    //generate token for user
//    public String generateToken(User user) {
//        Instant instant = Instant.now();
//        return Jwts.builder().setSubject(user.getEmail()).claim(JWTConstants.CLAIM_USER_ID, user.getId())
//                .claim("active_mode", adminConfig.getActiveMode())
//                .claim("scopes", Arrays.asList(Enums.Scopes.ACCESS_TOKEN.authority())).setIssuedAt(Date.from(instant))
//                .setExpiration(Date.from(instant.plusSeconds(jwtExpirationAccessToken)))
//                .signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
//    }
//
//    // set token validity time and algorithm
//    private String doGenerateToken(User user) {
//        Claims claims = Jwts.claims().setSubject(user.getEmail());
//        claims.put("auth", user.getAuthorities().stream().map(s -> new SimpleGrantedAuthority(s.getAuthority()))
//                .collect(Collectors.toList()));
//
//        return Jwts.builder().setClaims(claims).setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
//                .signWith(SignatureAlgorithm.HS512, secret).compact();
//    }
//
//    //validate token
//    public Boolean validateToken(String token, UserDetails userDetails) {
//        final String username = getUsernameFromToken(token);
//        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
//    }
//}