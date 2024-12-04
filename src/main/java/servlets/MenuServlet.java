package servlets;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

import dao.MenuDao;
import entities.MenuItem;

@WebServlet("/menu")
public class MenuServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    MenuDao menuDAO = new MenuDao();  

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            if ("add".equals(action)) {
                addMenuItem(request, response);
            } else if ("update".equals(action)) {
                updateMenuItem(request, response);
            } else if ("delete".equals(action)) {
                deleteMenuItem(request, response);
            } else {
                forwardToProductPage(request, response, "invalid_action");
            }
        } catch (Exception e) {
            e.printStackTrace();  
            forwardToProductPage(request, response, "error");
        }
    }

    private void addMenuItem(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String tenMon = request.getParameter("ten_mon");
        double gia = Double.parseDouble(request.getParameter("gia"));
        String moTa = request.getParameter("mo_ta");
        String nguyenLieu = request.getParameter("nguyen_lieu");
        String hinhAnh = request.getParameter("hinh_anh");
        String category = request.getParameter("category");


        // Kiểm tra thông tin trước khi thêm
        if (tenMon == null || gia <= 0 || moTa == null || nguyenLieu == null||  hinhAnh == null || category == null) {
            forwardToProductPage(request, response, "invalid_data");
            return;
        }

        MenuItem menuItem = new MenuItem(0, tenMon, gia, moTa, nguyenLieu, hinhAnh, category);
        boolean success = menuDAO.addMenuItem(menuItem);

        if (success) {
        	 listAllMenuItems(request, response);
        } else {
            forwardToProductPage(request, response, "failure");
        }
    }

    private void updateMenuItem(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int id = Integer.parseInt(request.getParameter("id"));
        String tenMon = request.getParameter("ten_mon");
        double gia = Double.parseDouble(request.getParameter("gia"));
        String moTa = request.getParameter("mo_ta");
        String nguyenLieu = request.getParameter("nguyen_lieu");
        String hinhAnh = request.getParameter("hinh_anh");
        String category = request.getParameter("category");


        // Kiểm tra thông tin trước khi cập nhật
        if (id <= 0 || tenMon == null || gia <= 0 || moTa == null || nguyenLieu == null || hinhAnh == null || category == null) {
            forwardToProductPage(request, response, "invalid_data");
            return;
        }MenuItem menuItem = new MenuItem(id, tenMon, gia, moTa, nguyenLieu, hinhAnh, category);
        boolean success = menuDAO.updateMenuItem(menuItem);

        if (success) {
        	 listAllMenuItems(request, response);
        } else {
            forwardToProductPage(request, response, "failure");
        }
    }

    private void deleteMenuItem(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int id = Integer.parseInt(request.getParameter("id"));

        // Kiểm tra ID hợp lệ trước khi xóa
        if (id <= 0) {
            forwardToProductPage(request, response, "invalid_id");
            return;
        }

        boolean success = menuDAO.deleteMenuItem(id);

        if (success) {
        	 listAllMenuItems(request, response);
        } else {
            forwardToProductPage(request, response, "failure");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            if ("search".equals(action)) {
                searchMenuItems(request, response);
            } else {
                listAllMenuItems(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();  // In lỗi để dễ dàng kiểm tra
            throw new ServletException("Error while processing request", e);
        }
    }

    private void searchMenuItems(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String tenMon = request.getParameter("ten_mon");
        if (tenMon == null || tenMon.isEmpty()) {
            forwardToProductPage(request, response, "invalid_search");
            return;
        }

        List<MenuItem> menuItems = menuDAO.searchMenuItems(tenMon);
        request.setAttribute("menuItems", menuItems);
        forwardToProductPage(request, response, null);
    }

    private void listAllMenuItems(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<MenuItem> menuItems = menuDAO.getAllMenuItems();
        request.setAttribute("menuItems", menuItems);
        forwardToProductPage(request, response, null);
    }

    private void forwardToProductPage(HttpServletRequest request, HttpServletResponse response, String status) throws ServletException, IOException {
        if (status != null) {
            request.setAttribute("status", status);
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/product.jsp");
        dispatcher.forward(request, response);
    }
}