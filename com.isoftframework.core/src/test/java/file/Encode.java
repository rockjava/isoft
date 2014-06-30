package file;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

public class Encode {


	public static void main(String[] args){
		
		byte b=127;
		try {
			//FileOutputStream out =new FileOutputStream("E:\\test\\Test.java");
			File file=new File("E:\\test\\Test.java");
			FileInputStream in =new FileInputStream(file);
			byte[] buf=new byte[3];
			in.read(buf);
			int r;
			int i=0;
			while(i<buf.length){
				
				if(i>2)
					break;
				System.out.print(new Byte(buf[i]).intValue() );
				i++;
			}
			
			System.out.println("\n"+i);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
