package cheidelb_CSCI201L_Assignment4;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/Login")
public class Login extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		CustomDatabase cd = new CustomDatabase();
		String id = cd.getValidatedIduser(request.getParameter("uname"), request.getParameter("pwd"));
		
		System.out.println("ID: " + id);
		PrintWriter out = response.getWriter();
		
		if (id != null && id != "") {
			Cookie cookie = new Cookie("userid", id);
			cookie.setMaxAge(60 * 60);
			response.addCookie(cookie);
			response.setContentType("text/html");
			out.print("valid");
		}else {
			out.print("invalid");
		}
	}
	
	
}


