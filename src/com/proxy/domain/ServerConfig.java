package com.proxy.domain;

public class ServerConfig {

	private String host;
	private int port;
	private String remoteServer;
	private int remotePort;
	private boolean enc;
	
	
	//是否需要响应Https协议，如果被代理端直接连接到代理服务器，就需要响应，如果是通过中间件连接过来的，中间件必须先响应Https协议代理服务器此时不需要再次响应
	private boolean responseHttps;
	
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	
	public boolean isResponseHttps() {
		return responseHttps;
	}
	public void setResponseHttps(boolean responseHttps) {
		this.responseHttps = responseHttps;
	}
	
	
	public String getRemoteServer() {
		return remoteServer;
	}
	public void setRemoteServer(String remoteServer) {
		this.remoteServer = remoteServer;
	}
	public int getRemotePort() {
		return remotePort;
	}
	public void setRemotePort(int remotePort) {
		this.remotePort = remotePort;
	}
	public boolean isEnc() {
		return enc;
	}
	public void setEnc(boolean enc) {
		this.enc = enc;
	}
	@Override
	public String toString() {
		return "ServerConfig [host=" + host + ", port=" + port + ", remoteServer=" + remoteServer + ", remotePort="
				+ remotePort + ", enc=" + enc + ", responseHttps=" + responseHttps + "]";
	}
	
}
