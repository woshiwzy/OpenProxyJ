package com.proxy.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import com.alibaba.fastjson.util.IOUtils;
import com.proxy.common.Constant;
import com.proxy.common.NetContentGrabber;
import com.proxy.common.OnBrokenCallBackInconnctor;
import com.proxy.common.OnDataReceiveCallBack;
import com.proxy.common.RequestBannerMethodAna;
import com.proxy.domain.ForWard;
import com.proxy.utils.Log;

/**
 * 连接件，把来自浏览器等被代理端的请求盲转发
 */
public class ConnectorThreadTest extends Thread {

	private OnBrokenCallBackInconnctor onBrokenCallBackInconnctor;// 处理异常回调

	private Socket localSocket;// 接受本地浏览器代理输入Socket
	private InputStream proxyedInputStream;// 被代理的输入数据(把这个输入流的东西转发到代理)
	private OutputStream proxyedOutputStream;// 被代理的输出流(把代理服务器的返回写入到被代理的服务器)
	private boolean isCloseIO = false;

//	private boolean readBanner = false;
	private NetContentGrabber netContentGrabber;

	public ConnectorThreadTest(Socket socket, ForWard forward, OnBrokenCallBackInconnctor onBrokenCallBackInconnctor) {
		this.localSocket = socket;
		this.onBrokenCallBackInconnctor = onBrokenCallBackInconnctor;

		try {
			this.proxyedInputStream = this.localSocket.getInputStream();
			this.proxyedOutputStream = this.localSocket.getOutputStream();
			// 链接代理服务器
//			proxyedOutputStream.write(Constant.HTTPS_CONNECT_MSG.getBytes("utf-8"));
		} catch (IOException e) {
			closeConnect();
			e.printStackTrace();
			Log.redPrint("本地转发器错误:" + e.getLocalizedMessage());
		} finally {
			if (null != onBrokenCallBackInconnctor) {
				onBrokenCallBackInconnctor.onBroken(new Exception("在ConnectorThread构造中通信结束"), "Connector.Run",
						ConnectorThreadTest.this);
			}
		}

		start();
	}

	@Override
	public void run() {
		if (null != proxyedInputStream) {
			try {
				int readSize = -1;
				// 读取浏览器数据，发送给代理服务器
				byte[] buffer = new byte[Constant.BUFFER_SIZE * 2];

				boolean getHeader = false;

				while ((readSize = proxyedInputStream.read(buffer)) != -1) {
					if (getHeader == false) {
//						Log.redPrint("首次收到数据:\n" + new String(buffer,0,readSize));
						ForWard forward = RequestBannerMethodAna.anaForward(new String(buffer));
						if (null != forward && null == netContentGrabber) {
							netContentGrabber = new NetContentGrabber(forward, new OnDataReceiveCallBack() {
								@Override
								public void onReceiveData(byte[] buffer, int start, int offset) {
									try {
										if (!isCloseIO) {
											proxyedOutputStream.write(buffer, start, offset);
										}
									} catch (IOException e) {
										e.printStackTrace();
									}
								}

								@Override
								public void onBrokenInGrabber(Exception e, String tag) {

								}
							});
							netContentGrabber.start();
							if (forward.isHttps() && !isCloseIO) {
								proxyedOutputStream.write(Constant.HTTPS_CONNECT_MSG.getBytes("utf-8"));
								Log.println("回应Https");
							} else {
								netContentGrabber.sendData(buffer, 0, readSize);
							}
						}
						getHeader = true;
					} else {
						netContentGrabber.sendData(buffer, 0, readSize);
					}
				}

			} catch (IOException e) {
//				e.printStackTrace();
				closeConnect();
				Log.redPrint("本地转发器异常结束:" + e.getLocalizedMessage());
			} finally {
				if (null != onBrokenCallBackInconnctor) {
					onBrokenCallBackInconnctor.onBroken(new Exception("正常数据连接结束"), "Connector.Run",
							ConnectorThreadTest.this);
				}
			}
		}
	}

	public void closeConnect() {
		IOUtils.close(proxyedInputStream);
		IOUtils.close(proxyedOutputStream);
		IOUtils.close(localSocket);
		isCloseIO = true;
	}

}
