package com.netinfo.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Component
@ConfigurationProperties(prefix = "system")
public class SysProperties {

    private String appName;
    private String token;
    private String gisServerPath;
    private String secret;
	/**
	 * @return the appName
	 */
	public String getAppName() {
		return appName;
	}
	/**
	 * @param appName the appName to set
	 */
	public void setAppName(String appName) {
		this.appName = appName;
	}
	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}
	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}
	/**
	 * @return the gisServerPath
	 */
	public String getGisServerPath() {
		return gisServerPath;
	}
	/**
	 * @param gisServerPath the gisServerPath to set
	 */
	public void setGisServerPath(String gisServerPath) {
		this.gisServerPath = gisServerPath;
	}
	/**
	 * @return the secret
	 */
	public String getSecret() {
		return secret;
	}
	/**
	 * @param secret the secret to set
	 */
	public void setSecret(String secret) {
		this.secret = secret;
	}
    
    

}
