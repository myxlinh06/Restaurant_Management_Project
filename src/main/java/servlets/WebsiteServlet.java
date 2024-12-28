package servlets;
import dao.MenuDao;
import entities.MenuItem;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/web")
public class WebsiteServlet extends HttpServlet {
    private MenuDao menuDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        menuDAO = new MenuDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
          
            // Fetch all menu items
            List<MenuItem> menuItems = menuDAO.getAllMenuItems();

            request.setAttribute("menuItems", menuItems);

            // Forward to JSP
            request.getRequestDispatcher("/views/web.jsp").forward(request, response);
        } catch (ClassNotFoundException e) {
            throw new ServletException("Database error", e);
        }
    }
}