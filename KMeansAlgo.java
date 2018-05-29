package codes;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.opencsv.CSVReader;

import net.sf.javaml.clustering.Clusterer;
import net.sf.javaml.clustering.KMeans;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.tools.data.ARFFHandler;
import net.sf.javaml.tools.data.FileHandler;

public class KMeansAlgo {

    /**
     * Tests the k-means algorithm with default parameter settings.
     */
    public static void main(String[] args) throws Exception {
   	Connection con=Database.getDBConnection();

	File file = new File("E:/Webcrawler/dataofsite.csv");
	List<String> lines = FileUtils.readLines(file);
	List<String> textdata = new ArrayList<String>();
	PreparedStatement ps4 = con.prepareStatement("SELECT distinct(dataofsite.datacontent) FROM dataofsite, sites WHERE dataofsite.siteId = 1");
	System.out.println("SELECT dataofsite.datacontent FROM dataofsite, sites WHERE dataofsite.siteId = 1 ");

	ResultSet rs = ps4.executeQuery();
	while(rs.next())
	{
		textdata.add(rs.getString("datacontent"));
	}
//	String label="";
	System.out.println(lines);
	for(int j=0;j<textdata.size();j++){
		
			if(textdata.get(j).startsWith("https://") && ((textdata.get(j).endsWith(".jpg"))|| (textdata.get(j).endsWith(".png")))){
				System.out.println(textdata.get(j)+"--image");
				//label="";
			}
			else if(textdata.get(j).startsWith("https://") || textdata.get(j).startsWith("http://")){
				System.out.println(textdata.get(j)+"--link");
			}else{
				System.out.println(textdata.get(j)+"--text");
			}
	
	}
    }
}
