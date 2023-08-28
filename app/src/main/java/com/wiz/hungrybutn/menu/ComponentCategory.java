package com.wiz.hungrybutn.menu;

import com.google.gson.annotations.SerializedName;
import com.wiz.hungrybutn.chef.Component;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ComponentCategory extends RealmObject {
     @PrimaryKey
     private int id ;
     private  String name;
     private  String name_en;
     private int icon ;
    @SerializedName("ingredients")
    private RealmList<Component> Components;

    public ComponentCategory() {
    }

    public ComponentCategory(String name, String name_en, int icon , RealmList<Component> Components, int id) {
        this.id = id ;
        this.name = name;
        this.name_en = name_en;
        this.icon = icon;
        this.Components = Components;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Component> getComponents() {
        if (Components == null){
            return null;
        }
        return Components;
    }

    public void setComponents(RealmList<Component> components) {
        this.Components = components;
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
