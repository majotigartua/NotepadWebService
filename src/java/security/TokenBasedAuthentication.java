package security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.TimeZone;
import model.pojo.Response;
import model.pojo.Session;
import model.pojo.User;
import util.Constants;

public class TokenBasedAuthentication {
    
    public static Session generateAccessToken(Session session) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("security.jsonwebtokenconfiguration");
        String secretKey = resourceBundle.getString("SECRET_KEY");
        int expirationTimeInMinutes = Integer.parseInt(resourceBundle.getString("EXPIRATION_TIME_IN_MINUTES"));
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS512;
        Calendar currentTimeZone = Calendar.getInstance(TimeZone.getTimeZone("CST"));
        Date currentTime = currentTimeZone.getTime();
        currentTimeZone.add(Calendar.MINUTE, expirationTimeInMinutes);
        Date expirationTime = currentTimeZone.getTime();
        String accessToken = Jwts.builder()
                .setSubject(session.getUser().toString())
                .setIssuer(session.getUser().toString())
                .setIssuedAt(currentTime)
                .setExpiration(expirationTime)
                .claim("user", session.getUser())
                .signWith(signatureAlgorithm, secretKey)
                .compact();
        session.setAccessToken(accessToken);
        return session;
    }
    
    public static Response validateAccessToken(String accessToken) {
        Response response = new Response();
        ResourceBundle resourceBundle = ResourceBundle.getBundle("security.jsonwebtokenconfiguration");
        String secretKey = resourceBundle.getString("SECRET_KEY");
        if (!accessToken.isEmpty()) {
            try {
                Claims unencryptedAccessToken = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(accessToken).getBody();
                Session session = new Session();
                session.setUser((User) unencryptedAccessToken.get("user"));
                response.setError(false);
                response.setSession(session);
            } catch (ExpiredJwtException | MalformedJwtException | SignatureException | UnsupportedJwtException | IllegalArgumentException exception) {
                response.setError(true);
                response.setMessage(exception.getMessage());
            }
        } else {
            response.setError(true);
            response.setMessage(Constants.NULL_OR_EMPTY_ACCESS_TOKEN);
        }
        return response;
    }
    
}