package cheidelb_CSCI201L_Assignment4;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Purchase")
public class Purchase extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("\nPurchase");
		
		String ticker = request.getParameter("ticker");
		String quantity = request.getParameter("quantity");
		String buysell = request.getParameter("buysell");
		
		System.out.println("Ticker: " + ticker);
		System.out.println("Quantity: " + quantity);
		System.out.println("BuySell: " + buysell);
		
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
		
		System.out.println("ID: " + id);
		if(id != -1) {
			
			float balance = db.getBalance(id);
			Stock curr = sh.getStockFromApi(ticker);
			
			float last = Float.parseFloat(curr.getLast());
			int q = Integer.parseInt(quantity);
			
			if(buysell.toLowerCase().equals("buy")) {
				
				if((balance - last * q) >= 0) {
					db.buyStock(id, ticker, q, last);
					out.print("done");
					out.flush();
					out.close();
					db.close();
					return;
				}
				
			} else if(buysell.toLowerCase().equals("sell")) {
				
				float[] pfs = db.getPortfolioSummary(id, ticker);
				if(pfs[0] >= q) {
					db.sellStock(id, ticker, q, last);
					out.print("done");
					out.flush();
					out.close();
					db.close();
					return;
				}
			}
		}
		
		out.print("invalid");
		out.flush();
		out.close();
		db.close();
	}
}
