package test;
import java.io.File;  
  
/** 
 * 用于文件夹下的java文件的编码自动转换，如gbk转utf-8 
 *  
 * @author Wen Fuqiang 
 * @company Fayhong Technology Co., Ltd. 
 * @date 2010-1-26 
 */  
public class EncodingConverter {  
  
    /** 
     * main方法入口 
     *  
     * @param args 
     *            args[0] 给定需要转换的文件夹 args[1] 指定需要转换的编码，如utf-8等 
     */  
    public static void main(String[] args) {  
        // if (args.length<2){   
        // System.out.println("please input path of folder and encoding name");   
        // System.exit(1);   
        // }   
        // else{   
        // ec.convertEncode(args[0], args[1]);   
        // }   
  
        EncodingConverter ec = new EncodingConverter();  
  
        // 暂时用src_path替换args[0]，encoding_name替换arg[1]   
        String src_path = "E:\\test";  
        String encoding_name = "utf-8";  
        ec.convertEncode(src_path, encoding_name);  
  
    }  
  
    public void convertEncode(String sourceFloder, String encoding_name) {  
        File file = new File(sourceFloder);  
        String[] files = file.list();  
  
        for (String s : files) {  
            if (s.indexOf('.') == -1) { // 表明这是个子目录，回归调用此函数   
                convertEncode(file.getAbsolutePath() + "\\" + s, encoding_name);  
            } else {  
              //  if (s.endsWith("xml")) { // 只处理以Java结尾的文件   
                    doConvertEncode(file.getAbsolutePath() + "\\" + s, file.getAbsolutePath()+ "\\" + s, encoding_name);  
               // }  
            }  
        }  
    }  
  
    /** 
     * 完成具体的编码转换工作 
     *  
     * @param inputFile 
     *            输入文件 
     * @param outputFile 
     *            输出文件 
     * @param encoding_name 
     *            需要转成的编码格式 
     */  
    public void doConvertEncode(String inputFile, String outputFile,  
            String encoding_name) {  
        Runtime rt = Runtime.getRuntime();  
        String cmd[] = { "native2ascii.exe", "-reverse", "-encoding",  
                encoding_name, inputFile, outputFile };  
        System.out.println("Execing convert command for " + inputFile + " ...");  
  
        try {  
            Process proc = rt.exec(cmd);  
  
            // any error message?   
            StreamGobbler errorGobbler = new StreamGobbler(proc.getErrorStream(), "ERROR");  
  
            // any output?   
            StreamGobbler outputGobbler = new StreamGobbler(proc.getInputStream(), "OUTPUT");  
  
            // kick them off   
            errorGobbler.start();  
            outputGobbler.start();  
  
            // any error???   
            int exitVal = proc.waitFor();  
            System.out.println("ExitValue: " + exitVal);  
  
        } catch (Throwable e) {  
            e.printStackTrace();  
        }  
    }  
}  