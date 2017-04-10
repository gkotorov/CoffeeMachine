package db;

import java.sql.*;
import java.util.List;

import prod.AbstractDrink;
import prod.Americano;
import prod.Cappucchino;
import prod.Chocolate;
import prod.DrinkType;
import prod.Espresso;
import prod.Latte;
import prod.Mochachino;

public class DrinkDaoImpl implements DrinkDao {

	static final String drinkView = "`coffee_machine`.`drink_view`";

	static final String selectDrink = "Select * from " + drinkView + " where name = ?";
	
	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/coffee_machine";

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
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void close() {
		try {
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
			}
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
	}
	
	@Override
	public List<AbstractDrink> getAllAbstractDrinks() {
		return null;
	}

	@Override
	public AbstractDrink getAbstractDrink(DrinkType drinkType) {

		int id = 0;
		String name = "drink name";
		int water = 0;
		int milk = 0;
		int coffee = 0;
		int cacao = 0;
		int shugar = 0;
		int money = 0;
		
		init();

		try {
			stmt = conn.prepareStatement(selectDrink);
			stmt.setString(1, drinkType.toString());
			rs = stmt.executeQuery();
			
			while (rs.next()) {
				// Retrieve by column name
				id = rs.getInt("id");
				name = rs.getString("name");
				water = rs.getInt("water");
				milk = rs.getInt("milk");
				coffee = rs.getInt("coffee");
				cacao = rs.getInt("cacao");
				shugar = rs.getInt("shugar");
				money = rs.getInt("money");

				// Display values
				System.out.print("ID: " + id);
				System.out.print(", water: " + water);
				System.out.print(", milk: " + milk);
				System.out.print(", coffee: " + coffee);
				System.out.print(", cacao: " + cacao);
				System.out.print(", shugar: " + shugar);
				System.out.println(", money: " + money);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		close();

		if (money > 0) {
			money *= (-1);
		}

		switch (drinkType) {
		case ESPRESSO:
			return new Espresso(name, water, milk, coffee, cacao, shugar, money);
		case CAPPUCHINO:
			return new Cappucchino(name, water, milk, coffee, cacao, shugar,
					money);
		case AMERICANO:
			return new Americano(name, water, milk, coffee, cacao, shugar,
					money);
		case CHOCOLATE:
			return new Chocolate(name, water, milk, coffee, cacao, shugar,
					money);
		case LATTE:
			return new Latte(name, water, milk, coffee, cacao, shugar, money);
		case MOCHACHINO:
			return new Mochachino(name, water, milk, coffee, cacao, shugar,
					money);
		default:
			return null;
		}
	}

}
