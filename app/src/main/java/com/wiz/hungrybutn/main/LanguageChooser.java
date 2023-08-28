package com.wiz.hungrybutn.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.skydoves.transformationlayout.TransformationAppCompatActivity;
import com.skydoves.transformationlayout.TransformationCompat;
import com.skydoves.transformationlayout.TransformationLayout;
import com.skydoves.transformationlayout.TransitionExtensionKt;
import com.wiz.hungrybutn.R;
import com.wiz.hungrybutn.chef.ChefActivity;
import com.wiz.hungrybutn.network.Config;
import com.wiz.hungrybutn.network.SharedPreferanceHandler;

import static com.skydoves.transformationlayout.TransitionExtensionKt.onTransformationStartContainer;

public class LanguageChooser extends AppCompatActivity {
    Button enBtn , arBtn ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        TransitionExtensionKt.onTransformationStartContainer(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_chooser);
        arBtn = findViewById(R.id.button_ar) ;
        enBtn = findViewById(R.id.button_en) ;
        arBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferanceHandler.getInstance(LanguageChooser.this).putString(Config.Key_LANGUAGE,Config.LANGUAGE_AR);
                Intent intent = new Intent(LanguageChooser.this, ChefActivity.class);
                startActivity(intent);
//                TransformationCompat.INSTANCE.startActivity(arL, intent);
//                LocaleHelper.setLocale(LanguageChooser.this, "ar");


            }
        });
        enBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferanceHandler.getInstance(LanguageChooser.this).putString(Config.Key_LANGUAGE,Config.LANGUAGE_EN);
                Intent intent = new Intent(LanguageChooser.this, ChefActivity.class);
                startActivity(intent);
//                TransformationCompat.INSTANCE.startActivity(enL, intent);
//                LocaleHelper.setLocale(LanguageChooser.this, "en");

            }
        });

    }
}
