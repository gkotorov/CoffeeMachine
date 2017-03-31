package prod;

public class DrinkFactory {
	public AbstractDrink getDrink(Drink drink, int shugar){
		switch (drink) {
		case ESPRESSO:
			return new Espresso(shugar);
		case CAPPUCHINO:
			return new Cappucchino(shugar);
		case AMERICANO:
			return new Americano(shugar);
		case CHOCOLATE:
			return new Chocolate(shugar);
		case LATTE:
			return new Latte(shugar);
		case MOCHACHINO:
			return new Mochachino(shugar);
		default:
			return null;
//			return new Espresso(shugar);
		}
	}
}
