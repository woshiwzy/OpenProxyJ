package com.proxy.domain;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.proxy.common.Constant;

/**
 * 解析后的转发地址
 */
public class ForWard {

	private String headerContent;
	private boolean isHttps;
	private String host;
	private int port;

	public ForWard() {
	}

	public ForWard(String host, int port) {
		super();
		this.host = host;
		this.port = port;
	}

	public boolean isHttps() {
		return isHttps;
	}

	public void setHttps(boolean isHttps) {
		this.isHttps = isHttps;
	}

	public String getHost() {
		return host.trim();
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public boolean isParseSuccess() {
		if (null != host && -1 != port) {
			return true;
		}
		return false;
	}

	public String getHeaderContent() {
		return headerContent;
	}

	public void setHeaderContent(String headerContent) {
		this.headerContent = headerContent;
	}

	public byte[] getHeaderBanner() {

		ByteArrayOutputStream byo = new ByteArrayOutputStream();
		String headerBanner = host + ":" + port;
		byte[] targetBannerByte = headerBanner.getBytes();
		int len = targetBannerByte.length;
		try {
			byo.write(targetBannerByte);
			// 凑够50个字节
			for (int i = 0, isize = (Constant.HEADER_BAND - len); i < isize; i++) {
				byo.write(Constant.SPACE);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		byte[] header = byo.toByteArray();
		System.out.println("头部长度:" + header.length + " 头部内容：" + (new String(header)));

		return header;
	}

	
	
	@Override
	public String toString() {
		return "ForWard [isHttps=" + isHttps + ", host=" + host + ", port=" + port + "]";
	}

}
