package entities;

public class ChiTietBan {
	private int id;
	private int idPhong;
	private int idBan;
	private int idMon;
	private int soLuong;
	private double donGia;
	private double thanhTien;

	public ChiTietBan(int id, int idPhong, int idBan, int idMon, int soLuong, double donGia) {
		this.id = id;
		this.idPhong = idPhong;
		this.idBan = idBan;
		this.idMon = idMon;
		this.soLuong = soLuong;
		this.donGia = donGia;
		this.thanhTien = soLuong * donGia;
	}

	// Getters and setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdPhong() {
		return idPhong;
	}

	public void setIdPhong(int idPhong) {
		this.idPhong = idPhong;
	}

	public int getIdBan() {
		return idBan;
	}

	public void setIdBan(int idBan) {
		this.idBan = idBan;
	}

	public int getIdMon() {
		return idMon;
	}

	public void setIdMon(int idMon) {
		this.idMon = idMon;
	}

	public int getSoLuong() {
		return soLuong;
	}

	public void setSoLuong(int soLuong) {
		this.soLuong = soLuong;
		this.thanhTien = this.soLuong * this.donGia;
	}

	public double getDonGia() {
		return donGia;
	}

	public void setDonGia(double donGia) {
		this.donGia = donGia;
		this.thanhTien = this.soLuong * this.donGia;
	}

	public double getThanhTien() {
		return thanhTien;
	}
}