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

	DbManager a = new DbManager();

	@Override
	public List<AbstractDrink> getAllAbstractDrinks() {
		return null;
	}

	@Override
	public AbstractDrink getAbstractDrink(DrinkType drinkType) {

		String sqlQuery = "Select * from `coffee_machine`.`drink_view` where name = '"
				+ drinkType.toString() + "'";

		DbManager.init();
		java.sql.ResultSet rs = DbManager.getResultSet(sqlQuery);

		int id = 0;
		String name = "drink name";
		int water = 0;
		int milk = 0;
		int coffee = 0;
		int cacao = 0;
		int shugar = 0;
		int money = 0;

		try {
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

		DbManager.close();

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
