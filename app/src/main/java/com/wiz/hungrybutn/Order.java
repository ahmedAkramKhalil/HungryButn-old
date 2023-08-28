package com.wiz.hungrybutn;

import com.wiz.hungrybutn.chef.Component;
import com.wiz.hungrybutn.menu.Product;

import java.util.List;

public class Order {
    public final static  int TAKE_AWAY = 1 ;
    public final static  int EAT_IN = 0 ;

    public   int user_id ;
    public   String name ;
    public  List<Integer> ingredients ;
//    public  List<Product> products ;
    public  float price ;
    public  int to_go ;
    public  String name_en ;
    public String option ;
    public int option_type ;

}
