package info.schuwan.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

public class JWTService {
    private static final String SECRET = "DontEverDoThis";
    private static final SignatureAlgorithm SIGNING_ALGO = SignatureAlgorithm.HS256;
    private final Key key;
    private static final long TTL = 3600000L;

    public JWTService() {
        byte[] secretBytes = DatatypeConverter.parseBase64Binary(SECRET);
        key = new SecretKeySpec(secretBytes, SIGNING_ALGO.getJcaName());
    }
    public String generate(String subject) {
        Date now = new Date(System.currentTimeMillis());
        return Jwts.builder()
                .setSubject(subject)
                .setExpiration(new Date(now.getTime() + TTL))
                .signWith(SIGNING_ALGO, key)
                .compact();
    }

    public Claims decode(String token) {
        return Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET))
                .parseClaimsJws(token)
                .getBody();
    }
}
