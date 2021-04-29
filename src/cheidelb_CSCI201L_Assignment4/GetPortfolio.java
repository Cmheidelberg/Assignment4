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

@WebServlet("/GetPortfolio")
public class GetPortfolio extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	protected void service(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		System.out.println("\nGetPortfolio");
				
		PrintWriter out = response.getWriter();
		CustomDatabase db = new CustomDatabase();
		StockHelper sh = new StockHelper();
		
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
		
		DecimalFormat df = new DecimalFormat("#,###,###.##");
		df.setRoundingMode(RoundingMode.HALF_UP);
		
		System.out.println("ID: " + id);
		if(id != -1) { 
			ArrayList<String> tickers = db.getPortfolioTickers(id);
			
			for(String t: tickers) {
				out.println("<div class=\"outer-card\">");
				
				Stock curr = sh.getStockFromApi(t);
				float[] bodyInfo = sumStockCard(id, t);
				
				//HEAD OF CARD
				out.println("<div class=\"card-head\">");
				out.println("<h2>"+curr.getTicker()+"</h2>");
				out.println("<h3>" +curr.getName()+ "</h3>");
				out.println("</div>");
				
				
				//BODY OF CARD
				out.println("<div class=\"card-body\">");
				
					out.println("<div class=\"body-left\" style=\"float: left;margin: auto; width: 45%;\">");
						
						out.println("<div style=\"float: left\" class=\"lefts\">");
							out.println("<p>Quantity:</p>");
							out.println("<p>Avg. Cost/Share:</p>");
							out.println("<p>Total Cost: </p>");
							
						out.println("</div>");
						
						out.println("<div style=\"float: right\" class=\"rights\">");
							out.println("<p >"+df.format(bodyInfo[0])+"</p>");
							out.println("<p>"+df.format(bodyInfo[1])+"</p>");
							out.println("<p>"+df.format(bodyInfo[2])+"</p>");
						out.println("</div>");
				
					out.println("</div>");

				out.println("<div class=\"body-right\" style=\"float: right;margin: auto; width: 45%;\">");
					out.println("<div  style=\"float: left\" class=\"lefts\">");
						out.println("<p>Change:</p>");
						out.println("<p>Curr Price:</p>");
						out.println("<p>Market Value:</p>");
					out.println("</div>");
				
					out.println("<div  style=\"float: right\" class=\"rights\">");
						out.println("<p>"+df.format(Float.parseFloat(curr.getLast()))+"</p>");
						out.println("<p>" + df.format(Float.parseFloat(curr.getLast()) - bodyInfo[1])+"</p>");
						out.println("<p>"+df.format(bodyInfo[0]*Float.parseFloat(curr.getLast()))+"</p>");
					out.println("</div>");
			
				out.println("</div>");
				out.println("<br><br><br><br><br><br><br><br><br><br>");
				out.println("</div>");
				
				//PURCHASE PANNEL (BOTOM OF CARD)
				out.println("<div class=\"card-footer\">");
				out.println("<br>");
				out.println("<Form onsubmit=\"purchase(\'"+ curr.getTicker().toLowerCase() +"\');return false\">");
				out.println("Quantity: <input type=\"search\" id=\"quantity-"+curr.getTicker().toLowerCase()+
							"\" size=\"3\" name=\"quantity\"><br>");
				out.println("<input type=\"radio\" id=\"buy\" name=\"bs\" value=\"buy\" required>");
				out.println("<label for=\"buy\">Buy</label>");
				
				out.println("<input type=\"radio\" id=\"sell\" name=\"bs\" value=\"sell\" required>");
				out.println("<label for=\"sell\">Sell</label><br>");
				
				out.println("<input type=\"submit\" value=\"Submit\"><br>");
				out.println("</Form>");
				out.println("<br>");
				out.println("</div>");
				
				out.println("</div>");
				out.println("<br>");
			}
			
			

		}
		
		
	}
	
	//Returns float array of stock portfolio information from give uid
	//[quantity, Avg Cost, Total Cost]
	public float[] sumStockCard(int iduser, String ticker) {
		float[] outp;
		CustomDatabase db = new CustomDatabase();
		outp = db.getPortfolioSummary(iduser, ticker);
		db.close();
		return outp;
		
	}
}
