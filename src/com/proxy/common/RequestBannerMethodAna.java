package com.proxy.common;

import com.proxy.domain.ForWard;

/**
 * 请求分析工具
 */
public class RequestBannerMethodAna {

	public static final String HTTPS_CONNECT = "CONNECT";

	public static final String HTTP_GET = "GET";
	public static final String HTTP_POST = "POST";
	public static final String HTTP_PUT = "PUT";
	public static final String HTTP_HEAD = "HEAD";
	public static final String HTTP_DELETE = "DELETE";
	public static final String HTTP_OPTIONS = "OPTIONS";
	public static final String HTTP_TRACE = "TRACE";
	public static final String HTTP_PATCH = "PATCH";
	
	public static final String HTTP_HEADER_HOST = "Host";

	public static ForWard anaForward(byte header[], int start, int offset) {
		return anaForward(new String(header, start, offset));
	}

	public static ForWard anaForward(String header) {
//		System.out.println("需要分析的头数据：====================");
//		System.out.println("需要分析的头数据：" + header);
//		System.out.println("===================================");

		ForWard forward = new ForWard();
		if (null != header) {
			forward.setHeaderContent(header);
			String[] metas = header.split("\r\n");
//			System.out.println("内容长度：" + metas.length);
			// http
//				GET http://login.psvr.wps.cn/api/v1/connectors?protocol=udp&usertype=10 HTTP/1.1
//				Host: login.psvr.wps.cn
			// https
//			0==> CONNECT mtalk.google.com:5228 HTTP/1.1
//			1==> Host: mtalk.google.com:5228

			if (metas.length >= 2) {
				for (String headerMeta : metas) {
					if (null == headerMeta || headerMeta.length() == 0) {
						continue;
					}
					if (headerMeta.startsWith(HTTPS_CONNECT)) {
						forward.setHttps(true);
						forward.setPort(443);
						continue;
					}

					if (headerMeta.toLowerCase().startsWith(HTTP_HEADER_HOST.toLowerCase())) {
						String[] hostMeta = headerMeta.split(":");
						if (null != hostMeta && hostMeta.length >= 2) {
							forward.setHost(hostMeta[1]);

							if (hostMeta.length == 3) {
								forward.setPort(Integer.parseInt(hostMeta[2]));
							} else if (hostMeta.length == 2 && !forward.isHttps()) {
								forward.setPort(80);
							}
						}
						continue;
					}

					if (forward.isParseSuccess()) {
						break;
					}

				}

			}
		}

		return forward;
	}

}
