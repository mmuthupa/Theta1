package objectRepository;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * Date    06-October-2017
 */

public class LoadPropertySingleton 
{
	public static Properties configResourceBundle;
	static LoadPropertySingleton objectLoad=null;
	private final String propertyFilePath = System.getProperty("user.dir") + "//Configuration//Config.properties";
	BufferedReader reader;
	
	private LoadPropertySingleton()
	{
		
			try {
				reader = new BufferedReader(new FileReader(propertyFilePath));
				configResourceBundle = new Properties();
				try {
					configResourceBundle.load(reader);
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				throw new RuntimeException("Configuration.properties not found at " + propertyFilePath);
			}		
	}
	public static LoadPropertySingleton getInstance()
	{
		if(objectLoad == null)
		{
			synchronized(LoadPropertySingleton.class)
			{
				if(objectLoad == null)
					objectLoad=new LoadPropertySingleton();
			}
		}
		return objectLoad;
	}
}
