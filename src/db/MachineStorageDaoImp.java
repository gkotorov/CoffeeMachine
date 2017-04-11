package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.EnumMap;
import java.util.Map;

import prod.IngredientType;
import prod.MachineStorage;

public class MachineStorageDaoImp implements MachineStorageDao {

	static final String dbName = "coffee_machine";
	static final String storageView = "`" + dbName + "`.`machine_storage_view`";
	static final String storageTable = "`" + dbName + "`.`machine_storage`";
	static final String ingredientTable = "`" + dbName + "`.`ingredient`";

	static final String selectStorage = "Select * from " + storageView;
	static final String updateStorage = "UPDATE "
			+ storageTable
			+ " ms, "
			+ ingredientTable
			+ " ing SET ms.ingr_weight = ? WHERE ms.ingr = ing.id and ing.name = ?;";

	MachineStorage ms = MachineStorage.getInstance();

	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/" + dbName;
	static final String DBServer_URL = "jdbc:mysql://localhost:3306/";

	// Database credentials
	static final String USER = "root";
	static final String PASS = "ss11";

	static Connection conn = null;
	static PreparedStatement stmt = null;

	static ResultSet rs = null;

	public static void init() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			conn.setAutoCommit(false);
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void close() {
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

	@Override
	public void getStorage() {
		Map<IngredientType, Integer> ingred = new EnumMap<IngredientType, Integer>(
				IngredientType.class);

		init();

		try {
			stmt = conn.prepareStatement(selectStorage);
			rs = stmt.executeQuery();

			String name = "";
			int ingr_weight = 0;
			while (rs.next()) {
				name = rs.getString("name");
				ingr_weight = rs.getInt("ingr_weight");

				for (IngredientType ingr : IngredientType.values()) {
					if (ingr.toString().equalsIgnoreCase(name)) {
						ingred.put(ingr, ingr_weight);
					}
				}
			}
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		close();

		ms.setAll(ingred);
	}

	@Override
	public void updateMachineStorage() {
		System.out.println("updateMachineStorage");
		init();

		try {
			Map<IngredientType, Integer> ingred = ms.getAll();
			stmt = conn.prepareStatement(updateStorage);
			for (IngredientType key : ingred.keySet()) {
				stmt.setInt(1, ingred.get(key));
				stmt.setString(2, key.toString());
				stmt.addBatch();
			}
			stmt.executeBatch();
			conn.commit();
		} catch (SQLException se) {
			se.printStackTrace();
		}
		close();
	}

	public static void initDB() {
		System.out.println("  initDB ");

		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DBServer_URL, USER, PASS);
			
			rs = conn.getMetaData().getCatalogs();
			while (rs.next()) {
				// Get the database name, which is at position 1
				String databaseName = rs.getString(1);
				System.out.println(databaseName);
				if (dbName.equalsIgnoreCase(databaseName)) {
					System.out.println("close()");
					 close();
					 return;
				}
			}
//			create DB
			stmt = conn.prepareStatement("Create database " + dbName);
//			stmt.setString(1, dbName);
			stmt.execute();

//			use DB
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			
//			create table: ingredient
			String sql1 = "CREATE TABLE `coffee_machine`.`ingredient` ( "
					+"`id` INT NOT NULL AUTO_INCREMENT, "
					+"`name` VARCHAR(45) NOT NULL, "
					+"PRIMARY KEY (`id`), "
					+"UNIQUE INDEX `name_UNIQUE` (`name` ASC)); ";
			stmt = conn.prepareStatement(sql1);
			stmt.execute();

//			create table: drinkType
			sql1 = "CREATE TABLE `coffee_machine`.`drinkType` ( "
					+"`id` INT NOT NULL AUTO_INCREMENT, "
					+"`name` VARCHAR(45) NOT NULL, "
					+"PRIMARY KEY (`id`), "
					+"UNIQUE INDEX `name_UNIQUE` (`name` ASC)); ";
			stmt = conn.prepareStatement(sql1);
			stmt.execute();

//			create table: drink
			sql1 = "CREATE TABLE `coffee_machine`.`drink` ( "
					+ "`id` INT NOT NULL AUTO_INCREMENT, "
					+ "`drinkType` INT NOT NULL, "
					+ "`ingr` INT NOT NULL, "
					+ "`ingr_weight` INT NOT NULL, "
					+ "PRIMARY KEY (`id`), "
					+ "INDEX `FK_drink_idx` (`drinkType` ASC), "
					+ "INDEX `FK_ingr_idx` (`ingr` ASC), "
					+ "CONSTRAINT `FK_drink` "
					+ "FOREIGN KEY (`drinkType`) "
					+ "REFERENCES `coffee_machine`.`drinktype` (`id`) "
					+ "ON DELETE CASCADE "
					+ "ON UPDATE CASCADE, "
					+ "CONSTRAINT `FK_ingr` "
					+ "FOREIGN KEY (`ingr`) "
					+ "REFERENCES `coffee_machine`.`ingredient` (`id`) "
					+ "ON DELETE CASCADE "
					+ "ON UPDATE CASCADE); ";
			stmt = conn.prepareStatement(sql1);
			stmt.execute();

//			create table: chronology
			sql1 = "CREATE TABLE `coffee_machine`.`chronology` ( "
					+ "`id` INT NOT NULL AUTO_INCREMENT, "
					+ "`drink` INT NOT NULL, "
					+ "`datetime` DATETIME NOT NULL, "
					+ "PRIMARY KEY (`id`), "
					+ " CONSTRAINT `FK_dr` "
					+ "FOREIGN KEY (`drink`) "
					+ "REFERENCES `coffee_machine`.`drink` (`id`) "
					+ "ON DELETE CASCADE "
					+ "ON UPDATE CASCADE); ";
			stmt = conn.prepareStatement(sql1);
			stmt.execute();

//			create table: machine_storage
			sql1 = "CREATE TABLE `coffee_machine`.`machine_storage` ( "
					+ "`id` INT NOT NULL AUTO_INCREMENT, "
					+ "`ingr` INT NOT NULL, "
					+ "`ingr_weight` INT NOT NULL, "
					+ "PRIMARY KEY (`id`), "
					+ "CONSTRAINT `FK_ingred` "
					+ "FOREIGN KEY (`ingr`) "
					+ "REFERENCES `coffee_machine`.`ingredient` (`id`) "
					+ "ON DELETE CASCADE "
					+ "ON UPDATE CASCADE); ";
			stmt = conn.prepareStatement(sql1);
			stmt.execute();

//			create view: drink_view
			sql1 = "CREATE VIEW `drink_view` AS " 
				+ "SELECT * " 
				+ "FROM drinktype " 
				+ "inner join  " 
				+ "(SELECT Water.drink, Water.water, Milk.milk, Coffee.coffee, Cacao.cacao, Shugar.shugar, Money.money " 
				+ "FROM (SELECT d.drinkType as drink, d.ingr_weight as water " 
				+ "FROM drink d, ingredient ing " 
				+ "WHERE d.ingr = ing.id and ing.name like 'water') AS Water " 
				+ "JOIN (SELECT d.drinkType as drink, d.ingr_weight as milk " 
				+ "FROM drink d, ingredient ing " 
				+ "WHERE d.ingr = ing.id and ing.name like 'milk') AS Milk " 
				+ "JOIN (SELECT d.drinkType as drink, d.ingr_weight as Coffee " 
				+ "FROM drink d, ingredient ing " 
				+ "WHERE d.ingr = ing.id and ing.name like 'coffee') AS Coffee " 
				+ "JOIN (SELECT d.drinkType as drink, d.ingr_weight as Cacao " 
				+ "FROM drink d, ingredient ing " 
				+ "WHERE d.ingr = ing.id and ing.name like 'cacao') AS Cacao " 
				+ "JOIN (SELECT d.drinkType as drink, d.ingr_weight as Shugar " 
				+ "FROM drink d, ingredient ing " 
				+ "WHERE d.ingr = ing.id and ing.name like 'shugar') AS Shugar " 
				+ "JOIN (SELECT d.drinkType as drink, d.ingr_weight as Money " 
				+ "FROM drink d, ingredient ing " 
				+ "WHERE d.ingr = ing.id and ing.name like 'money') AS Money " 
				+ "ON Water.drink = Milk.drink and Water.drink = Coffee.drink and Water.drink = Cacao.drink and Water.drink = Shugar.drink and Water.drink = Money.drink) AS INGRED " 
				+ "on INGRED.drink = drinkType.id ";
			stmt = conn.prepareStatement(sql1);
			stmt.execute();

//			create view: machine_storage_view
			sql1 = "CREATE VIEW `machine_storage_view` AS "
					+ "SELECT ms.id, ing.name, ms.ingr_weight "
					+ "FROM machine_storage ms, ingredient ing "
					+ "WHERE ms.ingr = ing.id; ";
			stmt = conn.prepareStatement(sql1);
			stmt.execute();
			
//			INSERT statement
//			INSERT into ingredient
			conn.setAutoCommit(false);
			sql1 = "INSERT INTO `coffee_machine`.`ingredient` (`name`) VALUES (?)";
			stmt = conn.prepareStatement(sql1);
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
			sql1 = "INSERT INTO `coffee_machine`.`drinkType` (`name`) VALUES (?);";
			stmt = conn.prepareStatement(sql1);
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
			sql1 = "INSERT INTO `coffee_machine`.`drink` (`drinkType`, `ingr`, `ingr_weight`) VALUES (?, ?, ?);";
			stmt = conn.prepareStatement(sql1);
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
			sql1 = "INSERT INTO `coffee_machine`.`machine_storage` (`ingr`, `ingr_weight`) VALUES (?, ?);";
			stmt = conn.prepareStatement(sql1);
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
			
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		close();
	}

}
