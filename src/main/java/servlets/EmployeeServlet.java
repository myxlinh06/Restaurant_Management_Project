package servlets;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

import dao.EmployeeDao;
import entities.Employee;

@WebServlet("/employee")
public class EmployeeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    EmployeeDao employeeDAO = new EmployeeDao();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            if ("add".equals(action)) {
                addUser(request, response);
            } else if ("update".equals(action)) {
                updateUser(request, response);
            } else if ("delete".equals(action)) {
                deleteUser(request, response);
            } else {
                forwardToUserPage(request, response, "invalid_action");
            }
        } catch (Exception e) {
            e.printStackTrace();
            forwardToUserPage(request, response, "error");
        }
    }

    private void addUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String firstName = request.getParameter("first_name");
        String lastName = request.getParameter("last_name");
        String email = request.getParameter("email");
        String userName = request.getParameter("user_name");
        String password = request.getParameter("password");
        String quyen = request.getParameter("quyen");

        // Kiểm tra thông tin trước khi thêm
        if (firstName == null || lastName == null || email == null || userName == null || password == null || quyen == null) {
            forwardToUserPage(request, response, "invalid_data");
            return;
        }

        Employee user = new Employee(0, firstName, lastName, email, userName, password, null, quyen);
        boolean success = employeeDAO.addUser(user);

        if (success) {
            listAllUsers(request, response);
        } else {
            forwardToUserPage(request, response, "failure");
        }
    }

    private void updateUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int id = Integer.parseInt(request.getParameter("id"));
        String firstName = request.getParameter("first_name");
        String lastName = request.getParameter("last_name");
        String email = request.getParameter("email");
        String userName = request.getParameter("user_name");
        String password = request.getParameter("password");
        String quyen = request.getParameter("quyen");

        // Kiểm tra thông tin trước khi cập nhật
        if (id <= 0 || firstName == null || lastName == null || email == null || userName == null || password == null || quyen == null) {
            forwardToUserPage(request, response, "invalid_data");
            return;
        }

        Employee user = new Employee(id, firstName, lastName, email, userName, password, null, quyen);
        boolean success = employeeDAO.updateUser(user);

        if (success) {
            listAllUsers(request, response);
        } else {
            forwardToUserPage(request, response, "failure");
        }
    }

    private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int id = Integer.parseInt(request.getParameter("id"));

        // Kiểm tra ID hợp lệ trước khi xóa
        if (id <= 0) {
            forwardToUserPage(request, response, "invalid_id");
            return;
        }

        boolean success = employeeDAO.deleteUser(id);

        if (success) {
            listAllUsers(request, response);
        } else {
            forwardToUserPage(request, response, "failure");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            if ("search".equals(action)) {
                searchUsers(request, response);
            } else {
                listAllUsers(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Error while processing request", e);
        }
    }

    private void searchUsers(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String userName = request.getParameter("user_name");
        if (userName == null || userName.isEmpty()) {
            forwardToUserPage(request, response, "invalid_search");
            return;
        }

        List<Employee> users = employeeDAO.searchUsers(userName);
        request.setAttribute("users", users);
        forwardToUserPage(request, response, null);
    }

    private void listAllUsers(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<Employee> users = employeeDAO.getAllUsers();
        request.setAttribute("users", users);
        forwardToUserPage(request, response, null);
    }

    private void forwardToUserPage(HttpServletRequest request, HttpServletResponse response, String status) throws ServletException, IOException {
        if (status != null) {
            request.setAttribute("status", status);
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/employee.jsp");
        dispatcher.forward(request, response);
    }
}
