package com.netinfo.filters;


import java.io.IOException;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netinfo.entity.SysUserEntity;
import com.netinfo.entity.SysUsersRoleEntity;
import com.netinfo.security.TokenAuthenticationService;
import com.netinfo.security.UserAuthentication;
import com.netinfo.service.SysUserService;

public class StatelessLoginFilter extends AbstractAuthenticationProcessingFilter {

    private final TokenAuthenticationService tokenAuthenticationService;
    private final SysUserService userService;

    public StatelessLoginFilter(String urlMapping,TokenAuthenticationService tokenAuthenticationService,
    		SysUserService userService, AuthenticationManager authenticationManager) {
        super(urlMapping);
        this.tokenAuthenticationService = tokenAuthenticationService;
        this.userService = userService;
        setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        final SysUserEntity user = toUser(request);
        //final SysUserEntity realUser = this.userService.findOne(user.getUserId());
        List<SysUsersRoleEntity> tmpUserroleList = this.userService.getRoleList(user.getUserId());
        if(tmpUserroleList != null && tmpUserroleList.size()>0)
        	user.setRoleId(tmpUserroleList.get(0).getRoleId());
        
        final UsernamePasswordAuthenticationToken loginToken = user.toAuthenticationToken();
        return getAuthenticationManager().authenticate(loginToken);
    }

    private SysUserEntity toUser(HttpServletRequest request) throws IOException {
        return new ObjectMapper().readValue(request.getInputStream(), SysUserEntity.class);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        final UserDetails authenticatedUser = userService.loadUserByUsername(authResult.getName());
        final UserAuthentication userAuthentication = new UserAuthentication(authenticatedUser);
        tokenAuthenticationService.addJwtTokenToHeader(response, userAuthentication);
        SecurityContextHolder.getContext().setAuthentication(userAuthentication);
    }
}