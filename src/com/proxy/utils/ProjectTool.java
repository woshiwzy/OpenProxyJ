package com.proxy.utils;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import com.alibaba.fastjson.JSONObject;
import com.proxy.domain.ServerConfig;

public class ProjectTool {

	/**
	 * 读取配置服务器配置文件
	 * 
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public static ServerConfig readConfigFile(String path) throws IOException {

		StringBuffer sbf = new StringBuffer();
		if (null != path) {
			File configFile = new File(path);
			if (configFile.isFile() && configFile.exists()) {
				sbf.append(FileUtils.readFileToString(configFile, "utf8"));
			}
		}

		String configContent = sbf.toString();
		ServerConfig serverConfig = new ServerConfig();

		if (null != configContent && configContent.length() > 0) {
			JSONObject json = JSONObject.parseObject(configContent);

			serverConfig.setRemotePort(json.getIntValue("remotePort"));
			serverConfig.setRemoteServer(json.getString("remoteServer"));
			serverConfig.setPort(json.getIntValue("port"));
			serverConfig.setHost(json.getString("host"));
			serverConfig.setEnc(json.getBooleanValue("enc"));
			serverConfig.setResponseHttps(json.getBooleanValue("responseHttps"));
		}

		return serverConfig;
	}
}
