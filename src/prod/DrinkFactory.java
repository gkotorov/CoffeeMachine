package prod;

import db.DrinkDao;
import db.DrinkDaoImpl;

public class DrinkFactory {
	public AbstractDrink getDrink(DrinkType drink, int shugar){

		DrinkDao drDao = new DrinkDaoImpl();
		AbstractDrink aDrink = drDao.getAbstractDrink(drink);
		
		switch (drink) {
		case ESPRESSO:
			return (Espresso)aDrink;
		case CAPPUCHINO:
			return (Cappucchino)aDrink;
		case AMERICANO:
			return (Americano)aDrink;
		case CHOCOLATE:
			return (Chocolate)aDrink;
		case LATTE:
			return (Latte)aDrink;
		case MOCHACHINO:
			return (Mochachino)aDrink;
		default:
			return null;
		}
	}
}
