package cheidelb_CSCI201L_Assignment4;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/GetFavorites")
public class GetFavorites extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("\nGetFavorites");
		PrintWriter out = response.getWriter();
		CustomDatabase db = new CustomDatabase();
		
		int id = -1;
		
		Cookie[] cookies = request.getCookies();
		Cookie cookie = null;
		
		if( cookies != null ) {
		    for (int i = 0; i < cookies.length; i++) {
		       cookie = cookies[i];
		       if(cookie.getName().equals("userid")) {
		    	   id = Integer.parseInt(cookie.getValue());
		       }
		    }
		} 
		
		System.out.println("ID: " + id);
		ArrayList<Favorite> favorites = null;
		if(id != -1) {
			favorites = db.getFavorites(id);
		} else {
			out.print("none");
			return;
		}
		
		if(favorites != null && favorites.size() > 0) {
			out.println("<div class=\"favorites-box\">");
			
			for(Favorite f : favorites) {
				StockHelper sh = new StockHelper();
				Stock d = sh.getStockFromApi(f.getTicker());
				System.out.println("f ticker: " + f.getTicker() );
				
				out.println("<div id=\"field-"+f.getTicker().toLowerCase()+"\"><fieldset style=\"border-color: light-grey; border-radius: 5px;\">");
				
				DecimalFormat df = new DecimalFormat("#.##");
				df.setRoundingMode(RoundingMode.HALF_UP);

			    
				
				if(d != null) {
					float change = (Float.parseFloat(d.getLast()) - Float.parseFloat(d.getPrevClose()));
					float pcent = (change*100)/d.getClose();
					
				    String span = "";
				    if(change < 0) {
				    	span = "style=\"color:red\"";
				    } else if(change > 0) {
				    	span = "style=\"color:green\"";
				    }
				    
					out.println("<button class=\"exitbutton\" "
								+ "id=\"exitbutton-"+f.getTicker().toLowerCase()+"\" "
								+ "onclick=\"removeFavoriteStock(\'"+f.getTicker().toLowerCase()+"\')\" "
								+ "type=\"submit\"><i class=\"fas fa-times\"></i></button>");
					
					
					out.println("<a href=\"javascript:getCardInfo(\'"+f.getTicker().toLowerCase()+"\');\">"
									+ "<div style=\"overflow: auto;\">");
					out.println("<div class=\"left-info\">");
					out.println("<h2>" + f.getTicker() + "</h2>");
					out.println("<h3>" + d.getName() + "</h3>");
					out.println("</div>");
					
					out.println("<div class=\"right-info\">");
				    out.println("<h2><span "+span+">"+ d.getLast()+ "</span></h2>");
					out.println("<h3><span "+span+">" + df.format(change) + " (" + df.format(pcent) +")%</span></h3>");
					out.println("</div></div></a>");		
					
					
				} else {
					out.println("<h2>" + f.getTicker() + "</h2><br>");
					out.println("<p2>Error fetching stock information</p>");
				}
				out.println("</fieldset></div>");
				out.println("<div id=\"br-"+f.getTicker().toLowerCase()+"\"><br></div>");
				
			}
			
			
		} else {
			out.print("none");
			return;
		}
		
		out.println("</div>");
		
		out.flush();
		out.close();
		db.close();
	}
	

}
