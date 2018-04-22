package com.netinfo.bak;

import java.util.EventListener;
import java.util.LinkedHashMap;
import java.util.Map;

import org.jasig.cas.client.authentication.AuthenticationFilter;
import org.jasig.cas.client.session.SingleSignOutFilter;
import org.jasig.cas.client.session.SingleSignOutHttpSessionListener;
import org.jasig.cas.client.util.AssertionThreadLocalFilter;
import org.jasig.cas.client.util.HttpServletRequestWrapperFilter;
import org.jasig.cas.client.validation.Cas20ServiceTicketValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.cas.ServiceProperties;
import org.springframework.security.cas.authentication.CasAssertionAuthenticationToken;
import org.springframework.security.cas.authentication.CasAuthenticationProvider;
import org.springframework.security.cas.web.CasAuthenticationEntryPoint;
import org.springframework.security.cas.web.CasAuthenticationFilter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;

import com.ncs.bigdtplatform.authsrv.client.filter.ProxyReceivingTicketValidationFilter;
import com.netinfo.config.CasPropComponent;
import com.netinfo.config.CustomUserDetailsService;


//@Configuration
//@EnableWebSecurity
//@Order(2)
public class CasSecurityConfig extends  WebSecurityConfigurerAdapter{

	@Autowired
	private CasPropComponent casPropComponent;
	
	private int orderNum =20;
	 
	
	@Override  
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {  
        super.configure(auth);  
        auth.authenticationProvider(casAuthenticationProvider());  
        //inMemoryAuthentication 从内存中获取  
        //auth.inMemoryAuthentication().withUser("chengli").password("123456").roles("USER")  
        //.and().withUser("admin").password("123456").roles("ADMIN");  
          
        //jdbcAuthentication从数据库中获取，但是默认是以security提供的表结构  
        //usersByUsernameQuery 指定查询用户SQL  
        //authoritiesByUsernameQuery 指定查询权限SQL  
        //auth.jdbcAuthentication().dataSource(dataSource).usersByUsernameQuery(query).authoritiesByUsernameQuery(query);  
          
        //注入userDetailsService，需要实现userDetailsService接口  
        //auth.userDetailsService(userDetailsService);  
    }  
      
    /**定义安全策略*/  
    @Override  
    protected void configure(HttpSecurity http) throws Exception {  
        http.authorizeRequests()//配置安全策略  
            //.antMatchers("/","/hello").permitAll()//定义/请求不需要验证  
            .anyRequest().authenticated()//其余的所有请求都需要验证  
            .and()  
        .logout()  
            .permitAll()//定义logout不需要验证  
            .and()  
        .formLogin();//使用form表单登录  
          
        http.exceptionHandling().authenticationEntryPoint(casAuthenticationEntryPoint())  
            .and()  
            .addFilter(authenticationFilter())  
            //.addFilterBefore(casLogoutFilter(), LogoutFilter.class)  
            .addFilterBefore(getSingleSignOutFilter(), SingleSignOutFilter.class);  
          
        //http.csrf().disable(); //禁用CSRF  
    }  
      
    /**认证的入口*/  
    @Bean  
    public CasAuthenticationEntryPoint casAuthenticationEntryPoint() {  
        CasAuthenticationEntryPoint casAuthenticationEntryPoint = new CasAuthenticationEntryPoint();  
        casAuthenticationEntryPoint.setLoginUrl(casPropComponent.getCasServerLoginUrl());  
        casAuthenticationEntryPoint.setServiceProperties(serviceProperties());  
        return casAuthenticationEntryPoint;  
    }  
      
    /**指定service相关信息*/  
    @Bean  
    public ServiceProperties serviceProperties() {  
        ServiceProperties serviceProperties = new ServiceProperties();  
        serviceProperties.setService(casPropComponent.getAppServerName());  
        serviceProperties.setAuthenticateAllArtifacts(true);  
        return serviceProperties;  
    }  
      
    /**CAS认证过滤器*/  
    @Bean  
    public CasAuthenticationFilter casAuthenticationFilter() throws Exception {  
        CasAuthenticationFilter casAuthenticationFilter = new CasAuthenticationFilter();  
        casAuthenticationFilter.setAuthenticationManager(authenticationManager());  
        casAuthenticationFilter.setFilterProcessesUrl(casPropComponent.getAppServerName());  
        
        return casAuthenticationFilter;  
    }  
      
