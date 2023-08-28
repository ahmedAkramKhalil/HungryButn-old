package com.wiz.hungrybutn.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wiz.hungrybutn.R;
import com.wiz.hungrybutn.chef.ChefActivity;
import com.wiz.hungrybutn.network.Config;
import com.wiz.hungrybutn.network.SharedPreferanceHandler;

public class MenuOrChef extends AppCompatActivity {
        LinearLayout chef , search ;
    TextView chefTV, searchTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_or_chef);


        chef = findViewById(R.id.chef_layout);
        search = findViewById(R.id.search_meal_layout);


        chefTV = findViewById(R.id.chef_tv);
        searchTV = findViewById(R.id.search_tv);



        if(SharedPreferanceHandler.getInstance(MenuOrChef.this).getString(Config.Key_LANGUAGE).equalsIgnoreCase(Config.LANGUAGE_AR))
        {
            chefTV.setText(R.string.hun_chef_ar);
            chefTV.setText(R.string.browse_created_meal_ar);

        }else {
            chefTV.setText(R.string.hun_chef);
            chefTV.setText(R.string.browse_created_meal);


        }


        chef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuOrChef.this, ChefActivity.class));
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuOrChef.this, ChefActivity.class));
            }
        });

    }
}
