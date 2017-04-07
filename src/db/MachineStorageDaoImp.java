package db;

import java.sql.SQLException;
import java.util.EnumMap;
import java.util.Map;

import prod.IngredientType;
import prod.MachineStorage;

public class MachineStorageDaoImp implements MachineStorageDao {

	MachineStorage ms = MachineStorage.getInstance();

	@Override
	public void getStorage() {

		String sqlQuery = "Select * from `coffee_machine`.`machine_storage_view`";

		DbManager.init();
		java.sql.ResultSet rs = DbManager.getResultSet(sqlQuery);

		Map<IngredientType, Integer> ingred = new EnumMap<IngredientType, Integer>(
				IngredientType.class);

		String name = "";
		int ingr_weight = 0;
		try {
			while (rs.next()) {
				name = rs.getString("name");
				ingr_weight = rs.getInt("ingr_weight");

				System.out.print("name: " + name);
				System.out.println(", ingr_weight: " + ingr_weight);

				for (IngredientType ingr : IngredientType.values()) {
					if (ingr.toString().equalsIgnoreCase(name)) {
						ingred.put(ingr, ingr_weight);
					}
				}
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		for (IngredientType key : ingred.keySet()) {
			System.out.println(key.toString() + " :=: " + ingred.get(key));
		}

		DbManager.close();

		ms.setAll(ingred);
	}

	@Override
	public void updateMachineStorage(MachineStorage storage) {

		DbManager.init();
		
		String sqlQuery;
		Map<IngredientType, Integer> ingred = storage.getAll();
		for (IngredientType key : ingred.keySet()) {
			sqlQuery = "UPDATE machine_storage ms, ingredient ing SET ms.ingr_weight = " + ingred.get(key) + " WHERE ms.ingr = ing.id and ing.name = '" + key + "';";
			DbManager.update(sqlQuery);

		}

		DbManager.close();
	}

}
