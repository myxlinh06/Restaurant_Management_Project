package entities;

public class Ban {
    private int id;
    private int idPhong;
    private String tenBan;
    private String status;

    public Ban(int id, int idPhong, String tenBan, String status) {
        this.id = id;
        this.idPhong = idPhong;
        this.tenBan = tenBan;
        this.status = status;
    }

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public int getIdPhong() { return idPhong; }
    public void setIdPhong(int idPhong) { this.idPhong = idPhong; }
    
    public String getTenBan() { return tenBan; }
    public void setTenBan(String tenBan) { this.tenBan = tenBan; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}