package com.isoftframework.common.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

import com.isoftframework.common.io.file.ExtensionFileFilter;
import com.isoftframework.common.io.file.FileOperateUtil;

public class EncodingConverter {

	public final static String UTF8_BOM="efbbbf";
	public static void convert(File oldFile)throws Exception {
		convert(oldFile,null,null);
		/*if(oldFile.isFile()){
			RandomAccessFile input = new RandomAccessFile(oldFile,"r");
			if(read(input,3).equals(UTF8_BOM)){
				System.out.println("UTF8_BOM:"+oldFile.getPath());
				input.seek(3);
			}else{
				input.seek(0);
			}
			File tmp=File.createTempFile("tmp","tmp");
			FileOutputStream output = new FileOutputStream(tmp);
			 
			byte[] b = new byte[1024 * 8];
			int len=-1;
			while ((len = input.read(b)) != -1) {
				output.write(b, 0, len);
			}
			output.flush();
			output.close();
			input.close();
			
			output = new FileOutputStream( oldFile);
			FileInputStream fin=new FileInputStream(tmp);
			
			b = new byte[1024 * 8];
			len=-1;
			while ((len = fin.read(b)) != -1) {
				output.write(b, 0, len);
			}
			output.flush();
			output.close();
			fin.close();
			
		}else{
			File[] childFiles=oldFile.listFiles();
			if(childFiles!=null && childFiles.length>0){
				for(File child:childFiles){
					convert(child);
				}
			}
		}*/
	}
	public static void convert(File oldFile,String suffix)throws Exception {
		convert(oldFile,null,suffix);
	}
	public static void convert(File oldFile, String newPath,String suffix)throws Exception {
		
		File newFile=null;
		if(newPath!=null)
			newFile=new File(newPath,oldFile.getName() );
		else
			newFile=File.createTempFile("tmp","tmp");
		
		if(oldFile.isFile()){
			System.out.println("convert:"+oldFile.getPath());
			RandomAccessFile input = new RandomAccessFile(oldFile,"r");
			if(read(input,3).equals(UTF8_BOM)){
				System.out.println("UTF8_BOM:"+oldFile.getPath());
				input.seek(3);
			}else{
				input.seek(0);
			}
			FileOutputStream output = new FileOutputStream( newFile);
			byte[] b = new byte[1024 * 8];
			int len=-1;
			while ((len = input.read(b)) != -1) {
				output.write(b, 0, len);
			}
			output.flush();
			output.close();
			input.close();
			if(newPath==null){
				copyFile(newFile, oldFile);
			}
		}else{
			if(newPath!=null && !newFile.exists()){
				newFile.mkdirs();
			}
			
			File[] childFiles=oldFile.listFiles(new ExtensionFileFilter(suffix));
			if(childFiles!=null && childFiles.length>0){
				for(File child:childFiles){
					if(newPath==null){
						convert(child,null,suffix);
					}else{
						convert(child,newFile.getPath(),suffix);
					}
					
				}
			}
		}
	}
	public static void copyFile(File source,File dist) throws IOException{
		FileInputStream input=new FileInputStream(source);
		FileOutputStream output = new FileOutputStream( dist);
		byte[] b = new byte[1024 * 8];
		int len=-1;
		while ((len = input.read(b)) != -1) {
			output.write(b, 0, len);
		}
		output.flush();
		output.close();
		input.close();
	}
	public static String read(RandomAccessFile rac,int len) throws IOException{
		int i=0;
		StringBuffer sb=new StringBuffer();
		while(i<len){
			i++;
			sb.append(Integer.toHexString(rac.read()));
		}
		return sb.toString();
	}
	
	public static void main(String[] args){
		
		try {
			/*RandomAccessFile rac=new RandomAccessFile("E:\\test\\Test.java","r");
			String s=EncodingConverter.read(rac, 3);
			System.out.println(s);*/
			//D:\\workspace\\eclipse_jee\\isoft
			//EncodingConverter.convert(new File("D:\\workspace\\eclipse_jee\\isoft"),"E:\\test");
			//System.out.println("oldFile.length()="+new File("E:\\test\\Test.java").length());
			EncodingConverter.convert(new File("E:\\test\\isoft"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
