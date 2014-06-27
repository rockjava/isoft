package com.isoftframework.common.net;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URL;

import com.isoftframework.common.io.file.FileOperateUtil;


public class NetUtil {

	/**
	 * 获取tao bao js,css资源文件的方法
	 * @param host
	 * @param servletPath
	 * @param queryStr
	 * @throws Exception
	 */
	
	public void getTalBaoRes(String url) throws Exception{
		String sepra="??";
		String sepra2=".cn/";
		
		String host="";
		String servletPath="";
		String queryStr="";
		int idx=url.indexOf(sepra);
		if(idx!=-1){
			//http://a.tbcdn.cn/apps/trademanager/1.0/??common.css,item_list_v2.1.css
			//http://a.tbcdn.cn/apps/trademanager/1.0/
			String requestUrl=url.substring(0,idx);
			queryStr=url.substring(idx+2);
			
			int idx2=requestUrl.indexOf(sepra2);
			host=requestUrl.substring(0,idx2+sepra2.length());
			
			servletPath=requestUrl.substring(idx2+sepra2.length(),requestUrl.length());
			 
			if(!(host+servletPath+"??"+queryStr).equals(url)){
				throw new Exception("解析错误！\n"+"host="+host+"\n servletPath="+servletPath+"\nqueryStr="+queryStr);
			}else{
				this.getTaoBaoRes(host, servletPath, queryStr);
			}
		}
		else{
			//http://a.tbcdn.cn/tbsp/tbsp.css?t=20090602.css
			int idx2=url.indexOf(sepra2);
			host=url.substring(0,idx2+sepra2.length());
			queryStr=url.substring(idx2+sepra2.length(),url.length());
			 
			if(!(host+servletPath+queryStr).equals(url)){
				throw new Exception("解析错误！\n"+"host="+host+"\n servletPath="+servletPath+"\nqueryStr="+queryStr);
			}else{
				this.getTaoBaoRes(host, servletPath, queryStr);
			}
		}
	}
	public void getTaoBaoRes(String host,String servletPath,String queryStr) throws Exception{
		
		if(servletPath.startsWith("/")){
			servletPath=servletPath.substring(1);
		}
		 
		if (queryStr.contains(",")) {

			String[] fpathList = queryStr.split(",");
			for (int i = 0; i < fpathList.length; i++) {
				 
				write(  host,  servletPath,  fpathList[i],  fileHome);
			}
			

		} else {
			 
			write(  host,  servletPath,  queryStr,  fileHome);
		}
		//ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		 
	}
	
	private void write(String host,String servletPath,String requestPath,String fileHome) throws FileNotFoundException, IOException{
		String fpath=requestPath;
		if(fpath.contains("?")){
			fpath=fpath.substring(0,fpath.indexOf("?"));
		}
		
		String toFile=(fileHome+servletPath + fpath).replace("/", File.separator);
		String srcUrl=host+servletPath+"??"+requestPath;
		
		this.printUrlSource(srcUrl,toFile);
	}
	public void printUrlSource(String urlPath,String tofile) throws FileNotFoundException, IOException {
		printUrlSource(  urlPath,  tofile, false);
	}
	public void printUrlSource(String urlPath,String tofile,boolean overWrite) throws FileNotFoundException, IOException {
		if(overWrite || !new File(tofile).exists()){
			/*URL url = new URL(urlPath);
			InputStream ins = url.openStream();
			print(ins,new FileOutputStream(FileOperateUtil.newFile(tofile)));
			System.out.println("下载："+urlPath);*/
			printUrlSource(urlPath,new FileOutputStream(FileOperateUtil.newFile(tofile)));
		}else{
			System.out.println("已存在："+urlPath);
		}
		
		 
	}
	
	public void printUrlSource(String urlPath,OutputStream out) throws FileNotFoundException, IOException {
		URL url = new URL(urlPath);
		InputStream ins = url.openStream();
		print(ins,out);
		 
	}
	/**
	 * @param urlPath
	 * @param tofile
	 * @throws IOException 
	 */
	public void print(InputStream ins,OutputStream out) throws IOException {
		 
			//InputStream ins = url.openStream();
			PrintWriter printWriter=new PrintWriter(out);
			BufferedReader bReader = new BufferedReader(new InputStreamReader(ins));

			String info =null;
			while ((info = bReader.readLine()) != null) {
				printWriter.println(info);
			}
			printWriter.flush();
			printWriter.close();
			
		 

	}
	String src="D:\\workspace\\eclipse_jee\\isoftstore\\src\\main\\webapp\\resources\\kissy\\k\\1.4.1";
	 
	public   void bianli(File file) throws Exception {
		String url="http://g.tbcdn.cn/kissy/k/1.3.0";
		//
		//File file = new File(filePath);
		// 如果dir对应的文件不存在，或者不是一个目录，则退出
		if (!file.exists() ) {
			System.err.println( file + "目录不存在！");
			return  ;
		}
		if(!file.isDirectory()){
			//System.out.println(file.getPath());
			//System.out.println(file.getPath().substring(src.length()));
			url=url+file.getPath().substring(src.length()).replace("\\", "/");
			//System.out.println(url);
			try{
				getTalBaoRes(url);
			}catch(Exception e){
				e.printStackTrace();
			}
			
			return  ;
			//return file.delete();
		}
		
		// 删除文件夹下的所有文件(包括子目录)
		File[] files = file.listFiles();
		for (int i = 0; i < files.length; i++) {
			// 删除子文件
			bianli(files[i]);
		}

		 
	
	}
	 
	String fileHome="D:\\workspace\\eclipse_jee\\isoftstore\\src\\main\\webapp\\resources\\";
	//String fileHome="E:\\output\\";
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		 try {
			//new NetUtil().
			 NetUtil netUtil=new NetUtil();
			// netUtil.bianli(new File(netUtil.src));
			// netUtil.printUrlSource("http://mai.taobao.com/home/get_seller_menu.htm?t=1393831979251", System.out);
			 /*
			  * http://g.tbcdn.cn/??kissy/k/1.4.1/dom/class-list.js?t=1.js 
			  * http://a.tbcdn.cn/s/fdc/??spm.js,spmact.js?v=140217
			  * http://a.tbcdn.cn/??apps/sportalapps/global/1.0/seller-global-min.css,apps/sportal/3.0/common/css/mcdull-header-min.css,apps/sportal/3.0/common/css/base-min.css,apps/sportal/3.0/common/css/head-foot-min.css,apps/sportal/3.0/common/css/menu-min.css
			  */
			// netUtil.getTalBaoRes("http://g.tbcdn.cn/mui/mallbar/1.3.2/mallbar-tab.css");
			 netUtil.printUrlSource("http://tdecorate.tbcdn.cn/dc/fetchDc.htm?pid=330965160&sellerId=742644335&ecity=1&t=1396509676000", new FileOutputStream("E:\\test\\t.txt"));
			// netUtil.getTaoBaoRes("http://a.tbcdn.cn/","", "s/kissy/1.2.0/suggest.js?t=20130128171456");
			 //netUtil.printUrlSource("http://mai.taobao.com/home/get_seller_menu.htm?t=1393831979251", netUtil.fileBase+"s/aplus_v2.js");
			 System.out.println("-------finnished==");
		 } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
