package com.wiz.hungrybutn.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.skydoves.transformationlayout.TransformationAppCompatActivity;
import com.skydoves.transformationlayout.TransformationCompat;
import com.skydoves.transformationlayout.TransformationLayout;
import com.skydoves.transformationlayout.TransitionExtensionKt;
import com.wiz.hungrybutn.R;
import com.wiz.hungrybutn.chef.ChefActivity;
import com.wiz.hungrybutn.network.Config;
import com.wiz.hungrybutn.network.SharedPreferanceHandler;

public class WhereEat extends AppCompatActivity {
    LinearLayout eatIN;
    FrameLayout  takeAway;
    TextView eat, away;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        TransitionExtensionKt.onTransformationStartContainer(this);
        final FrameLayout  takeL ;
        final LinearLayout inL ;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_where_eat);
        inL = findViewById(R.id.in_tran);
        takeL = findViewById(R.id.take_tran) ;

        eatIN = findViewById(R.id.eat_in_layout);
        takeAway = findViewById(R.id.take_away_layout);
        eat = findViewById(R.id.eat_in_tv);
        away = findViewById(R.id.away_tv);
        if(SharedPreferanceHandler.getInstance(WhereEat.this).getString(Config.Key_LANGUAGE).equalsIgnoreCase(Config.LANGUAGE_AR))
        {
           eat.setText(R.string.eat_in_ar);
           away.setText(R.string.take_away_ar);
        }else {

            eat.setText(R.string.eat_in);
            away.setText(R.string.take_away);

        }
        eatIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 try {
                     SharedPreferanceHandler.getInstance(WhereEat.this).putInt(Config.Key_TOGO, 0);
                     Intent intent = new Intent(WhereEat.this, ChefActivity.class);
//                     TransformationCompat.INSTANCE.startActivity(inL, intent);

                 }catch (Exception e){
                     e.printStackTrace();
                 }
                startActivity(new Intent(WhereEat.this, ChefActivity.class));

            }
        });
        takeAway.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferanceHandler.getInstance(WhereEat.this).putInt(Config.Key_TOGO, 1);
                Intent intent = new Intent(WhereEat.this, ChefActivity.class);
//                TransformationCompat.INSTANCE.startActivity(takeL, intent);

                startActivity(new Intent(WhereEat.this, ChefActivity.class));
            }
        });
    }
}
