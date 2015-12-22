package com.ulplanet.trip.common.utils.dict;

import java.io.Serializable;


/**
 * 最简单的key -> value模型.
 *
 */
public class KeyValueBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	protected Object key;
	protected Object value;
	protected String valueid;

	/**
	 * 
	 * @param key
	 * @param value
	 */
	public KeyValueBean(Object key, Object value) {
		this.key = key;
		this.value = value;
	}

	/**
	 * 构造一个KVBean.
	 * 
	 * @param key 
	 * @param value
	 * @param valueid
	 */
	public KeyValueBean(Object key, Object value, String valueid) {
		super();
		this.key = key;
		this.value = value;
		this.valueid = valueid;
	}

	public Object getKey() {
		return key;
	}

	/**
	 * 获得显示信息值,这个值可能是计算的结果.
	 * 
	 * @return
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * 获得value值,这个是原始的数据.
	 * 
	 * @return
	 */
	public Object getValue2() {
		return value;
	}

	public String getValueid() {
		return valueid;
	}
	

	public void setKey(Object key) {
		this.key = key;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public void setValueid(String valueid) {
		this.valueid = valueid;
	}

	public String toString() {
		return "KeyValueBean" + ", key(" + key + ")" + ", value(" + value + ")" + ", valueid(" + valueid + ")";
	}

}
