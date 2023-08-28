package com.wiz.hungrybutn.chef;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.rt.printerlibrary.exception.SdkException;
import com.wiz.hungrybutn.R;
import com.wiz.hungrybutn.main.Dashboard;
import com.wiz.hungrybutn.menu.ComponentCategory;
import com.wiz.hungrybutn.print.ImagePrint;

import java.util.ArrayList;
import java.util.List;

public class InvoiceActivity extends AppCompatActivity {
    RecyclerView mRecyclerView;
    InvoiceAdapter invoiceAdapter;
    TextView priceTv;
    ConstraintLayout constraintLayout;
    Button print, cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice);
        constraintLayout = findViewById(R.id.printLayout);
        print = findViewById(R.id.done_btn);
        cancel = findViewById(R.id.cancel_btn);
        mRecyclerView = (RecyclerView) findViewById(R.id.item_list);
        priceTv = findViewById(R.id.total_tv);

        List<String> names = new ArrayList<>();
        List<String> prices = new ArrayList<>();
        for (ComponentCategory category : ChefActivity.categories) {
            for (Component component : category.getComponents()) {
                if (component.getAmount() > 0) {
                    names.add(component.getName_en());
                    if (component.getAmount() == 1) {
                        prices.add(component.getPrice() + "");

                    } else {
                        prices.add(component.getAmount() + " x " + component.getPrice());

                    }
                }
            }
        }
        invoiceAdapter = new InvoiceAdapter(getApplicationContext(), names, prices);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation_up_to_down);
        mRecyclerView.setAdapter(invoiceAdapter);
        mRecyclerView.setLayoutAnimation(controller);
        invoiceAdapter.notifyDataSetChanged();
        mRecyclerView.scheduleLayoutAnimation();
        //  invoiceAdapter.notifyDataSetChanged();
        //  mRecyclerView.scheduleLayoutAnimation();


        print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Bitmap bitmap = setViewToBitmapImage(constraintLayout);
                        try {
                            ImagePrint.getInstance(InvoiceActivity.this).CheckAllPermission();
                            ImagePrint.getInstance(InvoiceActivity.this).print(bitmap);
                        } catch (SdkException e) {
                            e.printStackTrace();
                        }

                    }
                });


                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(10000);
                            restartApp();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restartApp();

            }
        });
    }

    void restartApp() {
        Intent mStartActivity = new Intent(getApplicationContext(), Dashboard.class);
        int mPendingIntentId = 123456;
        PendingIntent mPendingIntent = PendingIntent.getActivity(getApplicationContext(), mPendingIntentId, mStartActivity, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager mgr = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent);
        System.exit(0);

    }

    @Override
    protected void onStart() {
        super.onStart();


    }

    @Override
    protected void onResume() {
        super.onResume();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).start();
        animateTextView(0, (int) ChefActivity.priceNum, priceTv);


    }

    public void animateTextView(int initialValue, int finalValue, final TextView textview) {
        DecelerateInterpolator decelerateInterpolator = new DecelerateInterpolator(0.9f);
        int start = Math.min(initialValue, finalValue);
        int end = Math.max(initialValue, finalValue);
        int difference = Math.abs(finalValue - initialValue);
        Handler handler = new Handler();
        for (int count = start; count <= end; count++) {
            int time = Math.round(decelerateInterpolator.getInterpolation((((float) count) / difference)) * 50) * count;
            final int finalCount = ((initialValue > finalValue) ? initialValue - count : count);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    textview.setText(String.valueOf(finalCount));
                }
            }, time);
        }
    }

    public static Bitmap setViewToBitmapImage(View view) {
//Define a bitmap with the same size as the view
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        //Bind a canvas to it
        Canvas canvas = new Canvas(returnedBitmap);
        //Get the view's background
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null)
            //has background drawable, then draw it on the canvas
            bgDrawable.draw(canvas);
//        else
//            //does not have background drawable, then draw white background on the canvas
//            canvas.drawColor(Color.WHITE);
        // draw the view on the canvas
        view.draw(canvas);
        //return the bitmap
        return returnedBitmap;
    }
}