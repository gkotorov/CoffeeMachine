package prod;

public class Ingredient {
	private IngredientType ingridient;
	private int value;
	
	public Ingredient(IngredientType ingridient, int value){
		this.ingridient = ingridient;
		this.value = value;
	}
	
	public IngredientType getIngredientType(){
		return ingridient;
	}
	
	public int getValue(){
		return value;
	}
	
}
