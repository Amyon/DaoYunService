package lu.springboot.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户从 token 中取出 Tele
 */
public class TokenUtil {

    public static String getTokenTele(){
        String token =getRequest().getHeader("token");
        String Tele = JWT.decode(token).getAudience().get(0);
        return Tele;
    }

    public static HttpServletRequest getRequest(){
        ServletRequestAttributes requestAttributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        return requestAttributes == null ? null :requestAttributes.getRequest();
    }
}
