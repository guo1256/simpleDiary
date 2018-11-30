package calenda;

import javax.swing.JLabel;

public class thread_mottoSparkle implements Runnable {
	private JLabel jlbl;
	  public thread_mottoSparkle(JLabel jlbl) {
	    this.jlbl=jlbl;
	  }
	  public void run() {
	    String content=jlbl.getText();
	    int L=content.length();
	    int index=0;
	    while(true){
	      jlbl.setText(content.substring(0, index));
	      try
	      {
	        Thread.sleep(500);
	      }
	      catch(Exception ex)
	      {
	        ex.printStackTrace();
	      }
	      index++;
	      if(index==(L+1))
	        index=0;
	    }
	  }
}
