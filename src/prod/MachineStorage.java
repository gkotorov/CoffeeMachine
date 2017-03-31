package prod;

import java.util.EnumMap;
import java.util.Map;

public class MachineStorage {
	
	Map<IngredientType, Integer> ingred = new EnumMap<IngredientType, Integer>(IngredientType.class);
	
	private static MachineStorage machineStorage = new MachineStorage();
	private MachineStorage(){
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
	public void setWater(int ingValue) throws NotEnoughResourcesException{
		setResourcese(IngredientType.WATER, ingValue);
	}
	public void setMilk(int ingValue) throws NotEnoughResourcesException{
		setResourcese(IngredientType.MILK, ingValue);
	}
	public void setCoffee(int ingValue) throws NotEnoughResourcesException{
		setResourcese(IngredientType.COFFEE, ingValue);
	}
	public void setCacao(int ingValue) throws NotEnoughResourcesException{
		setResourcese(IngredientType.CACAO, ingValue);
	}
	public void setShugar(int ingValue) throws NotEnoughResourcesException{
		setResourcese(IngredientType.SHUGAR, ingValue);
	}
	public void setMoney(int ingValue) throws NotEnoughResourcesException{
		setResourcese(IngredientType.MONEY, ingValue);
	}
	
	private void setResourcese(IngredientType ing, int drinkIngr) throws NotEnoughResourcesException{
		int momentValue = ingred.get(ing);
		int newValue = momentValue - drinkIngr;
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
