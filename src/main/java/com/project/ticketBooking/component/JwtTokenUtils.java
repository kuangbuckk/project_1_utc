package com.project.ticketBooking.component;

import com.project.ticketBooking.exceptions.InvalidParamException;
import com.project.ticketBooking.models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.security.SecureRandom;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class JwtTokenUtils {
    @Value("${jwt.expiration}")
    private int expiration;

    @Value("${jwt.secretKey}")
    private String secretKey;

    //To generate JWT tokens
    public String generateToken(User user) throws InvalidParamException {
        Map<String, Object> claims = new HashMap<>(); // claims là một bản đồ dữ liệu (key-value) dùng để lưu các thông tin mà bạn muốn "nhúng" vào JWT.
//        this.generateSecretKey();
        claims.put("email", user.getEmail());
        claims.put("userId", user.getId());
        if (user.getOrganization() != null) {
            claims.put("organizationId", user.getOrganization().getId());
        }
        try {
            String token = Jwts.builder()
                    .setClaims(claims) // Thêm dữ liệu claims vào token. Đây là phần chính chứa thông tin bạn muốn lưu trữ trong JWT.
                    .setSubject(user.getEmail()) // subject là một trường trong payload của JWT, thường dùng để đại diện cho danh tính chính của người dùng (ở đây là email).
                    // Thiết lập thời gian hết hạn cho JWT.
                    // System.currentTimeMillis() trả về thời gian hiện tại tính bằng mili-giây.
                    // expiration * 1000L là số giây bạn muốn JWT có hiệu lực (thường được đặt trong cấu hình). Sau thời gian này, token sẽ hết hạn và không còn hợp lệ.
                    .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000L))
                    // Ký token bằng khóa bí mật (getSignInKey()) và thuật toán mã hóa HS256 (HMAC with SHA-256).
                    // Phần khóa bí mật được dùng để bảo vệ token. Chỉ những ai có khóa này mới có thể giải mã và xác thực token.
                    .signWith(getSignInKey(), SignatureAlgorithm.HS256) //secret key để dịch ngược lại ra claims
                    .compact();
            return token;
        } catch (Exception e) {
            //TODO: Use Logger instead
           throw new InvalidParamException("Cannot create jwt token, error: " + e.getMessage());
        }
    }

    //Hàm getSignInKey() mà bạn cung cấp được sử dụng
    // để tạo ra một khóa bí mật (signing key) cho việc ký và xác thực JWT
    private Key getSignInKey(){
        byte[] bytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(bytes);
    }

//    private String generateSecretKey(){
//        SecureRandom random = new SecureRandom();
//        byte[] keyBytes = new byte[32]; //256 bit key
//        random.nextBytes(keyBytes);
//        String secretKey = Encoders.BASE64.encode(keyBytes);
//        return secretKey;
//    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public  <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = this.extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    //check exp
    public boolean isTokenExpired(String token) {
        Date expirationDate = this.extractClaim(token, Claims::getExpiration);
        return expirationDate.before(new Date());
    }

    public Integer extractOrganizationId(String token) {
        return extractClaim(token, claims -> (Integer) claims.get("organizationId"));
    }

    public String extractEmail(String token) {
        String email = extractClaim(token, Claims::getSubject);
        return email;
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        String email = extractEmail(token);
        return (email.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
