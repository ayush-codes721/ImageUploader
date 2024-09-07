package ImageUploader.Service.JWT;

import ImageUploader.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
public class JwtService {

    @Value("${jwt.secretKey}")
    private String secretKey;
    Date expirationDate = new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(20));


    private SecretKey secretKey(){
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String createAccessToken(User user) {

        String token = Jwts.builder()
                .subject(user.getId().toString())
                .claim("username", user.getUsername())
                .issuedAt(new Date())
                .expiration(expirationDate)
                .signWith(secretKey())
                .compact();

        return token;
    }

    public Claims getClaims(String token) {

        return Jwts.parser()
                .verifyWith(secretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public Long getIdFromToken(String token) {

        Claims claims = getClaims(token);
        String subject = claims.getSubject();
        return Long.valueOf(subject);
    }

    public String getUsernameFromToken(String token) {

        Claims claims = getClaims(token);

        return claims.get("username", String.class);
    }

}
