package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.User;
import service.UserService;

public class SignUpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String fn = request.getParameter("txtSignUpFirstName").trim();
  		String ln =	request.getParameter("txtSignUpLastName").trim();
  		String email = request.getParameter("txtSignUpEmailId").trim();
  		String pass = request.getParameter("txtSignUpPassword").trim();
  		User newUser = new User(fn,ln,email,pass);
  		List<User> userList = UserService.selectAllUser();
  		if(userList != null)
  		for(User u : userList){
  			if( email.equals(u.getEmailId()) ) {
  				response.sendRedirect("welcome.jsp?UserExist=true");
  				return;					// this return statement ensures that no content is added to the response further https://javapapers.com/servlet/how-to-avoid-illegalstateexception-in-java-servlet/
  			}
  		}
  		long flag = 0;
 		flag = UserService.insertUser(newUser);
 		newUser = null;
 		
		if(flag > 0){
			response.sendRedirect("welcome.jsp?SignUpSuccessful=true");
				return;					// this return statement ensures that no content is added to the response further https://javapapers.com/servlet/how-to-avoid-illegalstateexception-in-java-servlet/
		}
		else{
			response.sendRedirect("welcome.jsp?InsertionError=true");
				return;					// this return statement ensures that no content is added to the response further https://javapapers.com/servlet/how-to-avoid-illegalstateexception-in-java-servlet/
		}
		
	}
}
