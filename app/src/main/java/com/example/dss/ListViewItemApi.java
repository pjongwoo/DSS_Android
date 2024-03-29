package com.example.dss;

public class ListViewItemApi {

    String company_name;
    String div_name;
    String big_image;
    String name;

    String validity; //유호기간
    String ingredient_detail; //약성분
    String manufacturing; // 효능
    String usage;// 주의사항

    public String getValidity() {
        return validity;
    }

    public void setValidity(String validity) {
        this.validity = validity;
    }

    public String getManufacturing() {
        return manufacturing;
    }

    public void setManufacturing(String manufacturing) {
        this.manufacturing = manufacturing;
    }

    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    public String getIngredient_detail() {
        return ingredient_detail;
    }

    public void setIngredient_detail(String ingredient_detail) {
        this.ingredient_detail = ingredient_detail;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getDiv_name() {
        return div_name;
    }

    public void setDiv_name(String div_name) {
        this.div_name = div_name;
    }

    public String getBig_image() {
        return big_image;
    }

    public void setBig_image(String big_image) {
        this.big_image = big_image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
