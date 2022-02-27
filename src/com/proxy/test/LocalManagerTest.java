package com.proxy.test;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

import com.proxy.common.DataFilter;
import com.proxy.common.OnBrokenCallBackInconnctor;
import com.proxy.domain.ForWard;
import com.proxy.domain.ServerConfig;
import com.proxy.middleware.ConnectorThread;
import com.proxy.utils.Log;

public class LocalManagerTest {

	private static HashMap<String, ConnectorThreadTest> connectorMapPool = new HashMap<String, ConnectorThreadTest>();

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
			Log.redPrint("收到本地代理请求:" + clientAddress.toString() + " 线程池大小:" + connectorMapPool.size());

			ConnectorThreadTest connector = new ConnectorThreadTest(clientSocket, forWardTarget,
					new OnBrokenCallBackInconnctor() {
						@Override
						public void onBroken(Exception e, String tag, Thread connector) {
							Log.println("删除前本地转发连接线程池池大小:" + connectorMapPool.size() + "==>关闭原因:" + e.getMessage());
							ConnectorThreadTest gcThread = connectorMapPool.remove(connector.toString());
							if (null != gcThread && !gcThread.isInterrupted()) {
								gcThread.interrupt();
							}
							gcThread = null;
							Log.println("删除后本地转发连接线程池池大小:" + connectorMapPool.size());
						}
					});

//			connector.start();
			connectorMapPool.put(connector.toString(), connector);
		}
	}
}
