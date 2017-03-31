package prod;

public abstract class AbstractDrink {
	
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
	private Ingredient cofee;
	private Ingredient cacao;
	private Ingredient shugar;
	private Ingredient money;
	
	public AbstractDrink(String name, int water, int milk, int cofee, int cacao, int shugar, int money){
		this.name = name;
		this.water = new Ingredient(IngredientType.WATER, water);
		this.milk = new Ingredient(IngredientType.MILK, milk);
		this.cofee = new Ingredient(IngredientType.COFFEE, cofee);
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

	public int getCofee() {
		return cofee.getValue();
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
        		aDrink.cofee == cofee &&
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
        result = 31 * result + cofee.hashCode();
        result = 31 * result + cacao.hashCode();
        result = 31 * result + shugar.hashCode();
        result = 31 * result + money.hashCode();
        return result;
	}
}
