package cheidelb_CSCI201L_Assignment4;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import java.text.*;
import java.math.*;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

@WebServlet("/SearchStock")
public class SearchStock extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	//This is the servlet that will handle the search stock functionaluty on the home page
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("\nSearchStock");
		PrintWriter out = response.getWriter();
		String ticker = request.getParameter("searchstock");
		String forceGuest = request.getParameter("forceguest");
		CustomDatabase db = new CustomDatabase();
		
		int id = -1;
		StockHelper sh = new StockHelper();
		Stock d = sh.getStockFromApi(ticker);
		
		Cookie[] cookies = request.getCookies();
		Cookie cookie = null;
		
  	  	boolean found = false;
		if( cookies != null ) {
		    for (int i = 0; i < cookies.length; i++) {
		       cookie = cookies[i];
		       if(cookie.getName().equals("userid")) {
		    	   found = true;
		    	   id = Integer.parseInt(cookie.getValue());
		       }
		    }
		} 
		
		if(forceGuest == null) 
			forceGuest = "false";
		
		User user = null;
		if(id != -1) {
			user = db.getUser(id);
		}
		
		
		if(d != null) {
			
			NumberFormat myFormat = NumberFormat.getInstance();
			String highPrice = d.gethigh();
			String lowPrice = d.getLow();
			String openPrice = d.getOpen();
			float close = d.getClose();
			String volume = d.getVolume();
			String startDate = d.getStartDate();
			String description = d.getDescription();
				
		    String leftStyle = "style=\"float:left;margin-bottom: 30px;text-align: left;\"";
		    String rightStyle ="style=\"float:right;margin-bottom: 30px;text-align: right;\"";
		    String botStyle = "style=\"clear:both;\"";
		    String bpstyle = "style=\"line-height: 10px;font-size: 15px;\"";
		    String compdescStyle = "style=\"text-align: left\"";
			
			// Display head of page
			if(user != null) {
				
				DecimalFormat df = new DecimalFormat("#,###,###.##");
				df.setRoundingMode(RoundingMode.HALF_UP);
				Date date = new Date(); 
				
				float change = 0;
				float pcent = 0;
				
				SimpleDateFormat sdfHour = new SimpleDateFormat("HH:mm");
				String marketOpen = "06:30";
				String marketClose = "13:00";
				
				//if market open
				
				if(sdfHour.format(date).compareTo(marketOpen) >= 0 && sdfHour.format(date).compareTo(marketClose) <= 0) {
			
				} else {
		
				}
		
				change = (Float.parseFloat(d.getLast()) - Float.parseFloat(d.getPrevClose()));
				pcent = (change*100)/d.getClose();
				
				
				
			    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
			     
			    String span = "";
			    if(change < 0) {
			    	span = "style=\"color:red\"";
			    } else if(change > 0) {
			    	span = "style=\"color:green\"";
			    }
			    
			    //Find out if stock is one of the user's favorites and fill the favorite icon
			    String favoriteStar = "";
			    ArrayList<Favorite> fav = db.getFavorites(id);
			    if(!forceGuest.toLowerCase().equals("true")) {
				    favoriteStar = "<button id=\"searchbutton\" onclick=\"toggleFavoriteStock()\" "
				    		+ "type=\"submit\"><i id=\"star-icon\" class=\"far fa-star\"></i></button>";
				    for (Favorite f: fav) {
				    	if(f.getTicker().toLowerCase().equals(ticker.toLowerCase())) {
				    		favoriteStar = "<button id=\"searchbutton\" onclick=\"toggleFavoriteStock()\" "
				    				+ "type=\"submit\"><i id=\"star-icon\" class=\"fas fa-star\"></i></button>";
				    	}
				    }
				    
			    }
			    
			    out.println("<div id=\"customout\">");
			    //Left hand div
				out.println("<div id=\"left_side\"" +leftStyle +"><h1 id=\"ticker-header\">"+  d.getTicker()+ favoriteStar + "</h1>");
				out.println("<h3>"+ d.getName() +"</h3>");
				out.println("<h4>"+ d.getExchangeCode() +"</h4>");
				out.println("<Form onsubmit=\"purchase(\'"+ d.getTicker().toLowerCase() +"\');return false\">");
				out.println("Quantity: <input type=\"search\" id=\"quantity-"+d.getTicker().toLowerCase()
							+"\" size=\"3\" name=\"quantity\"><br>");
				out.println("<input type=\"submit\" value=\"Buy\"><br>");
				out.println("</Form></div>");
					
				// Right hand div
			    out.println("<div id=\"right_side\""+ rightStyle+
			    		"><h1><span "+span+">"+ d.getLast()+ "</span></h1>");
				out.println("<h3><span "+span+">" + df.format(change) + 
						" (" + df.format(pcent) +")%</span></h3>");
				out.println("<h4>" + formatter.format(date) +"</h4></div>");
				
				out.println("</div>");
			
				//Market open/closed
				
			} else {
				out.println("<h1 id=\"ticker-header\">"+  d.getTicker() +"</h1>");
				out.println("<h3>"+ d.getName() +"</h3>");
				out.println("<h4>"+ d.getExchangeCode() +"</h4>");
			}
			
			out.println("<div id=\"quantity_form\" "+botStyle+">");
			out.println("<h5>Summary</h5><hr><br>");
			out.println("<div id=\"bulletpoints\" "+ bpstyle +"><p>High Price: "+ myFormat.format(Double.parseDouble(highPrice)) +"<p>");
			out.println("<p>Low Price: "+ myFormat.format(Double.parseDouble(lowPrice)) +"<p>");
			out.println("<p>Open Price: "+ myFormat.format(Double.parseDouble(openPrice)) +"<p>");
			out.println("<p>Close: "+ myFormat.format(close) +"<p>");
			out.println("<p>Volume: "+ myFormat.format(Double.parseDouble(volume)) +"<p></div>");
			
			out.println("<br><h2>Company's Description</h2>");
			out.println("<div id=\"compdesc\" "+compdescStyle+"><p>Start Date: "+ startDate +"<p>");
			out.println("<p>Description: "+ description +"<p></div></div>");
	   
		} else {
			out.print("invalid");
		}
		out.close();
		db.close();
	}
}

