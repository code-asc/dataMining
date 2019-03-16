package tools;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * This class is used to retrive all the file related infomation
 * from the property file.
 * @author sandeepchowdaryannabathuni
 *
 */
public class Config {
	
	private static String CONFIG = "src/config.properties";
	
	
	public static String getProperty(String key) throws IOException {
		String property = null;
		
		FileInputStream file = new FileInputStream(CONFIG);
		Properties prop = new Properties();
		
		prop.load(file);
		
		property = prop.getProperty(key);
		
		return property;
	}
}
