package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.LogFactory;

import java.io.IOException;

/**
 * Servlet implementation class Logout
 */
@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {


	private static final long serialVersionUID = 1L;


	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		LogFactory.getLogger().info("Logging out");
		// remove session userLogin
		request.getSession().removeAttribute("userLogin");
		// redirect to /login
		response.sendRedirect(request.getContextPath() + "/login");
	}
}

