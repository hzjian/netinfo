package com.netinfo.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jasig.cas.client.util.AbstractCasFilter;
import org.jasig.cas.client.validation.Assertion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.netinfo.entity.SysUserEntity;
import com.netinfo.security.JwtTokenHandler;
import com.netinfo.service.SysUserService;

public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

	private final static Logger logger = LoggerFactory.getLogger(JwtAuthenticationTokenFilter.class);
	private static String CONST_CAS_ASSERTION ="_const_cas_assertion_";

    @Autowired
    private JwtTokenHandler jwtTokenHandler; 
    
    @Autowired
    private SysUserService  sysUserService;

    @Value("${jwt.tokenName}")
    private String tokenName;
    
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

        String auth_token = request.getHeader(this.tokenName);
        if (!StringUtils.isEmpty(auth_token) && auth_token.startsWith(this.tokenHead)) {
            auth_token = auth_token.substring(this.tokenHead.length());
        } else {
            // 不按规范,不允许通过验证
            auth_token = null;
        }
        
        final HttpSession session = request.getSession(false);  
        final Assertion assertion = (Assertion) (session == null ? 	request.getAttribute(AbstractCasFilter.CONST_CAS_ASSERTION) : 	session.getAttribute(AbstractCasFilter.CONST_CAS_ASSERTION));
    	// AttributePrincipal principal = assertion.getPrincipal();
    	// String authType= assertion.getPrincipal().getAttributes().get("authType").toString();
    	// authType 表示账号的类型，1表示账号为管理员，0表示普通用户。    用户名命名规范：4位区域代码+4位用户代码。

        // 如果session中的CONST_CAS_ASSERTION（即"_const_cas_assertion_"）不为空，则跳过此过滤器  
        if(assertion != null )
        {
        	Assertion  tmp = assertion;
        	//logger.info("64");
        	/*
        	logger.info("CONST_CAS_ASSERTION=="+tmp.getPrincipal().getName() );
        	 
        	for (String key : tmp.getPrincipal().getAttributes().keySet()) {  
        		  
        		logger.info("Key = " + key);  
        	  
        	}  
        	  
        	for (Object value : tmp.getPrincipal().getAttributes().values()) {
        	  
        		logger.info("Value = " + value.toString());  
        	  
        	}  
        	*/
        	//tmp.getPrincipal().getAttributes().get("userUUID");
        	String taskId = tmp.getPrincipal().getName();
        	try {
        		//System.out.println("taskId=="+taskId);
				Long lntaskId = Long.parseLong(taskId);
				
				//System.out.println("lntaskId=="+lntaskId);
				SysUserEntity userDetails = sysUserService.loadUserByTaskId(lntaskId);
				//System.out.println("userDetails=="+userDetails.getUsername());
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
	            //logger.info(String.format("Authenticated user %s, setting security context", username));
	            
	            //logger.info("authentication=="+authentication.getName());
	            SecurityContextHolder.getContext().setAuthentication(authentication);
	            //logger.info("authentication==75");

	            String tmpToken = jwtTokenHandler.createTokenForUser(userDetails);
	            response.addHeader(this.tokenName, tmpToken);
	            response.addHeader("x-auth-name", userDetails.getUserId());
				
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	//logger.info("userName == "+ tmp.getPrincipal().getAttributes().get("userName"));
        	
        	//UserDetails userDetails = sysUserService.loadUserByUsername(tempUserName);

        	//logger.info("userDetails=="+userDetails.getUsername() +" userDetails.getAuthorities() " + userDetails.getAuthorities());
        	
        }   
        else
        {

	        String username = jwtTokenHandler.getUsernameFromToken(auth_token);
	        ///logger.info(String.format("Checking authentication for user %s.", username));
	
	        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
	            // It is not compelling necessary to load the use details from the database. You could also store the information
	            // in the token and read it from it. It's up to you ;)
	        	// UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
	            UserDetails userDetails = jwtTokenHandler.getUserFromToken(auth_token);
	
	            // For simple validation it is completely sufficient to just check the token integrity. You don't have to call
	            // the database compellingly. Again it's up to you ;)
	            if (jwtTokenHandler.validateToken(auth_token, userDetails)) {
	                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
	                //logger.info(String.format("Authenticated user %s, setting security context", username));
	                SecurityContextHolder.getContext().setAuthentication(authentication);
	            }
	        }
        }
        chain.doFilter(request, response);
    }
}