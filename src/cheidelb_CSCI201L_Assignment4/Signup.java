package cheidelb_CSCI201L_Assignment4;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Signup")
public class Signup extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("\nSignup");
		String uname = request.getParameter("sign_uname");
		String pwd = request.getParameter("subm_pwd");
		String email = request.getParameter("sign_email");
		
		CustomDatabase cd = new CustomDatabase();
		String unid = cd.getIduser(uname);
		String emid = cd.getIduserFromEmail(email);
		
		
		System.out.println("Signup Username id: " + unid);
		System.out.println("Signup Email id: " + emid);
		
		PrintWriter out = response.getWriter();
		
		if (unid != null && unid != "") {
			out.print("username_taken");
		} else if(emid != null && emid != "") {
			out.print("email_taken");
		} else {
			
			out.print(cd.initializeNewUser(uname, email, pwd));
		}
		
	}
	
}
