package calenda;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;

public class CalendarApp extends JFrame{
	private CalendarPanel calendarPanel = new CalendarPanel();
	private static JLabel jlblLearn = new JLabel("未知");
	private String versionID = "欢迎使用 版本：1.0.0";
	private JLabel JlblVersionID = new JLabel(versionID);
	//按钮
	private JButton jbtPrior = new JButton("←");
	private JButton jbtNext = new JButton("→");
	private JButton jbtScanDiary = new JButton("看");
	//字体
	Font font=new Font("宋体",Font.PLAIN,4);//宋体 普通样式  4pt
	private static int year;
	public void init(){
		year = calendarPanel.getYear();
		calendarPanel.setBackground(Color.WHITE);
		//按钮的面板在日历面板下面
		JPanel jpButtons = new JPanel(new FlowLayout());
		jbtPrior.setBackground(Color.WHITE);
		jbtNext.setBackground(Color.WHITE);
		jbtScanDiary.setBackground(Color.WHITE);
		/*
		 * 给四个按钮添加鼠标事件
		 */
		//后退按钮
		jbtPrior.addMouseListener(new MouseListener() {
			
			public void mouseReleased(MouseEvent e) {
				
			}
			
			public void mousePressed(MouseEvent e) {
				
			}
			
			public void mouseExited(MouseEvent e) {
				jbtPrior.setBackground(Color.WHITE);
				
			}
			
			public void mouseEntered(MouseEvent e) {
				jbtPrior.setBackground(Color.green);
				
			}
			
			public void mouseClicked(MouseEvent e) {
				
			}
		});
		//前进按钮
		jbtNext.addMouseListener(new MouseListener() {
			
			public void mouseReleased(MouseEvent e) {
				
			}
			
			public void mousePressed(MouseEvent e) {
				
			}
			
			public void mouseExited(MouseEvent e) {
				jbtNext.setBackground(Color.WHITE);
				
			}
			
			public void mouseEntered(MouseEvent e) {
				jbtNext.setBackground(Color.green);
				
			}
			
			public void mouseClicked(MouseEvent e) {
				
			}
		});
		//看按钮
		jbtScanDiary.addMouseListener(new MouseListener() {
			
			public void mouseReleased(MouseEvent e) {
				
			}
			
			public void mousePressed(MouseEvent e) {
				
			}
			
			public void mouseExited(MouseEvent e) {
				jbtScanDiary.setBackground(Color.WHITE);
				
			}
			
			public void mouseEntered(MouseEvent e) {
				jbtScanDiary.setBackground(Color.green);
				
			}
			
			public void mouseClicked(MouseEvent e) {
				
			}
		});
		jpButtons.add(jbtPrior);
		jpButtons.add(jbtNext);
		jpButtons.add(jbtScanDiary);
		jpButtons.setBackground(Color.WHITE);
		/**
		 * 添加日历主要组件日期 和 按钮
		 */
		JPanel jpCalendar = new JPanel(new BorderLayout());
		jpCalendar.add(calendarPanel,BorderLayout.CENTER);
		jpCalendar.add(jpButtons,BorderLayout.SOUTH);
		
		/**
		 *  未知模块和版本号 合并
		 */
		JPanel jpUnknow = new JPanel(new FlowLayout());
		jpUnknow.setBorder(new TitledBorder("先空着"));
		jpUnknow.add(jlblLearn);
		jpUnknow.setBackground(Color.WHITE);
		JPanel jpVersionID = new JPanel(new FlowLayout());
		jpVersionID.setFont(font);
		jpVersionID.add(JlblVersionID);
		jpVersionID.setBackground(Color.WHITE);
		JPanel jpBelow = new JPanel(new BorderLayout(2, 1));
		jpBelow.add(jpUnknow,BorderLayout.CENTER);
		jpBelow.add(jpVersionID,BorderLayout.SOUTH);
		
		this.add(jpCalendar,BorderLayout.CENTER);
		this.add(jpBelow,BorderLayout.SOUTH);
		this.setBackground(Color.WHITE);
		this.setSize(700,500);
		this.setLocationRelativeTo(null);
		this.setTitle("日记本");
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		jbtScanDiary.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				new Thread(new thread_scanDiary()).start();
			}
		});
		
		jbtPrior.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				int currentMonth = calendarPanel.getMonth();
				if(currentMonth==0){
					calendarPanel.setYear(year--);
				}
				calendarPanel.setMonth((currentMonth-1)%12);
			}
		});		
		jbtNext.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				int currentMonth = calendarPanel.getMonth();
				if(currentMonth==11){
					calendarPanel.setYear(++year);
				}
				calendarPanel.setMonth((currentMonth+1)%12);				
			}
		});		
	}
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			
			public void run() {
				new CalendarApp().init();
			}
		});
	}
}
