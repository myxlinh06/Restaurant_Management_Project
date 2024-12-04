package servlets;

import dao.OrderDao;
import entities.Order;
import entities.OrderItem;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.logging.LogFactory;

@WebServlet("/order")
public class OrderServlet extends HttpServlet {
    private OrderDao orderDao;
    private static final Logger LOGGER = Logger.getLogger(OrderServlet.class.getName());

    @Override
    public void init() throws ServletException {
        super.init();
        orderDao = new OrderDao();  // Initialize OrderDao
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action != null) {
            switch (action) {
                case "view":
                    try {
                        viewOrder(request, response);
                    } catch (IOException | SQLException e) {
                        LOGGER.log(Level.SEVERE, "Error viewing order", e);
                    }
                    break;
                case "list":
                    try {
                        showOrderPage(request, response);
                    } catch (IOException | ClassNotFoundException | ServletException e) {
                        LOGGER.log(Level.SEVERE, "Error listing orders", e);
                    }
                    break;
                default:
                    response.getWriter().write("Invalid action.");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action != null) {
            switch (action) {
                case "create":
                    try {
                        createOrder(request, response);
                    } catch (ClassNotFoundException | IOException e) {
                    	LOGGER.log(Level.SEVERE, "Error creating order", e);
                    }
                    break;
                case "addItem":
                    try {
                        addOrderItem(request, response);
                    } catch (ClassNotFoundException | SQLException | IOException e) {
                        LOGGER.log(Level.SEVERE, "Error adding item", e);
                    }
                    break;
                case "removeItem":
                    try {
                        removeOrderItem(request, response);
                    } catch (ClassNotFoundException | IOException e) {
                        LOGGER.log(Level.SEVERE, "Error removing item", e);
                    }
                    break;
                default:
                    response.getWriter().write("Invalid action.");
            }
        }
    }

    private void viewOrder(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        try {
            int orderId = Integer.parseInt(request.getParameter("id"));
            List<OrderItem> orderItems = orderDao.getOrderItemsByOrderId(orderId);

            if (orderItems != null && !orderItems.isEmpty()) {
                Order order = new Order(orderId, "Bàn " + orderId);
                order.setOrderItems(orderItems);
                response.getWriter().write("Thông tin đơn hàng: " + order.getTableName() + " - Tổng tiền: " + order.getTotalAmount());
            } else {
                response.getWriter().write("Không tìm thấy đơn hàng với ID: " + orderId);
            }
        } catch (NumberFormatException e) {
            response.getWriter().write("Invalid order ID.");
        }
    }

    private void listOrders(HttpServletRequest request, HttpServletResponse response) throws IOException, ClassNotFoundException {
        List<Order> orders = orderDao.getAllOrders();
        for (Order order : orders) {
            response.getWriter().write("ID: " + order.getId() + " - Bàn: " + order.getTableName() + " - Tổng tiền: " + order.getTotalAmount() + "\n");
        }
    }

    private void createOrder(HttpServletRequest request, HttpServletResponse response) throws IOException, ClassNotFoundException {
        try {
            int orderId = Integer.parseInt(request.getParameter("id"));
            String tableName = request.getParameter("tableName");
            Order order = new Order(orderId, tableName);
            orderDao.addOrder(order);
            response.getWriter().write("Đơn hàng đã được tạo với ID: " + orderId);
        } catch (NumberFormatException e) {
            response.getWriter().write("Invalid Order ID.");
        }
    }

    private void addOrderItem(HttpServletRequest request, HttpServletResponse response) throws IOException, ClassNotFoundException, SQLException {
        try {
            int orderId = Integer.parseInt(request.getParameter("orderId"));
            int itemId = Integer.parseInt(request.getParameter("itemId"));
            String name = request.getParameter("name");
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            int price = Integer.parseInt(request.getParameter("price"));

            OrderItem item = new OrderItem(itemId, name, quantity, price);
            orderDao.addOrderItem(orderId, item);
            response.getWriter().write("Mục đã được thêm vào đơn hàng.");
        } catch (NumberFormatException e) {
            response.getWriter().write("Invalid input parameters.");
        }
    }

    private void removeOrderItem(HttpServletRequest request, HttpServletResponse response) throws IOException, ClassNotFoundException {
        try {
            int orderId = Integer.parseInt(request.getParameter("orderId"));
            int itemId = Integer.parseInt(request.getParameter("itemId"));
            orderDao.deleteOrderItem(orderId, itemId);
            response.getWriter().write("Mục đã được xóa khỏi đơn hàng.");
        } catch (NumberFormatException e) {
            response.getWriter().write("Invalid input parameters.");
        }
    }

    private void showOrderPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ClassNotFoundException {
        List<Order> orders = orderDao.getAllOrders();
        request.setAttribute("orders", orders);
        request.getRequestDispatcher("/views/orders.jsp").forward(request, response);
    }
}
