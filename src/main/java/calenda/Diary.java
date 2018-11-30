package calenda;

import java.io.*;
import javax.swing.JOptionPane;

public class Diary implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//私有变量
	private String fileName;
	private String theme;
	private String content;
	public static final String PATH = new calendaUtil().getPath();
	//构造方法	
	public Diary(String fileName, String theme, String content) {
		if(theme.length()==0){
			JOptionPane.showMessageDialog(null,"标题 不要忘了标题");
			return;
		}else if(content.length()==0){
			JOptionPane.showMessageDialog(null, "这里是内容");
			return;
		}else{
			File calendarDiaryFile = new File(PATH);
			if(!calendarDiaryFile.exists()){
				calendarDiaryFile.mkdirs();
			}
		}
		this.fileName = PATH+"/"+fileName+".dat";
		this.theme = theme;
		this.content = content;
	}
	
	//写
	public void write() throws IOException{
		File file = new File(fileName);
		FileOutputStream fos = new FileOutputStream(file);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(this);
		oos.close();
	}
	
	//读
	public static Diary read(String fileName) throws IOException, ClassNotFoundException{
		File file = new File(fileName);
		FileInputStream fis = new FileInputStream(file);
		ObjectInputStream ois = new ObjectInputStream(fis);
		Diary d = (Diary)ois.readObject();
		ois.close();
		return d;
	}	
	
	public String getTheme() {
		return theme;
	}
	public void setTheme(String theme) {
		this.theme = theme;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
}