    /**cas 认证 Provider*/  
    @Bean  
    public CasAuthenticationProvider casAuthenticationProvider() {  
        CasAuthenticationProvider casAuthenticationProvider = new CasAuthenticationProvider();  
        casAuthenticationProvider.setAuthenticationUserDetailsService(customUserDetailsService());  
        //casAuthenticationProvider.setUserDetailsService(customUserDetailsService()); //这里只是接口类型，实现的接口不一样，都可以的。  
        casAuthenticationProvider.setServiceProperties(serviceProperties());  
        casAuthenticationProvider.setTicketValidator(cas20ServiceTicketValidator());  
        casAuthenticationProvider.setKey("casAuthenticationProviderKey");  
        return casAuthenticationProvider;  
    }  
       
      
    /**用户自定义的AuthenticationUserDetailsService*/  
    @Bean  
    public AuthenticationUserDetailsService<CasAssertionAuthenticationToken> customUserDetailsService(){  
        return new CustomUserDetailsService();  
    }  
      
    @Bean  
    public Cas20ServiceTicketValidator cas20ServiceTicketValidator() {  
        return new Cas20ServiceTicketValidator(casPropComponent.getCasServerLoginUrl());  
    }  
      
    /**单点登出过滤器*/  
    @Bean  
    public SingleSignOutFilter singleSignOutFilter() {  
        SingleSignOutFilter singleSignOutFilter = new SingleSignOutFilter();  
        singleSignOutFilter.setCasServerUrlPrefix(casPropComponent.getCasServerLoginUrl());  
        singleSignOutFilter.setIgnoreInitConfiguration(true);  
        return singleSignOutFilter;  
    }  
      
    /**请求单点退出过滤器*/  
    /*
    @Bean  
    public LogoutFilter casLogoutFilter() {  
        LogoutFilter logoutFilter = new LogoutFilter(casProperties.getCasServerLogoutUrl(), new SecurityContextLogoutHandler());  
        logoutFilter.setFilterProcessesUrl(casProperties.getAppLogoutUrl());  
        return logoutFilter;  
    }  
	*/

    ////  1----
	@Bean
	public SingleSignOutFilter getSingleSignOutFilter()
	{
		SingleSignOutFilter ssFilter = new SingleSignOutFilter();
		return ssFilter;

	}
	
	
	@Bean 
	public AuthenticationFilter authenticationFilter()
	{
		
		AuthenticationFilter tmpAuth = new AuthenticationFilter();
		tmpAuth.setCasServerLoginUrl(casPropComponent.getCasServerLoginUrl());
		tmpAuth.setServerName(casPropComponent.getAppServerName());
		
		return tmpAuth;
	}
	//  3----
	
	
	@Bean
	public ProxyReceivingTicketValidationFilter getProxyReceivingTicketValidationFilter()
	{
		Map<String,String> paramList = new LinkedHashMap<String,String>();
		paramList.put("casServerUrlPrefix", "http://172.16.8.35/authsrv");
		paramList.put("serverName", "http://localhost");
		
		ProxyReceivingTicketValidationFilter tmpProxy = new ProxyReceivingTicketValidationFilter();
		
		tmpProxy.setServerName("http://localhost");
		//tmpProxy.setActualAuthUrl("http://172.16.8.35/authsrv");
		
		tmpProxy.setProxyReceptorUrl("http://172.16.8.35/authsrv");
		//tmpProxy.setCasServerUrlPrefix("http://172.16.8.35/authsrv");
		return tmpProxy;
		
		
	}
	
	//  4----
	@Bean
	public HttpServletRequestWrapperFilter getHttpServletRequestWrapperFilter()
	{
		HttpServletRequestWrapperFilter httpwrapperFilter = new HttpServletRequestWrapperFilter();

		return httpwrapperFilter;
		
	} 
	
	//  5----
	@Bean
	public AssertionThreadLocalFilter getAssertionThreadLocalFilter()
	{
		AssertionThreadLocalFilter assertfilter = new AssertionThreadLocalFilter();

		return assertfilter;
	}
	

