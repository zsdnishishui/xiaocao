package com.study.util;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
 
 
public class Alert {
 
 
 public Alert(String message, TableItem item) {
  Shell shell = new Shell();
  shell.setText("展示结果");
  GridLayout gShellLay = new GridLayout();
	shell.setLayout(gShellLay);
	// 创建多行Text组件，包含边框，自动换行，包括垂直滚动条
	Text text = new Text(shell, SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
	// 为文本框指定一个布局结构对象，这里让文本框尽可能的占满Panel的空间。
	GridData gTextData = new GridData(GridData.GRAB_HORIZONTAL | GridData.GRAB_VERTICAL | GridData.FILL_BOTH);
	text.setLayoutData(gTextData);
	text.append("标题："+item.getText(1)+"\n");
	text.append("类型："+item.getText(2)+"\n");
	text.append("时间："+item.getText(3)+"\n");
	text.append("结果：\n"+message);
	shell.open();
 }
 
 
 
}