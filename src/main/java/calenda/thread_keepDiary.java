package calenda;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class thread_keepDiary extends JFrame implements Runnable {
	private JFrame jf = this;
	private JTextArea jta = new JTextArea();
	private JButton jbtSave = new JButton("保存");
	private JLabel jlblTitle = new JLabel("主题");
	private JTextField jtfTitle = new JTextField(16);
	private String id;
	public thread_keepDiary (String time){
		JPanel jpTitle = new JPanel();
		jpTitle.setLayout(new BorderLayout());
		jpTitle.add(jlblTitle,BorderLayout.WEST);
		jpTitle.add(jtfTitle,BorderLayout.CENTER);
		jta.setLineWrap(true);
		jta.setWrapStyleWord(true);
		JScrollPane jsp = new JScrollPane(jta);
		jsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		this.id = time;
		//保存按钮
		jbtSave.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				String theme = jtfTitle.getText().trim();
				String content = jta.getText();
				Diary d_today = new Diary(id,theme,content);
				if(theme.length()==0||content.length()==0){
					return;
				}
				try {
					d_today.write();
					JOptionPane.showMessageDialog(null, "保存成功！");
					jf.dispose();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		this.setTitle("记录"+id);
		this.add(jpTitle,BorderLayout.NORTH);
		this.add(jsp,BorderLayout.CENTER);
		this.add(jbtSave,BorderLayout.SOUTH);
		this.setSize(500,500);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	public void run() {
		this.setVisible(true);
	}

}
