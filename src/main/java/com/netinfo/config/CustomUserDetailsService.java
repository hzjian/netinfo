package com.netinfo.config;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.cas.authentication.CasAssertionAuthenticationToken;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.netinfo.entity.SysUserEntity;
import com.netinfo.entity.SysUsersRoleEntity;
import com.netinfo.repository.SysUserRepository;
import com.netinfo.repository.SysUsersRoleRepositry;

@Service
public class CustomUserDetailsService implements AuthenticationUserDetailsService<CasAssertionAuthenticationToken> {

    @Autowired
    private SysUserRepository sysUserRepository;
    
    @Autowired
    private SysUsersRoleRepositry sysUsersRoleRepositry ;
    
	@Override
	public UserDetails loadUserDetails(CasAssertionAuthenticationToken token) throws UsernameNotFoundException {

		final Optional<SysUserEntity> user = sysUserRepository.findByUserId(token.getName());
        
        List<SysUsersRoleEntity> userRoleList =  this.sysUsersRoleRepositry.findByUserId(token.getName());
        if(userRoleList != null && userRoleList.size()>0)
        	user.get().setRoleId(userRoleList.get(0).getRoleId());

        final AccountStatusUserDetailsChecker detailsChecker = new AccountStatusUserDetailsChecker();
        user.ifPresent(detailsChecker::check);
        return user.orElseThrow(() -> new UsernameNotFoundException("user not found."));
	}

}
