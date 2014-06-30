package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

/** IO 工具类 */
public class IOCVUtils {
	/** 源文件编码 */
	public static String sourceEncoding = "GBK";
	/** 目标编码 */
	public static String targetEncoding = "UTF-8";

	/**
	 * 文件内容转编码
	 * 
	 * @param sourceFile
	 * @param targetFile
	 * @throws UnsupportedEncodingException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void changeEncoding(File sourceFile, File targetFile)
			throws UnsupportedEncodingException, FileNotFoundException,
			IOException {
		FileInputStream fin = null;
		FileOutputStream fout = null;
		FileChannel fcin = null;
		FileChannel fcout = null;
		if (sourceEncoding == null) {
			IOCVUtils.sourceEncoding = System.getProperty("file.encoding");
		}
		try {
			fin = new FileInputStream(sourceFile);
			fout = new FileOutputStream(targetFile);
			fcin = fin.getChannel();
			fcout = fout.getChannel();
			ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
			while (true) {
				buffer.clear();
				int r = fcin.read(buffer);
				if (r == -1) {
					break;
				}
				buffer.flip();
				fcout.write(ByteBuffer.wrap(Charset.forName(sourceEncoding)
						.decode(buffer).toString().getBytes(targetEncoding)));
			}
		} finally {
			if (fin != null) {
				fin.close();
				fin = null;
			}
			if (fcin != null) {
				fcin.close();
				fcin = null;
			}
			if (fout != null) {
				fout.close();
				fout = null;
			}
			if (fcout != null) {
				fcout.close();
				fcout = null;
			}
		}
	}

	/**
	 * 文件内容转编码
	 * 
	 * @param sourceFile
	 * @param targetFile
	 * @throws UnsupportedEncodingException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void changeEncoding(String sourceFile, String targetFile)
			throws UnsupportedEncodingException, FileNotFoundException,
			IOException {
		File fl1 = new File(sourceFile);
		File fo1 = new File(targetFile);
		changeEncoding(fl1, fo1);
	}

	/**
	 * 文件内容转编码
	 * 
	 * @param sourceFile
	 * @param targetFile
	 * @param sourceEncoding
	 *            源文件编码 默认源文件的系统存储编码 System.getProperty("file.encoding");
	 * @param targetEncoding
	 *            目标编码 默认utf-8
	 * @throws UnsupportedEncodingException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void changeEncoding(String sourceFile, String targetFile,
			String sourceEncoding, String targetEncoding)
			throws UnsupportedEncodingException, FileNotFoundException,
			IOException {
		IOCVUtils.sourceEncoding = sourceEncoding;
		IOCVUtils.targetEncoding = targetEncoding;
		changeEncoding(sourceFile, targetFile);
	}
}