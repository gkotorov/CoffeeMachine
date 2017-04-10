package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.EnumMap;
import java.util.Map;

import prod.IngredientType;
import prod.MachineStorage;

public class MachineStorageDaoImp implements MachineStorageDao {

	static final String storageView = "`coffee_machine`.`machine_storage_view`";
	static final String storageTable = "`coffee_machine`.`machine_storage`";
	static final String ingredientTable = "`coffee_machine`.`ingredient`";

	static final String selectStorage = "Select * from " + storageView;
	static final String updateStorage = "UPDATE "
			+ storageTable
			+ " ms, "
			+ ingredientTable
			+ " ing SET ms.ingr_weight = ? WHERE ms.ingr = ing.id and ing.name = ?;";

	MachineStorage ms = MachineStorage.getInstance();

	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/coffee_machine";

	// Database credentials
	static final String USER = "root";
	static final String PASS = "ss11";

	static Connection conn = null;
	static PreparedStatement stmt = null;

	static ResultSet rs = null;

	public static void init() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			conn.setAutoCommit(false);
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void close() {
		try {
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
			}
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
	}

	@Override
	public void getStorage() {
		Map<IngredientType, Integer> ingred = new EnumMap<IngredientType, Integer>(
				IngredientType.class);

		init();

		try {
			stmt = conn.prepareStatement(selectStorage);
			rs = stmt.executeQuery();

			String name = "";
			int ingr_weight = 0;
			while (rs.next()) {
				name = rs.getString("name");
				ingr_weight = rs.getInt("ingr_weight");

				for (IngredientType ingr : IngredientType.values()) {
					if (ingr.toString().equalsIgnoreCase(name)) {
						ingred.put(ingr, ingr_weight);
					}
				}
			}
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		close();

		ms.setAll(ingred);
	}

	@Override
	public void updateMachineStorage(MachineStorage storage) {
		System.out.println("updateMachineStorage");
		init();

		try {
			Map<IngredientType, Integer> ingred = storage.getAll();
			stmt = conn.prepareStatement(updateStorage);
			for (IngredientType key : ingred.keySet()) {
				stmt.setInt(1, ingred.get(key));
				stmt.setString(2, key.toString());
				stmt.addBatch();
			}
			stmt.executeBatch();
			conn.commit();
		} catch (SQLException se) {
			se.printStackTrace();
		}
		close();
	}

}
