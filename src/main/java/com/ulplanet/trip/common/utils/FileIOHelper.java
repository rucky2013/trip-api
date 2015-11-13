package com.ulplanet.trip.common.utils;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.CharArrayWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

/**
 * 读写数据....
 * ① 对于读操作,无论如何,最终都尝试去close InputStream.
 * ② 对于写操作,saveXXXX,也将尝去close OutputStream.
 * ③ 另外,把数据当作String处理时候,是utf-8编码.
 * <br/>
 * 做这些约定的原因是,当前场景下实用性.
 * 
 * @author lh.jia
 * 
 */
public class FileIOHelper {

	public interface FileDataExecutor {
		void execute(String filedata);
	}

	public interface FileExecutor {
		void execute(File file);
	}

	public static final int BUF_SIZE = 1024 * 100;

	public static final String DEFAULT_ENCODING = "utf8";

	/**
	 * 清空一个目录.
	 * 
	 * @param folder 需要清除的目录.如果该参数实际上是一个file,不处理,返回true,
	 * @return
	 */
	public static boolean clearFolder(File folder) {
		return folder.isFile() ? true : _clearFolder(folder);
	}

	/**
	 * 关闭流.
	 * 
	 * @param os
	 */
	public static void close(Closeable closeable) {
		if (closeable != null) {
			try {
				closeable.close();
			} catch (Exception e) {
			    e.printStackTrace();
			}
		}
	}

	/**
	 * 拷贝一个文件或目录,成功返回true. 如果是目录,是深度拷贝,即对于目录中原有的子元素(文件),如果和新拷贝数据没冲突,不会被覆盖.
	 * 
	 * @param source
	 * @param target
	 * @param filter
	 * @return
	 */
	public static boolean copy(File source, File target) {
		if (source == null || !source.exists()) {
			throw new RuntimeException("Source file/folder is null or not exists.");
		}
		return source.isFile() ? _copyFile(source, target) : _copyFolder(source, target);
	}

	/**
	 * Stream copy, use default buf_size.
	 * 
	 * @param is
	 * @param os
	 * @throws IOException
	 */
	public static void copy(InputStream is, OutputStream os) throws IOException {
		copy(is, os, BUF_SIZE);
	}

	/**
	 * copy data from reader to writer.
	 * 
	 * @param reader
	 * @param writer
	 * @throws IOException
	 */
	public static void copy(Reader reader, Writer writer) throws IOException {
		char[] buf = new char[BUF_SIZE];
		int len;
		try {
			while ((len = reader.read(buf)) != -1) {
				writer.write(buf, 0, len);
			}
		} catch (IOException e) {
			throw e;
		} finally {
			close(reader);
		}

	}

	/**
	 * Stream copy.
	 * 
	 * @param is
	 * @param os
	 * @param bufsize
	 * @throws IOException
	 */
	public static void copy(InputStream is, OutputStream os, int bufsize) throws IOException {
		byte[] buf = new byte[bufsize];
		int c;
		try {
			while ((c = is.read(buf)) != -1) {
				os.write(buf, 0, c);
			}
		} catch (IOException e) {
			throw e;
		} finally {
			close(is);
		}
	}

	/**
	 * 删除一个file对象,无论是file还是folder(也无论是否为空),
	 * 
	 * @param folder
	 * @return
	 */
	public static boolean delete(File folder) {
		if (!folder.exists()) {
			return true;
		}

		return folder.isFile() ? _deleteFile(folder) : _deleteFolder(folder);
	}

	/**
	 * 处理一个目录下的全部.
	 * 
	 * @param folder
	 * @param executor
	 */
	public static void loopInFolder(File folder, FileExecutor executor) {
		for (File child : folder.listFiles()) {
			if (child.isDirectory()) {
				loopInFolder(child, executor);
			} else {
				executor.execute(child);
			}
		}
	}

	/**
	 * 将目标的文件或目录移动到新位置上.
	 * 
	 * @param source
	 * @param target
	 * @return
	 */
	public static boolean move(File source, File target) {
		return copy(source, target) && delete(source);
	}

