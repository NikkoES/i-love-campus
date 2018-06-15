package com.ilovecampus.ilovecampus.model.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Extras implements Serializable {

    @SerializedName("slider_1")
    String sliderSatu;
    @SerializedName("slider_2")
    String sliderDua;
    @SerializedName("slider_3")
    String sliderTiga;
    @SerializedName("slider_4")
    String sliderEmpat;
    @SerializedName("slider_5")
    String sliderLima;
    @SerializedName("visi")
    String visi;
    @SerializedName("misi")
    String misi;
    @SerializedName("tentang")
    String tentang;

    public Extras(String sliderSatu, String sliderDua, String sliderTiga, String sliderEmpat, String sliderLima, String visi, String misi, String tentang) {
        this.sliderSatu = sliderSatu;
        this.sliderDua = sliderDua;
        this.sliderTiga = sliderTiga;
        this.sliderEmpat = sliderEmpat;
        this.sliderLima = sliderLima;
        this.visi = visi;
        this.misi = misi;
        this.tentang = tentang;
    }

    public String getSliderSatu() {
        return sliderSatu;
    }

    public String getSliderDua() {
        return sliderDua;
    }

    public String getSliderTiga() {
        return sliderTiga;
    }

    public String getSliderEmpat() {
        return sliderEmpat;
    }

    public String getSliderLima() {
        return sliderLima;
    }

    public String getVisi() {
        return visi;
    }

    public String getMisi() {
        return misi;
    }

    public String getTentang() {
        return tentang;
    }
}
