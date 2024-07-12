package Model;

import io.jsonwebtoken.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TokenUtil {

    private static final String SECRET_KEY = "webcomics";

    public static String generateToken(User user) {
        try {
            long currentTimeMillis = System.currentTimeMillis();
            long expirationTimeMillis = currentTimeMillis + (14L * 24 * 60 * 60 * 1000); // 2 settimane
            Map<String, Object> claims = new HashMap<>();
            claims.put("email", user.getEmail());
            return Jwts.builder()
                    .setSubject(user.getEmail())
                    .setClaims(claims)
                    .setIssuedAt(new Date(currentTimeMillis))
                    .setExpiration(new Date(expirationTimeMillis))
                    .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                    .compact();
        } catch (JwtException e) {
            e.printStackTrace(System.out);
            return "";
        }
    }

    public static boolean validateToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getExpiration().after(new Date());
        } catch (SignatureException e) {
            return false;
        }
    }

    public static String getEmailFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }
}