	/**
	 * 
	 * 
	 * @param is
	 * @return
	 * @throws IOException
	 */
	public static byte[] readBytes(InputStream is) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		copy(is, baos);
		return baos.toByteArray();
	}

	public static String read(Reader reader) throws IOException {
		CharArrayWriter writer = new CharArrayWriter();
		copy(reader, writer);
		return writer.toString();
	}

	/**
	 * 从指定文件中读取数据字符串.
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static String readData(File file) throws IOException {
		return readData(new FileInputStream(file));
	}

	/**
	 * 读取数据流.
	 * 
	 * @param is
	 * @return
	 * @throws IOException
	 */
	public static String readData(InputStream is) throws IOException {
		return new String(readBytes(is), DEFAULT_ENCODING);
	}

	/**
	 * 从指定文件中读取数据字符串.
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static String readData(String file) throws IOException {
		return readData(new File(file));
	}

	/**
	 * 保存一个数据到指定文件中.
	 * 
	 * @param file
	 * @param data
	 * @return
	 * @throws
	 * @throws IOException
	 */
	public static void saveData(File file, String data) throws IOException {
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		saveData(new FileOutputStream(file), data);
	}
	
	/**
	 * 将数据保存到指定位置上.
	 * 
	 * @param file
	 * @param data
	 * @param append
	 * @throws IOException
	 */
	public static void saveData(String file, String data, Boolean append) throws IOException {
		saveData(new File(file), data, append);
	}
	
	/**
	 * 保存一个数据到指定文件中
	 * 
	 * @param file
	 * @param data
	 * @param append
	 * @throws IOException
	 */
	public static void saveData(File file, String data, Boolean append) throws IOException {
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		saveData(new FileOutputStream(file, append), data);
	}

	/**
	 * 保存bytes到一个输出流中并且关闭它 
	 * 
	 * @param os
	 * @param data
	 * @throws IOException
	 */
	public static void saveData(OutputStream os, byte[] data) throws IOException {
		try {
			os.write(data);
		} catch (IOException e) {
			throw e;
		} finally {
			close(os);
		}
	}

	/**
	 * 保存String到指定os中.
	 * 
	 * @param os
	 * @param data
	 * @throws IOException
	 */
	public static void saveData(OutputStream os, String data) throws IOException {
		BufferedOutputStream bos = null;
		try {
			byte[] bs = data.getBytes(DEFAULT_ENCODING);
			bos = new BufferedOutputStream(os, BUF_SIZE);
			bos.write(bs);
		} catch (IOException e) {
			throw e;
		} finally {
			close(bos);
		}
	}

	/**
	 * 将数据保存到指定位置上.
	 * 
	 * @param file
	 * @param data
	 * @throws IOException
	 */
	public static void saveData(String file, String data) throws IOException {
		saveData(new File(file), data);
	}

	private static boolean _clearFolder(File folder) {
		for (File child : folder.listFiles()) {
			boolean flag = child.isFile() ? _deleteFile(child) : _deleteFolder(child);
			if (!flag) {
				return false;
			}
		}

		return true;
	}

	/**
	 * 文件拷贝,成功返回true.
	 * 
	 * @param source
	 * @param target
	 * @return
	 */
	private static boolean _copyFile(File source, File target) {
		FileInputStream fis = null;
		FileOutputStream fos = null;
		try {
			fis = new FileInputStream(source);
			if (!target.getParentFile().exists()) {
				target.getParentFile().mkdirs();
			}
			fos = new FileOutputStream(target);

			byte[] buffer = new byte[BUF_SIZE];
			int size = 0;
			while ((size = fis.read(buffer)) != -1) {
				fos.write(buffer, 0, size);
			}

			return true;

		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			close(fis);
			close(fos);
		}

	}

	/**
	 * 目录拷贝,成功返回true 这个是个深度拷贝:只覆盖存在的文件.
	 * 
	 * @param source
	 * @param target
	 * @return
	 */
	private static boolean _copyFolder(File source, File target) {
		if (!target.exists()) {
			target.mkdirs();
		}

		for (File child : source.listFiles()) {
			File nchild = new File(target, child.getName());
			boolean flag = child.isFile() ? _copyFile(child, nchild) : _copyFolder(child, nchild);
			if (!flag) {
				return false;
			}
		}

		return true;
	}

	private static boolean _deleteFile(File file) {
		return file.delete();
	}

	private static boolean _deleteFolder(File folder) {
		for (File child : folder.listFiles()) {
			boolean flag = child.isFile() ? _deleteFile(child) : _deleteFolder(child);
			if (!flag) {
				return false;
			}
		}

		return folder.delete();
	}
}
