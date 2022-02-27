package com.proxy.remote.starter;

import java.io.File;
import java.io.IOException;

import com.proxy.domain.ForWard;
import com.proxy.domain.ServerConfig;
import com.proxy.remote.ProxyServerManager;
import com.proxy.utils.ProjectTool;

public class RemoteStarter {

	public static void main(String[] args) throws IOException, InterruptedException {

		String configPath = System.getProperty("user.dir") + File.separator + "RemoteConfig.json";
		ServerConfig config = ProjectTool.readConfigFile(configPath);

		System.out.println("工作目录:" + configPath);
		System.out.println("服务器配置信息:" + config.toString());

//		config.setHost("127.0.0.1");
//		config.setPort(1666);
//		config.setResponseHttps(true);

		ProxyServerManager.startProxyServer(new ForWard(config.getHost(), config.getPort()), config.isResponseHttps());
	}

}
