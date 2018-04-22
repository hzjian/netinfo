package com.netinfo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.netinfo.entity.SysUserEntity;
import com.netinfo.entity.SysUsersRangeEntity;
import com.netinfo.entity.SysUsersRoleEntity;
import com.netinfo.enums.ResultEnum;
import com.netinfo.exception.NetException;
import com.netinfo.repository.SysUserRepository;
import com.netinfo.repository.SysUsersRangeRepository;
import com.netinfo.repository.SysUsersRoleRepositry;


@Service
public class SysUserService implements UserDetailsService {

	private static final String ROLE_USER = "ROLE_USER";
	
    @Autowired
    private SysUserRepository sysUserRepository;
    
    @Autowired
    private SysUsersRoleRepositry sysUsersRoleRepositry;
    
    @Autowired
    private SysUsersRangeRepository sysUsersRangeRepository;
    
    
    @Transactional
    public void insertTwo() {
        SysUserEntity userA = new SysUserEntity();
        userA.setUserCnname("usera");
        sysUserRepository.save(userA);


        SysUserEntity userB = new SysUserEntity();
        userB.setUserCnname("userb");
        sysUserRepository.save(userB);
    }

    public void getEnabled(String userId) throws Exception{
    	SysUserEntity user = sysUserRepository.findOne(userId);
        Integer isEnable = user.getEnabled();
        if (isEnable == 0) {
            throw new NetException(ResultEnum.INVALIDUSER);
        }else if (isEnable == 1) {
            throw new NetException(ResultEnum.VALIDUSER);
        }

    }

    /**
     * 通过Id查询
     * @param id
     * @return
     */
    public SysUserEntity findOne(String userId) {
        return sysUserRepository.findOne(userId);
    }
    
    public Page<SysUserEntity> getAll(PageRequest pageInfo) {
		// TODO Auto-generated method stub
		return this.sysUserRepository.findAll(pageInfo);
	}

	public List<SysUserEntity> findByUserCnname(String cnname) {
		// TODO Auto-generated method stub
		return this.sysUserRepository.findByUserCnname(cnname);
	}

	public void delete(String id) {
		this.sysUserRepository.delete(id);
		
	}

	public SysUserEntity save(SysUserEntity user) {
		// TODO Auto-generated method stub
		return this.sysUserRepository.save(user);
	}


	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final Optional<SysUserEntity> user = sysUserRepository.findByUserId(username);
        
        List<SysUsersRoleEntity> userRoleList =  this.sysUsersRoleRepositry.findByUserId(username);
        if(userRoleList != null && userRoleList.size()>0)
        	user.get().setRoleId(userRoleList.get(0).getRoleId());

        final AccountStatusUserDetailsChecker detailsChecker = new AccountStatusUserDetailsChecker();
        user.ifPresent(detailsChecker::check);
        return user.orElseThrow(() -> new UsernameNotFoundException("user not found."));
    }
	
	public SysUserEntity loadUserByTaskId(Long taskId) {
       final List<SysUserEntity> user = sysUserRepository.findByCtaskId(taskId);
       if(user!= null && user.size()>0)
    	   return user.get(0);
       else
    	   return null;
    }

	public void delete(SysUserEntity user) {
		// TODO Auto-generated method stub
		this.sysUserRepository.delete(user);
		
	}

	public List<SysUsersRoleEntity> getRoleList(String userId) {
		// TODO Auto-generated method stub
		return this.sysUsersRoleRepositry.findByUserId(userId);
	}

	public List<SysUsersRangeEntity> getRangeList(String userId) {
		// TODO Auto-generated method stub
		return this.sysUsersRangeRepository.findByUserId(userId);
	}

	public void deleteRoleByUserId(SysUserEntity user) {
		// TODO Auto-generated method stub
		this.sysUsersRoleRepositry.deleteByUserId(user.getUserId());
		
	}

	public void saveUserRoleRelate(SysUsersRoleEntity sysUsersRoleEntity) {
		this.sysUsersRoleRepositry.save(sysUsersRoleEntity);
	}

	public void deleteRangeByUserId(SysUserEntity user) {
		// TODO Auto-generated method stub
		this.sysUsersRangeRepository.deleteByUserId(user.getUserId());
	}

	public void saveUserRangeRelate(SysUsersRangeEntity sysUsersRangeEntity) {
		// TODO Auto-generated method stub
		this.sysUsersRangeRepository.save(sysUsersRangeEntity);
	}
}
