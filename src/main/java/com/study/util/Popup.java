package com.study.util;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
 
 
public class Popup extends Thread {
 
 
 Shell shell;
 protected int moveStep = 2; //每次移动的pixel
 protected int upPosition; //能移动到的最上面坐标
 protected int downPosition; //当前popup的边框坐标
 protected int leftPosition; //popup左边边框坐标 
 
 public Popup(final String message) {
  shell = new Shell(SWT.ON_TOP);
  Text text = new Text(shell, SWT.MULTI | SWT.WRAP);
  text.setBounds(10, 20, 180, 80);
  text.setBackground(shell.getBackground());
  text.setText(message);
  //取屏莫大小
  Rectangle area = Display.getDefault().getClientArea();
  upPosition = area.height - 100;//计算出popup界面在屏幕显示的最高位置
  downPosition = area.height + 100;//计算出popup界面的初始位置
  leftPosition = area.width - 180;
  shell.setSize(180, 100);
  //初始化popup位置
  shell.setLocation(leftPosition, downPosition);
  shell.open();
 }
 
 
 public void run() {
  Display display = shell.getDisplay();
  while (true) {
   try {
    Thread.sleep(10);
 
 
    //判断当前位置是否小于能出现的最高位置，小于的话就说明还可以向上移动。
    if ((downPosition - moveStep) > upPosition) {
     display.asyncExec(new Runnable() {
      public void run() {
       shell.setLocation(leftPosition, downPosition- moveStep);
       downPosition -= moveStep;
      }
     });
     //此时已经移动到了最高位置，显示5秒钟后，关闭窗口并退出。
    } else {
     Thread.sleep(2000);
     display.asyncExec(new Runnable() {
      public void run() {
       shell.dispose();
      }
     });
    }
   } catch (InterruptedException e) {
    e.printStackTrace();
   }
  }
 }
}