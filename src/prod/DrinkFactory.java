package prod;

import db.DrinkDao;
import db.DrinkDaoImpl;
import db.MachineStorageDao;
import db.MachineStorageDaoImp;

public class DrinkFactory {
	public AbstractDrink getDrink(DrinkType drink, int shugar){

		DrinkDao drDao = new DrinkDaoImpl();
		AbstractDrink aDrink = drDao.getAbstractDrink(drink);
		
		switch (drink) {
		case ESPRESSO:
			return (Espresso)aDrink;
		case CAPPUCHINO:
			
			MachineStorageDao msDao = new MachineStorageDaoImp();
			
			msDao.getStorage();
			
			return (Cappucchino)aDrink;
		case AMERICANO:
			
			MachineStorageDao msDao1 = new MachineStorageDaoImp();
			
			msDao1.updateMachineStorage(MachineStorage.getInstance());
			
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
