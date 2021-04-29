package cheidelb_CSCI201L_Assignment4;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/LoginCookie")
public class LoginCookie extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Cookie[] cookies = request.getCookies();
		Cookie cookie = null;
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

  	  	boolean found = false;
  	  	String value = "";
  	  
  	  	if( cookies != null ) {
	  
	  	  	for (int i = 0; i < cookies.length; i++) {
	  	  		cookie = cookies[i];
	  	  		if(cookie.getName().equals("userid")) {
	  	  			found = true;
	  	  			value = cookie.getValue();
	  	  		}
	  	  	}
  	  	} 

      	if(!found) {
      		out.print("none");
  		} else {
  			out.print(value);
		}
      	out.close();
	}
	
	
	private static void addParameters(PrintWriter out, ArrayList<String> parms, HttpServletRequest req) {
		for(String s : parms) {
			out.println("\""+s+"\": "+req.getParameter(s));
		}
	}
	
}