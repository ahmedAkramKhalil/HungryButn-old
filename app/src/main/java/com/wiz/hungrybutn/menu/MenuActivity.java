package com.wiz.hungrybutn.menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.mlsdev.animatedrv.AnimatedRecyclerView;
import com.wiz.hungrybutn.R;

import java.util.ArrayList;
import java.util.List;

public class MenuActivity extends AppCompatActivity  implements MenuAdapter.ItemClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        List<Category > categories = new ArrayList<>();
        Category category = new Category();
        for (int i = 0 ; i < 8  ; i++){
            categories.add(category);
        }

        MenuAdapter adapter = new MenuAdapter(this,categories);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);

        RecyclerView recyclerView = (AnimatedRecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(mLayoutManager);

        adapter.notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();


    }

    @Override
    public void onItemClick(View view, int position) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
