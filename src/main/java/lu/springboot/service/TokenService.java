package lu.springboot.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lu.springboot.entity.dy_user;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 下发Token
 */
@Service("tokenService")
public class TokenService {
    public String getToken(dy_user user) {
        Date start = new Date();
        long currentTime = System.currentTimeMillis() + 1000 * 60 * 60;//60* 60 * 1000;一小时有效时间
        Date end = new Date(currentTime);


        String token = JWT.create().withAudience(user.getUser_tele()).withIssuedAt(start).withExpiresAt(end)
                .sign(Algorithm.HMAC256(user.getUser_pwd()));
        return token;
    }
}
