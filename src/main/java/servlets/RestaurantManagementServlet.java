package servlets;

import dao.PhongDao;
import dao.BanDao;
import dao.MenuDao;
import entities.Phong;
import entities.Ban;
import entities.MenuItem;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@WebServlet("/restaurant")
public class RestaurantManagementServlet extends HttpServlet {
    private PhongDao phongDAO;
    private BanDao banDAO;
    private MenuDao menuDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        phongDAO = new PhongDao();
        banDAO = new BanDao();
        menuDAO = new MenuDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            // Fetch all rooms
            List<Phong> rooms = phongDAO.getAllPhong();

            // Fetch tables for each room
            Map<Integer, List<Ban>> tables = new HashMap<>();
            for (Phong room : rooms) {
                List<Ban> roomTables = banDAO.getBanByPhong(room.getId());
                tables.put(room.getId(), roomTables);
            }

            // Fetch all menu items
            List<MenuItem> menuItems = menuDAO.getAllMenuItems();

            // Set attributes
            request.setAttribute("rooms", rooms);
            request.setAttribute("tables", tables);
            request.setAttribute("menuItems", menuItems);

            // Forward to JSP
            request.getRequestDispatcher("/views/restaurant.jsp").forward(request, response);
        } catch (ClassNotFoundException e) {
            throw new ServletException("Database error", e);
        }
    }
}