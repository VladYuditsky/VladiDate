package com.base;

	import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Logger;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

	public class PropertyController {

		public final static Logger log = Logger.getLogger(PropertyController.class.getName());
		// define json properties
		static String properties = "src/main/resources/properties.json";
		private static JSONParser parser = new JSONParser();
		
		// read json properties file
		private static JSONObject propertiesJson() {
			
			try {
				Object obj = parser.parse(new FileReader(properties));
				JSONObject propertiesJSON = (JSONObject) obj;
				return propertiesJSON;			
			} catch(Exception e) {
				log.severe("Properties file cannot be read or invalid json...");
				e.printStackTrace();
				return null;
			}
		}
		

		// PROPERTIES -------------------------------------------------------------
		public String getBrowserProperty() {
			JSONObject json = propertiesJson();
			return (String) json.get("Browser");
		}
		
		public static boolean getHeadlessProperty() {
			
			JSONObject json = propertiesJson();
			return (boolean) json.get("Headless");
		}
		
		public static String getUsernameProperty() {
			JSONObject json = propertiesJson();
			return (String) json.get("username");
		}
		
		public static String getPasswordProperty() {
			JSONObject json = propertiesJson();
			return (String) json.get("password");
		}
		
		public static String getOKCEmailProperty() {
			JSONObject json = propertiesJson();
			return (String) json.get("okc_email");
		}
		
		public static int getPOFMessages() {
			JSONObject json = propertiesJson();
			String num_str = (String) json.get("POFSwipes");
			
			int num = Integer.parseInt(num_str);
			return num;
		}
		
	public static ArrayList<String> getMessageToSend() {

		ArrayList<String> list = new ArrayList<String>();
		JSONObject json = propertiesJson();
		JSONArray array = (JSONArray) json.get("Messages");

		for (int i = 0; i < array.size(); i++) {
			list.add(array.get(i).toString());
		}
		
		return list;
	}
		
	
	// COMMAND LINE -------------------------------------------------------------
		// for command line runnable
		// browser is retrieved with 'browsers' command '-Dbrowsers=...'
		public String getMavenBrowserProperty() {
			java.io.InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("my.properties");
			java.util.Properties properties = new Properties();
			try {
				properties.load(inputStream);
				String browser;
				browser = properties.getProperty("browsers");
				return browser;
			} catch (IOException e) {
				log.warning("No set browser property");
				e.printStackTrace();
				return null;
			}
		}
		
		public String getMavenHeadlessProperty() {
			java.io.InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("my.properties");
			java.util.Properties properties = new Properties();
			try {
				properties.load(inputStream);
				String browser;
				browser = properties.getProperty("ghosts");
				return browser;
			} catch (IOException e) {
				log.warning("No set headless property");
				e.printStackTrace();
				return null;
			}
		}
		
		public String getMavenSwipesProperty() {
			java.io.InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("my.properties");
			java.util.Properties properties = new Properties();
			try {
				properties.load(inputStream);
				String swipes;
				swipes = properties.getProperty("swipes");
				return swipes;
			} catch (IOException e) {
				log.warning("No set swipes property");
				e.printStackTrace();
				return null;
			}
		}
		
		// SET JSON Properties --------------------------------------------
		@SuppressWarnings("unchecked")
		public void setJsonHeadlessProperty(String ghost) {
			
			boolean headless = false;
			// verify headless input is boolean
			try {
				headless = Boolean.parseBoolean(ghost);
				// update browser key of json
				JSONObject json = propertiesJson();
				json.put("Headless", headless);
				
				String formattedJson = json.toString();
				formattedJson = formattedJson.replace("{", "{\n\t");
				formattedJson = formattedJson.replaceAll(",", ",\n\t");
				formattedJson = formattedJson.replace("}", "\n}");
				
				PrintWriter writer;
				try {
					writer = new PrintWriter(properties);
					writer.print("");
					writer.print(formattedJson);
					writer.close();
					
				} catch (FileNotFoundException e) {
					log.warning("Cannot read properties.json file...");
				}
				
			} catch(Exception e) {
				log.warning("Cannot read properties.json file...");
			}
		}
		
		
		@SuppressWarnings("unchecked")
		public void setJsonBrowserProperty(String browser) {
			// update browser key of json
			JSONObject json = propertiesJson();
			json.put("Browser", browser);

			String formattedJson = json.toString();
			formattedJson = formattedJson.replace("{", "{\n\t");
			formattedJson = formattedJson.replaceAll(",", ",\n\t");
			formattedJson = formattedJson.replace("}", "\n}");
			
			PrintWriter writer;
			try {
				writer = new PrintWriter(properties);
				writer.print("");
				writer.print(formattedJson);
				writer.close();
				
			} catch (FileNotFoundException e) {
				log.warning("Cannot read properties.json file...");
			}
		}
		
		@SuppressWarnings("unchecked")
		public void setJsonSwipesProperty(String swipes) {
			// update browser key of json
			JSONObject json = propertiesJson();
			json.put("Swipes", swipes);

			String formattedJson = json.toString();
			formattedJson = formattedJson.replace("{", "{\n\t");
			formattedJson = formattedJson.replaceAll(",", ",\n\t");
			formattedJson = formattedJson.replace("}", "\n}");
			
			PrintWriter writer;
			try {
				writer = new PrintWriter(properties);
				writer.print("");
				writer.print(formattedJson);
				writer.close();
				
			} catch (FileNotFoundException e) {
				log.warning("Cannot read properties.json file...");
			}
		}
		
		// used to call each test beforeAll annotation to configure properties file and return the browser to run the test it
		public String loadProperties() {
			
			String browser, headless, swipes;
			PropertyController properties = new PropertyController();
			// if not invoked with mvn or is not valid browser
			browser = properties.getMavenBrowserProperty();
			if(browser.toLowerCase().equals("chrome") || browser.toLowerCase().equals("edge") || browser.toLowerCase().equals("firefox")) {
				properties.setJsonBrowserProperty(browser);	
			} else {
				if(!browser.equals("${browser}")) {
					log.warning("Browser argument must match 'Chrome', 'Firefox', 'Edge'... (Ex. -Dbrowser='Chrome')");						
				}
			}
			
			headless = properties.getMavenHeadlessProperty();
			if(headless.toLowerCase().equals("true") || headless.toLowerCase().equals("false")) {
				properties.setJsonHeadlessProperty(headless);
			} else {
				// if invoked via IDE
				if(!headless.equals("${ghost}")) {
					log.warning("Headless argument must match 'true', 'false'... (Ex. -Dghosts='true')");			
				}
			}
			
			swipes = properties.getMavenSwipesProperty();
			try {
				Integer.parseInt(swipes);
				properties.setJsonSwipesProperty(swipes);
			} catch(Exception e) {
				if(!swipes.equals("${swipe}")) {
					log.warning("Swipe argument must be a number");			
				}
			}
			
			// update browser
			browser = properties.getBrowserProperty();
			return browser;
		}
	}


