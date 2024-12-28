package servlets;

import dao.BanDao;
import dao.PhongDao;
import entities.Ban;
import entities.Phong;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/phong")
public class PhongServlet extends HttpServlet {

    private PhongDao phongDao;
    private BanDao banDao;

    @Override
    public void init() {
        phongDao = new PhongDao(); // Khởi tạo đối tượng DAO cho phòng
        banDao = new BanDao();     // Khởi tạo đối tượng DAO cho bàn
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if ("viewBans".equals(action)) {
                viewBans(request, response);
            } else {
                listRooms(request, response);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Tham số không hợp lệ.");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi khi tải dữ liệu.");
        }
    }
    
    

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("addBan".equals(action)) {
            addBan(request, response); // Xử lý thêm bàn
        } else {
            doGet(request, response); // Xử lý mặc định
        }
    }

    // Thêm bàn mới
    private void addBan(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String tenBan = request.getParameter("tenBan");
        String status = request.getParameter("status");
        int idPhong;

        try {
            idPhong = Integer.parseInt(request.getParameter("idPhong"));
        } catch (NumberFormatException e) {
            request.setAttribute("error", "ID phòng không hợp lệ.");
            doGet(request, response); // Quay lại trang trước
            return;
        }

        if (tenBan == null || tenBan.trim().isEmpty()) {
            request.setAttribute("error", "Tên bàn không được để trống.");
            request.setAttribute("idPhong", idPhong);
            doGet(request, response); // Hiển thị lại danh sách bàn cùng thông báo lỗi
            return;
        }

        try {
            // Tạo đối tượng Ban mới
            Ban newBan = new Ban(0, idPhong, tenBan, status != null ? status : "available"); // Mặc định trạng thái là "available"

            // Gọi DAO để thêm bàn
            BanDao banDao = new BanDao();
            boolean success = banDao.addBan(newBan);

            if (success) {
                response.sendRedirect("phong?action=viewBans&idPhong=" + idPhong); // Quay lại danh sách bàn
            } else {
                request.setAttribute("error", "Không thể thêm bàn.");
                doGet(request, response); // Hiển thị lỗi
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi khi thêm bàn.");
        }
    }


    // Hiển thị danh sách phòng
    private void listRooms(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ClassNotFoundException {
        List<Phong> phongList = phongDao.getAllPhong();
        request.setAttribute("phongList", phongList);
        request.getRequestDispatcher("views/room.jsp").forward(request, response);
    }

    // Hiển thị danh sách bàn theo phòng
    private void viewBans(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ClassNotFoundException {
        int idPhong = Integer.parseInt(request.getParameter("idPhong")); // Có thể ném NumberFormatException
        List<Ban> banList = banDao.getBanByPhong(idPhong);

        request.setAttribute("banList", banList);
        request.setAttribute("idPhong", idPhong);

        if (banList.isEmpty()) {
            request.setAttribute("message", "Không có bàn nào trong phòng này.");
        }
        request.getRequestDispatcher("views/ban.jsp").forward(request, response);
    }

    // Thêm phòng mới
    private void addRoom(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ClassNotFoundException {
        String tenPhong = request.getParameter("tenPhong");

        if (tenPhong == null || tenPhong.trim().isEmpty()) {
            request.setAttribute("error", "Tên phòng không được để trống.");
            listRooms(request, response); // Hiển thị lại danh sách phòng cùng thông báo lỗi
            return;
        }

        Phong phong = new Phong(0, tenPhong); // ID tự động tạo bởi DB
        boolean success = phongDao.addPhong(phong);

        if (success) {
            response.sendRedirect("phong"); // Redirect về trang danh sách phòng sau khi thêm thành công
        } else {
            request.setAttribute("error", "Không thể thêm phòng.");
            listRooms(request, response);
        }
    }
}
