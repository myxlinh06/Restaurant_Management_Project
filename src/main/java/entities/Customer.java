package entities;

public class Customer {
    private int id;
    private String email;
    private String hoTen;
    private String phone;
    private String diaChi;
    private String tinh;
    private String huyen;
    private String xa;

    // Constructor
    public Customer(int id, String email, String hoTen, String phone, String diaChi, String tinh, String huyen, String xa) {
        this.id = id;
        this.email = email;
        this.hoTen = hoTen;
        this.phone = phone;
        this.diaChi = diaChi;
        this.tinh = tinh;
        this.huyen = huyen;
        this.xa = xa;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getTinh() {
        return tinh;
    }

    public void setTinh(String tinh) {
        this.tinh = tinh;
    }

    public String getHuyen() {
        return huyen;
    }

    public void setHuyen(String huyen) {
        this.huyen = huyen;
    }

    public String getXa() {
        return xa;
    }

    public void setXa(String xa) {
        this.xa = xa;
    }
}
