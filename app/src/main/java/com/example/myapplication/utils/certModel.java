package com.example.myapplication.utils;

public class certModel {


   public String title,price,desc,image,addetails,area,latlong,search,id,name;


    public certModel() {
    }

    public certModel(String title,String image) {
        this.image = image;
        this.title=title;
    }

   // public String getAddetails() {return addetails; }

    public String getName(){return name;}

    public String getArea() {
        return area;
    }

    public String getSearch() {
        return search;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getLatlong() {
        return latlong;
    }

    public void setLatlong(String latlong) {
        this.latlong = latlong;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAddetails() {
        return addetails;
    }

    public void setAddetails(String addetails) {
        this.addetails = addetails;
    }
}
