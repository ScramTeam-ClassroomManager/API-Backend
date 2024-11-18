package it.unical.classroommanager_api.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JWTService {

	@Value("${jwt.secret}")
	private String jwtKey;

	@Value("${jwt.expiration}")
	private Long expiration;

	public String extractSerialNumber(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	private Claims extractAllClaims(String token) {
		return
				(Claims)Jwts.parser()
				.build()
				.parseSignedClaims(token)
				.getPayload();
	}

	public Boolean isTokenExpired(String token) {
		return Boolean.valueOf(extractExpiration(token).before(new Date()));
	}

	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		return createToken(claims, userDetails.getUsername());
	}


	private String createToken(Map<String, Object> claims, String subject) {
		return
				Jwts.builder()
				.claims(claims)
				.subject(subject)
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + expiration))
				.signWith(Keys.secretKeyFor(SignatureAlgorithm.HS256)).compact();
	}

	public Boolean validateToken(String token, UserDetails userDetails) {
		String username = extractSerialNumber(token);
		return Boolean.valueOf((username.equals(userDetails.getUsername()) && !isTokenExpired(token).booleanValue()));
	}
}


