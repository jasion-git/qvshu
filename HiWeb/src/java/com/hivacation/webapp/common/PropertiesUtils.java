package com.hivacation.webapp.common;

import java.io.IOException;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;

public class PropertiesUtils {
	
	public static String getString(String key) {
		return prop.getProperty(key);
	}
	
	public static Integer getInteger(String key) {
		return Integer.parseInt(prop.getProperty(key));
	}
	
	private static Properties prop = new Properties();
	
	static {
		try {
			prop.load(new ClassPathResource("configure.properties").getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
