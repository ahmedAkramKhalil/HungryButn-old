package com.wiz.hungrybutn.chef;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Component extends RealmObject implements Parcelable {
    @PrimaryKey
    private int id ;
    private String name;
    private String name_en;
    private float price  = 0 ;
    private int photo_id ;
    private String description ;
   // @Ignore @Expose (serialize = false, deserialize = false)
    boolean clicked = false;
  //  @Ignore @Expose(serialize = false, deserialize = false)
    int amount  = 0 ;

    public int getAmount() {
        return amount;
    }

    public void setAmount(int action) {
        Log.d("amount " , "" + action + " - " + this.amount) ;
        int i = this.amount ;

        if (action == 0 ){

         if (amount> 0 ){

             this.amount = i--;
         }
        }else if (action == 1 ){
            this.amount = i++;

        }

    }

    public Component() {
    }

    public Component(int id, String name, String name_en, float price, int photo_id, String description) {
        this.id = id;
        this.name = name;
        this.name_en = name_en;
        this.price = price;
        this.photo_id = photo_id;
        this.description = description;
    }

    protected Component(Parcel in) {
        id = in.readInt();
        name = in.readString();
        name_en = in.readString();
        price = in.readFloat();
        photo_id = in.readInt();
        description = in.readString();
    }

    public static final Creator<Component> CREATOR = new Creator<Component>() {
        @Override
        public Component createFromParcel(Parcel in) {
            return new Component(in);
        }

        @Override
        public Component[] newArray(int size) {
            return new Component[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getPhoto_id() {
        return photo_id;
    }

    public void setPhoto_id(int photo_id) {
        this.photo_id = photo_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(name_en);
        dest.writeFloat(price);
        dest.writeInt(photo_id);
        dest.writeString(description);
    }



}
