<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="entities.MenuItem"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>The Sea Restaurant - Menu</title>

</head>
<body>
    <!-- Search Bar -->
    <div class="search-bar">
        <input type="text" placeholder="Bạn cần tìm gì....">
    </div>

    <!-- Navigation -->
    <div class="nav-container">
        <div class="logo">The Sea Restaurant</div>
        <div class="main-nav">
            <a href="#">TRANG CHỦ</a>
            <a href="#" class="active">MENU</a>
            <a href="#">ĐẶT TIỆC</a>
            <a href="#">LIÊN HỆ</a>
        </div>
    </div>

    <!-- Main Content -->
    <div class="container">
        <!-- Sidebar -->
        <div class="sidebar">
            <h3>Bộ lọc sản phẩm</h3>
            <div class="filter-section">
                <input type="text" placeholder="Tìm Loại">
                <h4>Loại</h4>
                <label>
                    <input type="checkbox"> Khai Vị
                </label>
                <label>
                    <input type="checkbox"> Món khai vị
                </label>
            </div>
        </div>

        <!-- Menu Content -->
        <div class="menu-content">
            <div class="category-title">Khai vị</div>
            <div class="menu-grid">
                <%
                    List<MenuItem> menuItems = (List<MenuItem>) request.getAttribute("menuItems");
                    if (menuItems == null || menuItems.isEmpty()) {
                %>
                    <div class="no-data">No menu items available. Please check back later!</div>
                <%
                    } else {
                        for (MenuItem item : menuItems) {
                %>
                    <div class="menu-item">
                        <img src="images/<%= item.getHinhAnh() %>" alt="<%= item.getTenMon() %>">
                        <h3><%= item.getTenMon() %></h3>
                        <p><%= item.getGia() %> VND</p>
                    </div>
                <%
                        }
                    }
                %>
            </div>
        </div>
    </div>

    <!-- Shopping Cart Icon -->
    <div class="cart-icon">
        🛒
    </div>
</body>

    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: Arial, sans-serif;
            background-color: #f0f0f0;
        }

        /* Top Search Bar */
        .search-bar {
            background-color: #1a4b72;
            padding: 10px 0;
            text-align: center;
        }

        .search-bar input {
            width: 300px;
            padding: 8px;
            border-radius: 20px;
            border: none;
            background-color: #2c5d84;
            color: white;
        }

        .search-bar input::placeholder {
            color: #fff;
            opacity: 0.7;
        }

        /* Navigation */
        .nav-container {
            background-color: rgba(128, 128, 128, 0.8);
            padding: 15px 0;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .logo {
            margin-left: 20px;
            color: white;
            font-style: italic;
            font-size: 24px;
        }

        .main-nav {
            display: flex;
            gap: 30px;
            margin-right: 20px;
        }

        .main-nav a {
            color: white;
            text-decoration: none;
            font-weight: bold;
        }

        .main-nav a.active {
            color: #4CAF50;
        }

        /* Main Container */
        .container {
            display: flex;
            max-width: 1200px;
            margin: 20px auto;
            background-color: white;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }

        /* Sidebar */
        .sidebar {
            width: 250px;
            padding: 20px;
            border-right: 1px solid #ddd;
        }

        .sidebar h3 {
            color: #333;
            margin-bottom: 15px;
        }

        .sidebar input[type="text"] {
            width: 100%;
            padding: 8px;
            margin-bottom: 15px;
            border: 1px solid #ddd;
            border-radius: 4px;
        }

        .sidebar .filter-section {
            margin-bottom: 20px;
        }

        .sidebar label {
            display: block;
            margin-bottom: 8px;
        }

        /* Menu Grid */
        .menu-content {
            flex: 1;
            padding: 20px;
        }

        .category-title {
            background-color: #87CEEB;
            color: white;
            padding: 10px;
            margin-bottom: 20px;
        }

        .menu-grid {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
            gap: 20px;
        }

        .menu-item {
            border: 1px solid #ddd;
            padding: 10px;
            text-align: center;
            transition: transform 0.2s;
        }

        .menu-item:hover {
            transform: translateY(-5px);
            box-shadow: 0 5px 15px rgba(0,0,0,0.1);
        }

        .menu-item img {
            width: 100%;
            height: 150px;
            object-fit: cover;
            margin-bottom: 10px;
        }

        .menu-item h3 {
            color: #333;
            margin: 10px 0;
        }

        .menu-item p {
            color: #4CAF50;
            font-weight: bold;
        }

        /* Shopping Cart Icon */
        .cart-icon {
            position: fixed;
            top: 100px;
            right: 20px;
            background-color: white;
            padding: 10px;
            border-radius: 50%;
            box-shadow: 0 2px 5px rgba(0,0,0,0.2);
        }

        .no-data {
            text-align: center;
            font-size: 18px;
            color: #555;
            margin-top: 20px;
            grid-column: 1 / -1;
        }
    </style>
</html>