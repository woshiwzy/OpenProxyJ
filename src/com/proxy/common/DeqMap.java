package com.proxy.common;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map.Entry;

/**
 * 双向HashMap
 * 既可以用用key获取value，也可以把value当做key，获取原始的Key
 * 仅限于没有重复的key-value键值对
 * @param <K>
 * @param <V>
 */
public class DeqMap<K, V> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HashMap<K, V> map1;
	private HashMap<V, K> map2;

	public DeqMap() {
		map1 = new HashMap<>();
		map2 = new HashMap<>();
	}

	public void put(K k1, V v1, V v2, K k2) {
		map1.put(k1, v1);
		map2.put(v2, k2);
	}
	
	
	public void put(K k, V v) {
		map1.put(k, v);
		map2.put(v, k);
	}

	public V getVByK(K key) {
		return map1.get(key);
	}

	public K getKByV(V v) {
		return map2.get(v);
	}


	@Override
	public String toString() {

		StringBuilder sbf = new StringBuilder();

//		System.out.println(this.map1.size());
		for (Entry<K, V> entry1 : this.map1.entrySet()) {
			sbf.append(entry1.getKey() + "=" + entry1.getValue() + ",");
		}

		sbf.append(Constant.ENC_DEC_TAG);

//		System.out.println(this.map2.size());
		for (Entry<V, K> entry2 : this.map2.entrySet()) {
			sbf.append(entry2.getKey() + "=" + entry2.getValue() + ",");
		}

		return sbf.toString();
	}

}
