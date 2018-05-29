package Servlets;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;

import codes.Database;
import codes.SvmClassifier;

/**
 * Servlet implementation class CheckRelatedContent
 */
@WebServlet("/CheckRelatedContent")
public class CheckRelatedContent extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Connection con = Database.getDBConnection();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckRelatedContent() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
		try {
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			
			File file = new File("E:/Webcrawler/webcrawler/dataset.csv");
			List<String> lines = FileUtils.readLines(file);
			
			
			List<String> textdata = new ArrayList<String>();
			
			PreparedStatement ps = con.prepareStatement("SELECT dataofsite.datacontent FROM dataofsite, sites WHERE dataofsite.siteId = sites.ID AND dataofsite.typeofdata = 'text'");
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				textdata.add(rs.getString("datacontent"));
			}
			
			List<Integer> flags = new ArrayList<Integer>();
			List<Integer> ones = new ArrayList<Integer>();
			List<Integer> zeros = new ArrayList<Integer>();
			List<String> labels = new ArrayList<String>();
			
			for(int j = 0; j < textdata.size(); j++)
			{
				
				for(int i = 0; i < lines.size(); i++)
				{
					flags.add(textdata.get(j).contains(lines.get(i)) ? 1 : 0);
				}
				SvmClassifier svm = new SvmClassifier();
				ones.add(Collections.frequency(flags, 1));
				zeros.add(Collections.frequency(flags, 0));
				flags.clear();
				if(ones.get(j) > 0)
				{
					labels.add("content");
				}
				else
				{
					labels.add("noncontent");					
				}
				PreparedStatement ps1 = con.prepareStatement("update dataofsite set content='" + labels.get(j) + "' where datacontent = '" + textdata.get(j) + "'");
				ps1.executeUpdate();
			}
			
			String label = "";
			
			PreparedStatement ps2 = con.prepareStatement("SELECT dataofsite.datacontent,dataofsite.id FROM dataofsite, sites WHERE dataofsite.siteId = sites.ID AND dataofsite.typeofdata = 'link' OR dataofsite.typeofdata = 'image'");
			ResultSet rs2 = ps2.executeQuery();
			while(rs2.next())
			{
				if(rs2.getString("datacontent").contains("thebetterindia"))
					label = "content";
				else
					label = "noncontent";
				
				PreparedStatement ps1 = con.prepareStatement("update dataofsite set content='" + label + "' where id = " + rs2.getInt("id") + "");
				ps1.executeUpdate();
			}
			
			
		
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}
