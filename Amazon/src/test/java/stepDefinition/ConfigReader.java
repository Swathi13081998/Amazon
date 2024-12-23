package stepDefinition;
import java.io.FileInputStream;
	import java.io.IOException;
	import java.util.Properties;

	public class ConfigReader {

	    private static Properties properties;

	    static {
	    	try (FileInputStream file = new FileInputStream("C:/Users/ranjith/eclipse-workspace/Amazon/src/test/resources/config/config.properties")) {
	            properties = new Properties();
	            properties.load(file);
	        } catch (IOException e) {
	            e.printStackTrace();
	            throw new RuntimeException("Failed to load config.properties file");
	        }
	    }

	    public static String getProperty1(String email) {
	        return properties.getProperty(email);
	    }
	    
	    public static String getProperty(String password) {
	        return properties.getProperty(password);
	    }
	}



