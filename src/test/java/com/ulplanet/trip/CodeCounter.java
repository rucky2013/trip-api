package com.ulplanet.trip;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 
 * 代码行数统计
 * Create by zhangxd on 07/23/2015
 *
 */
public class CodeCounter {

	/**
	 * 代码行数统计
	 */
	public static void main(String[] args) {
		String file = CodeCounter.class.getResource("/").getFile();
		String path = file.replace("target/test-classes", "src");

		ArrayList<File> al = getFile(new File(path));
		for (File f : al) {
			if (f.getName().matches(".*\\.java$")){ // 匹配java格式的文件
				count(f);
				System.out.println(f);
			}
		}
		System.out.println("统计文件：" + files);
		System.out.println("代码行数：" + codeLines);
		System.out.println("注释行数：" + commentLines);
		System.out.println("空白行数：" + blankLines);
	}
	
	private static long files = 0;
    private static long codeLines = 0;
    private static long commentLines = 0;
    private static long blankLines = 0;
    private static final ArrayList<File> fileArray = new ArrayList<>();
	
	/**
	 * 获得目录下的文件和子目录下的文件
	 */
	private static ArrayList<File> getFile(File f) {
		File[] ff = f.listFiles();
		if (ff == null) {
			return fileArray;
		}
		for (File child : ff) {
			if (child.isDirectory()) {
				getFile(child);
			} else
				fileArray.add(child);
		}
		return fileArray;

	}

	/**
	 * 统计方法
	 */
	private static void count(File f) {
		BufferedReader br = null;
		boolean flag = false;
		try {
			br = new BufferedReader(new FileReader(f));
			String line;
			while ((line = br.readLine()) != null) {
				line = line.trim(); // 除去注释前的空格
				if (line.matches("^[ ]*$")) { // 匹配空行
					blankLines++;
				} else if (line.startsWith("//")) {
					commentLines++;
				} else if (line.startsWith("/*") && !line.endsWith("*/")) {
					commentLines++;
					flag = true;
				} else if (line.startsWith("/*") && line.endsWith("*/")) {
					commentLines++;
				} else if (flag) {
					commentLines++;
					if (line.endsWith("*/")) {
						flag = false;
					}
				} else {
					codeLines++;
				}
			}
			files++;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}