package entities;

public class Phong {
    private int id;
    private String tenPhong;

    public Phong(int id, String tenPhong) {
        this.id = id;
        this.tenPhong = tenPhong;
    }

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getTenPhong() { return tenPhong; }
    public void setTenPhong(String tenPhong) { this.tenPhong = tenPhong; }
}