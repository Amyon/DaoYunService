package lu.springboot.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lu.springboot.entity.User;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 下发Token
 */
@Service("tokenService")
public class TokenService {
    public String getToken(User user) {
        Date start = new Date();
        long currentTime = System.currentTimeMillis() + 1000 * 60 * 60;//60* 60 * 1000;一小时有效时间
        Date end = new Date(currentTime);


        String token = JWT.create().withAudience(user.getTele()).withIssuedAt(start).withExpiresAt(end)
                .sign(Algorithm.HMAC256(user.getPassWord()));
        return token;
    }
}
