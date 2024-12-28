package servlets;

import dao.CustomerDao;
import entities.Customer;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/customer")
public class CustomerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CustomerDao customerDao = new CustomerDao();

    // Hiển thị danh sách khách hàng
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String keyword = request.getParameter("search"); // Lấy từ khóa tìm kiếm từ form

        try {
            List<Customer> customers;
            if (keyword != null && !keyword.isEmpty()) {
                customers = customerDao.searchCustomers(keyword); // Tìm kiếm theo từ khóa
            } else {
                customers = customerDao.getAllCustomers(); // Lấy tất cả khách hàng
            }
            request.setAttribute("customers", customers);
            request.getRequestDispatcher("/views/customer.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error");
        }
    }
    // Thêm hoặc sửa khách hàng
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idStr = request.getParameter("id"); // Nếu có ID thì là sửa khách hàng
        String email = request.getParameter("email");
        String hoTen = request.getParameter("hoTen");
        String phone = request.getParameter("phone");
        String diaChi = request.getParameter("diaChi");
        String tinh = request.getParameter("tinh");
        String huyen = request.getParameter("huyen");
        String xa = request.getParameter("xa");

        Customer customer;

        if (idStr != null && !idStr.isEmpty()) {
            // Nếu có id, thì đây là sửa thông tin khách hàng
            int id = Integer.parseInt(idStr);
            customer = new Customer(id, email, hoTen, phone, diaChi, tinh, huyen, xa);
            try {
                if (customerDao.updateCustomer(customer)) {
                    response.sendRedirect(request.getContextPath() + "/customer");
                } else {
                    request.setAttribute("error", "Failed to update customer");
                    request.getRequestDispatcher("/views/customer.jsp").forward(request, response);
                }
            } catch (Exception e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error");
            }
        } else {
            // Nếu không có id, thì đây là thêm mới khách hàng
            customer = new Customer(0, email, hoTen, phone, diaChi, tinh, huyen, xa);
            try {
                if (customerDao.addCustomer(customer)) {
                    response.sendRedirect(request.getContextPath() + "/customer");
                } else {
                    request.setAttribute("error", "Failed to add customer");
                    request.getRequestDispatcher("/views/customer.jsp").forward(request, response);
                }
            } catch (Exception e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error");
            }
        }
    }
}
