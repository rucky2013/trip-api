package com.ulplanet.trip.common.utils;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;

public class TokenUtils {
 
	private static final String PRIVATEKEY = "kjf^%923(&@#-123=-h加^&!(i{\12nmcx";
	
	public static String getToken(String key) {
		return Hashing.md5().newHasher().
				putString(key, Charsets.UTF_8).
				putString(PRIVATEKEY, Charsets.UTF_8).hash().toString();
	}
	
	public static boolean validToken(String token, String key) {
		String confirm = getToken(key);
        return confirm.equals(token);
	}

}