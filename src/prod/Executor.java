package prod;

import java.util.Scanner;

public class Executor {
	static Machine coffeeMachine;
	
	static Scanner scan = new Scanner(System.in);

	public static void showMenu() {
		int swValue = 0;
//		Scanner scan = new Scanner(System.in);
		// Display menu graphics
		System.out.println("============================");
		System.out.println("|       COFFEE MACHINE     |");
		System.out.println("============================");
		System.out.println("|     11.- SHUGAR: " + coffeeMachine.getShugar() + " 22.+  |");
		System.out.println("============================");
		System.out.println("| Options:                 |");
		System.out.println("|        1. ESPRESSO       |");
		System.out.println("|        2. CAPPUCHINO     |");
		System.out.println("|        3. AMERICANO      |");
		System.out.println("|        4. LATTE          |");
		System.out.println("|        5. MOCHACHINO     |");
		System.out.println("|        6. CHOCOLATE      |");
		System.out.println("|        8. MULTIPLE       |");
		System.out.println("============================");
		System.out.println("|        20. FILL_MACHINE  |");
		System.out.println("|        55. STATISTIC     |");
		System.out.println("|        0. Exit           |");
		System.out.println("============================");
		try {
				swValue = Integer.parseInt(scan.nextLine());
		} catch (NumberFormatException  e) {
			// invalid code
			swValue = 515;
		}
		System.out.println("Option " + swValue + " selected");
		switch (swValue) {
		case 11:
			coffeeMachine.removeShugar();
			break;
		case 22:
			coffeeMachine.addShugar();
			break;
		case 1:
			coffeeMachine.makeEspresso();
			break;
		case 2:
			coffeeMachine.makeCappuchino();
			break;
		case 3:
			coffeeMachine.makeAmericano();
			break;
		case 4:
			coffeeMachine.makeLatte();
			break;
		case 5:
			coffeeMachine.makeMochachino();
			break;
		case 6:
			coffeeMachine.makeChocolate();
			break;
		case 8:
			makeManyDrinks();
			break;
		case 20:
			coffeeMachine.fillMachine();
			break;
		case 55:
			System.out.println("Statistic");
			coffeeMachine.showStatistic();
			break;
		case 0:
			System.out.println("Exit selected");
			break;
		default:
			System.out.println("Invalid selection");
			System.out.println("Please try again");
			break;
		}
		showMenu();
	}
	
	public static void makeManyDrinks(){
		System.out.println("Please set your order");
		System.out.println("Example: 4 times CAPPUCHINO and 2 times LATTE");
		System.out.println("Example: 2,2,2,4,4");
		
		String order = scan.nextLine();
		System.out.println("Order is " + order);
		coffeeMachine.makeManyDrinks(order);
	}
	
	public static void main(String[] args) {
		coffeeMachine = Machine.getInstance();

		showMenu();
	}

}
