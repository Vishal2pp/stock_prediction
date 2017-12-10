package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedHashMap;

import dto.Data;

public class SaveData {

	public int saveData(LinkedHashMap<String, Data> data1){
		try{
			Connection con = new GetConnection().getConnection();
			if(con == null){
				System.out.println("Connection failed");
				return 0;
			}
			
			String tableName = "";

			for(String key : data1.keySet()){
				Data d = data1.get(key);
				tableName = d.getSymbol().trim().toLowerCase();
			}

			Statement st = con.createStatement();
			ResultSet r = st.executeQuery("show TABLES");
			System.out.println(r);
			boolean flag = false;

			while(r.next()){
				if(r.getString(1).equals(tableName)){
					System.out.println("Table exists");
					flag = true;
					break;
				}
			}

			if(flag){
				String q = "DROP TABLE `"+tableName+"`";
				st.executeUpdate(q);
			}
			String q = "CREATE TABLE `"+tableName+"`(`date` VARCHAR(20) NOT NULL,`open` float NOT NULL,"
					+ "`close` float NOT NULL,`high` float NOT NULL,`low` float NOT NULL,"
					+ "`volume` int(11) NOT NULL,`amt_change` float NOT NULL,"
					+ "`percent_change` float NOT NULL,CONSTRAINT PRIMARY KEY(date))";

			st.executeUpdate(q);

			for(String key : data1.keySet()){
				Data data = data1.get(key);
				String query = "insert into `"+tableName+"` values(?,?,?,?,?,?,?,?)";
				java.sql.PreparedStatement stmnt = con.prepareStatement(query);
				stmnt.setString(1, data.getDate());
				stmnt.setFloat(2, data.getOpen().floatValue());
				stmnt.setFloat(3, data.getClose().floatValue());
				stmnt.setFloat(4, data.getHigh().floatValue());
				stmnt.setFloat(5, data.getLow().floatValue());
				stmnt.setInt(6, (int)data.getvolume());
				stmnt.setFloat(7, data.getAmt_change());
				stmnt.setFloat(8, data.getPercent_change());
				stmnt.execute();
			}
			con.close();
			st.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return 0;
	}
}
