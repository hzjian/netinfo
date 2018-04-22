package com.netinfo.config;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.netinfo.filters.JwtAuthenticationTokenFilter;
import com.netinfo.filters.StatelessLoginFilter;
import com.netinfo.security.TokenAuthenticationService;
import com.netinfo.service.SysUserService;


@Configuration
@EnableWebSecurity
@Order(3)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	private static final Logger logger = Logger.getLogger(WebSecurityConfig.class);
	
  
	/*
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	
    	http.headers().disable();
        http
            .authorizeRequests()
                .antMatchers("/", "/**","/home","/api").permitAll()
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/index.html")
                .permitAll()
                .and()
            .logout()
                .permitAll();
        
        http.csrf().disable();
        
        File directory = new File("");//设定为当前文件夹 
        logger.info("path===="+directory.getAbsolutePath());
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .inMemoryAuthentication()
                .withUser("user").password("password").roles("USER");
    }
	 */
	
	@Autowired  
    private SysUserService userService;
	
	@Autowired
	private TokenAuthenticationService tokenAuthenticationService;

	 @Bean
	public JwtAuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
        return new JwtAuthenticationTokenFilter();
    }
	 

	
	
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {

    	httpSecurity
        // 由于使用的是JWT，我们这里不需要csrf,csrf又称跨域请求伪造，攻击方通过伪造用户请求访问受信任站点
        .csrf().disable()
        .exceptionHandling()
        .and()
	        // 基于token，所以不需要session
	        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
	        .authorizeRequests()
	        //.antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
	
	        // 允许对于网站静态资源的无授权访问
	        .antMatchers(
	                HttpMethod.GET,
	                "/","'/login/**",
	                "/dist/**",
	                "/assets/**",
	                "/*.html",
	                "/favicon.ico",
	                "/**/*.html",
	                "/**/*.css",
	                "/**/*.js",
	                "/**/*.json",
	                "/**/*.jpg",
	                "/**/*.png",
	                "/**/*.wof","/**/*.woff2",
	                "/**/*.ttf"
	        		)
	        .permitAll()
	        .antMatchers("/auth/login").permitAll().antMatchers("/api/*/**").permitAll()
	    .anyRequest() 
        // 除上面外的所有请求全部需要鉴权认证
        .authenticated();

    	
		// 禁用缓存
    	httpSecurity.headers().cacheControl();
    	
    	httpSecurity.addFilterBefore(
                new StatelessLoginFilter("/auth/login", tokenAuthenticationService, userService, authenticationManager()),
                UsernamePasswordAuthenticationFilter.class);
		
    	httpSecurity.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);

    }
    
    @Autowired
    public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(this.userService)
                .passwordEncoder(new BCryptPasswordEncoder());
    }
    
    
    @Override
    protected UserDetailsService userDetailsService() {
        return userService;
    }

}