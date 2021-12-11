package yelpReviewDatabaseApp;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.fasterxml.jackson.databind.ObjectMapper;


public class DBConnection {
	
	private static String DATABASE = System.getenv("DB_NAME");
	private static String DB_USER = System.getenv("DB_USERNAME");
	private static String DB_PASSWORD = System.getenv("DB_PASSWORD");
	private Connection conn;
	private String url;
	
	public DBConnection(){
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		url = "jdbc:postgresql://localhost/" + DATABASE + "?user=" + DB_USER + "&password=" + DB_PASSWORD; 
	}
	
	public boolean open() {
		try {
			conn = DriverManager.getConnection(url);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean close() {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public ResultSet query(String query) {
		try {
			Statement statement = conn.createStatement();
			ResultSet result = statement.executeQuery(query);
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean prepareInitialDatabase() {
		assert conn != null;
		try {
			Statement statement = conn.createStatement();
			statement.executeUpdate(
					"CREATE TABLE IF NOT EXISTS business ("
					+ "business_id varchar(300) PRIMARY KEY, "
					+ "name varchar(300), "
					+ "address varchar(600), "
					+ "city varchar(75), "
					+ "state varchar(25), "
					+ "postal_code varchar(25), "
					+ "latitude decimal(10,2), "
					+ "longitude decimal(10, 2), "
					+ "stars decimal(10,2), "
					+ "review_count int, "
					+ "is_open varchar(300),"
					+ "keywords varchar(600),"
					+ "net_sentiment float8)");
		}
		catch (SQLException e) {
			System.out.println(e.getMessage());
			return false;
		}
		return true;
	}
	
	public boolean insertNetSentiment() {
		FileReader file = null;
		try {
			file = new FileReader("output text location");
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
  	  	BufferedReader reader = new BufferedReader(file);
  	  	// initialize and read in pos/neg words
  	  	String line;
  	  	try {
			while ((line = reader.readLine()) != null){
				String[] input = line.split(",");
				if(input.length == 2) {
					updateSentiment(input[0],input[1]);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	public boolean updateSentiment(String business_id, String net_sentiment) {
		try {
			Statement statement = conn.createStatement();
			String command = "UPDATE business SET net_sentiment = '" + net_sentiment
					+ "' WHERE business_id='" + business_id + "'";
			statement.executeUpdate(command);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean insert(Business business) {
		try {
			Statement statement = conn.createStatement();
			String command = "INSERT INTO business VALUES " + business.toString();
			statement.executeUpdate(command);
		} catch (SQLException e) {
			//e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean importData() {
		BufferedReader reader = null;
		try{
			FileInputStream fileInputStream = new FileInputStream("./src/data/yelp_academic_dataset_business.json");
			InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
			reader = new BufferedReader(inputStreamReader);
			String line = null;
			ExecutorService fixedThreadPool = Executors.newFixedThreadPool(100);
			while((line = reader.readLine()) != null){
				ImportThread t = new ImportThread(line);
				fixedThreadPool.execute(t);
			}
			reader.close();
		} catch (Exception e) {
			//e.printStackTrace();
			return false;
		} 
		return true;
	}
	
	class ImportThread implements Runnable {
		private String str;
		
		public ImportThread(String str) {
			this.str = str;
		}
	
		public void run() {
			try {
				ObjectMapper mapper = new ObjectMapper();
				Business business = mapper.readValue(str, Business.class);
				insert(business);
			}
			catch (Exception e) {
				//e.printStackTrace();
			} 
		}
	}
	
	
}
