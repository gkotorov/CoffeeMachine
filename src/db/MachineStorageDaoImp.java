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

	MachineStorage ms = MachineStorage.getInstance();
	
	static Connection conn = null;
	static PreparedStatement stmt = null;
	static ResultSet rs = null;

	public static void initConn() {
		try {
			Class.forName(ConstsDB.JDBC_DRIVER);
			conn = DriverManager.getConnection(ConstsDB.DB_URL, ConstsDB.USER, ConstsDB.PASS);
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
			if (stmt != null) {
				stmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void getStorage() {
		Map<IngredientType, Integer> ingred = new EnumMap<IngredientType, Integer>(
				IngredientType.class);

		initConn();

		try {
			stmt = conn.prepareStatement(ConstsDB.SELECT_STORAGE);
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
	public void updateMachineStorage() {
		System.out.println("updateMachineStorage");
		initConn();

		try {
			Map<IngredientType, Integer> ingred = ms.getAll();
			stmt = conn.prepareStatement(ConstsDB.UPDATE_STORAGE);
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
