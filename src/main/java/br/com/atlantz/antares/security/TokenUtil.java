package br.com.atlantz.antares.security;

import br.com.atlantz.antares.model.dto.LoginDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Collections;
import java.util.Date;
import java.util.UUID;

public class TokenUtil
{
    public static final String EMITER = "antares-app";
    public static final long EXPIRATION = 12*60*60*1000; // token expiration time: 12h
    public static final String SECRET_KEY = "jW0yAOPY9bl2ngC72d12wd231liuR39cdbiuLBWy223";

    public static AuthToken encode(UUID subject, String role)
    {
        try
        {
            Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
            String jwtToken = Jwts.builder()
                                  .subject(subject.toString())
                                  .issuer(EMITER)
                                  .claim("ROLE", "ROLE_"+role)
                                  .expiration(new Date(System.currentTimeMillis() + EXPIRATION))
                                  .signWith(key)
                                  .compact();
            return new AuthToken(jwtToken);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static Authentication decode(HttpServletRequest request)
    {
        try
        {
            String token = request.getHeader("Authorization");
            if (token != null)
            {
                token = token.replace("Bearer ", "");
                SecretKey secretKey = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
                JwtParser parser = Jwts.parser().verifyWith(secretKey).build();
                Claims claims = (Claims) parser.parse(token).getPayload();

                String subject = claims.getSubject();
                String issuer = claims.getIssuer();
                Date expiration = claims.getExpiration();
                String role = claims.get("ROLE").toString();

                if ((issuer != null && issuer.equals(EMITER))
                        && (subject != null && !subject.isEmpty())
                        && (expiration != null && expiration.after(new Date()))
                   )
                {
                    return new UsernamePasswordAuthenticationToken(subject, null, Collections.singletonList(new SimpleGrantedAuthority(role)));
                }
            }
        }
        catch (Exception e)
        {
            // TODO - treatement for SignatureException
            e.printStackTrace();
        }
        return null;
    }
}
