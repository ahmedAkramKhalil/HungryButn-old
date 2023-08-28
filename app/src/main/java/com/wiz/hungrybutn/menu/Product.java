package com.wiz.hungrybutn.menu;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Product extends RealmObject implements Parcelable {
    @PrimaryKey
    private int id ;
    private int user_id ;
    private int category_id ;
    private int photo_id ;
    private String description ;
    private float price ;
    private float sale_price ;
    private int is_active ;
    private int type ;
    private int reviews ;
    private int reviews_score ;
    private String arName ;
    private String enName ;
    public Product() {
    }

    public Product(int id, int user_id, int category_id, int photo_id, String description, float price, float sale_price, int is_active, int type, int reviews, int reviews_score, String arName, String enName) {
        this.id = id;
        this.user_id = user_id;
        this.category_id = category_id;
        this.photo_id = photo_id;
        this.description = description;
        this.price = price;
        this.sale_price = sale_price;
        this.is_active = is_active;
        this.type = type;
        this.reviews = reviews;
        this.reviews_score = reviews_score;
        this.arName = arName;
        this.enName = enName;
    }


    protected Product(Parcel in) {
        id = in.readInt();
        arName = in.readString();
        enName = in.readString();
        price = in.readFloat();
        user_id =in.readInt();
        category_id = in.readInt();
        photo_id = in.readInt();
        description = in.readString();
        sale_price = in.readFloat();
        is_active = in.readInt();
        type = in.readInt();;
        reviews = in.readInt();
        reviews_score = in.readInt();

    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getSale_price() {
        return sale_price;
    }

    public void setSale_price(float sale_price) {
        this.sale_price = sale_price;
    }

    public int getIs_active() {
        return is_active;
    }

    public void setIs_active(int is_active) {
        this.is_active = is_active;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getReviews() {
        return reviews;
    }

    public void setReviews(int reviews) {
        this.reviews = reviews;
    }

    public int getReviews_score() {
        return reviews_score;
    }

    public void setReviews_score(int reviews_score) {
        this.reviews_score = reviews_score;
    }

    public String getArName() {
        return arName;
    }

    public void setArName(String arName) {
        this.arName = arName;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }


    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(arName);
        dest.writeString(enName);
        dest.writeFloat(price);
        dest.writeInt(user_id);
        dest.writeInt(category_id);
        dest.writeInt(photo_id);
        dest.writeString(description);
        dest.writeFloat(sale_price);
        dest.writeInt(is_active);
        dest.writeInt(type);
        dest.writeInt(reviews);
        dest.writeInt(reviews_score);
    }
}
