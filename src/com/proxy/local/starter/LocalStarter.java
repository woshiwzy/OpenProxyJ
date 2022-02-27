package com.proxy.local.starter;

import com.proxy.domain.ServerConfig;
import com.proxy.local.LocalManager;
import com.proxy.utils.Log;
import com.proxy.utils.ProjectTool;

import java.io.File;
import java.io.IOException;

/**
 * 需要代理的电脑运行程序
 */
public class LocalStarter {

    public static void main(String[] args) throws IOException, InterruptedException {

        String configPath = System.getProperty("user.dir") + File.separator + "LocalConfig.json";
        ServerConfig config = ProjectTool.readConfigFile(configPath);
        Log.println("工作目录:" + configPath);
        Log.println("配置信息:" + config.toString());
        LocalManager.startLocalService(config);

    }

}
