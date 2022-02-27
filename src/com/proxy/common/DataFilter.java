package com.proxy.common;

import java.io.Serializable;

import com.proxy.utils.ShuffTool;

public class DataFilter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static boolean isDataProtect = true;// 会否进行数据保护

	private DeqMap<Byte, Byte> deqMap;

	public DataFilter() {
		deqMap = ShuffTool.createPassWordBook();
	}

	public DataFilter(DeqMap deqMap) {
		this.deqMap = deqMap;
	}

	public void encodeData(byte[] buffer, int start, int offset) {
		if (isDataProtect) {
			for (int i = start; i < offset; i++) {
				buffer[i] = deqMap.getVByK(buffer[i]);
			}
		}
	}

	public void decodeData(byte[] buffer, int start, int offset) {
		if (isDataProtect) {
			for (int i = start; i < offset; i++) {
				buffer[i] = deqMap.getKByV(buffer[i]);
			}
		}

	}


	/**
	 * 获取序列化内容，方便分发传输
	 * 
	 * @return
	 */
	public String getStringInChannel() {
		return Constant.PASSBOOK_TAG_START + this.deqMap.toString() + Constant.PASSBOOK_TAG_END;
	}

	@Override
	public String toString() {
		return deqMap.toString();
	}
}
