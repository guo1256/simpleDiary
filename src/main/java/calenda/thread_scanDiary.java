package calenda;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class thread_scanDiary extends JFrame implements Runnable {
	
	private static final long serialVersionUID = 1L;
	//日历总个数
	private static int num;
	private File file;
	private File[] Diaries;
	//声明JTable模型
	private JTable jtable;
	//声明格言面板 标签 内容
	private JPanel jpMotto =new JPanel();
	private JLabel jlblMotto = new JLabel();
	private Font font = new Font("宋体",Font.ITALIC,20);
	private String wish = "---------------------------------";
	//弹出式菜单
	private JPopupMenu jPopupMenul = new JPopupMenu();
	//菜单
	private JMenuItem jmiScan = new JMenuItem("查看");
	private JMenuItem jmiDelete = new JMenuItem("删除");
	public void run() {		
		//弹出式菜单增加子菜单
		jmiScan.setForeground(Color.RED);
		jmiDelete.setForeground(Color.RED);
		jPopupMenul.add(jmiScan);
		jPopupMenul.addSeparator();
		jPopupMenul.add(jmiDelete);
		/**
		 * 获取文件列表
		 */
		file = new File(calenda.Diary.PATH);
		Diaries = file.listFiles();
		num = Diaries.length;
		String[] head = {"时间","主题"};
		Object[][] diary = new Object[num][2];
		for (int i = 0; i < num; i++) {
			try {
				Diary d = Diary.read(calenda.Diary.PATH+"/"+Diaries[i].getName());
				String time = Diaries[i].getName().replaceFirst(".dat", "");
				String theme = d.getTheme();
				diary[i][0] = time;
				diary[i][1] = theme;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		/**
		 * 格言面板区
		 */
		jlblMotto.setText(wish);
		jlblMotto.setFont(font);
		jlblMotto.setForeground(Color.RED);
		jpMotto.add(jlblMotto);
		jpMotto.setBackground(Color.WHITE);
		/**
		 * 日历列表面板区
		 */
		final DefaultTableModel tableModel = new DefaultTableModel(diary,head);
		jtable = new JTable(tableModel);
		jtable.setBackground(Color.WHITE);
		jtable.setRowHeight(30);
		jtable.setDoubleBuffered(false);
		jtable.setComponentPopupMenu(jPopupMenul);
		jtable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
		tcr.setHorizontalAlignment(SwingConstants.CENTER);
		jtable.setDefaultRenderer(Object.class, tcr);
		JScrollPane jsp = new JScrollPane(jtable);

		//查看菜单
		jmiScan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(jtable.getSelectedRow()>=0){
					int index = jtable.getSelectedRow();
					String filename = calenda.Diary.PATH+"/"+Diaries[index].getName();
					try {
						Diary d = Diary.read(filename);
						JFrame jf = new JFrame();
						JTextArea jta = new JTextArea();
						JLabel jlblTitle = new JLabel("主题");
						JTextField jtfTitle = new JTextField(16);
						JPanel jpTitle = new JPanel();
						jpTitle.setLayout(new BorderLayout());
						jpTitle.add(jlblTitle,BorderLayout.WEST);
						jpTitle.add(jtfTitle,BorderLayout.CENTER);
						jta.setLineWrap(true);
						jta.setWrapStyleWord(true);
						JScrollPane jsp = new JScrollPane(jta);
						jsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
						//标题
						jtfTitle.setText(d.getTheme());
						jta.setText(d.getContent());
						jtfTitle.setEnabled(true);//标题能否编辑
						jta.setEditable(true);//能容能否编辑
						//保存
						JButton jbtSave = new JButton("保存");
						jbtSave.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								String theme = jtfTitle.getText().trim();
								String content = jta.getText();
								Diary d_today = new Diary(Diaries[index].getName().replaceFirst(".dat", ""),theme,content);
								try {
									d_today.write();
									JOptionPane.showMessageDialog(null, "保存成功！");
									jf.dispose();
								} catch (IOException e1) {
									e1.printStackTrace();
								}
							}
						});
						jf.setTitle("日记"+Diaries[index].getName().replaceFirst(".dat", ""));
						jf.add(jsp,BorderLayout.CENTER);
						jf.add(jpTitle,BorderLayout.NORTH);
						jf.add(jbtSave,BorderLayout.SOUTH);
						jf.setSize(400,400);
						jf.setLocationRelativeTo(null);
						jf.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
						jf.setVisible(true);
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}else{
					JOptionPane.showMessageDialog(null, "没有日记");
				}		
			}
		});

		//删除菜单
		jmiDelete.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
		        if(jtable.getSelectedRow()>=0){
		          int index=jtable.getSelectedRow();
		          String filename=calenda.Diary.PATH+"/"+Diaries[index].getName();
		          File file=new File(filename);
		          int option=JOptionPane.showConfirmDialog(null, "你确定要删除日记"+Diaries[index].getName()+"？");
		           if(option==JOptionPane.YES_OPTION)
		           {
		             file.delete();
		             tableModel.removeRow(index);
		             SwingUtilities.updateComponentTreeUI(jtable);
		             JOptionPane.showMessageDialog(null, "删除成功！");
		           }
		        }
		        else{
		          JOptionPane.showMessageDialog(null, "请先选中一个日记！");
		        }			
			}
		});
		/**
	     * 主框架布局
	     */
	    this.add(jsp,BorderLayout.CENTER);
	    this.add(jpMotto, BorderLayout.SOUTH);
	    this.setSize(600, 500);
	    this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	    this.setLocationRelativeTo(null);
	    this.setTitle("日记列表");
	    this.setVisible(true);
	    new Thread(new thread_mottoSparkle(jlblMotto)).start();
	}

}
