package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.User;
import service.UserService;

public class LogInServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String emailId = request.getParameter("txtLogInEmailId").trim();
  		String pass = request.getParameter("txtLogInPassword").trim();
  		User user = UserService.selectUserByEmailId(emailId);
  		if(user != null) {
  			if(user.getPassword().equals(pass)) {

//  			Check If Password of user trying to login exists or not
  				Cookie cookieArray[] = request.getCookies();
				boolean userPasswordExist = false;
				int userExistAt = -1;
				if (cookieArray != null) {
					for (int i = cookieArray.length; i > 0; i--) {
						
						userPasswordExist = cookieArray[i-1].getName().equals(emailId.replace('@', '_')) && cookieArray[i-1].getValue().equals(pass);
						if(userPasswordExist) {
							userExistAt = i-1;
							break;
						}
					}
				}
//  			Check If Password of user trying to login exists or not

//				add or update password if user wants
				if( request.getParameter("txtLogInRememberPassword") != null ){
					// Add cookie of remember password if does not exist.
					if(! userPasswordExist) {
						Cookie cookieAddOrUpdate = new Cookie(emailId.replace('@','_'), pass);
						cookieAddOrUpdate.setMaxAge(60*60*24*365*5);
						response.addCookie(cookieAddOrUpdate);
					}
				}
//				add or update password if user wants

//				delete password if user wants
  				else {
  					if(userPasswordExist) {
  						Cookie cookieRemove = cookieArray[userExistAt];
						cookieRemove.setMaxAge(0);
						response.addCookie(cookieRemove);
					}
  				}
//				delete password if user wants

  		  		request.getSession().setAttribute("Current User",user);
  		  		user = null;
  				response.sendRedirect("dms.jsp");
  				return;					// this return statement ensures that no content is added to the response further https://javapapers.com/servlet/how-to-avoid-illegalstateexception-in-java-servlet/
  				
  			}else{
  				response.sendRedirect("welcome.jsp?PasswordNotMatch=true&EmailId="+emailId);
  				return;					// this return statement ensures that no content is added to the response further https://javapapers.com/servlet/how-to-avoid-illegalstateexception-in-java-servlet/
  			}
  		}
  		else {
				response.sendRedirect("welcome.jsp?EmailIdNotMatch=true");
				return;					// this return statement ensures that no content is added to the response further https://javapapers.com/servlet/how-to-avoid-illegalstateexception-in-java-servlet/
			}
	}

}
