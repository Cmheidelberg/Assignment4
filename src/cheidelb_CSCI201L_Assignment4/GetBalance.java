package cheidelb_CSCI201L_Assignment4;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/GetBalance")
public class GetBalance extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("\nGetBalance");
				
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
		if(id != -1) {
			float balance = db.getBalance(id);
			DecimalFormat df = new DecimalFormat("#,###,###.##");
			df.setRoundingMode(RoundingMode.HALF_UP);
			System.out.println("balance: " + df.format(balance));
			out.print(df.format(balance));
		} else {
			out.print("none");
			return;
		}
		
		out.flush();
		out.close();
		db.close();
	}
}
