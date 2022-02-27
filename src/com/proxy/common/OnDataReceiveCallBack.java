package com.proxy.common;


public interface OnDataReceiveCallBack {

	
	public void onReceiveData(byte[] buffer, int start, int offset);
	public void onBrokenInGrabber(Exception e,String tag);
}
