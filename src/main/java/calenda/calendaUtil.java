package calenda;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class calendaUtil {

    public String getPath(){
        InputStream in = calendaUtil.class.getResourceAsStream("../config.properties");
        Properties pro = new Properties();
        try {
            pro.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pro.getProperty("PATH");
    }
}
