package io.github.hoongjian_0w0.cmsback.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

/**
 * JWT utility class
 */
public class JwtUtil {

    // Token expiration time: 60 * 60 * 1000 milliseconds = 1 hour
    public static final Long JWT_TTL = 60 * 60 * 1000L;

    // Secret key (salt)
    public static final String JWT_KEY = "cms";

    // Generate a unique token ID (UUID without dashes)
    public static String getUUID() {
        String token = UUID.randomUUID().toString().replaceAll("-", "");
        return token;
    }

    /**
     * Create JWT
     * @param subject The data to be stored in the token (JSON format)
     * @param ttlMillis Expiration time in milliseconds
     * @return Signed JWT string
     */
    public static String createJWT(String subject, Long ttlMillis) {
        JwtBuilder builder = getJwtBuilder(subject, ttlMillis, getUUID()); // Set expiration time
        return builder.compact();
    }

    /**
     * Create token
     * @param id        Token ID
     * @param subject   Payload (can be JSON)
     * @param ttlMillis Expiration time in milliseconds
     * @return          JWT string
     */
    public static String createJWT(String id, String subject, Long ttlMillis) {
        JwtBuilder builder = getJwtBuilder(subject, ttlMillis, id); // Set expiration
        return builder.compact();
    }

    // Business logic to generate JWT
    private static JwtBuilder getJwtBuilder(String subject, Long ttlMillis, String uuid) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        SecretKey secretKey = generalKey();
        long nowMillis = System.currentTimeMillis(); // Get current system time
        Date now = new Date(nowMillis);

        if (ttlMillis == null) {
            ttlMillis = JwtUtil.JWT_TTL;
        }

        long expMillis = nowMillis + ttlMillis;
        Date expDate = new Date(expMillis);

        return Jwts.builder()
                .setId(uuid) // Unique ID
                .setSubject(subject) // Subject: can be JSON data
                .setIssuer("CMS") // Issuer
                .setIssuedAt(now) // Issue time
                .signWith(signatureAlgorithm, secretKey) // Use HS256 algorithm and secret key
                .setExpiration(expDate); // Expiration time
    }

    /**
     * Generate secret key for signing JWT
     * @return SecretKey
     */
    public static SecretKey generalKey() {
        byte[] encodedKey = Base64.getDecoder().decode(JwtUtil.JWT_KEY);
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }
    /**
     * Parse JWT
     *
     * @param jwt the token to be parsed
     * @return Claims object containing the payload
     * @throws Exception if parsing fails
     */
    public static Claims parseJWT(String jwt) throws Exception {
        SecretKey secretKey = generalKey();
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(jwt)
                .getBody();
    }

}
