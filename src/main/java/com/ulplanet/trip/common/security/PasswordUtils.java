package com.ulplanet.trip.common.security;

import com.ulplanet.trip.common.utils.Encodes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;

public class PasswordUtils {

    private static Logger logger = LoggerFactory.getLogger(PasswordUtils.class);

	public static final String HASH_ALGORITHM = "SHA-1";
	public static final int HASH_INTERATIONS = 1024;
	public static final int SALT_SIZE = 8;

    private static final String CHARSET_NAME = "UTF-8";
	
	/**
	 * 生成安全的密码，生成随机的16位salt并经过1024次 sha-1 hash
	 */
	public static String entryptPassword(String plainPassword) {
        try {
            byte[] salt = Digests.generateSalt(SALT_SIZE);
            byte[] hashPassword = Digests.sha1(plainPassword.getBytes(CHARSET_NAME), salt, HASH_INTERATIONS);
            return Encodes.encodeHex(salt) + Encodes.encodeHex(hashPassword);
        } catch (UnsupportedEncodingException e) {
            logger.error("entryptPassword Exception", e);
            return null;
        }
	}
	
	/**
	 * 验证密码
	 * @param plainPassword 明文密码
	 * @param password 密文密码
	 * @return 验证成功返回true
	 */
	public static boolean validatePassword(String plainPassword, String password) {
        try {
            byte[] salt = Encodes.decodeHex(password.substring(0,16));
            byte[] hashPassword = Digests.sha1(plainPassword.getBytes(CHARSET_NAME), salt, HASH_INTERATIONS);
            return password.equals(Encodes.encodeHex(salt) + Encodes.encodeHex(hashPassword));
        } catch (UnsupportedEncodingException e) {
            logger.error("validatePassword Exception", e);
            return false;
        }
	}
	
	public static void main(String[] args) {
		System.out.println(entryptPassword("admin"));
		System.out.println(validatePassword("admin", "14957cc92a93b03d798628f6aba63f1f6831c7b6804e21b41e231916"));
	}
	
}
