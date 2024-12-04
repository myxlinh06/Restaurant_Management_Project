package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;


import dao.*;
import utils.*;
import entities.*;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet(urlPatterns = "/login")
public class LoginServlet extends HttpServlet {


   private static final long serialVersionUID = 1L;

    private static UserDao userDao = new UserDao();
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	LogFactory.getLogger().severe("Running on doPost method of LoginServlet");
        String userName = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            if (userDao.loginUser(userName, password)) {
                // Đăng nhập thành công
                HttpSession session = (HttpSession) request.getSession();
                User user = new User(userName, password);
                session.setAttribute("userLogin", user);
                response.sendRedirect(request.getContextPath() + "/home");
            } else {
                // Đăng nhập thất bại
                request.setAttribute("loginFail", "User name or password is incorrect");
                request.getRequestDispatcher("/views/login.jsp").forward(request, response);
            }
        } catch (ClassNotFoundException | SQLException e) {
            LogFactory.getLogger().severe("Exception in method doPost of LoginServlet: " + e.getMessage());
        }
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LogFactory.getLogger().severe("Running on doGet method of LoginServlet");
        request.getRequestDispatcher("views/login.jsp").forward(request, response);
    }


}