package com.proxy.test;

import java.io.File;
import java.io.IOException;

import com.proxy.domain.ForWard;
import com.proxy.domain.ServerConfig;
import com.proxy.remote.ProxyServerManager;
import com.proxy.utils.ProjectTool;

public class LocalProxyServerConnectorTest {

	public static void main(String[] args) throws IOException, InterruptedException {

		String configPath = System.getProperty("user.dir") + File.separator + "RemoteConfig.json";
		System.out.println(configPath + " 是否存在配置文件:" + (new File(configPath)).exists());
		ServerConfig config = ProjectTool.readConfigFile(configPath);
		
		config.setHost("127.0.0.1");
		config.setPort(1666);
		config.setResponseHttps(true);
		LocalManagerTest.startLocalService(config);
		
//		System.out.println("服务器配置信息:" + config.toString());
//		ProxyServerManager.startProxyServer(new ForWard(config.getHost(),config.getPort()),config.isResponseHttps());

	}

}
