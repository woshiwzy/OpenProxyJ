package com.proxy.common;

/**
 * 连接断开回调方法
 */
public  interface OnBrokenCallBack {
	void onBroken(Exception e, String tag,ReadDataThread readDataThread);
}