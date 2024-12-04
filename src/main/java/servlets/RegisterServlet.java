package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import dao.UserDao;
import entities.User;


/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet(urlPatterns = "/register")
public class RegisterServlet extends HttpServlet {


    private static final long serialVersionUID = 1L;
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Trả về trang register.jsp
        request.getRequestDispatcher("views/register-user.jsp").forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, 
	HttpServletResponse response) throws ServletException, IOException {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        
        User user = new User(firstName, lastName, email, userName, password);
        try {
            UserDao userDAO = new UserDao(); // Lớp xử lý cơ sở dữ liệu
            boolean isSuccess = userDAO.registerUser(user);
            if (isSuccess) {
                response.sendRedirect("views/login.jsp");
            } else {
                request.setAttribute("error", "Đăng ký thất bại. Vui lòng thử lại.");
                request.getRequestDispatcher("register-user.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi hệ thống! Vui lòng thử lại sau.");
            request.getRequestDispatcher("views/register-user.jsp").forward(request, response);
        }
    }
}