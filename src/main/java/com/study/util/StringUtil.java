package com.study.util;

import java.util.UUID;
import java.util.regex.Pattern;

public class StringUtil {
	public static String host = "https://cc.jb6v.icu/";
	public static String url = host+"thread0806.php";
	
	/**
	 * 检测字符串是否不为空(null,"","null")
	 * @param s
	 * @return 不为空则返回true，否则返回false
	 */
	public static boolean notEmpty(String s){
		return s!=null && !"".equals(s) && !"null".equals(s);
	}
	/**
	 * 
	 * @Title: isEmpty   
	 * @Description: TODO   * 判断是否是空字符串null和""
	 * @param: @param str
	 * @param: @return      
	 * @return: boolean      
	 * @throws
	 */
    public static boolean isEmpty(String str){
        if (str != null  && !str.equals("")) {
            return false;
        }
        return true;
    }
    /**
     * 
     * @Title: equals   
     * @Description: TODO  判断两个字符串是否相等 如果都为null则判断为相等,一个为null另一个not null则判断不相等 否则如果s1=s2则相等
     * @param: @param s1
     * @param: @param s2
     * @param: @return      
     * @return: boolean      
     * @throws
     */
    public static boolean equals(String s1, String s2) {
        if (StringUtil.isEmpty(s1) && StringUtil.isEmpty(s2)) {
            return true;
        } else if (!StringUtil.isEmpty(s1) && !StringUtil.isEmpty(s2)) {
            return s1.equals(s2);
        }
        return false;
    }
 
    /**
     * 
     * @Title: getUUID   
     * @Description: TODO(这里用一句话描述这个方法的作用)   
     * @param: @return      
     * @return: String      
     * @throws
     */
    public static String getUUID(){
        return UUID.randomUUID().toString();
    }
    /**
	 * 得到32位的uuid
	 * @return
	 */
	public static String get32UUID(){
		
		return UUID.randomUUID().toString().trim().replaceAll("-", "");
	}
	public static boolean isInteger(String str) {  
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");  
        return pattern.matcher(str).matches();  
}
}
