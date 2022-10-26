package com.lib.security;
import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

public class AuthTokenFilter extends OncePerRequestFilter {

    @Autowired 
	private JwtUtils jwtUtils;	
    
    @Autowired
    private UserDetailsService userDetailsService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String jwtToken = parseJwt(request); // requestin içinden gelen tokeni alıyorum	
		try {
			if(jwtToken!=null && jwtUtils.validateToken(jwtToken)) { // 2.parametrede token validation yapılıyor
				String userName = jwtUtils.getUserNameFromJwtToken(jwtToken);
				UserDetails userDetails = userDetailsService.loadUserByUsername(userName); // user -validation
				
				request.setAttribute("mail",userDetails.getUsername());
				
				UsernamePasswordAuthenticationToken authentication = 
						new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(authentication); // login kullanıcı bilgilerini Contexte atıyorum
			}
		} catch (UsernameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		filterChain.doFilter(request, response);
		
		
	}
	
	
	private String parseJwt(HttpServletRequest request) {
		// requestin header kısmındaki Authorization
		String header = request.getHeader("Authorization");
		if(StringUtils.hasText(header)&&header.startsWith("Bearer ")) {
			return header.substring(7);
			
		}
		return null;
		
		
	}
	
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		AntPathMatcher antMatcher = new AntPathMatcher();
		return antMatcher.match("/register", request.getServletPath()) || 
				antMatcher.match("/login", request.getServletPath());
	}

}



