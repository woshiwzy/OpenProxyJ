package com.proxy.common;

import com.alibaba.fastjson.util.IOUtils;
import com.proxy.domain.ForWard;
import com.proxy.utils.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * 数据读取线程，适用于 Http和Https 代理请求转发到真实的主机
 */
public class ReadDataRunnable extends Thread {

	private Socket socket;
	private InputStream inputStream;
	private OutputStream outputStream;
	private NetContentGrabber netGrabber;
	private boolean responseHttpsProtocol;// 是否需要在本服务器响应Https协议

	private DataFilter dataFilter;

	public ReadDataRunnable(Socket socket, boolean responseHttpsProtocol) throws IOException {
		this.socket = socket;
		this.dataFilter = new DataFilter();
		this.responseHttpsProtocol = responseHttpsProtocol;
		this.inputStream = this.socket.getInputStream();
		this.outputStream = this.socket.getOutputStream();
	}

	/**
	 * 断网回调
	 * @param e
	 * @param tag
	 */
	private void onBroken(Exception e, String tag) {

		IOUtils.close(outputStream);
		IOUtils.close(inputStream);
		IOUtils.close(socket);

		if (null != netGrabber) {
			netGrabber.closeConnect();
		}
	}

	private NetContentGrabber getNetContentGrabber(ForWard forward) {

		if (null == netGrabber) {
			netGrabber = new NetContentGrabber(forward, new OnDataReceiveCallBack() {

				@Override
				public void onBrokenInGrabber(Exception e, String tag) {
					onBroken(e, tag);
				}

				@Override
				public void onReceiveData(byte[] buffer, int start, int offset) {
					try {
						if (null != dataFilter) {
							dataFilter.encodeData(buffer, start, offset);// 加密
						}
						outputStream.write(buffer, start, offset);
					} catch (IOException e) {
						Log.redPrint("转发数据异常:" + e.getLocalizedMessage());
						onBroken(e, "getNetContentGrabber");
					}
				}
			});
		}

		return netGrabber;
	}

	@Override
	public void run() {
		if (null != inputStream) {
			try {
				ByteArrayOutputStream byo = new ByteArrayOutputStream();
				// 只要能读取到目标地址就停止读取，建立代理连接
				// 继续读取信息写入到目标服务器
				NetContentGrabber netGrabber = null;
				int ret = -1;
				byte[] dataBuffer = new byte[512];
				boolean getHeader = false;
				while ((ret = inputStream.read(dataBuffer)) != -1) {
					if (null != dataFilter) {// 解密
						dataFilter.decodeData(dataBuffer, 0, ret);
					}

					if (!getHeader) {
						byo.write(dataBuffer, 0, ret);// 保存本次转发的头部信息
						byte[] nowData = byo.toByteArray();
//						System.out.println(new String(nowData));
						String header = new String(nowData);
						ForWard forward = RequestBannerMethodAna.anaForward(header);
//						System.out.println("头信息：" + forward.toString());
						if (null != forward && forward.isParseSuccess()) {
							netGrabber = getNetContentGrabber(forward);
							netGrabber.start();
							if (forward.isHttps()) {
								// 如果读到这个头不要转发给目标服务器
								// 如果是Https协议需要告诉浏览器一个固定的头部
								if (responseHttpsProtocol) {

									byte[] httpsResonseBytes = Constant.HTTPS_CONNECT_MSG.getBytes();
									if (null != dataFilter) {
										dataFilter.encodeData(httpsResonseBytes, 0, httpsResonseBytes.length);// 加密
									}
									outputStream.write(httpsResonseBytes);
									outputStream.flush();

									Log.redPrint("回应:" + Constant.HTTPS_CONNECT_MSG + " ====>>>" + forward.getHost());
								} else {
									Log.redPrint("无须回应:" + Constant.HTTPS_CONNECT_MSG + " ====>>>" + forward.getHost());
								}

							} else {
								// 如果是Http原封原样的写到目标通道中
								netGrabber.sendData(nowData, 0, nowData.length);
							}
							getHeader = true;
						}
					} else {
						// 得到头以后的数据就正常盲转发
						netGrabber.sendData(dataBuffer, 0, ret);
					}
				}
				
			} catch (IOException e) {
				onBroken(e, "异常结束:"+e.getLocalizedMessage());
				Log.println("IO异常:" + e.getLocalizedMessage());
			}finally {
				onBroken(null, "Normal end");
			}
		}
	}


}
