package com.proxy.remote;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import com.proxy.common.DataFilter;
import com.proxy.common.OnBrokenCallBack;
import com.proxy.common.ReadDataRunnable;
import com.proxy.common.ReadDataThread;
import com.proxy.domain.ForWard;
import com.proxy.utils.Log;

/**
 * 代理服务器端运行的程序
 */
public class ProxyServerManager {

	private static ThreadPoolExecutor pool;
	static {
		pool= (ThreadPoolExecutor)Executors.newCachedThreadPool();
	}

	public static void startProxyServer(ForWard forWard,boolean responseHttpsProtocol) throws IOException {
		ServerSocket serverSocket = new ServerSocket(forWard.getPort());
		Socket clientSocket = null;
		Log.println("LocalProxy Server Ready On:" + forWard.getHost() + ":" + forWard.getPort());

		while ((clientSocket = serverSocket.accept()) != null) {
			InetAddress clientAddress = clientSocket.getInetAddress();
			Log.println("新请求:" + clientAddress.getCanonicalHostName() + "线程池大小:" + pool.getPoolSize()+" 核心线程:"+pool.getCorePoolSize()+" 活动线程:"+pool.getActiveCount());
			ReadDataRunnable readDataRunnable=new ReadDataRunnable(clientSocket,responseHttpsProtocol);
			pool.submit(readDataRunnable);
		}
		System.out.println("=======end=======");
	}
	
}
