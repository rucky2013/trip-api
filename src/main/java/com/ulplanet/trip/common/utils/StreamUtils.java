package com.ulplanet.trip.common.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

/**
 * 
 * 流处理工具类
 * @author zhangxd
 * @date 2015年7月12日
 *
 */
public class StreamUtils {

	final static int BUFFER_SIZE = 4096;
	/**
	 * 将InputStream转换成String
	 * 
	 * @param in
	 *            InputStream
	 * @return String
	 * @throws Exception
	 * 
	 */
	public static String inputStream2String(InputStream in) {

		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] data = new byte[BUFFER_SIZE];
		String string = null;
		int count;
		try {
			while ((count = in.read(data, 0, BUFFER_SIZE)) != -1)
				outStream.write(data, 0, count);
		} catch (IOException e) {
			e.printStackTrace();
		}

        try {
			string = new String(outStream.toByteArray(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return string;
	}

	/**
	 * 将InputStream转换成某种字符编码的String
	 * 
	 * @param in
	 * @param encoding
	 * @return
	 * @throws Exception
	 */
	public static String inputStream2String(InputStream in, String encoding) {
		String string = null;
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] data = new byte[BUFFER_SIZE];
		int count;
		try {
			while ((count = in.read(data, 0, BUFFER_SIZE)) != -1)
				outStream.write(data, 0, count);
		} catch (IOException e) {
			e.printStackTrace();
		}

        try {
			string = new String(outStream.toByteArray(), encoding);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return string;
	}

	/**
	 * 将String转换成InputStream
	 * 
	 * @param in
	 * @return
	 * @throws Exception
	 */
	public static InputStream string2InputStream(String in) throws Exception {

        return new ByteArrayInputStream(in.getBytes("UTF-8"));
	}

	/**
	 * 将String转换成InputStream
	 * 
	 * @param in
	 * @return
	 * @throws Exception
	 */
	public static byte[] string2Byte(String in) {
		byte[] bytes = null;
		try {
			bytes = inputStream2Byte(string2InputStream(in));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bytes;
	}

	/**
	 * 将InputStream转换成byte数组
	 * 
	 * @param in
	 *            InputStream
	 * @return byte[]
	 * @throws IOException
	 */
	public static byte[] inputStream2Byte(InputStream in) throws IOException {

		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] data = new byte[BUFFER_SIZE];
		int count;
		while ((count = in.read(data, 0, BUFFER_SIZE)) != -1)
			outStream.write(data, 0, count);

        return outStream.toByteArray();
	}

	/**
	 * 将byte数组转换成InputStream
	 * 
	 * @param in
	 * @return
	 * @throws Exception
	 */
	public static InputStream byte2InputStream(byte[] in) {

        return new ByteArrayInputStream(in);
	}

	/**
	 * 将byte数组转换成String
	 * 
	 * @param in
	 * @return
	 * @throws Exception
	 */
	public static String byte2String(byte[] in) {

		InputStream is = null;
		try {
			is = byte2InputStream(in);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return is == null ? null : inputStream2String(is, "UTF-8");
	}
	/**
	 * 将byte数组转换成String
	 * 
	 * @param in
	 * @return
	 * @throws Exception
	 */
	public static String getString(String in) {

		String is = null;
		try {
			is = byte2String(string2Byte(in));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return is;
	}

	// InputStream 转换成byte[]
	public byte[] getBytes(InputStream is) throws IOException {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] b = new byte[BUFFER_SIZE];
		int len;

		while ((len = is.read(b, 0, BUFFER_SIZE)) != -1) {
			baos.write(b, 0, len);
		}

		baos.flush();

        return baos.toByteArray();
	}
	/**
	 * 根据文件路径创建文件输入流处理
	 * 以字节为单位（非 unicode ）
	 * @param filepath
	 * @return
	 */
	public static FileInputStream getFileInputStream(String filepath) {
		FileInputStream fileInputStream = null;
		try {
			fileInputStream = new FileInputStream(filepath);
		} catch (FileNotFoundException e) {
			System.out.print("错误信息:文件不存在");
			e.printStackTrace();
		}
		return fileInputStream;
	}
	/**
	 * 根据文件对象创建文件输入流处理
	 * 以字节为单位（非 unicode ）
	 * @param file
	 * @return
	 */
	public static FileInputStream getFileInputStream(File file) {
		FileInputStream fileInputStream = null;
		try {
			fileInputStream = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			System.out.print("错误信息:文件不存在");
			e.printStackTrace();
		}
		return fileInputStream;
	}
	/**
	  * 根据文件对象创建文件输出流处理
	 * 以字节为单位（非 unicode ）
	 * @param file
	 * @param append true:文件以追加方式打开,false:则覆盖原文件的内容
	 * @return
	 */
	public static FileOutputStream getFileOutputStream(File file,boolean append) {
		FileOutputStream fileOutputStream = null;
		try {
			fileOutputStream = new FileOutputStream(file,append);
		} catch (FileNotFoundException e) {
			System.out.print("错误信息:文件不存在");
			e.printStackTrace();
		}
		return fileOutputStream;
	}
	/**
	 * 根据文件路径创建文件输出流处理
	 * 以字节为单位（非 unicode ）
	 * @param filepath
	 * @param append true:文件以追加方式打开,false:则覆盖原文件的内容
	 * @return
	 */
	public static FileOutputStream getFileOutputStream(String filepath,boolean append) {
		FileOutputStream fileOutputStream = null;
		try {
			fileOutputStream = new FileOutputStream(filepath,append);
		} catch (FileNotFoundException e) {
			System.out.print("错误信息:文件不存在");
			e.printStackTrace();
		}
		return fileOutputStream;
	}

	public static File getFile(String filepath) {
		return new File(filepath);
	}
	public static ByteArrayOutputStream getByteArrayOutputStream() {
		return new ByteArrayOutputStream();
	}

}