package prod;

public class AbstractDrink {
	
//	private String name;
//	private int water;
//	private int milk;
//	private int cofee;
//	private int cacao;
//	private int shugar;
//	private int money;
	
	private String name;
	private Ingredient water;
	private Ingredient milk;
	private Ingredient coffee;
	private Ingredient cacao;
	private Ingredient shugar;
	private Ingredient money;
	
	public AbstractDrink(String name, int water, int milk, int coffee, int cacao, int shugar, int money){
		this.name = name;
		this.water = new Ingredient(IngredientType.WATER, water);
		this.milk = new Ingredient(IngredientType.MILK, milk);
		this.coffee = new Ingredient(IngredientType.COFFEE, coffee);
		this.cacao = new Ingredient(IngredientType.CACAO, cacao);
		this.shugar = new Ingredient(IngredientType.SHUGAR, shugar);
		this.money = new Ingredient(IngredientType.MONEY, money);
	}
	
	public void makeDrink(){
		System.out.println(name + " was made! ");
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getWater() {
		return water.getValue();
	}

	public int getMilk() {
		return milk.getValue();
	}

	public int getCoffee() {
		return coffee.getValue();
	}

	public int getCacao() {
		return cacao.getValue();
	}

	public int getShugar() {
		return shugar.getValue();
	}

	public int getMoney() {
		return money.getValue();
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) return true;
        if (!(o instanceof AbstractDrink)) {
            return false;
        }
        AbstractDrink aDrink = (AbstractDrink) o;
        return aDrink.name.equals(name) &&
        		aDrink.water == water &&
        		aDrink.milk == milk &&
        		aDrink.coffee == coffee &&
        		aDrink.cacao == cacao &&
        		aDrink.shugar == shugar &&
        		aDrink.money == money;
	}
	

	@Override
	public int hashCode() {
		int result = 31;
        result = 31 * result + name.hashCode();
        result = 31 * result + water.hashCode();
        result = 31 * result + milk.hashCode();
        result = 31 * result + coffee.hashCode();
        result = 31 * result + cacao.hashCode();
        result = 31 * result + shugar.hashCode();
        result = 31 * result + money.hashCode();
        return result;
	}
}
