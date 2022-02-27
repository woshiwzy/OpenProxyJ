package com.proxy.local;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import com.proxy.common.DataFilter;
import com.proxy.common.OnBrokenCallBackInconnctor;
import com.proxy.domain.ForWard;
import com.proxy.domain.ServerConfig;
import com.proxy.middleware.ConnectorRunnable;
import com.proxy.middleware.ConnectorThread;
import com.proxy.utils.Log;

public class LocalManager {

	private static ThreadPoolExecutor pool;

	static {
//		 pool =(ThreadPoolExecutor) Executors.newFixedThreadPool(30);
		pool =(ThreadPoolExecutor) Executors.newCachedThreadPool();
//		pool =(ThreadPoolExecutor) Executors.newSingleThreadExecutor();//error
	}

	public static void startLocalService(ServerConfig config) throws IOException, InterruptedException {
		// 代理服务器地址
		String proxyHost = config.getRemoteServer();
		// 代理服务器端口
		int proxyPort = config.getRemotePort();
		// 本地服务永远是localhost 或者 127.0.0.1
		String localhost = config.getHost();
		// 本地代理端口可以是任意没有被占用的端口
		int localport = config.getPort();

		ServerSocket serverSocket = new ServerSocket(localport);
		Socket clientSocket = null;
		System.out.println("代理转发服务已开启:" + localhost + ":" + localport);
		ForWard forWardTarget = new ForWard(proxyHost, proxyPort);// 转发目标(代理服务器地址或者中专服务器地址)

		while ((clientSocket = serverSocket.accept()) != null) {
			InetAddress clientAddress = clientSocket.getInetAddress();
			ConnectorRunnable connectorRunnable=new ConnectorRunnable(clientSocket,forWardTarget);
			pool.submit(connectorRunnable);
			Log.redPrint("收到本地代理请求:" + clientAddress.toString() + " 线程池大小:" + pool.getPoolSize()+" 核心线程:"+pool.getCorePoolSize()+" 活动线程："+pool.getActiveCount());
		}


	}
}
