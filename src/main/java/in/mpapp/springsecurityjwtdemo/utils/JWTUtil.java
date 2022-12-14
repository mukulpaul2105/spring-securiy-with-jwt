package in.mpapp.springsecurityjwtdemo.utils;

import in.mpapp.springsecurityjwtdemo.dtos.TokenDTO;
import in.mpapp.springsecurityjwtdemo.dtos.UserDTO;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class JWTUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiry}")
    private Long expiry;

    /*
    1 jwt utility -----> Creating token
     */
    public TokenDTO generateToken(final UserDTO userDTO) {
         final Map<String, Object> claims = new HashMap<>();
         claims.put("account", userDTO.getId());
         final LocalDateTime currentTime = LocalDateTime.now();
         log.info("Current Local Date Time: {} ", currentTime);
         final LocalDateTime expiryLDT = currentTime.plus(expiry, ChronoUnit.MINUTES);
         log.info("Expiry Local Date Time: {} ", expiryLDT);
         // This line will basically convert Locale Date Time to Date
         final Date expiryDate = Date.from(expiryLDT.atZone(ZoneId.systemDefault()).toInstant());
         log.info("Local Date Time to Date: {} ", expiryLDT, " " , expiryDate);
         final SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
         final String token = Jwts.builder()
                 .setClaims(claims)
                 .setSubject(userDTO.getUsername())
                 .setExpiration(expiryDate)
                 .signWith(secretKey, SignatureAlgorithm.HS512)
                 .compact();
         log.info("Generated Token: {} ", token);
         return new TokenDTO(token, expiryLDT);
    }

    /*
    jwt utility -------> parsing token
     */
//    public boolean validateToken(final String token, final UserDTO userDTO) {
//        log.info("Validating Token for User Details: {} ", token, userDTO);
//        Jwts.parserBuilder()
//    }

}
