package com.proxy.remote;

import java.io.IOException;

import com.proxy.domain.ForWard;
import com.proxy.domain.ServerConfig;
import com.proxy.utils.ProjectTool;

public class RemoteProxyServer {

	public static void main(String[] args) throws IOException, InterruptedException {

		ServerConfig config = ProjectTool.readConfigFile("/Users/wangzy/eclipse-workspace/MyNat/src/RemoteConfig.json");

		System.out.println("服务器配置信息:" + config.toString());
		String currentDir = System.getProperty("user.dir");

		ProxyServerManager.startProxyServer(new ForWard("127.0.0.1", 1666),config.isResponseHttps());

	}

}
