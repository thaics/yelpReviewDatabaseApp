package yelpReviewDatabaseApp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.ResultSet;

public class QueryTool {
	
	// run this only when starting the database for the first time with no data ... 
	public static void prepareDB() {
		DBConnection conn = new DBConnection();
		System.out.println("Connection Started: " + conn.open());
		System.out.println("Database Initialized: " + conn.prepareInitialDatabase());
		System.out.println("Filling Database: " + conn.importData());
		conn.insertNetSentiment();
		conn.close();
	}
	
	
	public static void main(String[] args) {
		DBConnection conn = new DBConnection();
		System.out.println("Connection Started: " + conn.open());
		// use only when setting up DB for the first time!
		prepareDB();
		try {
			FileReader input = new FileReader("input ... found in ./data/input.txt");
			BufferedReader reader = new BufferedReader(input);
			Query query = new Query();
			query.setCity(reader.readLine());
			query.setState(reader.readLine());
			query.setKeywords(reader.readLine());
			query.setK(reader.readLine());
			System.out.println(query.buildQuery());
			ResultSet rs = conn.query(query.buildQuery());
			DBTablePrinter.printResultSet(rs);
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Connection Closed: " + conn.close());
	}
}
