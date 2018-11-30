package calenda;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class CalendarPanel extends JPanel {
	/**
	 * 声明数据变量
	 */
	private static final long serialVersionUID = 1L;
	private JLabel jlblHeader = new JLabel(" ",JLabel.CENTER);
	private JButton[] jlblDay = new JButton[49];
	private Calendar calendar = new GregorianCalendar();
	private int year0 = calendar.get(Calendar.YEAR);
	private int month0 = calendar.get(Calendar.MONTH);
	private int day0 = calendar.get(Calendar.DAY_OF_MONTH);
	private int year;
	private int month;
	private int day;
	private JPanel jpDays = new JPanel(new GridLayout(0,7));
	//三种字体
	Font font1 = new Font("宋体", Font.ITALIC, 20);//宋体 斜体 字体大小20pt
	Font font2 = new Font("宋体", Font.BOLD, 26);//宋体 加粗 20pt
	Font font3 = new Font("宋体", Font.BOLD, 30);
	//面板   构造方法
	public CalendarPanel(){
		//设置日历头部件及日期标签的背景色为白色
		jlblHeader.setBackground(Color.WHITE);
		jpDays.setBackground(Color.WHITE);
		//声明每个标签
		for (int i = 0; i < jlblDay.length; i++) {
			final int n = i;
			jlblDay[i] = new JButton();
			//标签的边框
			jlblDay[i].setBorder(new LineBorder(Color.LIGHT_GRAY, 1));
			//标签内容
			jlblDay[i].setHorizontalAlignment(JButton.RIGHT);
			jlblDay[i].setVerticalAlignment(JButton.TOP);
			jlblDay[i].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(jlblDay[n].getForeground().equals(Color.lightGray)){
						return;
					}
					new Thread(new thread_keepDiary(jlblHeader.getText()+"-"+jlblDay[n].getText())).start();
				}
			});
		}
		calendar = new GregorianCalendar();
		year = calendar.get(Calendar.YEAR);
		month = calendar.get(Calendar.MONTH);
		day = calendar.get(Calendar.DATE);
		//更新日历
		updateCalendar();
		showHeader();
		showDays();
		//添加到主面板
		this.setLayout(new BorderLayout());
		this.add(jlblHeader,BorderLayout.NORTH);
		this.add(jpDays,BorderLayout.CENTER);
	}
	private void showHeader() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM",Locale.CHINA);
		String header = sdf.format(calendar.getTime());
		jlblHeader.setText(header);
		jlblHeader.setForeground(Color.BLUE);
		jlblHeader.setFont(font3);
	}
	private void showDayNames(){
		DateFormatSymbols dfs = new DateFormatSymbols(Locale.CHINA);
		String dayNames[] = dfs.getWeekdays();
		for (int i = 0; i <7; i++) {
			jlblDay[i].setText(dayNames[i+1]);
			jlblDay[i].setForeground(Color.BLUE);
			jlblDay[i].setHorizontalAlignment(JLabel.CENTER);
			jlblDay[i].setFont(font2);
			jpDays.add(jlblDay[i]);
		}
	}
	public void showDays() {
		jpDays.removeAll();
		showDayNames();
		//星期几
		int startingDayOfMonth = calendar.get(Calendar.DAY_OF_WEEK);
		Calendar cloneCalendar = (Calendar)calendar.clone();
		cloneCalendar.add(Calendar.DATE, -1);
		int daysInPrecedingMonth = cloneCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		for(int i = 0;i < startingDayOfMonth-1;i++){
			final int n = daysInPrecedingMonth-startingDayOfMonth+2+i;
			jlblDay[i+7].setForeground(Color.LIGHT_GRAY);
			jlblDay[i+7].setHorizontalAlignment(JButton.CENTER);
			jlblDay[i+7].setText(n+"");
			jlblDay[i+7].setFont(font1);
			jlblDay[i+7].removeActionListener(null);
			jpDays.add(jlblDay[i+7]);
		}
		int daysInCurrentMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		for (int i = 0; i < daysInCurrentMonth; i++) {
			if(i==day0-1&&year==year0&&month==month0){
				jlblDay[i-1+startingDayOfMonth+7].setForeground(Color.red);
				jlblDay[i-1+startingDayOfMonth+7].setHorizontalAlignment(JButton.CENTER);
				jlblDay[i-1+startingDayOfMonth+7].setText(i+1+"");
				jlblDay[i-1+startingDayOfMonth+7].setFont(font2);
				jlblDay[i-1+startingDayOfMonth+7].removeActionListener(null);
				jpDays.add(jlblDay[i-1+startingDayOfMonth+7]);
			}else{
				jlblDay[i-1+startingDayOfMonth+7].setForeground(Color.DARK_GRAY);
				jlblDay[i-1+startingDayOfMonth+7].setHorizontalAlignment(JButton.CENTER);
				jlblDay[i-1+startingDayOfMonth+7].setText(i+1+"");
				jlblDay[i-1+startingDayOfMonth+7].setFont(font1);
				jlblDay[i-1+startingDayOfMonth+7].removeActionListener(null);
				jpDays.add(jlblDay[i-1+startingDayOfMonth+7]);
			}
		}
		int j = 1;
		for (int i = daysInCurrentMonth-1+startingDayOfMonth+7; i%7!=0; i++) {
			jlblDay[i].setForeground(Color.LIGHT_GRAY);
			jlblDay[i].setHorizontalAlignment(JButton.CENTER);
			jlblDay[i].setText(j++ +"");
			jlblDay[i].setFont(font1);	
			jlblDay[i].removeActionListener(null);
			jpDays.add(jlblDay[i]);
		}
		jpDays.repaint();
	}
	public void updateCalendar() {
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.DATE, 1);
		year = calendar.get(Calendar.YEAR);
		month = calendar.get(Calendar.MONTH);
		day = calendar.get(Calendar.DATE);
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
		updateCalendar();
		showHeader();
		showDays();
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
		updateCalendar();
		showHeader();
		showDays();
	}
	
}
