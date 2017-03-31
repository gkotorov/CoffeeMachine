package prod;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class Machine {
//	Singleton Pattern
	private static Machine coffeeMachine = new Machine();
	private Machine(){};
	public static Machine getInstance(){
		return coffeeMachine;
	}
	private int shugar=2;
	final static int MAXSHUGAR=4;
	
//	notifikation
	final static int WATER = 900;
	final static int MILK = 900;
	final static int COFFEE = 990;
	final static int CACAO = 950;
	final static int SHUGAR = 990;
	final static int MONEY = 100;
	
	private static MachineStorage machineStorage = MachineStorage.getInstance();
	private static DrinkFactory df = new DrinkFactory();
	private static AbstractDrink drink;
	
	public void showMessage(){
		System.out.println("CoffeeMashine show message");
	}
	
	public int getShugar(){
		return shugar;
	}
	public void addShugar(){
		if(shugar<MAXSHUGAR){
			shugar++;
		}
	}
	public void removeShugar(){
		if(shugar>0){
			shugar--;
		}
	}
	public void makeEspresso(){
		makeDrink(Drink.ESPRESSO);
	}
	public void makeCappuchino(){
		makeDrink(Drink.CAPPUCHINO);
	}
	public void makeAmericano(){
		makeDrink(Drink.AMERICANO);
	}
	public void makeLatte(){
		makeDrink(Drink.LATTE);
	}
	public void makeMochachino(){
		makeDrink(Drink.MOCHACHINO);
	}
	public void makeChocolate(){
		makeDrink(Drink.CHOCOLATE);
	}
	
	public void makeManyDrinks(String ord){
		Espresso espresso = new Espresso(shugar);
		Cappucchino cappucchino = new Cappucchino(shugar);
		Americano americano = new Americano(shugar);
		Latte latte = new Latte(shugar);
		Mochachino mochachino = new Mochachino(shugar);
		Chocolate chocolate = new Chocolate(shugar);

		Map<AbstractDrink, Integer> drinksOrder = new HashMap<AbstractDrink, Integer>();
		
		String[] order = ord.split(",");
		AbstractDrink aDrink = null;
		int value = 0;
		for(String s : order){
			switch (s) {
			case "1":
				aDrink = espresso;
				break;
			case "2":
				aDrink = cappucchino;
				break;
			case "3":
				aDrink = americano;
				break;
			case "4":
				aDrink = latte;
				break;
			case "5":
				aDrink = mochachino;
				break;
			case "6":
				aDrink = chocolate;
				break;

			default:
				break;
			}
			if(drinksOrder.containsKey(aDrink)){
				value = drinksOrder.get(aDrink)+1;
				drinksOrder.put(aDrink, value);
			} else {
				drinksOrder.put(aDrink, 1);
			}
		}
		
		for(AbstractDrink drink : drinksOrder.keySet()){
			for(int i = 1; i <= drinksOrder.get(drink); i++){
				System.out.println("Now We make " + drink.getName() + " number: " + i);
				try {
					getResources(drink);
					drink.makeDrink();
				} catch (NotEnoughResourcesException e) {
					System.out.println(e.getMessage());
					System.out.println("Please try another option!");
					break;
				}
			}
		}
		checkStorage();
		showStatistic();
	}

	public void fillMachine(){
		System.out.println("Please set: WATER, MILK, COFFEE, CACAO, SHUGAR, MONEY");
		Map<IngredientType, Integer> ingred = new EnumMap<IngredientType, Integer>(IngredientType.class);
		for(IngredientType ing : machineStorage.getAll().keySet()){
			System.out.println("set " + ing.toString());
			ingred.put(ing, 100);
		}
		machineStorage.putResources(ingred);
	}
	
	public void showStatistic(){
		System.out.println("STORAGE:");
		Map<IngredientType, Integer> storage = machineStorage.getAll();
		for(IngredientType key : storage.keySet() ){
			System.out.println(key.toString() + " : " + storage.get(key));
		}
	}
	public void checkStorage(){
		Map<IngredientType, Integer> storage = machineStorage.getAll();
		Map<IngredientType, Integer> minIngr = minIngredients();
		
		boolean showMes = false;
		String mes = " Pleas fill: ";
		
		for(IngredientType ingr : storage.keySet()){
			if(storage.get(ingr) <= minIngr.get(ingr)){
				mes += ingr.toString() + ", ";
				showMes = true;
			}
		}
		
		if(showMes){
			System.out.println(mes);
		}else
			System.out.println("Storage is OK");
	}
	private Map<IngredientType, Integer> minIngredients(){
		Map<IngredientType, Integer> minIngr = new EnumMap<IngredientType, Integer>(IngredientType.class);
		minIngr.put(IngredientType.WATER, WATER);
		minIngr.put(IngredientType.MILK, MILK);
		minIngr.put(IngredientType.COFFEE, COFFEE);
		minIngr.put(IngredientType.CACAO, CACAO);
		minIngr.put(IngredientType.SHUGAR, SHUGAR);
		minIngr.put(IngredientType.MONEY, MONEY);
		
		return minIngr;
	}
	private void getResources(AbstractDrink aDrink) throws NotEnoughResourcesException{
		machineStorage.setWater(aDrink.getWater());
		machineStorage.setMilk(aDrink.getMilk());
		machineStorage.setCoffee(aDrink.getCofee());
		machineStorage.setCacao(aDrink.getCacao());
		machineStorage.setShugar(aDrink.getShugar());
		machineStorage.setMoney(aDrink.getMoney());
	}
	private void makeDrink(Drink d){
		drink = df.getDrink(d, shugar);
		try{
			getResources(drink);
			drink.makeDrink();
			checkStorage();
		} catch(NotEnoughResourcesException e){
			System.out.println(e.getMessage());
			System.out.println("Please try another option!");
		}
		showStatistic();
	}
	
	
}
