package Servlets;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.sun.org.apache.xpath.internal.functions.Function;

import codes.Database;
import codes.GetSetIds;
import codes.KMeansAlgo;
import codes.SvmClassifier;

/**
 * Servlet implementation class StartCrawler
 */
@WebServlet("/StartCrawler")
public class StartCrawler extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Connection con = Database.getDBConnection();
	String link= "https://www.thebetterindia.com/topics/blog/";


	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public StartCrawler() {
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


			String URL = request.getParameter("url");
			if(URL!=null && !URL.equals(" ") && URL.length()>30){
				if(URL.substring(0, 5).equals("https")){
					if(URL.substring(8, 30).equals("www.thebetterindia.com")){

						//System.out.println(URL);
						WebClient webClient = new WebClient(BrowserVersion.CHROME);

						webClient.getOptions().setJavaScriptEnabled(false);
						webClient.getOptions().setThrowExceptionOnScriptError(false);

						HtmlPage htmlpage = webClient.getPage(URL);
						String desription = "No Description Available";
						HtmlElement descr = htmlpage.getFirstByXPath("//meta[@name='description']");
						if(descr!=null){
							desription = descr.getAttribute("content");
							//System.out.println("Description:"+desription);
							desription =desription.replace("'", "''");
						}


						HtmlElement ttl = htmlpage.getFirstByXPath("//title");

						String title = ttl.asText();

						//System.out.println("Title:"+title);



						int siteid = GetSetIds.generateSiteId();

						List<HtmlAnchor> links=htmlpage.getByXPath("//a");
						//System.out.println(links);
						//System.exit(0);
						String textdoc=htmlpage.asText();

						PreparedStatement ps = con.prepareStatement("INSERT INTO `sites`(`ID`, `SiteUrl`, title, description) VALUES (" + siteid + ", '" + URL + "', '" + title + "', '" + desription + "')");
						System.out.println("INSERT INTO `sites`(`ID`, `SiteUrl`, title, description) VALUES (" + siteid + ", '" + URL + "', '" + title + "', '" + desription + "')");
						ps.executeUpdate();
						ps=con.prepareStatement("truncate dataofsite");
						ps.executeUpdate();

						List<HtmlElement> items =  htmlpage.getByXPath("//ul[@class='g1-collection-items']/li[@class='g1-collection-item g1-collection-item-1of3']");
						List<HtmlElement> eleimg =  htmlpage.getByXPath("//ul[@class='g1-collection-items']/li[@class='g1-collection-item g1-collection-item-1of3']/article/figure/a/span[@class='g1-frame-inner']/img");
						KMeansAlgo km = new KMeansAlgo();
						if(items.isEmpty()){  
							System.out.println("No items found !");
						}else{
							for(int i = 0; i < items.size(); i++)
							{
								PreparedStatement ps1 = con.prepareStatement("INSERT INTO `dataofsite`(`datacontent`, `typeofdata` ,`siteId`) VALUES ('" + items.get(i).asText() + "', 'Text', "+ siteid + ")");
								ps1.executeUpdate();

							}
						}
						if(eleimg.isEmpty()){  
							System.out.println("No images found !");
						}else{
							for(int i = 0; i < items.size(); i++)
							{

								PreparedStatement ps2 = con.prepareStatement("INSERT INTO `dataofsite`(`datacontent`,`typeofdata` , `siteId`) VALUES ('" + eleimg.get(i).getAttribute("src") + "', 'Image', " + siteid + ")");
								ps2.executeUpdate();

							}
						}

						List<HtmlElement> urls =  htmlpage.getByXPath("//a");
						//System.out.println(urls);
						for(int i = 0; i < urls.size(); i++)
						{
							String urll = urls.get(i).getAttribute("href");

							if(urll.startsWith("#") || urll.equals(""))
								continue;
							//System.out.println(""+urll);
							PreparedStatement ps3 = con.prepareStatement("INSERT INTO `dataofsite`(`datacontent`, `typeofdata` ,`siteId`) VALUES ('" + urll + "', 'Link' ," + siteid + ")");
							ps3.executeUpdate();

						}


						//out.println("<div class='col-lg-12 margintop10 field'><p><button class='btn btn-theme margintop10 pull-left' id='next' type='submit' onclick='return checkRelated();'>Next</button></p></div>");
					}else{
						out.println("<script type='text/javascript'>");
						out.println("alert('Enter valid URL')");
						out.println("window.location = 'index.jsp'");
						out.println("</script>");
					}

				}else{

					out.println("<script type='text/javascript'>");
					out.println("alert('Please enter proper protocol in the url.')");
					out.println("window.location = 'index.jsp'");
					out.println("</script>");
				}
				
			}else {
				out.println("<script type='text/javascript'>");
				out.println("alert('Please enter valid URL link.')");
				out.println("window.location = 'index.jsp'");
				out.println("</script>");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}



}
