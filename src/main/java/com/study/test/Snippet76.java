package com.study.test;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTError;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class Snippet76 {
	public static void main(String [] args) {Display display = new Display(); 
    Shell shell = new Shell(display); 
    shell.setSize(100, 100); 


    Browser browser = new Browser(shell, SWT.NONE); 
    browser.setBounds(5, 75, 1000, 1000); 

    shell.open(); 
    browser.setUrl("https://www.baidu.com/"); 

    String html = browser.getText(); //NOTHING! 
System.out.println(html);
    while (!shell.isDisposed()) { 

     if (!display.readAndDispatch() && html == null) { 

      display.sleep(); 
     } 
    } 
    display.dispose(); 

    System.out.println(html); ////NOTHING! 
	}
}
