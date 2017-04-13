package db;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MachineDB {
	
	private static Connection conn = null;
	private static PreparedStatement stmt = null;
	private static ResultSet rs = null;

	private static void initConn() {
		try {
			Class.forName(ConstsDB.JDBC_DRIVER);
			conn = DriverManager.getConnection(ConstsDB.DB_URL, ConstsDB.USER, ConstsDB.PASS);
			conn.setAutoCommit(false);
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void close() {
		try {
			rs.close();
			if (stmt != null) {
				stmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
		
	public static void initDB() {
		createDB();
		
		initConn();
		try {
//			use DB
			conn = DriverManager.getConnection(ConstsDB.DB_URL, ConstsDB.USER, ConstsDB.PASS);
			
//			create table: ingredient
			DatabaseMetaData dbm = conn.getMetaData();
			ResultSet tables = dbm.getTables(null, null, "ingredient", null);
			if (!tables.next()) {
				stmt = conn.prepareStatement(ConstsDB.CREATE_TABLE_INGREDIENT);
				stmt.execute();
			}
			

//			create table: drinkType
			tables = dbm.getTables(null, null, "drinkType", null);
			if (!tables.next()) {
				stmt = conn.prepareStatement(ConstsDB.CREATE_TABLE_DRINKTYPE);
				stmt.execute();
			}

//			create table: drink
			tables = dbm.getTables(null, null, "drink", null);
			if (!tables.next()) {
				stmt = conn.prepareStatement(ConstsDB.CREATE_TABLE_DRINK);
				stmt.execute();
			}

//			create table: chronology
			tables = dbm.getTables(null, null, "chronology", null);
			if (!tables.next()) {
				stmt = conn.prepareStatement(ConstsDB.CREATE_TABLE_CHRONOLOGY);
				stmt.execute();
			}

//			create table: storage
			tables = dbm.getTables(null, null, "storage", null);
			if (!tables.next()) {
				stmt = conn.prepareStatement(ConstsDB.CREATE_TABLE_STORAGE);
				stmt.execute();
			}

//			create view: drink_view
			tables = dbm.getTables(null, null, "drink_view", null);
			if (!tables.next()) {
				stmt = conn.prepareStatement(ConstsDB.CREATE_VIEW_DRINK);
				stmt.execute();
			}
			
//			create view: machine_storage_view
			tables = dbm.getTables(null, null, "machine_storage_view", null);
			if (!tables.next()) {
				stmt = conn.prepareStatement(ConstsDB.CREATE_VIEW_STORAGE);
				stmt.execute();
			}

//			fill tables
//			fillTableIngridient();
			
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private static void createDB(){
		try {
			Class.forName(ConstsDB.JDBC_DRIVER);
			conn = DriverManager.getConnection(ConstsDB.DBServer_URL, ConstsDB.USER, ConstsDB.PASS);
			
			rs = conn.getMetaData().getCatalogs();
			while (rs.next()) {
				// Get the database name, which is at position 1
				String databaseName = rs.getString(1);
				System.out.println(databaseName);
				if (ConstsDB.DB_NAME.equalsIgnoreCase(databaseName)) {
					System.out.println("close()");
					 close();
					 return;
				}
			}
//			create DB
			stmt = conn.prepareStatement(ConstsDB.CREATE_SCHEMA_COFFEE_MACHINE);
//			stmt.setString(1, dbName);
			stmt.execute();
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void fillTableIngridient() throws SQLException{
//			INSERT statement
//			INSERT into ingredient
			conn.setAutoCommit(false);
			stmt = conn.prepareStatement(ConstsDB.FILL_INGREDIENT);
			stmt.setString( 1, "water" );
			stmt.addBatch();
			stmt.setString( 1, "milk" );
			stmt.addBatch();
			stmt.setString( 1, "coffee" );
			stmt.addBatch();
			stmt.setString( 1, "cacao" );
			stmt.addBatch();
			stmt.setString( 1, "shugar" );
			stmt.addBatch();
			stmt.setString( 1, "money" );
			stmt.addBatch();
			stmt.executeBatch();
			
//			INSERT into ingredient
			stmt = conn.prepareStatement(ConstsDB.FILL_DRINKTYPE);
			stmt.setString( 1, "espresso" );
			stmt.addBatch();
			stmt.setString( 1, "cappuchino" );
			stmt.addBatch();
			stmt.setString( 1, "americano" );
			stmt.addBatch();
			stmt.setString( 1, "latte" );
			stmt.addBatch();
			stmt.setString( 1, "mochachino" );
			stmt.addBatch();
			stmt.setString( 1, "chocolate" );
			stmt.addBatch();
			stmt.executeBatch();
			
//			INSERT into drink
			conn.setAutoCommit(false);
			stmt = conn.prepareStatement(ConstsDB.FILL_DRINK);
			stmt.setInt(1, 1);
			stmt.setInt( 2, 1);
			stmt.setInt( 3, 50);
			stmt.addBatch();
			stmt.setInt(1, 1);
			stmt.setInt( 2, 2);
			stmt.setInt( 3, 0);
			stmt.addBatch();
			stmt.setInt(1, 1);
			stmt.setInt( 2, 3);
			stmt.setInt( 3, 5);
			stmt.addBatch();
			stmt.setInt(1, 1);
			stmt.setInt( 2, 4);
			stmt.setInt( 3, 0);
			stmt.addBatch();
			stmt.setInt(1, 1);
			stmt.setInt( 2, 5);
			stmt.setInt( 3, 0);
			stmt.addBatch();
			stmt.setInt(1, 1);
			stmt.setInt( 2, 6);
			stmt.setInt( 3, 80);
			stmt.addBatch();
			
			stmt.setInt(1, 2);
			stmt.setInt( 2, 1);
			stmt.setInt( 3, 0);
			stmt.addBatch();
			stmt.setInt(1, 2);
			stmt.setInt( 2, 2);
			stmt.setInt( 3, 100);
			stmt.addBatch();
			stmt.setInt(1, 2);
			stmt.setInt( 2, 3);
			stmt.setInt( 3, 0);
			stmt.addBatch();
			stmt.setInt(1, 2);
			stmt.setInt( 2, 4);
			stmt.setInt( 3, 5);
			stmt.addBatch();
			stmt.setInt(1, 2);
			stmt.setInt( 2, 5);
			stmt.setInt( 3, 0);
			stmt.addBatch();
			stmt.setInt(1, 2);
			stmt.setInt( 2, 6);
			stmt.setInt( 3, 120);
			stmt.addBatch();
			
			stmt.setInt(1, 3);
			stmt.setInt( 2, 1);
			stmt.setInt( 3, 50);
			stmt.addBatch();
			stmt.setInt(1, 3);
			stmt.setInt( 2, 2);
			stmt.setInt( 3, 20);
			stmt.addBatch();
			stmt.setInt(1, 3);
			stmt.setInt( 2, 3);
			stmt.setInt( 3, 5);
			stmt.addBatch();
			stmt.setInt(1, 3);
			stmt.setInt( 2, 4);
			stmt.setInt( 3, 0);
			stmt.addBatch();
			stmt.setInt(1, 3);
			stmt.setInt( 2, 5);
			stmt.setInt( 3, 0);
			stmt.addBatch();
			stmt.setInt(1, 3);
			stmt.setInt( 2, 6);
			stmt.setInt( 3, 100);
			stmt.addBatch();
			
			stmt.setInt(1, 4);
			stmt.setInt( 2, 1);
			stmt.setInt( 3, 0);
			stmt.addBatch();
			stmt.setInt(1, 4);
			stmt.setInt( 2, 2);
			stmt.setInt( 3, 120);
			stmt.addBatch();
			stmt.setInt(1, 4);
			stmt.setInt( 2, 3);
			stmt.setInt( 3, 0);
			stmt.addBatch();
			stmt.setInt(1, 4);
			stmt.setInt( 2, 4);
			stmt.setInt( 3, 30);
			stmt.addBatch();
			stmt.setInt(1, 4);
			stmt.setInt( 2, 5);
			stmt.setInt( 3, 0);
			stmt.addBatch();
			stmt.setInt(1, 4);
			stmt.setInt( 2, 6);
			stmt.setInt( 3, 120);
			stmt.addBatch();
			
			stmt.setInt(1, 5);
			stmt.setInt( 2, 1);
			stmt.setInt( 3, 50);
			stmt.addBatch();
			stmt.setInt(1, 5);
			stmt.setInt( 2, 2);
			stmt.setInt( 3, 50);
			stmt.addBatch();
			stmt.setInt(1, 5);
			stmt.setInt( 2, 3);
			stmt.setInt( 3, 5);
			stmt.addBatch();
			stmt.setInt(1, 5);
			stmt.setInt( 2, 4);
			stmt.setInt( 3, 5);
			stmt.addBatch();
			stmt.setInt(1, 5);
			stmt.setInt( 2, 5);
			stmt.setInt( 3, 0);
			stmt.addBatch();
			stmt.setInt(1, 5);
			stmt.setInt( 2, 6);
			stmt.setInt( 3, 120);
			stmt.addBatch();
			
			stmt.setInt(1, 6);
			stmt.setInt( 2, 1);
			stmt.setInt( 3, 20);
			stmt.addBatch();
			stmt.setInt(1, 6);
			stmt.setInt( 2, 2);
			stmt.setInt( 3, 50);
			stmt.addBatch();
			stmt.setInt(1, 6);
			stmt.setInt( 2, 3);
			stmt.setInt( 3, 5);
			stmt.addBatch();
			stmt.setInt(1, 6);
			stmt.setInt( 2, 4);
			stmt.setInt( 3, 5);
			stmt.addBatch();
			stmt.setInt(1, 6);
			stmt.setInt( 2, 5);
			stmt.setInt( 3, 0);
			stmt.addBatch();
			stmt.setInt(1, 6);
			stmt.setInt( 2, 6);
			stmt.setInt( 3, 100);
			stmt.addBatch();
			
			stmt.executeBatch();
			
//			INSERT into ingredient
			stmt = conn.prepareStatement(ConstsDB.FILL_STORAGE);
			stmt.setInt(1, 1);
			stmt.setInt( 2, 1000);
			stmt.addBatch();
			stmt.setInt(1, 2);
			stmt.setInt( 2, 1000);
			stmt.addBatch();
			stmt.setInt(1, 3);
			stmt.setInt( 2, 1000);
			stmt.addBatch();
			stmt.setInt(1, 4);
			stmt.setInt( 2, 1000);
			stmt.addBatch();
			stmt.setInt(1, 5);
			stmt.setInt( 2, 1000);
			stmt.addBatch();
			stmt.setInt(1, 6);
			stmt.setInt( 2, 1000);
			stmt.addBatch();
			stmt.executeBatch();
			
//			commit
			conn.commit();
			
		close();
	}

}
