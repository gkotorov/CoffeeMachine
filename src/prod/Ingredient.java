package prod;

public class Ingredient {
	private IngredientType ingridientType;
	private int value;
	
	public Ingredient(IngredientType ingridientType, int value){
		this.ingridientType = ingridientType;
		this.value = value;
	}
	
	public IngredientType getIngredientType(){
		return ingridientType;
	}
	
	public int getValue(){
		return value;
	}

	public void setIngridient(IngredientType ingridientType) {
		this.ingridientType = ingridientType;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
}
