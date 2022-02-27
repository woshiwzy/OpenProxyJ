package com.proxy.common;


/**
 * 连接断开回调方法
 */
public  interface OnBrokenCallBackInconnctor {
	void onBroken(Exception e, String tag,Thread connector);
}