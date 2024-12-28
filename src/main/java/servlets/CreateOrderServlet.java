package servlets;

import dao.ChiTietBanDAO;
import dao.BanDao;
import entities.ChiTietBan;
import com.google.gson.Gson;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/createOrder")
public class CreateOrderServlet extends HttpServlet {
    private ChiTietBanDAO chiTietBanDAO = new ChiTietBanDAO();
    private BanDao banDAO = new BanDao();
    private Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            // Read order data from request
            ChiTietBan chiTietBan = gson.fromJson(request.getReader(), ChiTietBan.class);
            
            // Create order in database
            boolean success = chiTietBanDAO.addChiTietBan(chiTietBan);
            
            // Update ban status
            if (success) {
                banDAO.updateBanStatus(chiTietBan.getIdBan(), "phuc_vu");
            }
            
            // Send response
            response.setContentType("application/json");
            if (success) {
                response.getWriter().write("{\"status\":\"success\"}");
            } else {
                response.getWriter().write("{\"status\":\"error\"}");
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"status\":\"error\",\"message\":\"" + e.getMessage() + "\"}");
        }
    }
}