package com.isoftframework.common.util;

/**
 * 匹配中文字符的正则表达式： [\u4e00-\u9fa5]
评注：匹配中文还真是个头疼的事，有了这个表达式就好办了

匹配双字节字符(包括汉字在内)：[^\x00-\xff]
评注：可以用来计算字符串的长度（一个双字节字符长度计2，ASCII字符计1）

匹配空白行的正则表达式：\n\s*\r
评注：可以用来删除空白行

匹配HTML标记的正则表达式： <(\S*?)[^>]*>.*? </\1> | <.*? />
评注：网上流传的版本太糟糕，上面这个也仅仅能匹配部分，对于复杂的嵌套标记依旧无能为力

匹配首尾空白字符的正则表达式：^\s* |\s*$
评注：可以用来删除行首行尾的空白字符(包括空格、制表符、换页符等等)，非常有用的表达式

匹配Email地址的正则表达式：\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*
评注：表单验证时很实用

匹配网址URL的正则表达式：[a-zA-z]+://[^\s]*
评注：网上流传的版本功能很有限，上面这个基本可以满足需求

匹配帐号是否合法(字母开头，允许5-16字节，允许字母数字下划线)：^[a-zA-Z][a-zA-Z0-9_]{4,15}$
评注：表单验证时很实用

匹配国内电话号码：\d{3}-\d{8} |\d{4}-\d{7}
评注：匹配形式如 0511-4405222 或 021-87888822

匹配腾讯QQ号：[1-9][0-9]{4,}
评注：腾讯QQ号从10000开始

匹配中国邮政编码：[1-9]\d{5}(?!\d)
评注：中国邮政编码为6位数字

匹配身份证：\d{15} |\d{18}
评注：中国的身份证为15位或18位

匹配ip地址：\d+\.\d+\.\d+\.\d+
评注：提取ip地址时有用

匹配特定数字：
^[1-9]\d*$　 　 //匹配正整数
^-[1-9]\d*$ 　 //匹配负整数
^-?[1-9]\d*$　　 //匹配整数
^[1-9]\d* |0$　 //匹配非负整数（正整数 + 0）
^-[1-9]\d* |0$　　 //匹配非正整数（负整数 + 0）
^[1-9]\d*\.\d* |0\.\d*[1-9]\d*$　　 //匹配正浮点数
^-([1-9]\d*\.\d* |0\.\d*[1-9]\d*)$　 //匹配负浮点数
^-?([1-9]\d*\.\d* |0\.\d*[1-9]\d* |0?\.0+ |0)$　 //匹配浮点数
^[1-9]\d*\.\d* |0\.\d*[1-9]\d* |0?\.0+ |0$　　 //匹配非负浮点数（正浮点数 + 0）
^(-([1-9]\d*\.\d* |0\.\d*[1-9]\d*)) |0?\.0+ |0$　　//匹配非正浮点数（负浮点数 + 0）
评注：处理大量数据时有用，具体应用时注意修正

匹配特定字符串：
^[A-Za-z]+$　　//匹配由26个英文字母组成的字符串
^[A-Z]+$　　//匹配由26个英文字母的大写组成的字符串
^[a-z]+$　　//匹配由26个英文字母的小写组成的字符串
^[A-Za-z0-9]+$　　//匹配由数字和26个英文字母组成的字符串
^\w+$　　//匹配由数字、26个英文字母或者下划线组成的字符串 
 *
 */
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class RegexUtil {
	
	/**
	 * order by
	 * //(?i)开启不区分大小写匹配  
	 *	//(?-i)停用大小写匹配 
	 */
	public static final String ORDER_BY_REGEX="(?i)order\\s+by(?-i)";
	/**
	 * 大写字母正则表达式
	 */
	public static final String UPCASE_REGEX="[A-Z]";
	
	 

	 /**
	  * this can be user replace with String.replaceAll
     * 把所有符合表达式的字符串替换为指定字符串
     * @param str待处理字符
     * @param regex查询表达式
     * @return 替换后的字符串
     */
	@Deprecated
    public static String regexReplace(String str, String regex,String repWithStr) {
        //  
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(str);
        String temp = str;
        //下面的while循环式进行循环匹配替换，把找到的所有
        //符合匹配规则的字串都替换为你想替换的内容
        String value = null;

        while (m.find()) {
            value = m.group(0);
            temp = temp.replace(value, repWithStr);
        }
        return temp;
    }
	  /**
     * 把所有符合表达式的字符串替换为以<>括住的字符串
     * @param str待处理字符
     * @param regex查询表达式
     * @return 替换后的字符串
     */
    public static String regexReplace(String str, String regex) {
        String value = null;

        //  
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(str);
        String temp = str;
        //下面的while循环式进行循环匹配替换，把找到的所有
        //符合匹配规则的字串都替换为你想替换的内容
        while (m.find()) {
            value = m.group(0);
            temp = temp.replace(value, "<" + value + ">");
        }
        return temp;
    }
    /**
     * 把大写字母开头的字符替换为'_'+字母的小写字母
     * @param str
     * @return
     */
    public static String replaceUpcaseWith_(String str) {
        

        String regex="[A-Z]";
        //  
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(str);
        String temp = str;
        //下面的while循环式进行循环匹配替换，把找到的所有
        //符合匹配规则的字串都替换为你想替换的内容
        String value = null;
        int i=0;
        while (m.find()) {
            value = m.group(0);
            temp = temp.replace(value,i>0?'_'+ value.toLowerCase():value.toLowerCase());
            i++;
        }
        return temp;
    }
    public static String replace_WithUpcase(String str) {
        

        String regex="_\\w";
        //  
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(str);
        String temp = str;
        //下面的while循环式进行循环匹配替换，把找到的所有
        //符合匹配规则的字串都替换为你想替换的内容
        String value = null;
        int i=0;
        while (m.find()) {
            value = m.group(0);
            temp = temp.replace(value,(value.charAt(1)+"").toUpperCase());
            i++;
        }
        return temp;
    }
    
    
    public static void main(String[] args){
    	String sql="login_user_name_wzq";
    	sql=replace_WithUpcase(sql);
    	//sql=regexReplace(sql,ORDER_BY_REGEX,"ORDER BY");
    	System.out.println(sql);
    }
    
    
}
