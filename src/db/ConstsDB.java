package db;

public final class ConstsDB {

	//Schema, table and view names
	static final String DB_NAME = "coffee_machine";
	static final String STORAGE_VIEW = "`" + DB_NAME
			+ "`.`machine_storage_view`";
	static final String TABLE_STORAGE = "`" + DB_NAME + "`.`machine_storage`";
	static final String TABLE_INGREDIENT = "`" + DB_NAME + "`.`ingredient`";
	static final String TABLE_DRINK = "`" + DB_NAME + "`.`drink`";
	static final String TABLE_CHRONOLOGY = "`" + DB_NAME + "`.`chronology`";
	static final String VIEW_DRINK = "`coffee_machine`.`drink_view`";
	static final String VIEW_STORAGE = "`coffee_machine`.`machine_storage_view`";
	static final String TABLE_DRINKTYPE = "`" + DB_NAME + "`.`drinkType`";

	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/" + DB_NAME;
	static final String DBServer_URL = "jdbc:mysql://localhost:3306/";

	// Database credentials
	static final String USER = "root";
	static final String PASS = "ss11";

	//Database create queries
//	create Schema  
	static final String CREATE_SCHEMA_COFFEE_MACHINE = "Create database " + DB_NAME;
	
//	INGREDIENT
	static final String CREATE_TABLE_INGREDIENT = "CREATE TABLE IF NOT EXISTS " + TABLE_INGREDIENT + " ( "
			+"`id` INT NOT NULL AUTO_INCREMENT, "
			+"`name` VARCHAR(45) NOT NULL, "
			+"PRIMARY KEY (`id`), "
			+"UNIQUE INDEX `name_UNIQUE` (`name` ASC)); ";
//	DRINKTYPE
	static final String CREATE_TABLE_DRINKTYPE = "CREATE TABLE IF NOT EXISTS " + TABLE_DRINKTYPE + " ( "
			+"`id` INT NOT NULL AUTO_INCREMENT, "
			+"`name` VARCHAR(45) NOT NULL, "
			+"PRIMARY KEY (`id`), "
			+"UNIQUE INDEX `name_UNIQUE` (`name` ASC)); ";
	
//	DRINK
	static final String CREATE_TABLE_DRINK = "CREATE TABLE IF NOT EXISTS " + TABLE_DRINK + " ( "
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
	
//	CHRONOLOGY
	static final String CREATE_TABLE_CHRONOLOGY = "CREATE TABLE IF NOT EXISTS " + TABLE_CHRONOLOGY + " ( "
			+ "`id` INT NOT NULL AUTO_INCREMENT, "
			+ "`drink` INT NOT NULL, "
			+ "`datetime` DATETIME NOT NULL, "
			+ "PRIMARY KEY (`id`), "
			+ " CONSTRAINT `FK_dr` "
			+ "FOREIGN KEY (`drink`) "
			+ "REFERENCES `coffee_machine`.`drink` (`id`) "
			+ "ON DELETE CASCADE "
			+ "ON UPDATE CASCADE); ";
	
//	STORAGE
	static final String CREATE_TABLE_STORAGE = "CREATE TABLE IF NOT EXISTS " + TABLE_STORAGE + " ( "
			+ "`id` INT NOT NULL AUTO_INCREMENT, "
			+ "`ingr` INT NOT NULL, "
			+ "`ingr_weight` INT NOT NULL, "
			+ "PRIMARY KEY (`id`), "
			+ "CONSTRAINT `FK_ingred` "
			+ "FOREIGN KEY (`ingr`) "
			+ "REFERENCES `coffee_machine`.`ingredient` (`id`) "
			+ "ON DELETE CASCADE "
			+ "ON UPDATE CASCADE); ";
	
//	VIEW_DRINK
	static final String CREATE_VIEW_DRINK = "CREATE OR REPLACE VIEW " + VIEW_DRINK + " AS " 
			+ "SELECT * " 
			+ "FROM drinktype " 
			+ "inner join " 
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
			
//	VIEW_STORAGE
	static final String CREATE_VIEW_STORAGE = "CREATE OR REPLACE VIEW " + VIEW_STORAGE + " AS "
			+ "SELECT ms.id, ing.name, ms.ingr_weight "
			+ "FROM machine_storage ms, ingredient ing "
			+ "WHERE ms.ingr = ing.id; ";
	
	
	//Database select and update queries
	static final String SELECT_STORAGE = "Select * from " + STORAGE_VIEW;
	static final String UPDATE_STORAGE = "UPDATE "
			+ TABLE_STORAGE
			+ " ms, "
			+ TABLE_INGREDIENT
			+ " ing SET ms.ingr_weight = ? WHERE ms.ingr = ing.id and ing.name = ?;";

	static final String SELECT_VIEW_DRINK = "Select * from " + VIEW_DRINK + " where name = ?";
	
//	Inserts
	static final String FILL_INGREDIENT = "INSERT INTO " + TABLE_INGREDIENT + " (`name`) VALUES (?)";
	static final String FILL_DRINKTYPE = "INSERT INTO " + TABLE_DRINKTYPE + " (`name`) VALUES (?);";
	static final String FILL_DRINK = "INSERT INTO " + TABLE_DRINK + " (`drinkType`, `ingr`, `ingr_weight`) VALUES (?, ?, ?);";
	static final String FILL_STORAGE = "INSERT INTO " + TABLE_STORAGE + " (`ingr`, `ingr_weight`) VALUES (?, ?);";
	
}
