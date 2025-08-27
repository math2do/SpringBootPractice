package in.math2do.practice.config;

import org.springframework.lang.*;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import jakarta.servlet.http.*;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ApiLoggingInterceptor implements HandlerInterceptor {

  @Override
  public boolean preHandle(@NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response, @NonNull Object handler) throws Exception {
    long startTime = System.currentTimeMillis();
    request.setAttribute("startTime", startTime);

    String clientIp = getClientIp(request);

    log.info("➡️ Incoming API call: {} {} from IP={}", request.getMethod(), request.getRequestURI(),
        clientIp);

    return true; // continue to controller
  }

  @Override
  public void afterCompletion(@NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response, @NonNull Object handler, @Nullable Exception ex)
      throws Exception {

    long startTime = (Long) request.getAttribute("startTime");
    long duration = System.currentTimeMillis() - startTime;

    log.info("✅ Completed API call: {} {} -> status={} in {} ms", request.getMethod(),
        request.getRequestURI(), response.getStatus(), duration);
  }

  private String getClientIp(HttpServletRequest request) {
    String xfHeader = request.getHeader("X-Forwarded-For");
    if (xfHeader == null) {
      return request.getRemoteAddr();
    }
    return xfHeader.split(",")[0]; // first IP in case of multiple proxies
  }
}
