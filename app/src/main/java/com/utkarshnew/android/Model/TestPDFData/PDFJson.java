
package com.utkarshnew.android.Model.TestPDFData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.utkarshnew.android.Model.CoursePDF;

import java.io.Serializable;
import java.util.ArrayList;

public class PDFJson implements Serializable {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("font_color")
    @Expose
    private String font_color;
    @SerializedName("bg_color")
    @Expose
    private String bg_color;
    @SerializedName("list")
    @Expose
    private ArrayList<CoursePDF> pdf_list = null;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFont_color() {
        return font_color;
    }

    public void setFont_color(String font_color) {
        this.font_color = font_color;
    }

    public String getBg_color() {
        return bg_color;
    }

    public void setBg_color(String bg_color) {
        this.bg_color = bg_color;
    }

    public ArrayList<CoursePDF> getPdf_list() {
        return pdf_list;
    }

    public void setPdf_list(ArrayList<CoursePDF> pdf_list) {
        this.pdf_list = pdf_list;
    }
}
