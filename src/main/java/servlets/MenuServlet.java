package servlets;

import jakarta.servlet.*;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

import dao.MenuDao;
import entities.MenuItem;

@WebServlet("/menu")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2, // 2MB
    maxFileSize = 1024 * 1024 * 10,      // 10MB
    maxRequestSize = 1024 * 1024 * 50    // 50MB
)
public class MenuServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String IMAGE_DIR = "images"; // Thư mục lưu ảnh
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
        String category = request.getParameter("category");

        // Xử lý upload file ảnh
        Part filePart = request.getPart("hinh_anh"); // 'hinh_anh' là tên input file
        String fileName = uploadImage(filePart, request);

        // Kiểm tra thông tin trước khi thêm
        if (tenMon == null || gia <= 0 || moTa == null || nguyenLieu == null || fileName == null || category == null) {
            forwardToProductPage(request, response, "invalid_data");
            return;
        }

        MenuItem menuItem = new MenuItem(0, tenMon, gia, moTa, nguyenLieu, fileName, category);
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
        String category = request.getParameter("category");

        // Xử lý upload file ảnh (có thể không thay đổi ảnh)
        Part filePart = request.getPart("hinh_anh");
        String fileName = uploadImage(filePart, request);

        // Kiểm tra thông tin trước khi cập nhật
        if (id <= 0 || tenMon == null || gia <= 0 || moTa == null || nguyenLieu == null || category == null) {
            forwardToProductPage(request, response, "invalid_data");
            return;
        }

        MenuItem menuItem = new MenuItem(id, tenMon, gia, moTa, nguyenLieu, fileName, category);
        boolean success = menuDAO.updateMenuItem(menuItem);

        if (success) {
            listAllMenuItems(request, response);
        } else {
            forwardToProductPage(request, response, "failure");
        }
    }

    private String uploadImage(Part filePart, HttpServletRequest request) throws IOException {
        if (filePart == null || filePart.getSize() <= 0) {
            return null;
        }

        // Lấy đường dẫn thư mục gốc của dự án (webapp/images)
        String appPath = request.getServletContext().getRealPath("");
        String uploadPath = appPath + File.separator + IMAGE_DIR;

        // Tạo thư mục nếu chưa tồn tại
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        // Lấy tên file
        String fileName = extractFileName(filePart);

        // Đường dẫn lưu file
        String filePath = uploadPath + File.separator + fileName;

        // Lưu file vào thư mục
        filePart.write(filePath);

        return fileName; // Trả về tên file để lưu vào cơ sở dữ liệu
    }

    private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        for (String content : contentDisp.split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf("=") + 2, content.length() - 1);
            }
        }
        return null;
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