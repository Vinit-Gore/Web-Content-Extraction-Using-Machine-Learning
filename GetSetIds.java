package codes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class GetSetIds {

	public static int siteId = 1;
	
	static Connection con = Database.getDBConnection();
	
	public static int generateSiteId()
	{
		try {
			PreparedStatement ps = con.prepareStatement("select ID from sites order by ID Desc limit 1");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				siteId = rs.getInt("ID") + 1;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return siteId;
	}
	
	
	
}
