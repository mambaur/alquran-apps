package com.example.alquran_apps.models;

public class JadwalModel {
    private String tanggal, imsyak, shubuh, terbit, dhuha, dzuhur, ashr, magrib, isya;

    public JadwalModel(String tanggal, String imsyak, String shubuh, String terbit, String dhuha, String dzuhur, String ashr, String magrib, String isya) {
        this.tanggal = tanggal;
        this.imsyak = imsyak;
        this.shubuh = shubuh;
        this.terbit = terbit;
        this.dhuha = dhuha;
        this.dzuhur = dzuhur;
        this.ashr = ashr;
        this.magrib = magrib;
        this.isya = isya;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getImsyak() {
        return imsyak;
    }

    public void setImsyak(String imsyak) {
        this.imsyak = imsyak;
    }

    public String getShubuh() {
        return shubuh;
    }

    public void setShubuh(String shubuh) {
        this.shubuh = shubuh;
    }

    public String getTerbit() {
        return terbit;
    }

    public void setTerbit(String terbit) {
        this.terbit = terbit;
    }

    public String getDhuha() {
        return dhuha;
    }

    public void setDhuha(String dhuha) {
        this.dhuha = dhuha;
    }

    public String getDzuhur() {
        return dzuhur;
    }

    public void setDzuhur(String dzuhur) {
        this.dzuhur = dzuhur;
    }

    public String getAshr() {
        return ashr;
    }

    public void setAshr(String ashr) {
        this.ashr = ashr;
    }

    public String getMagrib() {
        return magrib;
    }

    public void setMagrib(String magrib) {
        this.magrib = magrib;
    }

    public String getIsya() {
        return isya;
    }

    public void setIsya(String isya) {
        this.isya = isya;
    }
}