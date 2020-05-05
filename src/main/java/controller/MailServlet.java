package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.User;
import service.MailService;
import service.UserService;

public class MailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String toEmailId = request.getParameter("forgetEmailId").trim();
		User user = UserService.selectUserByEmailId(toEmailId);
		String fromMail = "";
		String fromMailPassword = "";
		String subject = "Forgot Password for Dynamic Mailing System";
		String body = "Your Password is:\n"+user.getPassword();
		String filePath = "";
		MailService.SendForgotPasswordMail(toEmailId, fromMail, fromMailPassword, subject, body, filePath);
		response.sendRedirect("welcome.jsp?PasswordSent=true&EmailId="+toEmailId);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
