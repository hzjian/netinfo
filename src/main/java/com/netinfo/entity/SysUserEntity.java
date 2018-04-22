package com.netinfo.entity;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * The persistent class for the SYS_USERS database table.
 * 
 */
@Entity
@Table(name = "SYS_USERS")
@NamedQuery(name = "SysUserEntity.findAll", query = "SELECT s FROM SysUserEntity s")
public class SysUserEntity implements UserDetails {
	private static final long serialVersionUID = 1L;

	@Id
	//@GeneratedValue(generator="system-uuid")
	//@GenericGenerator(name="system-uuid",strategy="uuid")
	@Column(name = "USER_ID")
	private String userId;

	@Column(name = "DEPT_ID")
	private String deptId;

	private Integer enabled;

	private Integer issys;

	@Column(name = "SUB_SYSTEM")
	private String subSystem;

	@Column(name = "USER_CNNAME")
	private String userCnname;

	@Column(name = "USER_DESC")
	private String userDesc;

	@Column(name = "USER_DUTY")
	private String userDuty;

	@Column(name = "USER_PASSWORD")
	private String userPassword;
	
	
	@Column(name = "CTASK_ID")
	private Long ctaskId;
	
	private boolean account_enabled;
	
	private boolean account_non_expired;
	
	private boolean account_non_locked;
	
	@Transient
	private String roleId;
	
	/*
	//@JsonIgnore
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "SYS_USERS_ROLES",
            joinColumns        = {@JoinColumn(name = "user_id", referencedColumnName = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id",  referencedColumnName = "role_id")}
    )
    private SysRoleEntity role;
	
	//@JsonIgnore
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "SYS_USERS_RANGE",
            joinColumns        = {@JoinColumn(name = "user_id", referencedColumnName = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "range_id",  referencedColumnName = "range_id")}
    )
    private SysRangeEntity range;
*/
	public SysUserEntity() {
	}

	public SysUserEntity(String userId2, String username, String psw, boolean account_enabled,
			boolean account_non_expired, boolean account_non_locked,
			Collection<? extends GrantedAuthority> authorities) {
		// TODO Auto-generated constructor stub
		this.userId = userId2;
		this.userCnname = username;
		this.userPassword = psw;
		this.account_enabled = account_enabled;
		this.account_non_expired = account_non_expired;
		this.account_non_locked = account_non_locked;
		if(authorities!= null && authorities.size()>0)
		{
			//System.out.println(authorities.toArray()[0].toString());
			 for(GrantedAuthority ga:authorities){
				 this.roleId = ga.getAuthority();
	            }
		}
			
		
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getDeptId() {
		return this.deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public Integer getEnabled() {
		return enabled;
	}

	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}

	public Integer getIssys() {
		return issys;
	}

	public void setIssys(Integer issys) {
		this.issys = issys;
	}

	public String getSubSystem() {
		return this.subSystem;
	}

	public void setSubSystem(String subSystem) {
		this.subSystem = subSystem;
	}

	public String getUserCnname() {
		return this.userCnname;
	}

	public void setUserCnname(String userCnname) {
		this.userCnname = userCnname;
	}

	public String getUserDesc() {
		return this.userDesc;
	}

	public void setUserDesc(String userDesc) {
		this.userDesc = userDesc;
	}

	public String getUserDuty() {
		return this.userDuty;
	}

	public void setUserDuty(String userDuty) {
		this.userDuty = userDuty;
	}

	public String getUserPassword() {
		return this.userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	
	public UsernamePasswordAuthenticationToken toAuthenticationToken() {
		// TODO Auto-generated method stub
		return  new UsernamePasswordAuthenticationToken(userId, userPassword, getAuthorities());
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> authorities = new ArrayList<>();
			if(this.getRoleId() != null)
			{
	            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(this.getRoleId());
	            authorities.add(authority);
			}
        return authorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.userPassword;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.userId;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}


	/**
	 * @return the roleId
	 */
	public String getRoleId() {
		return roleId;
	}

	/**
	 * @param roleId the roleId to set
	 */
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	/**
	 * @return the ctaskId
	 */
	public Long getCtaskId() {
		return ctaskId;
	}

	/**
	 * @param ctaskId the ctaskId to set
	 */
	public void setCtaskId(Long ctaskId) {
		this.ctaskId = ctaskId;
	}
	
	
}