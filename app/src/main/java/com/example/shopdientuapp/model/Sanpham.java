package com.example.shopdientuapp.model;

import java.io.Serializable;

public class Sanpham implements Serializable {
    private int id;
    private String tensanpham;
    private int giasanpham;
    private String hinhansanpham;
    private String motasanpham;
    private int idsanpham;

    public Sanpham(int id, String tensanpham, int giasanpham, String hinhansanpham, String motasanpham, int idsanpham) {
        this.id = id;
        this.tensanpham = tensanpham;
        this.giasanpham = giasanpham;
        this.hinhansanpham = hinhansanpham;
        this.motasanpham = motasanpham;
        this.idsanpham = idsanpham;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTensanpham() {
        return tensanpham;
    }

    public void setTensanpham(String tensanpham) {
        this.tensanpham = tensanpham;
    }

    public int getGiasanpham() {
        return giasanpham;
    }

    public void setGiasanpham(int giasanpham) {
        this.giasanpham = giasanpham;
    }

    public String getHinhansanpham() {
        return hinhansanpham;
    }

    public void setHinhansanpham(String hinhansanpham) {
        this.hinhansanpham = hinhansanpham;
    }

    public String getMotasanpham() {
        return motasanpham;
    }

    public void setMotasanpham(String motasanpham) {
        this.motasanpham = motasanpham;
    }

    public int getIdsanpham() {
        return idsanpham;
    }

    public void setIdsanpham(int idsanpham) {
        this.idsanpham = idsanpham;
    }
}
