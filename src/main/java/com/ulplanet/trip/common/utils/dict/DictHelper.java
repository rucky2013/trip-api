package com.ulplanet.trip.common.utils.dict;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * 
 * 数据字典辅助类.
 * @author zhangxd
 * @date 2015年7月26日
 *
 */
public class DictHelper {

	private static final Logger log = LogManager.getLogger(DictHelper.class);

	/**
	 * datas = Code - > data data = KeyValueBean, ....
	 */
	private static Map<String, List<KeyValueBean>> datas = new HashMap<String, List<KeyValueBean>>();

	private static volatile boolean inited = false;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		for (Entry<String, List<KeyValueBean>> data : datas.entrySet()) {
			System.out.println(data.getKey());
			for (KeyValueBean bean : data.getValue()) {
				System.out.println(bean.hashCode() + ":" + bean);
			}
		}
		KeyValueBean kv1 = new KeyValueBean(1, 2);
		KeyValueBean kv2 = new KeyValueBean(1, 2);
		System.out.println(kv1.hashCode() + "|" + kv2.hashCode());

	}

	private static void init() {
		if (inited) {
			return;
		}

		synchronized (DictHelper.class) {
			if (inited) {
				return;
			}
			
			try {
				_doinit();
				inited = true;
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		}
	}

	private static void _doinit() {
		DictReader provider = getDictReader();
		Map<String, List<KeyValueBean>> ds = provider.getDict();

		for (Entry<String, List<KeyValueBean>> ent : ds.entrySet()) {
			ent.setValue(Collections.unmodifiableList(ent.getValue()));
		}
		datas = ds;
	}
	
	private static DictReader getDictReader() {
		return new DictXmlReader();
	}

	static {
		init();
	}

	/**
	 * 检测dict是否已正常初始化,有问题,直接抛出运行时异常.
	 * 不采取外部代码checkInited(),以及外部处理异常的这些策略,因为这是"太少"发生的异常,没有必要做.
	 * 
	 */
	private static void checkInited() {
		if (!inited) {
			throw new IllegalStateException("DictHelper not inited.");
		}
	}

	/**
	 * 获得某个条目的数据字典数据,注意:这是一个不可修改的list,如果在使用中,需要对list中手动插入数据,请使用getsCopy.
	 * 
	 * @param code
	 * @return
	 * @see #getsCopy(String)
	 */
	public static List<KeyValueBean> gets(String code) {
		checkInited();

		List<KeyValueBean> data = datas.get(code);
		if (data == null) {
			log.error("error:Data is null!, code:" + code);
		}

		return data;
	}
	
	public static String[] allCodes() {
		return datas.keySet().toArray(new String[0]);
	}

	/**
	 * 获得某个条目的数据字典数据,拷贝形式.
	 * 
	 * @param code
	 * @return
	 */
	public static List<KeyValueBean> getsCopy(String code) {
		List<KeyValueBean> list = gets(code);
		List<KeyValueBean> copy = new ArrayList<KeyValueBean>();

		if (list != null) {
			copy.addAll(list);
		}

		return copy; 
	}

	/**
	 * 拷贝某个条目的数据字典列表,并且在列表头部插入指定的KVBean
	 * 
	 * @param code
	 * @param topKv
	 * @return
	 */
	public static List<KeyValueBean> getsCopy(String code, KeyValueBean topKv) {
		List<KeyValueBean> list = gets(code);
		List<KeyValueBean> copy = new ArrayList<KeyValueBean>();

		if (topKv != null) {
			copy.add(topKv);
		}
		if (list != null) {
			copy.addAll(list);
		}
		
		return copy;
	}

	/**
	 * 针对某个条件下,查询一个value对应的text信息.
	 * 
	 * @param code
	 * @param value
	 * @return
	 */
	public static String getText(String code, String value) {
		checkInited();

		List<KeyValueBean> data = gets(code);
		if (data == null) {
			return null;
		}

		for (KeyValueBean bean : data) {
			if (value.equals(bean.getKey())) {
				return Objects.toString(bean.getValue(), "");
			}
		}

		return null;
	}

}
