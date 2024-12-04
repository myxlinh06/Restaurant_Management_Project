package entities;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private int id;
    private String tableName;
    private List<OrderItem> orderItems;

    public Order() {
        this.orderItems = new ArrayList<>();
    }

    public Order(int id, String tableName) {
        this.id = id;
        this.tableName = tableName;
        this.orderItems = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public void addOrderItem(OrderItem item) {
        this.orderItems.add(item);
    }

    public void removeOrderItem(int itemId) {
        this.orderItems.removeIf(item -> item.getId() == itemId);
    }

    public int getTotalAmount() {
        return this.orderItems.stream()
                .mapToInt(item -> item.getPrice() * item.getQuantity())
                .sum();
    }
}