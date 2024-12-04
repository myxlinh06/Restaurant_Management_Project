<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="entities.OrderItem" %>
<%@ page import="entities.Order" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Thực đơn</title>
    <style>
        body { 
            font-family: Arial, sans-serif; 
            margin: 0; 
            padding: 0; 
            display: flex; 
            background-color: #f0f0f0;
        }
        .sidebar { 
            width: 200px; 
            background-color: #3b5998; 
            color: white; 
            padding: 20px; 
            height: 100vh;
            position: fixed;
        }
        .sidebar h2 { margin-top: 0; }
        .sidebar ul { list-style-type: none; padding: 0; }
        .sidebar li { margin-bottom: 10px; }
        .sidebar a { 
            color: white; 
            text-decoration: none; 
            display: block;
            padding: 10px;
            border-radius: 5px;
            transition: background-color 0.3s;
        }
        .sidebar a:hover, .sidebar a.active { background-color: #4267B2; }
        .main-content { 
            flex-grow: 1; 
            padding: 20px; 
            margin-left: 200px;
            background-color: white;
            min-height: 100vh;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
        .header { 
            display: flex; 
            justify-content: space-between; 
            align-items: center; 
            margin-bottom: 20px;
            padding-bottom: 10px;
            border-bottom: 1px solid #e0e0e0;
        }
        .search-bar { 
            display: flex; 
            align-items: center; 
            background-color: #f5f5f5;
            border-radius: 20px;
            padding: 5px 10px;
        }
        .search-bar input { 
            padding: 5px; 
            margin-right: 5px; 
            border: none;
            background-color: transparent;
            outline: none;
        }
        .search-bar button {
            background: none;
            border: none;
            cursor: pointer;
        }
        .menu-categories { 
            display: flex; 
            margin-bottom: 20px; 
            overflow-x: auto;
            padding-bottom: 10px;
        }
        .menu-categories button { 
            margin-right: 10px; 
            padding: 8px 15px; 
            background-color: #f0f0f0; 
            border: none; 
            cursor: pointer; 
            border-radius: 20px;
            transition: background-color 0.3s;
        }
        .menu-categories button:hover, .menu-categories button.active {
            background-color: #4267B2;
            color: white;
        }
        .menu-grid { 
            display: grid; 
            grid-template-columns: repeat(auto-fill, minmax(200px, 1fr)); 
            gap: 20px; 
        }
        .menu-item { 
            text-align: center; 
            background-color: white;
            border-radius: 10px;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
            transition: transform 0.3s;
            cursor: pointer;
        }
        .menu-item:hover {
            transform: translateY(-5px);
        }
        .menu-item img { 
            width: 100%; 
            height: 150px; 
            object-fit: cover;
            border-radius: 10px 10px 0 0;
        }
        .menu-item p {
            margin: 10px 0;
        }
        .menu-item p:last-child {
            color: #4CAF50;
            font-weight: bold;
        }
        .order-summary { 
            background-color: #f9f9f9; 
            padding: 20px; 
            margin-top: 20px;
            border-radius: 10px;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
        }
        .order-summary table { 
            width: 100%; 
            border-collapse: collapse; 
        }
        .order-summary th, .order-summary td { 
            padding: 10px; 
            text-align: left; 
            border-bottom: 1px solid #ddd; 
        }
        .quantity-control { 
            display: flex; 
            justify-content: center; 
            align-items: center; 
        }
        .quantity-control button { 
            width: 30px; 
            height: 30px; 
            font-size: 18px; 
            border: none; 
            background-color: #e0e0e0; 
            cursor: pointer;
            border-radius: 50%;
            transition: background-color 0.3s;
        }
        .quantity-control button:hover {
            background-color: #d0d0d0;
        }
        .quantity-control span {
            margin: 0 10px;
        }
        .action-buttons { 
            display: flex; 
            justify-content: space-between; 
            margin-top: 20px; 
        }
        .action-buttons button { 
            padding: 10px 20px; 
            font-size: 16px; 
            border: none; 
            cursor: pointer;
            border-radius: 5px;
            transition: background-color 0.3s;
        }
        #printButton { background-color: #4CAF50; color: white; }
        #notifyButton { background-color: #FF69B4; color: white; }
        #paymentButton { background-color: #4CAF50; color: white; }
        #printButton:hover, #paymentButton:hover { background-color: #45a049; }
        #notifyButton:hover { background-color: #ff5ca8; }
    </style>
</head>
<body>
    <div class="sidebar">
        <h2>Phòng bàn</h2>
        <ul>
            <li><a href="#" class="active">Thực đơn</a></li>
            <li><a href="#">Đơn hàng</a></li>
            <li><a href="#">Khách hàng</a></li>
            <li><a href="#">Nhân viên</a></li>
            <li><a href="#">Nguyên liệu</a></li>
            <li><a href="#">Hóa đơn</a></li>
            <li><a href="#">Lương</a></li>
        </ul>
    </div>
    <div class="main-content">
        <div class="header">
            <div class="search-bar">
                <input type="text" placeholder="Search...">
                <button>🔍</button>
            </div>
            <h2>Phòng 1 | Bàn 6</h2>
        </div>
        <div class="menu-categories">
            <button class="active">Tất cả</button>
            <button>Khai vị</button>
            <button>Gỏi</button>
            <button>Cơm</button>
            <button>Lẩu</button>
            <button>Món chính</button>
            <button>Combo</button>
        </div>
        <div class="menu-grid">
            <div class="menu-item">
                <img src="https://via.placeholder.com/200x150?text=Chả+ram+tôm+đất" alt="Chả ram tôm đất">
                <p>Chả ram tôm đất</p>
                <p>30.000đ</p>
            </div>
            <div class="menu-item">
                <img src="https://via.placeholder.com/200x150?text=Salad+Tôm" alt="Salad Tôm">
                <p>Salad Tôm</p>
                <p>50.000đ</p>
            </div>
            <!-- Add more menu items here -->
        </div>
        <form action="order" method="POST">
            <div class="order-summary">
                <h3>Đơn hàng</h3>
                <table>
                    <tr>
                        <th>Tên món</th>
                        <th>Số lượng</th>
                        <th>Đơn giá</th>
                        <th>Thành tiền</th>
                    </tr>
                    <%
                        Order order = (Order) request.getAttribute("order");
                        if (order != null) {
                            List<OrderItem> orderItems = order.getOrderItems();
                            for (OrderItem item : orderItems) {
                    %>
                    <tr>
                        <td><%= item.getName() %></td>
                        <td>
                            <div class="quantity-control">
                                <button type="submit" name="action" value="decrease_<%= item.getId() %>">-</button>
                                <span><%= item.getQuantity() %></span>
                                <button type="submit" name="action" value="increase_<%= item.getId() %>">+</button>
                            </div>
                        </td>
                        <td><%= String.format("%,d", item.getPrice()) %>đ</td>
                        <td><%= String.format("%,d", item.getPrice() * item.getQuantity()) %>đ</td>
                    </tr>
                    <%
                            }
                        }
                    %>
                </table>
                <p>Tổng tiền: <%= order != null ? String.format("%,d", order.getTotalAmount()) : "0" %>đ</p>
                <div class="action-buttons">
                    <button id="printButton" type="submit" name="action" value="print">IN</button>
                    <button id="notifyButton" type="submit" name="action" value="notify">THÔNG BÁO</button>
                    <button id="paymentButton" type="submit" name="action" value="payment">THANH TOÁN</button>
                </div>
            </div>
        </form>
    </div>
    <script>
        // Add interactivity
        document.querySelectorAll('.menu-categories button').forEach(button => {
            button.addEventListener('click', function() {
                document.querySelector('.menu-categories button.active').classList.remove('active');
                this.classList.add('active');
                // Here you would typically filter the menu items based on the selected category
            });
        });

        document.querySelectorAll('.menu-item').forEach(item => {
            item.addEventListener('click', function() {
                // Here you would typically add the item to the order
                console.log('Added to order:', this.querySelector('p').textContent);
            });
        });
    </script>
</body>
</html>