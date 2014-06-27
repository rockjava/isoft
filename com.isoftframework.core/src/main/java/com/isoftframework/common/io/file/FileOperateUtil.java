package com.isoftframework.common.io.file;

import static java.lang.System.out;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Arrays;

import org.apache.log4j.Logger;

public class FileOperateUtil {

	public static final int BUFSIZE = 1024 * 8;
	static Logger log=Logger.getLogger(FileOperateUtil.class);

	public FileOperateUtil() {
	}

	 
	/**
	 * 新建文件
	 * 自动创建缺失的文件夹
	 * @param filePathAndName
	 *            String 文件路径及名称 如c:/fqf.txt
	 * @param fileContent
	 *            String 文件内容
	 * @return boolean
	 */
	public static File newFile(String filePathAndName) {
		try {
			 
			filePathAndName=filePathAndName.replace("/", File.separator);
			
			File file=new File(filePathAndName);
			if(file.exists()){
				return file;
			}
			int nameIdx= filePathAndName.lastIndexOf(File.separator);
			if(nameIdx!=-1){
				String dirPath=filePathAndName.substring(0,nameIdx);
				File dir=new File(dirPath);
				if(!dir.exists()){
					dir.mkdirs();
				}
			}
			
			return new File(filePathAndName);

		} catch (Exception e) {
			System.out.println("新建文件操作出错 ");
			e.printStackTrace();
			return null;

		}

	}

	/**
	 * 复制文件
	 * @param oldPath
	 * @param newPath
	 * @throws Exception
	 */
	public static void copyFile(String oldPath, String newPath)throws Exception {
		copyFile(new File(oldPath),newPath);
	}
	/**
	 * 复制文件
	 * @param oldFile
	 * @param newPath
	 * @throws Exception
	 */
	public static void copyFile(File oldFile, String newPath)throws Exception {
		File newFile=new File(newPath,oldFile.getName() );
		if(oldFile.isFile()){
			FileInputStream input = new FileInputStream(oldFile);
			FileOutputStream output = new FileOutputStream( newFile);
			byte[] b = new byte[1024 * 8];
			int len=-1;
			while ((len = input.read(b)) != -1) {
				output.write(b, 0, len);
			}
			output.flush();
			output.close();
			input.close();
		}else{
			newFile.mkdirs();
			File[] childFiles=oldFile.listFiles();
			if(childFiles!=null && childFiles.length>0){
				for(File child:childFiles){
					copyFile(child,newFile.getPath());
				}
			}
		}
	}
	

	/**
	 * 移动文件
	 * 
	 * @param oldPath
	 *            String 如：c:/fqf.txt
	 * @param newPath
	 *            String 如：d:/fqf.txt
	 * @throws Exception 
	 */
	public static void moveFile(String oldPath, String newPath) throws Exception {
		copyFile(oldPath, newPath);
		deleteFile(oldPath);

	}


	public static boolean deleteFile(String filePath) throws Exception {
		return deleteFile(new File(filePath));
	}

	/**
	 * 删除文件 、目录（文件夹）以及目录下的文件
	 * @param file 被删除的文件
	 * @return 删除成功返回true,否则返回false
	 * @throws Exception
	 */
	public static boolean deleteFile(File file) throws Exception {
		
		if (!file.exists() ) {
			System.out.println("删除目录失败" + file + "目录不存在！");
			return false;
		}
		if(!file.isDirectory()){
			//System.out.println("delete "+file.getPath());
			return file.delete();
		}else{
			// 删除文件夹下的所有文件(包括子目录)
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				if (!deleteFile(files[i])) {
					//如果删除出现异常，停止继续删除
					System.out.println("删除失败!");
					return false;
				}
			}

			// 删除当前目录
			//System.out.println("delete "+file.getPath());
			return file.delete();
		}
	}
	
	/**
	 * 合并文本文件到目标文件
	 * @param outFile
	 * @param files
	 * @return
	 */
	public static File mergeTxtFiles(File desFile, String[] srcFiles) {
		out.println("Merge " + Arrays.toString(srcFiles) + " into " + desFile);
		FileChannel outChannel = null;
		try {
			outChannel = new FileOutputStream(desFile).getChannel();
			for (String f : srcFiles) {
				FileChannel fc = new FileInputStream(f).getChannel();
				ByteBuffer bb = ByteBuffer.allocate(BUFSIZE);
				while (fc.read(bb) != -1) {
					bb.flip();
					outChannel.write(bb);
					bb.clear();
				}
				fc.close();
			}

		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			try {
				if (outChannel != null) {
					outChannel.close();
				}
			} catch (IOException ignore) {
			}

		}
		return desFile;
	}

	public static void main(String[] args) throws Exception {
		try{
			System.out.println(deleteFile("G:\\LOSTFILE\\DIR75"));
			//copyFile("E:\\1.4.1","E:\\output");
			//mergeTxtFiles(new File("E:\\output\\tm\\fp\\2.0.5\\test.js"),new String[]{"E:\\output\\tm\\fp\\2.0.5\\init.js","E:\\output\\tm\\fp\\2.0.5\\init.js"});
			// deleteDirectory("E:/erp/KISBZMNV9.1(20130228)");
			System.out.println("finished");
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		
		
	}
}
