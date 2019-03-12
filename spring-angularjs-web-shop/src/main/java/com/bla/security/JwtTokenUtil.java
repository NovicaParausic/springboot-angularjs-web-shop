package com.bla.security;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.bla.common.TimeProvider;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtil {

    @Value("${jwt.header}")
    private String tokenHeader;
    
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;
    
    public String getUsernameFromToken(String token){
    	final Claims claims = getAllClaimsFromToken(token);
    	return claims.getSubject();
    }
    
    public String getUsernameFromRequest(HttpServletRequest request){
    	String authToken = request.getHeader(tokenHeader);
    	final String token = authToken.substring(7);
    	return getUsernameFromToken(token);
    }
    
    public Date getIssuedAtDateFromToken(String token){
    	final Claims claims = getAllClaimsFromToken(token);
    	return claims.getIssuedAt();
    }
    
    public Date getExpirationDateFromToken(String token){
    	final Claims claims = getAllClaimsFromToken(token);
    	return claims.getExpiration();
    }
    
    private Claims getAllClaimsFromToken(String token){
    	return Jwts.parser()
    			.setSigningKey(secret)
    			.parseClaimsJws(token)
    			.getBody();
    }
    
    private Boolean isTokenExpired(String token){
    	final Date expiration = getExpirationDateFromToken(token);
    	return expiration.before(TimeProvider.now());
    }
    
    private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset){
    	return (lastPasswordReset != null && created.before(lastPasswordReset));
    }
    
    public String generateToken(UserDetails userDetails){
    	final Date createdDate = TimeProvider.now();
    	final Date expirationDate = calculateExpirationDate(createdDate);
    	Claims claims = this.getAuthorities(userDetails);
    	
    	System.out.println("claims" + claims.toString());
    	System.out.println("doGenerateToken" + createdDate);
    	
    	return Jwts.builder()
    			.setSubject(userDetails.getUsername())
    			.setClaims(claims)
    			.setIssuedAt(createdDate)
    			.setExpiration(expirationDate)
    			.signWith(SignatureAlgorithm.HS512, secret)
    			.compact();
    }
    private Claims getAuthorities(UserDetails userDetails){
    	Claims claims = Jwts.claims().setSubject(userDetails.getUsername());
    	
    	Collection<? extends GrantedAuthority> grandAuths = userDetails.getAuthorities();
    	    	
    	Iterator<? extends GrantedAuthority> iter = grandAuths.iterator();
    	
    	while(iter.hasNext()) {
    		SimpleGrantedAuthority auth = (SimpleGrantedAuthority )iter.next();
    		claims.put("role", auth.getAuthority());
    	}
    	return claims;
    	
    }
    public Boolean canTokenBeRereshed(String token, Date lastPasswordReset){
    	final Date created = getIssuedAtDateFromToken(token);
    	return !isCreatedBeforeLastPasswordReset(created, lastPasswordReset)
    			&& !isTokenExpired(token);
    }
    
    public String refreshToken(String token){
    	final Date createdDate = TimeProvider.now();
    	final Date expirationDate = this.calculateExpirationDate(createdDate);
    	
    	final Claims claims = getAllClaimsFromToken(token);
    	claims.setIssuedAt(createdDate);
    	claims.setExpiration(expirationDate);
    	
    	return Jwts.builder()
    			.setClaims(claims)
    			.signWith(SignatureAlgorithm.HS512, secret)
    			.compact();
    }
    
    public Boolean validateToken(String token, UserDetails userDetails){
    	JwtUser user = (JwtUser) userDetails;
    	final String username = getUsernameFromToken(token);
    	final Date created = getIssuedAtDateFromToken(token);
    	
    	return (
    			username.equals(user.getUsername())
    				&& !isTokenExpired(token)
    				&& !isCreatedBeforeLastPasswordReset(created, user.getLastPasswordResetDate())
    			);
    }
    
    private Date calculateExpirationDate(Date createdDate){
    	return new Date(createdDate.getTime() + expiration * 1000);
    }
}
