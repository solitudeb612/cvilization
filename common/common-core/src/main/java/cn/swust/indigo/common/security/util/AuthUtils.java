package cn.swust.indigo.common.security.util;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;

import javax.servlet.http.HttpServletRequest;

/**
 * 认证授权相关工具类
 */
@Slf4j
@UtilityClass
public class AuthUtils {
    private final String BASIC_ = "Basic ";

    /**
     * @param token header中的参数
     */
    @SneakyThrows
    public String extractAndDecodeHeader(String token) {
        return null;
    }

    /**
     * *从header 请求中的clientId/clientsecect
     *
     * @param request
     * @return
     */
    @SneakyThrows
    public String extractAndDecodeHeader(HttpServletRequest request) {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
//		if (header == null ) {
//			throw new RuntimeException("请求头中认证授权信息为空");
//		}
        return header;
    }
}
