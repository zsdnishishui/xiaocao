package com.study.swt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.study.util.StringUtil;

public class MutiHello {
	private Shell shell = null;  
  
    public MutiHello( ){init(); }

	private void init() {
		shell = new Shell();  
		// 为Shell设置布局对象
		GridLayout gShellLay = new GridLayout();
		shell.setLayout(gShellLay);
		// 构造一个Composite构件作为文本框和按键的容器
		Composite panel = new Composite(shell, SWT.NONE);
		// 为Panel指定一个布局结构对象。这里让Panel尽可能的占满Shell，也就是全部应用程序窗口的空间。
		GridData gPanelData = new GridData(GridData.GRAB_HORIZONTAL | GridData.GRAB_VERTICAL | GridData.FILL_BOTH);
		panel.setLayoutData(gPanelData);
		// 为Panel也设置一个布局对象。文本框和按键将按这个布局对象来显示。
		GridLayout gPanelLay = new GridLayout();
		panel.setLayout(gPanelLay);
		Composite composite = new Composite(panel,SWT.NONE);
		GridLayout layoutComposite = new GridLayout();
		layoutComposite.numColumns = 5;
		layoutComposite.marginHeight = 1;
		composite.setLayout(layoutComposite);   
		Text name = new Text(composite, SWT.BORDER);
		// 生成按键
		Button butt = new Button(composite, SWT.PUSH);
		// 生成STOP按键
		Button search = new Button(composite, SWT.PUSH);
		search.setText("查询发帖人");
		// 生成STOP按键
		
		butt.setText("查询标题");
		Button pl = new Button(composite, SWT.PUSH);
		pl.setText("回复数");
		Button stop = new Button(composite, SWT.PUSH);
		stop.setText("停止");
		// 创建多行Text组件，包含边框，自动换行，包括垂直滚动条
		Text text = new Text(panel, SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		// 为文本框指定一个布局结构对象，这里让文本框尽可能的占满Panel的空间。
		GridData gTextData = new GridData(GridData.GRAB_HORIZONTAL | GridData.GRAB_VERTICAL | GridData.FILL_BOTH);
		text.setLayoutData(gTextData);
		Task task = new Task(text);
		// 为按键指定鼠标事件
		butt.addMouseListener(new MouseAdapter() {
			public void mouseDown(MouseEvent e) {
				if (StringUtil.isEmpty(name.getText())) {
					text.setText("标题不能为空");
				}else{
					task.setTitle(name.getText());
					task.setStop(false);
					task.setFun("title");
					if (!task.isStop()) {
						task.start();
					}
					
					
				}
				
			}
		});
		stop.addMouseListener(new MouseAdapter() {
			public void mouseDown(MouseEvent e) {
				task.setStop(true);
			}
		});
		search.addMouseListener(new MouseAdapter() {
			public void mouseDown(MouseEvent e) {
				task.setTitle(name.getText());
				task.setFun("people");
				task.setStop(false);
				if (!task.isStop()) {
					task.start();
				}
			}
		});
		pl.addMouseListener(new MouseAdapter() {
			public void mouseDown(MouseEvent e) {
				task.setTitle(name.getText());
				task.setFun("pl");
				task.setStop(false);
				if (!task.isStop()) {
					task.start();
				}
			}
		});
	}
	public static void main(String[] args) {
		Display display = Display.getDefault();  
		  
		MutiHello mutiTask= new MutiHello();  
  
        mutiTask.getShell().open();  
  
        while (!mutiTask.getShell().isDisposed()) {  
  
            if (!display.readAndDispatch()) {  
  
                display.sleep();  
  
            }  
  
        }  
	}

	 //获得和设置属性的getter和setter方法  
	  
    public Shell getShell() {  
  
        return shell;  
  
    }  
  
    public void setShell(Shell shell) {  
  
        this.shell = shell;  
  
    } 
}
  

  
