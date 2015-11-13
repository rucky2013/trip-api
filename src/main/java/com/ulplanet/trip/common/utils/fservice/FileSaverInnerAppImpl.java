package com.ulplanet.trip.common.utils.fservice;

import java.io.File;

import com.ulplanet.trip.common.utils.FileIOHelper;

/**
 * 
 * 默认实现方式,文件保存在WebApp下.
 * @author zhangxd
 * @date 2015年7月19日
 *
 */
public class FileSaverInnerAppImpl implements FileSaver {

	private File	workfolder;
	private String	workfolderName;

	public FileSaverInnerAppImpl() {
		setWorkfolderName(DEFAULT_FOLDERNAME);
	}
	
	public FileSaverInnerAppImpl(String workfolderName) {
		setWorkfolderName(workfolderName);
	}

	public void deleteFile(String realName) {
		FileIOHelper.delete(new File(workfolder, realName));
	}

	public String getWorkfolderName() {
		return workfolderName;
	}

	public void moveFile(String oldName, String newName) {
		File oldFile = new File(workfolder, oldName);
		File newFile = new File(workfolder, newName);
		FileIOHelper.copy(oldFile, newFile);
		FileIOHelper.delete(oldFile);
	}

	public void saveFile(File file, String realName) {
		FileIOHelper.copy(file, new File(workfolder, realName));
	}

	/**
	 * 设置文件存放目录的路径名(只能是相对当前AppPath的路径,默认为files)
	 * 
	 * @param workfolderName
	 */
	public void setWorkfolderName(String workfolderName) {
		if (FserviceUtil.isAbsolutePath(workfolderName)) {
			throw new RuntimeException("Not a relative path:" + workfolderName);
		}
		this.workfolderName = workfolderName;
		workfolder = FserviceUtil.getFile(workfolderName);
	}

	public void updateFile(File file, String realName) {
		saveFile(file, realName);
	}

}
