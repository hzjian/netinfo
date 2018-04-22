package com.netinfo.config;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jasig.cas.client.authentication.AuthenticationFilter;
import org.jasig.cas.client.session.SingleSignOutFilter;
import org.jasig.cas.client.session.SingleSignOutHttpSessionListener;
import org.jasig.cas.client.util.AssertionThreadLocalFilter;
import org.jasig.cas.client.util.HttpServletRequestWrapperFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;

import com.ncs.bigdtplatform.authsrv.client.filter.ProxyReceivingTicketValidationFilter;

//@Configuration 
public class CasConfig {
	
	@Autowired
	private CasPropComponent casPropComponent;
	
	private int orderNum =20;
	
	private static final Logger logger = Logger.getLogger(CasConfig.class);
	
	private String casServerUrl = "http://172.16.8.35/authsrv";
	
	private String appServerUrl = "http://172.16.15.160:8081";

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
		logger.info("getSingleSignOutFilter---43");
		return registrationBean;
	}
	
	//  2----
	@Bean 
	public FilterRegistrationBean getAuthenticationFilter()
	{
		
		Map<String,String> paramList = new LinkedHashMap<String,String>();
		paramList.put("casServerLoginUrl", this.casServerUrl);
		paramList.put("serverName", this.appServerUrl);
		
		AuthenticationFilter tmpAuth = new AuthenticationFilter();
		FilterRegistrationBean registrationBean=new FilterRegistrationBean();
		registrationBean.setFilter(tmpAuth);
		List<String> urlPatterns=new ArrayList<String>();
		urlPatterns.add("/*");//拦截路径，可以添加多个
		registrationBean.setUrlPatterns(urlPatterns);
		registrationBean.setInitParameters(paramList);
		registrationBean.setOrder(2+orderNum);
		logger.info("getAuthenticationFilter---64");
		return registrationBean;
	}
	//  3----
	
	@Bean
	public FilterRegistrationBean getProxyReceivingTicketValidationFilter()
	{
		Map<String,String> paramList = new LinkedHashMap<String,String>();
		paramList.put("casServerUrlPrefix", this.casServerUrl);
		paramList.put("serverName", this.appServerUrl);
		
		ProxyReceivingTicketValidationFilter tmpProxy = new ProxyReceivingTicketValidationFilter();
		
		FilterRegistrationBean registrationBean=new FilterRegistrationBean();
		registrationBean.setFilter(tmpProxy);
		List<String> urlPatterns=new ArrayList<String>();
		urlPatterns.add("/*");//拦截路径，可以添加多个
		registrationBean.setUrlPatterns(urlPatterns);
		registrationBean.setInitParameters(paramList);
		registrationBean.setOrder(3+orderNum);
		logger.info("getProxyReceivingTicketValidationFilter---85");
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
		logger.info("getHttpServletRequestWrapperFilter---99");
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
		logger.info("getAssertionThreadLocalFilter---113");
		return registrationBean;
	}
	

	//  6----
	@Bean 
	public ServletListenerRegistrationBean<EventListener> getSingleSignOutHttpSessionListener()
	{
		ServletListenerRegistrationBean<EventListener> registrationBean = 
				new ServletListenerRegistrationBean<EventListener>();
		registrationBean.setListener(new SingleSignOutHttpSessionListener());
		logger.info("getSingleSignOutHttpSessionListener---125");
		return registrationBean;
	}
}
