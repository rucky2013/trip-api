package com.ulplanet.trip.common.utils.dict;

import com.google.gson.Gson;
import com.ulplanet.trip.base.AppContext;
import com.ulplanet.trip.common.utils.FileIOHelper;
import com.ulplanet.trip.common.utils.GZipUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * 
 * 数据字典工具类.
 * @author zhangxd
 * @date 2015年7月26日
 *
 */
public class DictUtil {
	
	private static final String PATH = AppContext.getAppPath() + "/scripts/common/dictionary/";

    private static Logger logger = LoggerFactory.getLogger(DictUtil.class);
	
	/**
	 * 初始化数据字典，并按语言生成单独的
	 * dict-{lang}.js  未压缩版
	 * dict-{lang}.gzjs  压缩版
	 */
	public static void loadDict() {
		Gson gson = new Gson();
			
		StringBuilder dicts = new StringBuilder();
		
		dicts
			.append("Ext.define('DictModel',{")
				.append("extend:'Ext.data.Model',")
				.append("fields:['key','value']")
			.append("});");
		
		for (String code : DictHelper.allCodes()) {
			
			dicts
				.append("var ").append(code).append("Store=")
				.append("Ext.create('Ext.data.Store',{")
						.append("model:'DictModel',")
						.append("data:").append(gson.toJson(calcData(code)))
				.append("});");
		}
		
		String fname = "dict";
		
		File file = new File(PATH + fname + ".js");
		
		try {
			FileIOHelper.saveData(file, dicts.toString());
		} catch (IOException e) {
			System.out.println("Dict Create Failure. Detail:" + file.getAbsolutePath());
		}
		
		try {
			GZipUtil.compress(file, false);
			File gzFile = new File(PATH + fname + ".js" + GZipUtil.EXT);
			File gzjsFile = new File(PATH + fname + ".gzjs");
			if (!gzjsFile.delete()) {
                logger.debug("文件删除失败");
            }
			if (!gzFile.renameTo(gzjsFile)) {
                logger.debug("文件重命名失败");
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static List<Map<String, String>> calcData(String code) {
		List<Map<String, String>> ret = new ArrayList<>();
		
		for (KeyValueBean kv : DictHelper.getsCopy(code)) {
			Map<String, String> map = new HashMap<>();
			ret.add(map);
			
			String key = Objects.toString(kv.getKey());
			String val = Objects.toString(kv.getValue(), key);
			
			map.put("key", key);
			map.put("value", val);
		}
		
		return ret;
	}
}
