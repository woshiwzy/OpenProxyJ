package com.proxy.test;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class TestConnectQtServer {

	public static void main(String[] args) throws IOException, InterruptedException {

		Socket socket = new Socket("localhost", 8712);
		InputStream in = socket.getInputStream();
		byte[] buffer = new byte[2048];
		int ret = in.read(buffer);
		
		String HTTPS_CONNECT_MSG = "HTTP/1.1 200 Connection Established\r\n\r\n";
		String rrrr=new String(buffer,0,ret);
		rrrr.getBytes();
		System.out.println("结果:"+" size :"+ret+" 是否相等:"+HTTPS_CONNECT_MSG.equals(rrrr));
	}
}
