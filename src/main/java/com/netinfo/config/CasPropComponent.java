package com.netinfo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component  
@ConfigurationProperties(prefix="cas") 
public class CasPropComponent {

	private String casServerLoginUrl;
	private String appServerName;
	/**
	 * @return the casServerLoginUrl
	 */
	public String getCasServerLoginUrl() {
		return casServerLoginUrl;
	}
	/**
	 * @param casServerLoginUrl the casServerLoginUrl to set
	 */
	public void setCasServerLoginUrl(String casServerLoginUrl) {
		this.casServerLoginUrl = casServerLoginUrl;
	}
	/**
	 * @return the appServerName
	 */
	public String getAppServerName() {
		return appServerName;
	}
	/**
	 * @param appServerName the appServerName to set
	 */
	public void setAppServerName(String appServerName) {
		this.appServerName = appServerName;
	}
	
}
