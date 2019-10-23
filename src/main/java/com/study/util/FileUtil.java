package com.study.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FileUtil {
public static void writeFile(String text, String title, String type){
	text =  text.replace("\n","\\n").replace("\r", "");
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	File dataFile = new File("data.txt");
	String str ="{\"type\":\""+type+"\",\"title\":\""+title+"\",\"addtime\":\""+sdf.format(new Date())+"\",\"result\":\""+text+"\"}";

	try {
		BufferedWriter writer = new BufferedWriter(new FileWriter(dataFile,true));
		writer.write(str+"\r\n");// \r\n即为换行
		writer.flush(); // 把缓存区内容压入文件
		writer.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} 
	 
}
public static List<String> readFile(){
	File filename = new File("data.txt"); // 要读取以上路径的input。txt文件
	List<String> list = new ArrayList<String>();
	try {
		InputStreamReader reader = new InputStreamReader(
				new FileInputStream(filename));
		BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言
		String line = "";
		line = br.readLine();
		while (line != null) {
			list.add(line);
			line = br.readLine(); // 一次读入一行数据
			
		}
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} // 建立一个输入流对象reader
	return list;
}
}
