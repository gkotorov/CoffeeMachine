package db;

import java.util.List;

import prod.AbstractDrink;
import prod.DrinkType;

public interface DrinkDao {
	public List<AbstractDrink> getAllAbstractDrinks();

	public AbstractDrink getAbstractDrink(DrinkType ingrType);
}
