package prod;

import java.util.EnumMap;
import java.util.Map;

import db.MachineDB;

public class MachineStorage {
	
	Map<IngredientType, Integer> ingred = new EnumMap<IngredientType, Integer>(IngredientType.class);
	
//	private int water = 1000;
//	private int milk = 1000;
//	private int cofee = 1000;
//	private int cacao = 1000;
//	private int shugar = 1000;
//	private int money = 100;
//	int a = Ingredient.values().length;
	
	private static MachineStorage machineStorage = new MachineStorage();
	private MachineStorage(){
		MachineDB.initDB();
		ingred.put(IngredientType.WATER, 1000);
		ingred.put(IngredientType.MILK, 1000);
		ingred.put(IngredientType.COFFEE, 1000);
		ingred.put(IngredientType.CACAO, 1000);
		ingred.put(IngredientType.SHUGAR, 1000);
		ingred.put(IngredientType.MONEY, 1000);
	}
	public static MachineStorage getInstance(){
		return machineStorage;
	}
	
	public Map<IngredientType, Integer> getAll(){
		return ingred;
	}
	
	public void setAll(Map<IngredientType, Integer> ingred){
		this.ingred = ingred;
	}
	
//	sets and gets
	public void subtractWater(int ingValue) throws NotEnoughResourcesException{
		subsResourcese(IngredientType.WATER, ingValue);
	}
	public void subtractMilk(int ingValue) throws NotEnoughResourcesException{
		subsResourcese(IngredientType.MILK, ingValue);
	}
	public void subtractCoffee(int ingValue) throws NotEnoughResourcesException{
		subsResourcese(IngredientType.COFFEE, ingValue);
	}
	public void subtractCacao(int ingValue) throws NotEnoughResourcesException{
		subsResourcese(IngredientType.CACAO, ingValue);
	}
	public void subtractShugar(int ingValue) throws NotEnoughResourcesException{
		subsResourcese(IngredientType.SHUGAR, ingValue);
	}
	public void subtractMoney(int ingValue) throws NotEnoughResourcesException{
		subsResourcese(IngredientType.MONEY, ingValue);
	}
	
	private void subsResourcese(IngredientType ing, int drinkIngr) throws NotEnoughResourcesException{
		int momentValue = ingred.get(ing);
		int newValue = momentValue - drinkIngr;
		if(drinkIngr > momentValue){
			throw new NotEnoughResourcesException("Not enough resourceses");
		}
		ingred.put(ing, newValue);
	}
	
	public void fillWater(int ingValue) throws NotEnoughResourcesException{
		fillResourcese(IngredientType.WATER, ingValue);
	}
	public void fillMilk(int ingValue) throws NotEnoughResourcesException{
		fillResourcese(IngredientType.MILK, ingValue);
	}
	public void fillCoffee(int ingValue) throws NotEnoughResourcesException{
		fillResourcese(IngredientType.COFFEE, ingValue);
	}
	public void fillCacao(int ingValue) throws NotEnoughResourcesException{
		fillResourcese(IngredientType.CACAO, ingValue);
	}
	public void fillShugar(int ingValue) throws NotEnoughResourcesException{
		fillResourcese(IngredientType.SHUGAR, ingValue);
	}
	public void fillMoney(int ingValue) throws NotEnoughResourcesException{
		fillResourcese(IngredientType.MONEY, ingValue);
	}
	
	private void fillResourcese(IngredientType ing, int drinkIngr) throws NotEnoughResourcesException{
		int momentValue = ingred.get(ing);
		int newValue = momentValue + drinkIngr;
		if(drinkIngr > momentValue){
			throw new NotEnoughResourcesException("Not enough resourceses");
		}
		ingred.put(ing, newValue);
	}
	
	public void putResources(Map<IngredientType, Integer> resourses){
		for(IngredientType key : resourses.keySet() ){
			ingred.put(key, (ingred.get(key) + resourses.get(key)));
		}
	}
	
}
