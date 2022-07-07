package com.ichmal.jokiapp;

public class Order {
    private String nama, email, phone, idAkun, passAkun, tierAkun, status, tipeOrder, paket, role, username, tanggal, userID;
    private int orderBintang, harga, total;

    public Order(){

    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIdAkun() {
        return idAkun;
    }

    public void setIdAkun(String idAkun) {
        this.idAkun = idAkun;
    }

    public String getPassAkun() {
        return passAkun;
    }

    public void setPassAkun(String passAkun) {
        this.passAkun = passAkun;
    }

    public String getTierAkun() {
        return tierAkun;
    }

    public void setTierAkun(String tierAkun) {
        this.tierAkun = tierAkun;
    }

    public int getOrderBintang() {
        return orderBintang;
    }

    public void setOrderBintang(int orderBintang) {
        this.orderBintang = orderBintang;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTipeOrder() {
        return tipeOrder;
    }

    public void setTipeOrder(String tipeOrder) {
        this.tipeOrder = tipeOrder;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getPaket() {
        return paket;
    }

    public void setPaket(String paket) {
        this.paket = paket;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
