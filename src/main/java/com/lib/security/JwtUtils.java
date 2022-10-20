package com.lib.security;
import java.util.Date;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import com.lib.security.service.UserDetailsImpl;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtUtils {
	
	private String jwtSecret = "batch82";
	
	// 24*60*60*1000
	private long jwtExpirationMs = 86400000;//token yaşam süresi
	
	//************* GENERATE -- TOKEN  ************
	public String generateToken(Authentication authentication) {
		
		// anlık olarak login olarak kullanıcının bilgisini alıyorum
		    UserDetailsImpl userDetails  = (UserDetailsImpl) authentication.getPrincipal();
		 // Token builder() ile üretiliyor
		  // token üretilirken UserName ve secret key kullanılıyor  
		 return Jwts.builder() .
				               setSubject(userDetails.getUsername()).				 
				               setIssuedAt(new Date()).//token oluşturma zamanı
				               setExpiration(new Date(new Date().getTime()+ jwtExpirationMs)).//token son kullanma tarihi
				               signWith(SignatureAlgorithm.HS512, jwtSecret).//
				               compact() ;
	}
	

	//***************************************************
	
	//******************* VALIDATE-TOKEN**************
	public boolean validateToken(String token) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
			return true;
		} catch (ExpiredJwtException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedJwtException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedJwtException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SignatureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	//**********************************************
	
	//********** JWt tokenden userName'i alalım
	public String getUserNameFromJwtToken(String token) {
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
	}

}