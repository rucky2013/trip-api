
package com.ulplanet.trip.common.utils.dict;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ulplanet.trip.common.utils.StringHelper;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;


/**
 * 
 * 字典数据阅读器.
 * @author zhangxd
 * @date 2015年7月26日
 *
 */
public interface DictReader {

	/**
	 * 读取dict定义值,返回字典列表.
	 * 
	 * @return
	 */
	Map<String, List<KeyValueBean>> getDict();

}

/**
 * 使用xml配置形式的一个字典阅读器.
 * 
 * @author zy.fang
 *
 */
class DictXmlReader implements DictReader {

	private static final String	CFG_FILE	= "dict.xml";

	/** 特定rsid计算方式,auto,当使用该关键字,则系统按照固定算法,计算rsid */
	private static final String	RSPRE_AUTO	= "auto";

	/** 标签rsid */
	private static final String	TAG_RSPRE	= "rspre";

	/** 标签code,当前词组的代码,必须是unique的,每个code,需要在这里登记固定号码 */
	private static final String	TAG_CODE	= "code";

	/** 标签value */
	private static final String	TAG_VALUE	= "value";

	/** 标签text */
	private static final String	TAG_TEXT	= "text";

	/** 标签textid,即使用resource id来指定text信息 */
	private static final String	TAG_TEXTID	= "textid";

	public Map<String, List<KeyValueBean>> getDict() {
		Map<String, List<KeyValueBean>> nds = new HashMap<String, List<KeyValueBean>>();

		Document doc;
		try {
			doc = (Document) new SAXReader().read(DictHelper.class.getClassLoader().getResourceAsStream(CFG_FILE));
		} catch (DocumentException e) {
			throw new RuntimeException("Cannot read config file:" + CFG_FILE, e);
		}

		for (Object node : doc.getRootElement().elements()) {
			Element dnode = (Element) node;
			String code = dnode.attributeValue(TAG_CODE);
			if (StringHelper.isEmpty(code)) {
				throw new RuntimeException("code cannot be empty" + dnode.asXML());
			}

			String textbid = dnode.attributeValue(TAG_RSPRE);

			List<KeyValueBean> data = new ArrayList<KeyValueBean>();

			for (Object node2 : dnode.elements()) {
				Element enode = (Element) node2;
				String value = enode.attributeValue(TAG_VALUE);
				if (StringHelper.isEmpty(value)) {
					throw new RuntimeException("Entry value cannot be empty" + dnode.asXML());
				}

				String text = enode.attributeValue(TAG_TEXT);
				String textid = enode.attributeValue(TAG_TEXTID);
				textid = calTextid(code, textbid, value, textid);

				data.add(new KeyValueBean(value, text, textid));
			}

			nds.put(code,  data);
		}

		return nds;
	}
	
	/**
	 * 计算text的resourceid
	 * 
	 * @param code 词组代码 
	 * @param rspre 资源信息前缀 
	 * @param value 值 
	 * 
	 * @param textid
	 * @return
	 */
	private static String calTextid(String code, String rspre, String value, String textid) {
		// 如果指定了textid信息,则直接使用,
		if (!StringHelper.isEmpty(textid)) {
			return textid;
		} else if (StringHelper.isEmpty(rspre)) { // rspre和textid,起码有一个是有效的.
			return null;
			// throw new IllegalArgumentException("Parameter 'rspre' cannot be
			// empty when 'textid' is empty.");
		} else if (RSPRE_AUTO.equals(rspre)) { // 如果rspre是指定关键字auto,则使用系统算法计算pspre
			rspre = "dict/" + code + "/v_";
		}

		return rspre + value;
	}
	
}
