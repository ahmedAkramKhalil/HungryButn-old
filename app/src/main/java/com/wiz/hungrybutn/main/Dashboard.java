package com.wiz.hungrybutn.main;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.UiAutomation;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.OnCompositionLoadedListener;
import com.skydoves.transformationlayout.TransformationLayout;
import com.skydoves.transformationlayout.TransitionExtensionKt;
import com.wiz.hungrybutn.LocaleHelper;
import com.wiz.hungrybutn.R;
import com.wiz.hungrybutn.chef.InvoiceActivity;
import com.wiz.hungrybutn.network.Config;
import com.wiz.hungrybutn.network.NetworkHandler;
import com.wiz.hungrybutn.print.ImagePrint;

import java.util.HashMap;

import io.realm.Realm;

public class Dashboard extends AppCompatActivity {

    ConstraintLayout rootLayout;
    TransformationLayout startBtn;
    LottieAnimationView button;
    boolean internetAvailable = false;
    public static HashMap<String, Integer> componentImages;
    Thread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        TransitionExtensionKt.onTransformationStartContainer(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Realm.init(this);
        componentImages = new HashMap<>();
        setComponentImages();
        startBtn = findViewById(R.id.start_trans);
        button = findViewById(R.id.start_button);
        LocaleHelper.setLocale(Dashboard.this, "en");
        internetAvailable = NetworkHandler.isInternetAvailable();
        if (!NetworkHandler.isInternetAvailable()) {
            Toast.makeText(getApplicationContext(), "No Internet Connection Please Check Connection And try Again", Toast.LENGTH_LONG).show();
        }

        rootLayout = findViewById(R.id.root_dashboard);
//        Realm realm = Realm.getDefaultInstance();
//        realm.executeTransaction(new Realm.Transaction() {
//            @Override
//            public void execute(Realm realm) {
////                realm.deleteAll();
//            }
//        });

        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (NetworkHandler.isInternetAvailable()) {
                        try {


                            NetworkHandler.getInstance(getApplicationContext()).register(Config.REG_URL, Dashboard.this);
                            NetworkHandler.getInstance(getApplicationContext()).getComponents(Config.GET_COMPONENT_URL, Dashboard.this);
//                            NetworkHandler.getInstance(getApplicationContext()).getProducts(Config.GET_PRODUCT_URL, Dashboard.this);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        break;

                    } else {
                        Toast.makeText(getApplicationContext(), "No Internet Connection Please Check Connection And try Again", Toast.LENGTH_LONG).show();

                    }

                }
            }
        });
        thread.start();


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Dashboard.this, LanguageChooser.class);
//                TransformationCompat.INSTANCE.startActivity(startBtn, intent);
                startActivity(intent);

//                startAnimation(view);

            }
        });


    }

    public void setComponentImages() {
        componentImages.put("brioche bun", R.drawable.broiche_bun_1);
        componentImages.put("brioche bun red", R.drawable.broiche_bun__r1);
        componentImages.put("brioche bun yellow", R.drawable.broiche_bun__y1);
        componentImages.put("brioche bun black", R.drawable.broiche_bun__b1);
        componentImages.put("Toast", R.drawable.toast);
        componentImages.put("Deep Fried Cheese", R.drawable.deep_fried);
        componentImages.put("deep fried mac&cheese", R.drawable.deep_fried);
        componentImages.put("Meat 140g", R.drawable.meat_2);
        componentImages.put("Meat 170g", R.drawable.meat);
        componentImages.put("(KFC) Deep Fried Chicken", R.drawable.fried_chicken);
        componentImages.put("Caramelized Onion", R.drawable.caramelized_onion);
        componentImages.put("Grilled Pineapple", R.drawable.grilled_pineapple);
        componentImages.put("Egg", R.drawable.egg_p);
        componentImages.put("Mac&Cheese", R.drawable.mac_cheese);
        componentImages.put("Deep Fried Mac&Cheese", R.drawable.deep_fried);
        componentImages.put("Deep Fried Cheese", R.drawable.deep_fried);
        componentImages.put("Cheese Sticks", R.drawable.cheese_sticks);
        componentImages.put("Onion Rings", R.drawable.onion_rings);
        componentImages.put("Mushrooms", R.drawable.mushrooms);
        componentImages.put("Pickles", R.drawable.pickles);
        componentImages.put("Lettuce", R.drawable.lettuce);
        componentImages.put("Chopped Onion", R.drawable.chopped_onion);
        componentImages.put("Tomato", R.drawable.tomato_p);
        componentImages.put("Jalapeños", R.drawable.jalapenos);
        componentImages.put("Sauce1", R.drawable.sauce1);
        componentImages.put("Sauce2", R.drawable.sauce2);
        componentImages.put("Sauce3", R.drawable.sauce3);
        componentImages.put("Sauce4", R.drawable.sauce4);
        componentImages.put("Sauce5", R.drawable.sauce5);
        componentImages.put("Chips", R.drawable.chibs);
        componentImages.put("Doritos", R.drawable.doritos);
    }

    @Override
    protected void onStart() {
        ImagePrint.getInstance(Dashboard.this).CheckAllPermission();
        ImagePrint.getInstance(Dashboard.this).showUSBDeviceChooseDialog();
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (thread != null) {
            thread.stop();
        }

    }

//    public void startAnimation(final LottieAnimationView view) {
//        try {
//
//
////            ValueAnimator animator = ValueAnimator.ofFloat(ANIM_START, ANIM_END).setDuration(500);
////            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
////                @Override
////                public void onAnimationUpdate(ValueAnimator animation) {
////                    view.setProgress((float) animation.getAnimatedValue());
////                }
////            });
////            animator.start();
//
//            view.setAnimation(R.raw.run);
//
//            view.playAnimation();
//
////            view.setAnimation(component.json);
////            view.loop(true);
////            view.playAnimation();
//
//            Log.e("compone99nt", "startAnimation");
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
