package cheidelb_CSCI201L_Assignment4;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.*;
import javax.servlet.http.*;


@WebServlet("/Logout")
public class Logout extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Cookie[] cookies = request.getCookies();
		Cookie cookie = null;
		
		response.setContentType("text/html");
		
		PrintWriter out = response.getWriter();

  	  	boolean found = false;

  	  
		if( cookies != null ) {
		 
		    for (int i = 0; i < cookies.length; i++) {
		       cookie = cookies[i];
		       if(cookie.getName().equals("userid")) {
		    	   found = true;
		      	 	cookie.setMaxAge(0);
		        	 response.addCookie(cookie);
		         }
		      }
		   } 

      if(!found) {
    	  out.print("none");
    	  System.out.println("Logout: None");
      } else {
    	  System.out.println("Logout: Success");
    	  out.print("success");
      }
      out.close();
	}
	
	
	
	private static void addParameters(PrintWriter out, ArrayList<String> parms, HttpServletRequest req) {
		for(String s : parms) {
			out.println("\""+s+"\": "+req.getParameter(s));
		}
	}
}
