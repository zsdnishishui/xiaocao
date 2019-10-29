package com.study.swt;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.ole.win32.OLE;
import org.eclipse.swt.ole.win32.OleAutomation;
import org.eclipse.swt.ole.win32.OleClientSite;
import org.eclipse.swt.ole.win32.OleFrame;
import org.eclipse.swt.ole.win32.Variant;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.study.util.Alert;
import com.study.util.FileUtil;
import com.study.util.StringUtil;
import com.study.util.caoliuVideoThread;

public class MutiHello {
	private Shell shell = null;  
  
    public MutiHello( ){init(); }

	private void init() {
		shell = new Shell();
		shell.setText("小草社区（你懂的）");
		// 为Shell设置布局对象
		GridLayout gShellLay = new GridLayout();
		shell.setLayout(gShellLay);
		final TabFolder tabFolder = new TabFolder (shell, SWT.BORDER);
		GridData tabData = new GridData(GridData.FILL_HORIZONTAL| GridData.GRAB_VERTICAL | GridData.FILL_BOTH);
		tabFolder.setLayoutData(tabData);
		Rectangle clientArea = shell.getClientArea ();
		tabFolder.setLocation (clientArea.x, clientArea.y);
		TabItem item = new TabItem (tabFolder, SWT.NONE);
		item.setText ("查询文本");
		TabItem item2 = new TabItem (tabFolder, SWT.NONE);
		item2.setText ("下载图片");
		TabItem item3 = new TabItem (tabFolder, SWT.NONE);
		item3.setText ("下载视频");
		TabItem item4 = new TabItem (tabFolder, SWT.NONE);
		item4.setText ("查询记录");
		// 构造一个Composite构件作为文本框和按键的容器
		tab1(item,tabFolder);
		tab2(item2,tabFolder);
		tab3(item3,tabFolder);
		tab4(item4,tabFolder);
		tabFolder.pack ();
	}
	public static void main(String[] args) {
		Display display = Display.getDefault();  
		  
		MutiHello mutiTask= new MutiHello();  
  
        mutiTask.getShell().open();  
        try {
        	while (!mutiTask.getShell().isDisposed()) {  
        		  
                if (!display.readAndDispatch()) {  
      
                    display.sleep();  
      
                }  
      
            }
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("exit");
			System.exit(0);
		}
        
        System.exit(0);
	}

	 //获得和设置属性的getter和setter方法  
	  
    public Shell getShell() {  
  
        return shell;  
  
    }  
  
