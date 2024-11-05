package it.unical.classroommanager_api.security;

import it.unical.classroommanager_api.configuration.i18n.MessageLang;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;


@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private JWTService jwtService;
	
	@Autowired
	private CustomUserDetailsService userDetailService;

	@Autowired
	private MessageLang messageLang;
	
	@Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;
	
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

		String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		String token = null;
		String username = null;
		
		try {
			if (authorizationHeader != null && authorizationHeader.startsWith("Bearer")) {
				token = authorizationHeader.substring(7);
				try {
					username = this.jwtService.extractSerialNumber(token);
				} catch (Exception e) {
					e.printStackTrace();
					throw new UsernameNotFoundException(messageLang.getMessage("user.not.found"));
				} 
				UserDetails userDetails = this.userDetailService.loadUserByUsername(username);
				if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
					UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
					usernamePasswordAuthenticationToken.setDetails((new WebAuthenticationDetailsSource()).buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication((Authentication)usernamePasswordAuthenticationToken);
				} 
			} 
			filterChain.doFilter(request,response);
		}
		catch(Exception e) {
			resolver.resolveException(request, response, null, e);
		}
	}
	
	
}


