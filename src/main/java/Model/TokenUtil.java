package Model;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TokenUtil {

    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

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
                    .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
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
        } catch (JwtException e) {
            e.printStackTrace(System.out);
            return false;
        }
    }

    public static String getEmailFromToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();

            // Verifica se il token è scaduto
            if (claims.getExpiration().before(new Date())) {
                return null; // Token scaduto
            }

            return (String) claims.get("email"); // Restituisce il subject (solitamente l'email) dal token
        } catch (JwtException e) {
            return null;
        }
    }
}