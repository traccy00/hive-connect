//package fpt.edu.capstone.config.jwt;
//
//import io.timebird.timestore.common.model.UserTokenModel;
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.security.access.AccessDeniedException;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@Component
//public class JwtTokenFilter extends OncePerRequestFilter {
//
//  private static final Logger logger = LoggerFactory.getLogger(JwtTokenFilter.class);
//  private final JwtTokenProvider jwtTokenProvider;
//
//  public JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
//    this.jwtTokenProvider = jwtTokenProvider;
//  }
//
//  @Override
//  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//    throws ServletException, IOException {
//
//    response.setHeader("Access-Control-Allow-Origin", "*");
//    response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
//    response.setHeader("Access-Control-Max-Age", "3600");
//    response.setHeader("Access-Control-Allow-Headers",
//      "AccessToken, JCookieId, authorization, content-type, xsrf-token, FingerPrint, x-auth-token, x-requested-with, accept ,Origin, Access-Control-Allow-Headers, Access-Control-Allow-Origin");
//    response.addHeader("Access-Control-Expose-Headers", "xsrf-token, Content-Disposition");
//
//    if ("OPTIONS".equals(request.getMethod())) {
//      response.setStatus(HttpServletResponse.SC_OK);
//      return;
//    }
//
//    String token = jwtTokenProvider.resolveToken(request);
//    try {
//      if (StringUtils.isNotEmpty(token)) {
//        if (jwtTokenProvider.validateToken(token)) {
//          UserTokenModel userTokenModel = jwtTokenProvider.getUserByToken(token);
//          Authentication auth = jwtTokenProvider.getAuthentication(userTokenModel, request);
//          if (auth != null) {
//            SecurityContextHolder.getContext().setAuthentication(auth);
//            filterChain.doFilter(request, response);
//            return;
//          }
//        } else {
//          response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Token");
//          return;
//        }
//      }
//      filterChain.doFilter(request, response);
//    } catch (Exception e) {
//      logger.error(e.getMessage());
//      SecurityContextHolder.clearContext();
//      if (e instanceof AccessDeniedException || e.getCause() instanceof AccessDeniedException)
//        response.sendError(HttpServletResponse.SC_CONFLICT, "Access is Denied");
//      else
//        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal Server Error");
//    }
//  }
//}