    public void setShell(Shell shell) {  
  
        this.shell = shell;  
  
    }
    private void tab1(TabItem item,TabFolder tabFolder) {
    	Composite panel = new Composite(tabFolder, SWT.NONE);
		// 为Panel指定一个布局结构对象。这里让Panel尽可能的占满Shell，也就是全部应用程序窗口的空间。
		GridData gPanelData = new GridData(GridData.GRAB_HORIZONTAL | GridData.GRAB_VERTICAL | GridData.FILL_BOTH);
		panel.setLayoutData(gPanelData);
		// 为Panel也设置一个布局对象。文本框和按键将按这个布局对象来显示。
		GridLayout gPanelLay = new GridLayout();
		panel.setLayout(gPanelLay);
		item.setControl (panel);
		
		
		Text name = new Text(panel, SWT.BORDER|SWT.FILL);
		//为标题框设置布局结构对象
		GridData titleData = new GridData(GridData.FILL_HORIZONTAL);
		name.setLayoutData(titleData);
		Composite composite = new Composite(panel,SWT.NONE);
		GridLayout layoutComposite = new GridLayout();
		layoutComposite.numColumns = 6;
		layoutComposite.marginHeight = 1;
		composite.setLayout(layoutComposite);   
		
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
		Button save = new Button(composite, SWT.PUSH);
		save.setText("保存查询结果");
		//进度条
		final ProgressBar bar = new ProgressBar(composite, SWT.SMOOTH);
		bar.setMinimum(0);
        bar.setMaximum(100); 
		// 创建多行Text组件，包含边框，自动换行，包括垂直滚动条
		Text text = new Text(panel, SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		// 为文本框指定一个布局结构对象，这里让文本框尽可能的占满Panel的空间。
		GridData gTextData = new GridData(GridData.GRAB_HORIZONTAL | GridData.GRAB_VERTICAL | GridData.FILL_BOTH);
		text.setLayoutData(gTextData);
		//Task task = new Task(text,bar);
		// 为按键指定鼠标事件
		butt.addMouseListener(new MouseAdapter() {
			public void mouseDown(MouseEvent e) {
				if (StringUtil.isEmpty(name.getText())) {
					text.setText("标题不能为空");
				}else{
					ThreadGroup currentGroup = Thread.currentThread().getThreadGroup();
				      int noThreads = currentGroup.activeCount();
				      Thread[] lstThreads = new Thread[noThreads];
				      currentGroup.enumerate(lstThreads);
				      int textThreads=0;
				      for (int i = 1; i < noThreads; i++){
				    	  if (lstThreads[i].getName().startsWith("Thread")) {
				    		  textThreads++;
						}
				    	  
				      }
				     if (textThreads>0) {
				    	 MessageBox messageBox = new MessageBox(bar.getShell());
					        messageBox.setMessage("请先停止");
					        messageBox.open();
					}else{
						Task task = new Task(text,bar);
						task.setTitle(name.getText());
						task.setFun("title");
						if (!task.isStop()) {
							task.setStop(true);
							System.out.println(task.getState());
							if ("NEW".equals(task.getState().toString())) {
								task.start();
							}
						}
					}
					
					
					
				}
				
			}
		});
		stop.addMouseListener(new MouseAdapter() {
			public void mouseDown(MouseEvent e) {
				ThreadGroup currentGroup = Thread.currentThread().getThreadGroup();
					      int noThreads = currentGroup.activeCount();
					      Thread[] lstThreads = new Thread[noThreads];
					      currentGroup.enumerate(lstThreads);
					      for (int i = 1; i < noThreads; i++){
					    	  //停止此tab下的线程，其它tab下的不用管
					    	  if (lstThreads[i].getName().startsWith("Thread")) {
					    		  Task task=(Task)lstThreads[i];
						    	  task.setStop(false);
					    	  }
					    	  
					      }
					      
			}
		});
		search.addMouseListener(new MouseAdapter() {
			public void mouseDown(MouseEvent e) {
				if (StringUtil.isEmpty(name.getText())) {
					text.setText("标题不能为空");
				}else{
					ThreadGroup currentGroup = Thread.currentThread().getThreadGroup();
				      int noThreads = currentGroup.activeCount();
				      Thread[] lstThreads = new Thread[noThreads];
				      currentGroup.enumerate(lstThreads);
				      int textThreads=0;
				      for (int i = 1; i < noThreads; i++){
				    	  if (lstThreads[i].getName().startsWith("Thread")) {
				    		  textThreads++;
						}
				    	  
				      }
				     if (textThreads>0) {
				    	 MessageBox messageBox = new MessageBox(bar.getShell());
					        messageBox.setMessage("请先停止");
					        messageBox.open();
					}else{
						Task task = new Task(text,bar);
						task.setTitle(name.getText());
						task.setFun("people");
						if (!task.isStop()) {
							task.setStop(true);
							System.out.println(task.getState());
							if ("NEW".equals(task.getState().toString())) {
								task.start();
							}
						}
					}
					
					
					
				}
			}
		});
		
		pl.addMouseListener(new MouseAdapter() {
			public void mouseDown(MouseEvent e) {
				if (StringUtil.isEmpty(name.getText())) {
					text.setText("标题不能为空");
				}else{
					ThreadGroup currentGroup = Thread.currentThread().getThreadGroup();
				      int noThreads = currentGroup.activeCount();
				      Thread[] lstThreads = new Thread[noThreads];
				      currentGroup.enumerate(lstThreads);
				      int textThreads=0;
				      for (int i = 1; i < noThreads; i++){
				    	  if (lstThreads[i].getName().startsWith("Thread")) {
				    		  textThreads++;
						}
				    	  
				      }
				     if (textThreads>0) {
				    	 MessageBox messageBox = new MessageBox(bar.getShell());
					        messageBox.setMessage("请先停止");
					        messageBox.open();
					}else{
						Task task = new Task(text,bar);
						task.setTitle(name.getText());
						task.setFun("pl");
						if (!task.isStop()) {
							task.setStop(true);
							System.out.println(task.getState());
							if ("NEW".equals(task.getState().toString())) {
								task.start();
							}
						}
					}
					
					
					
				}
			}
		});
		save.addMouseListener(new MouseAdapter() {
			public void mouseDown(MouseEvent e) {
				//注意这一步替换，不然无法解析json
				String jieGuo  = text.getText().replace("\"", "\\\"");
				if (StringUtil.notEmpty(jieGuo)&&StringUtil.notEmpty(name.getText())) {
					FileUtil.writeFile(jieGuo,name.getText(),"查询文本");
					text.append("\n保存成功");
				}
			}
		});
    }
    private void tab2(TabItem item,TabFolder tabFolder) {
    	Composite panel = new Composite(tabFolder, SWT.NONE);
		// 为Panel指定一个布局结构对象。这里让Panel尽可能的占满Shell，也就是全部应用程序窗口的空间。
		GridData gPanelData = new GridData(GridData.GRAB_HORIZONTAL | GridData.GRAB_VERTICAL | GridData.FILL_BOTH);
		panel.setLayoutData(gPanelData);
		// 为Panel也设置一个布局对象。文本框和按键将按这个布局对象来显示。
		GridLayout gPanelLay = new GridLayout();
		panel.setLayout(gPanelLay);
		item.setControl (panel);
		//tabFolder.pack ();
		
		Text name = new Text(panel, SWT.BORDER|SWT.FILL);
		//为标题框设置布局结构对象
		GridData titleData = new GridData(GridData.FILL_HORIZONTAL);
		name.setLayoutData(titleData);
		Composite composite = new Composite(panel,SWT.NONE);
		GridLayout layoutComposite = new GridLayout();
		layoutComposite.numColumns = 6;
		layoutComposite.marginHeight = 1;
		composite.setLayout(layoutComposite);  
		Button imgBut= new Button(composite, SWT.PUSH);
		imgBut.setText("下载此页面中的图片");
		Button save = new Button(composite, SWT.PUSH);
		save.setText("保存下载记录");
		//进度条
		final ProgressBar bar = new ProgressBar(composite, SWT.SMOOTH);
		// 创建多行Text组件，包含边框，自动换行，包括垂直滚动条
		Text text = new Text(panel, SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		// 为文本框指定一个布局结构对象，这里让文本框尽可能的占满Panel的空间。
		GridData gTextData = new GridData(GridData.GRAB_HORIZONTAL | GridData.GRAB_VERTICAL | GridData.FILL_BOTH);
		text.setLayoutData(gTextData);
		//Task task = new Task(text,bar);
		// 为按键指定鼠标事件
		
		imgBut.addMouseListener(new MouseAdapter() {
			public void mouseDown(MouseEvent e) {
				String http = name.getText();
				if (StringUtil.notEmpty(http)) {
					ThreadGroup currentGroup = Thread.currentThread().getThreadGroup();
				      int noThreads = currentGroup.activeCount();
				      Thread[] lstThreads = new Thread[noThreads];
				      currentGroup.enumerate(lstThreads);
				      for (int i = 1; i < noThreads; i++){
				    	  if (lstThreads[i].getName().startsWith("pool")) {
				    		  return;
						}
				    	  
				      }
				      bar.setMinimum(0);
				      bar.setSelection(0);
					Task task = new Task(text,bar);
					task.setTitle(http);
					task.setFun("downImg");
					task.setName("downImgThread");
					if (!task.isStop()) {
						task.setStop(true);
						task.start();
					}
				}
				
			}
		});
		save.addMouseListener(new MouseAdapter() {
			public void mouseDown(MouseEvent e) {
				String jieGuo  = text.getText();
				if (StringUtil.notEmpty(jieGuo)&&StringUtil.notEmpty(name.getText())) {
					FileUtil.writeFile(jieGuo,name.getText(),"downImg");
					text.append("\n保存成功");
				}
			}
		});
    }
    private void tab3(TabItem item,TabFolder tabFolder) {
    	Composite panel = new Composite(tabFolder, SWT.NONE);
		// 为Panel指定一个布局结构对象。这里让Panel尽可能的占满Shell，也就是全部应用程序窗口的空间。
		GridData gPanelData = new GridData(GridData.GRAB_HORIZONTAL | GridData.GRAB_VERTICAL | GridData.FILL_BOTH);
		panel.setLayoutData(gPanelData);
		// 为Panel也设置一个布局对象。文本框和按键将按这个布局对象来显示。
		GridLayout gPanelLay = new GridLayout();
		panel.setLayout(gPanelLay);
		item.setControl (panel);
		//tabFolder.pack ();
		
		Text name = new Text(panel, SWT.BORDER|SWT.FILL);
		//为标题框设置布局结构对象
		GridData titleData = new GridData(GridData.FILL_HORIZONTAL);
		name.setLayoutData(titleData);
		Composite composite = new Composite(panel,SWT.NONE);
		GridLayout layoutComposite = new GridLayout();
		layoutComposite.numColumns = 6;
		layoutComposite.marginHeight = 1;
		composite.setLayout(layoutComposite);  
		Button videoBut= new Button(composite, SWT.PUSH);
		videoBut.setText("下载此页面中的视频");
		
		Button susBut= new Button(composite, SWT.PUSH);
		susBut.setText("停止下载");
		Button reBut= new Button(composite, SWT.PUSH);
		reBut.setText("重新下载");
		Button save = new Button(composite, SWT.PUSH);
		save.setText("保存下载记录");
		//进度条
		final ProgressBar bar = new ProgressBar(composite, SWT.SMOOTH);
		bar.setMinimum(0);
		bar.setMaximum(100);
		Button play = new Button(composite, SWT.PUSH);
		play.setText("播放");
		// 创建多行Text组件，包含边框，自动换行，包括垂直滚动条
		Text text = new Text(panel, SWT.BORDER|SWT.FILL);
		// 为文本框指定一个布局结构对象，这里让文本框尽可能的占满Panel的空间。
		GridData gTextData = new GridData(GridData.FILL_HORIZONTAL);
		text.setLayoutData(gTextData);
		Task task = new Task(text,bar);
		OleFrame frame = new OleFrame(panel, SWT.NONE);
		frame.setLayoutData(gPanelData);
		OleClientSite clientSite = new OleClientSite(frame, SWT.NONE, "WMPlayer.OCX");
		clientSite.doVerb(OLE.OLEIVERB_INPLACEACTIVATE);
		// 为按键指定鼠标事件
		
		videoBut.addMouseListener(new MouseAdapter() {
			public void mouseDown(MouseEvent e) {
				String http = name.getText();
				if (StringUtil.notEmpty(http)) {
					task.setTitle(http);
					task.setFun("downVideo");
					if (!task.isStop()) {
						task.setStop(true);
						task.start();
					}
				}
			}
		});
		
		reBut.addMouseListener(new MouseAdapter() {
			public void mouseDown(MouseEvent e) {
				if (caoliuVideoThread.stop) {
					if (StringUtil.isEmpty(name.getText())) {
						MessageBox messageBox = new MessageBox(bar.getShell());
				        messageBox.setMessage("页面路径不能为空");
				        messageBox.open();
					}else{
						Task reTask = new Task(text,bar);
						reTask.setTitle(name.getText());
						reTask.setFun("downVideo");
						caoliuVideoThread.stop=false;
						if (!reTask.isStop()) {
							reTask.setStop(true);
							reTask.start();
						}
					}
					
				}else{
					MessageBox messageBox = new MessageBox(bar.getShell());
			        messageBox.setMessage("请先停止");
			        messageBox.open();
				}
				
			}
		});
		susBut.addMouseListener(new MouseAdapter() {
			public void mouseDown(MouseEvent e) {
				caoliuVideoThread.stop=true;
			}
		});
		save.addMouseListener(new MouseAdapter() {
			public void mouseDown(MouseEvent e) {
				String jieGuo  = text.getText();
				String type = task.getFun();
				if (StringUtil.notEmpty(jieGuo)&&StringUtil.notEmpty(name.getText())&&StringUtil.notEmpty(type)) {
					FileUtil.writeFile(jieGuo,name.getText(),type);
					MessageBox messageBox = new MessageBox(bar.getShell());
			        messageBox.setMessage("保存成功");
			        messageBox.open();
				}
			}
		});
		play.addMouseListener(new MouseAdapter() {
			public void mouseDown(MouseEvent e) {
				String jieGuo  = text.getText();
				if (StringUtil.notEmpty(jieGuo)&&jieGuo.endsWith(".mp4")) {
					OleAutomation player = new OleAutomation(clientSite);
 					int playURL[] = player.getIDsOfNames(new String[] { "URL" });
 					if (playURL != null) {
 						Variant theFile = new Variant(jieGuo);
 						player.setProperty(playURL[0], theFile);
 					}
 					player.dispose();
					
				}
			}
		});
    }
    private void tab4(TabItem item,TabFolder tabFolder) {
    	Composite panel = new Composite(tabFolder, SWT.NONE);
		// 为Panel指定一个布局结构对象。这里让Panel尽可能的占满Shell，也就是全部应用程序窗口的空间。
		GridData gPanelData = new GridData(GridData.GRAB_HORIZONTAL | GridData.GRAB_VERTICAL | GridData.FILL_BOTH);
		panel.setLayoutData(gPanelData);
		// 为Panel也设置一个布局对象。文本框和按键将按这个布局对象来显示。
		GridLayout gPanelLay = new GridLayout();
		panel.setLayout(gPanelLay);
		item.setControl (panel);
		//tabFolder.pack ();
		
		
		Composite composite = new Composite(panel,SWT.NONE);
		
		GridData gTextData = new GridData(GridData.GRAB_HORIZONTAL | GridData.GRAB_VERTICAL | GridData.FILL_BOTH);
		composite.setLayoutData(gTextData);
		GridLayout layoutComposite = new GridLayout();
		composite.setLayout(layoutComposite);
		Button refresh = new Button(composite, SWT.PUSH);
		refresh.setText("刷新");
		Table table = new Table (composite, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION);
		table.setLinesVisible (true);
		table.setHeaderVisible (true);
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		//data.heightHint = 200;
		table.setLayoutData(data);
		String[] titles = {"序号","搜索", "类型", "记录时间", "操作"};
		for (int i=0; i<titles.length; i++) {
			TableColumn column = new TableColumn (table, SWT.NONE);
			column.setText (titles [i]);
			column.pack();
		}
		refreshTable(table,titles.length);
		refresh.addMouseListener(new MouseAdapter() {
			public void mouseDown(MouseEvent e) {
				TableItem tableItems[] = table.getItems();//得到所有的tableItem
	            for(int i = 0; i<tableItems.length; i++)
	            {
	                tableItems[i].dispose();//释放
	            }
				refreshTable(table,titles.length);
			}
		});
		
		
    }
    public void refreshTable(Table table, int length){

		List<String> list = FileUtil.readFile();
		for (int i=0; i<list.size(); i++) {
			String s = list.get(i);
			JSONObject jsonObject = JSON.parseObject(s);
	        String type = jsonObject.getString("type");
	        String addtime = jsonObject.getString("addtime");
	        String result = jsonObject.getString("result");
	        String title = jsonObject.getString("title");
			TableItem item1 = new TableItem (table, SWT.NONE);
			Button show = new Button(table, SWT.PUSH);
			show.setText("查看");
			TableEditor editor = new TableEditor(table);
			editor.grabHorizontal = true;//自动填充表格
			editor.minimumHeight = show.getSize().y;//设置editor最小高度
			editor.minimumWidth = show.getSize().x;//最小宽度
			item1.setText (0, (i+1)+"");
			item1.setText (1, title);
			item1.setText (2, type);
			item1.setText (3, addtime);
			editor.setEditor(show, item1, 4);//指定给哪个单元格设置该控件。
			show.addMouseListener(new MouseAdapter() {
				public void mouseDown(MouseEvent e) {
					/*MessageBox messageBox = new MessageBox(shell);
			        messageBox.setMessage(item1.getText(4));
			        messageBox.open();*/
					//MessageDialog.openInformation(shell,"结果",item1.getText(4));
					new Alert(result,item1);
				}
			});
		}
		for (int i=0; i<length; i++) {
			table.getColumn (i).pack ();
		}
    }
}
  

  
