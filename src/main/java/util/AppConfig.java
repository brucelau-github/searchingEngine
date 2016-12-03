package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class AppConfig extends Properties {
	static AppConfig appConfig;
	private AppConfig (){
		super();
	}
	public static AppConfig newInstance(){
		try {
			if(appConfig == null) {
			appConfig = new AppConfig();
			System.getProperty("user.dir");
			FileInputStream in = new FileInputStream("app.conf");
			appConfig.load(in);
			in.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return appConfig;
	}
}
