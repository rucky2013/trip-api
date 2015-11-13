package com.ulplanet.trip.util;

import org.apache.shiro.codec.Base64;

import javax.crypto.Cipher;
import java.security.Key;
import java.security.KeyFactory;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;

/**
 * Created by Administrator on 2015/10/19.
 */
public class RSACoder {

    public static final String PRIVATE_KEY = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBALADy+ABYBEzBQEpE3HGkFVu9eSXFbRPrMMoNJ4R30WaF4rdZLjHbIdBPKepUeXtJZnyCQGkQJGEa2Ro1nnalUeIz7bL+mnlqp87Dp8YwyYMJLg1pbLh5HzdCelo7haBEKspjAnR+sL9PfldDq8N2GHyRiwe75Lzz2q+5GFQfZ2vAgMBAAECgYBRRH92XswvRMIzxfZfWtG7UG8h6wwvY3Yd6UtaDGBtL3ddO1lM427diCZsNwZERdlNme3En0Tcqyo2gx07x8uSI2cWDIK/dpUNlhkzL6eWkIYBXYNVuLTgCD3gQb0AnHJen1ggpPz3DRIbqZ6ZDRIxydw1kX51maI4lclRFKHAKQJBAP8ZKhVN6hNvqeXhE/JgPbDpwvgWSjQZyAPPbwbVH7Bglmsj6LfIHENzH/ydcAvzklDr1nd4EWfBFhYllNtiS4sCQQCwoxH7x3q5rsDAL3ys9SrWHqRujD4mDMIWgdHKLIEjHdpNcEbTZ8pADk4oDWRe8CHSUjExXGPXm5/Lgm1Y5srtAkEA8r1lWCRt6tTEnKRSsL53tZKbHitHrNAjjcyjX6MJpnIicA0Zc1gN3Pj9pQnDBvxdbHcd+0zbcxk/BQ/519BvBQJBAK7h5Q45JALvSBt/aJr9aVnxoK9q3qVhgFA0W/clNP4Fsnt1LEpmulHNWAhDqhps+a94fQf4B0oS5JTjH0wjxMkCQQC6fhwBhwFHOht/BZ+L5Q3xnWf/hstJspI8aTqmNpf02l5VM6yn+ANeECPuk5drS2YraKk5nCiBIuh/fhWHGcnt";
    public static final String PUBLIC_Key = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCwA8vgAWARMwUBKRNxxpBVbvXklxW0T6zDKDSeEd9FmheK3WS4x2yHQTynqVHl7SWZ8gkBpECRhGtkaNZ52pVHiM+2y/pp5aqfOw6fGMMmDCS4NaWy4eR83QnpaO4WgRCrKYwJ0frC/T35XQ6vDdhh8kYsHu+S889qvuRhUH2drwIDAQAB";

    public static final String KEY_ALGORITHM = "RSA";
    /**
     * 加密<br>
     * 用公钥加密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPublicKey(byte[] data, String key)
            throws Exception {
        // 对公钥解密
        byte[] keyBytes = Base64.decode(key);

        // 取得公钥
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM, new org.bouncycastle.jce.provider.BouncyCastleProvider());
        Key publicKey = keyFactory.generatePublic(x509KeySpec);

        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm(),new org.bouncycastle.jce.provider.BouncyCastleProvider());
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        return Base64.encode(cipher.doFinal(data));
    }
    public static byte[] encryptByPublicKey(byte[] data)
            throws Exception {
        // 对公钥解密
        byte[] keyBytes = Base64.decode(PUBLIC_Key);

        // 取得公钥
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM,new org.bouncycastle.jce.provider.BouncyCastleProvider());
        Key publicKey = keyFactory.generatePublic(x509KeySpec);

        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm(),new org.bouncycastle.jce.provider.BouncyCastleProvider());
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        return Base64.encode(cipher.doFinal(data));
    }



    /**
     * 解密<br>
     * 用私钥解密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPrivateKey(byte[] data, String key)
            throws Exception {
        data = Base64.decode(data);
        // 对密钥解密
        byte[] keyBytes = Base64.decode(key);

        // 取得私钥
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM,new org.bouncycastle.jce.provider.BouncyCastleProvider());
        Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);

        // 对数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm(),new org.bouncycastle.jce.provider.BouncyCastleProvider());
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        return cipher.doFinal(data);
    }
    public static byte[] decryptByPrivateKey(byte[] data)
            throws Exception {
        data = Base64.decode(data);
        // 对密钥解密
        byte[] keyBytes = Base64.decode(PRIVATE_KEY);

        // 取得私钥
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM,new org.bouncycastle.jce.provider.BouncyCastleProvider());
        Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);

        // 对数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        return cipher.doFinal(data);
    }




    public static void main(String[] args) throws Exception {
        String inputStr = "timestamp="+new Date().getTime();
//        String inputStr = "timestamp=1445594503644";
        byte[] data = inputStr.getBytes();
//
        byte[] encodedData = encryptByPublicKey(data, PUBLIC_Key);
        System.out.println(new String(encodedData));
//        byte[] base64 = "rsT74QloY5BmCw7G13KgiHb1ZuOUEbE3ylIf4gG1WBOjnV+YlnBbG9fzN0fTug0QrURIHNaEmcOJyassoPDavrKjruztZP0CLLh2NvAMO+RPYJF4s3VYc1fd71KdAikO2oSM06jk4ZNrBRH05A4/pZs+vNQ7HuLriIzeKGDCwM=".getBytes();

        byte[] decodedData = decryptByPrivateKey(encodedData,
                PRIVATE_KEY);
//
        String outputStr = new String(decodedData);
        System.err.println("加密: " + new String(Base64.decode(encodedData)) + "\n\r" + "解密后: " + outputStr);

    }
}