	//  6----
	@Bean 
	public ServletListenerRegistrationBean<EventListener> getSingleSignOutHttpSessionListener()
	{
		ServletListenerRegistrationBean<EventListener> registrationBean = 
				new ServletListenerRegistrationBean<EventListener>();
		registrationBean.setListener(new SingleSignOutHttpSessionListener());
		return registrationBean;
	}

	
	 /*
	//  1----
	@Bean
	public FilterRegistrationBean getSingleSignOutFilter()
	{
		FilterRegistrationBean registrationBean=new FilterRegistrationBean();
		registrationBean.setFilter(new SingleSignOutFilter());
		List<String> urlPatterns=new ArrayList<String>();
		urlPatterns.add("/*");//拦截路径，可以添加多个
		registrationBean.setUrlPatterns(urlPatterns);
		registrationBean.setOrder(4+orderNum);
		return registrationBean;
	}
	
	//  2----
	@Bean 
	public FilterRegistrationBean getAuthenticationFilter()
	{
		
		Map<String,String> paramList = new LinkedHashMap<String,String>();
		paramList.put("casServerLoginUrl", "http://172.16.8.35/authsrv");
		paramList.put("serverName", "http://localhost");
		
		AuthenticationFilter tmpAuth = new AuthenticationFilter();
		FilterRegistrationBean registrationBean=new FilterRegistrationBean();
		registrationBean.setFilter(tmpAuth);
		List<String> urlPatterns=new ArrayList<String>();
		urlPatterns.add("/*");//拦截路径，可以添加多个
		registrationBean.setUrlPatterns(urlPatterns);
		registrationBean.setInitParameters(paramList);
		registrationBean.setOrder(2+orderNum);
		return registrationBean;
	}
	//  3----
	
	@Bean
	public FilterRegistrationBean getProxyReceivingTicketValidationFilter()
	{
		Map<String,String> paramList = new LinkedHashMap<String,String>();
		paramList.put("casServerUrlPrefix", "http://172.16.8.35/authsrv");
		paramList.put("serverName", "http://localhost");
		
		ProxyReceivingTicketValidationFilter tmpProxy = new ProxyReceivingTicketValidationFilter();
		
		FilterRegistrationBean registrationBean=new FilterRegistrationBean();
		registrationBean.setFilter(tmpProxy);
		List<String> urlPatterns=new ArrayList<String>();
		urlPatterns.add("/*");//拦截路径，可以添加多个
		registrationBean.setUrlPatterns(urlPatterns);
		registrationBean.setInitParameters(paramList);
		registrationBean.setOrder(3+orderNum);
		return registrationBean;
		
	}
	//  4----
	@Bean
	public FilterRegistrationBean getHttpServletRequestWrapperFilter()
	{
		FilterRegistrationBean registrationBean=new FilterRegistrationBean();
		registrationBean.setFilter(new HttpServletRequestWrapperFilter());
		List<String> urlPatterns=new ArrayList<String>();
		urlPatterns.add("/*");//拦截路径，可以添加多个
		registrationBean.setUrlPatterns(urlPatterns);
		registrationBean.setOrder(4+orderNum);
		return registrationBean;
	}
	
	//  5----
	@Bean
	public FilterRegistrationBean getAssertionThreadLocalFilter()
	{
		FilterRegistrationBean registrationBean=new FilterRegistrationBean();
		registrationBean.setFilter(new AssertionThreadLocalFilter());
		List<String> urlPatterns=new ArrayList<String>();
		urlPatterns.add("/*");//拦截路径，可以添加多个
		registrationBean.setUrlPatterns(urlPatterns);
		registrationBean.setOrder(5+orderNum);
		return registrationBean;
	}
	

	//  6----
	@Bean 
	public ServletListenerRegistrationBean<EventListener> getSingleSignOutHttpSessionListener()
	{
		ServletListenerRegistrationBean<EventListener> registrationBean = 
				new ServletListenerRegistrationBean<EventListener>();
		registrationBean.setListener(new SingleSignOutHttpSessionListener());
		return registrationBean;
	}
	*/
}
