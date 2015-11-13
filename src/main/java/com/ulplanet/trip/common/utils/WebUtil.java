package com.ulplanet.trip.common.utils;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Objects;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import com.ulplanet.trip.base.ServletContextHolder;


/**
 * 
 * Web层面的工具类.
 * @author zhangxd
 * @date 2015年7月21日
 *
 */
public class WebUtil {
	
	/**
	 * 将request请求中的参数及值转成一个Map格式.
	 * @param request
	 * @return
	 */
	public static Dto getRequestMap(HttpServletRequest request) {
		Dto dto = new BaseDto();
		Enumeration<?> enumeration = request.getParameterNames();
        while (enumeration.hasMoreElements()) {
            String name = Objects.toString(enumeration.nextElement());
            String[] values = request.getParameterValues(name);
            
            Object val = null;
            if (values.length == 1) {
            	val = values[0];
            } else {
            	val = Arrays.asList(values);
            }
            dto.put(name, val);
        }
		return dto;
	}
	
	
	/**
	 * URI编码,参考JS中的同名函数.
	 * 
	 * @param s
	 * @return
	 */
	public static String encodeURI(String s) {
		return Another.encodeURI(s);
	}
	
	/**
	 * 解码中文字符 对应前台Common.encodeURL
	 * @param url
	 * @return
	 */
	public static String decodeURL(String url) {
		if (StringHelper.isEmpty(url)) {
			return url;
		}
		
		try {
			return java.net.URLDecoder.decode(url , "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 获得当前环境的basePath
	 * @return
	 */
	public static String getBasePath() {
		ServletContext sc = ServletContextHolder.getServletContext();
		return sc.getContextPath() + "/";
	}
	
	/**
	 * 获取访问路径
	 * @param request
	 * @return
	 */
	public static String getBaseURL(HttpServletRequest request) {
	    String path = request.getContextPath();
	    String basePath = request.getScheme() + "://"
	            + request.getServerName() + ":" + request.getServerPort() + path + "/";
	    return basePath;
	}
	
}

/**
 * 
 */
class Another {

	/**
	 * 判断一个字符在某个环境下是否需要编码处理.
	 * 
	 */
	private interface EncodeJudge {

		/**
		 * 字符是否需要编码.
		 * 
		 * @param c
		 * @return
		 */
		boolean shouldEncode(char c);
	}

	/**
	 * 对于字符是否需要编码处理的简单实现.俩个条件之一:( > '~')或(指定字符之一)则需要编码.
	 * 
	 */
	private static final class EncodeJudgeSimpleImpl implements EncodeJudge {
		private static final char TOP_CHAR = '~';
		private char[] inners;

		/**
		 * @param inners
		 */
		private EncodeJudgeSimpleImpl(char[] inners) {
			this.inners = inners;
		}

		public boolean shouldEncode(char c) {
			if (c > TOP_CHAR) {
				return true;
			}

			if (isDefined(c)) {
				return true;
			}

			return false;
		}

		private boolean isDefined(char c) {
			for (char inner : inners) {
				if (c == inner) {
					return true;
				}
			}
			return false;
		}
	}

	private static EncodeJudge uriJudge = new EncodeJudgeSimpleImpl("\t\n\r \"%<>[\\]^`{|}".toCharArray());

	private static EncodeJudge uriCoponentJudge = 
	        new EncodeJudgeSimpleImpl("\t\n\r \"#$%&+,/:;<=>?@[\\]^`{|}".toCharArray());

	private static char[] hexs = "0123456789ABCDEF".toCharArray();
	
	public static String encodeURI(String uri) {
		return encodeWithJudge(uri, uriJudge);
	}

	public static String encodeURIComponent(String uri) {
		return encodeWithJudge(uri, uriCoponentJudge);
	}

	private static void addByte(StringBuilder sb, byte b) {
	    
		sb.append('%');
		sb.append(hexs[(b >> 4) & 0xf]);
		sb.append(hexs[b & 0xf]);
	}

	/**
	 * 
	 * @param uri
	 * @param judge
	 * @return
	 */
	private static String encodeWithJudge(String uri, EncodeJudge judge) {
		StringBuilder sb = new StringBuilder(uri.length() * 6);
		boolean changed = false;
		for (char c : uri.toCharArray()) {
			if (!judge.shouldEncode(c)) {
				sb.append(c);
				continue;
			}

			changed = true;
			if (c < 128) { // 2 ^ 7
				addByte(sb, (byte) c);
				
			} else if (c < 2048) { // 2 ^ 11
				addByte(sb, (byte) (((c >> 6) & 0x1f) | 0xc0));
				addByte(sb, (byte) ((c & 0x3f) | 0x80));
				
			} else if (c < 65536) { // 2 ^ 16
				addByte(sb, (byte) (((c >> 12) & 0xf) | 0xe0));
				addByte(sb, (byte) (((c >> 6) & 0x3f) | 0x80));
				addByte(sb, (byte) ((c & 0x3f) | 0x80));
				
			} else if (c < 2097152) { // 2 ^ 21
				addByte(sb, (byte) (((c >> 18) & 0x7) | 0xf0));
				addByte(sb, (byte) (((c >> 12) & 0x3f) | 0x80));
				addByte(sb, (byte) (((c >> 6) & 0x3f) | 0x80));
				addByte(sb, (byte) ((c & 0x3f) | 0x80));
				
			} else if (c < 67108864) { // 2 ^ 26
				addByte(sb, (byte) (((c >> 24) & 0x3) | 0xf8));
				addByte(sb, (byte) (((c >> 18) & 0x3f) | 0x80));
				addByte(sb, (byte) (((c >> 12) & 0x3f) | 0x80));
				addByte(sb, (byte) (((c >> 6) & 0x3f) | 0x80));
				addByte(sb, (byte) ((c & 0x3f) | 0x80));
			} else { // 2 ^ 31
				addByte(sb, (byte) (((c >> 30) & 0x1) | 0xfc));
				addByte(sb, (byte) (((c >> 24) & 0x3f) | 0x80));
				addByte(sb, (byte) (((c >> 18) & 0x3f) | 0x80));
				addByte(sb, (byte) (((c >> 12) & 0x3f) | 0x80));
				addByte(sb, (byte) (((c >> 6) & 0x3f) | 0x80));
				addByte(sb, (byte) ((c & 0x3f) | 0x80));
			}
		}

		return changed ? sb.toString() : uri;
	}

}