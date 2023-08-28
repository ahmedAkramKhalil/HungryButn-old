package com.wiz.hungrybutn.menu;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Category extends RealmObject {
    @PrimaryKey
     private int id ;
     private  String name;
     private  String name_en;
     private int icon ;
    @SerializedName("products")
    private RealmList<Product> products ;

    public Category() {
    }

    public Category(String name, String name_en, int icon , RealmList<Product> products , int id) {
        this.id = id ;
        this.name = name;
        this.name_en = name_en;
        this.icon = icon;
        this.products = products ;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(RealmList<Product> products) {
        this.products = products;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName_en() {
        return name_en;
    }

    public void setName_en(String name_en) {
        this.name_en = name_en;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
