package com.proxy.common;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import com.alibaba.fastjson.util.IOUtils;
import com.proxy.domain.ForWard;
import com.proxy.utils.Log;

/**
 * 代理通道
 */
public class NetContentGrabber extends Thread {

	private String host;
	private int port;
	private Socket socket;
	private InputStream inputStream;
	private OutputStream outputStream;

	private boolean isCloseIO = false;

	private OnDataReceiveCallBack onDataReceiveCallBack;

	public NetContentGrabber(ForWard forWard, OnDataReceiveCallBack onDataReceiveCallBack) {
		this.host = forWard.getHost();
		this.port = forWard.getPort();
		try {
			Log.println("准备JONIT链接到====>: " + host + ":" + port);
			this.socket = new Socket(host, port);
			Log.println("已经JONIT链接到====>: " + host + ":" + port);
			this.inputStream = this.socket.getInputStream();
			this.outputStream = this.socket.getOutputStream();
			this.onDataReceiveCallBack = onDataReceiveCallBack;

		} catch (Exception e) {
			closeConnect();
			Log.redPrint("链接故障:" + forWard.getHost() + " " + forWard.getPort() + " " + e.getLocalizedMessage());
			if (null != onDataReceiveCallBack) {
				onDataReceiveCallBack.onBrokenInGrabber(e, "NetContentGrabber.NetContentGrabber");
			}
		}finally {
			
		}

	}

	public void closeConnect() {
		IOUtils.close(outputStream);
		IOUtils.close(inputStream);
		IOUtils.close(socket);
		isCloseIO = true;
	}

	/**
	 * 向代理服务器发送数据
	 * 
	 * @param data
	 * @param start
	 * @param offset
	 * @throws IOException
	 */
	public void sendData(byte[] data, int start, int offset) throws IOException {
		if (null != this.outputStream && !isCloseIO) {
			this.outputStream.write(data, start, offset);
			this.outputStream.flush();
		}
	}

	/**
	 * 接受数据
	 * 
	 * @param buffer
	 * @param start
	 * @param offset
	 */
	private void onReceiveData(byte[] buffer, int start, int offset) {
		if (null != onDataReceiveCallBack) {
			onDataReceiveCallBack.onReceiveData(buffer, start, offset);
		}
	}

	@Override
	public void run() {
		if (null != this.inputStream) {
			byte[] buffer = new byte[Constant.BUFFER_SIZE];
			int ret = -1;
			try {
				while ((ret = this.inputStream.read(buffer)) != -1) {
					onReceiveData(buffer, 0, ret);
				}
			} catch (IOException e) {
				closeConnect();
				if (null != onDataReceiveCallBack) {
					onDataReceiveCallBack.onBrokenInGrabber(e, "NetContentGrabber.run");
				}
			} finally {
				
			}
			if (null != onDataReceiveCallBack) {
				onDataReceiveCallBack.onBrokenInGrabber(null, "NetContentGrabber.run");
			}

		}
	}

}
