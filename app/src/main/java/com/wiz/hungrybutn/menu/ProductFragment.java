package com.wiz.hungrybutn.menu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mlsdev.animatedrv.AnimatedRecyclerView;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;
import com.wiz.hungrybutn.R;

import java.util.ArrayList;
import java.util.List;


public class ProductFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_menu_item, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int position = FragmentPagerItem.getPosition(getArguments());
//        TextView title = (TextView) view.findViewById(R.id.item_title);
//        title.setText(String.valueOf(position));

        Product product = new Product() ;
        List<Product>  componentList = new ArrayList<>() ;
          componentList.add(product);

        ProductAdapter componentAdapter = new ProductAdapter(getContext(),componentList);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 1);

        RecyclerView recyclerView = (AnimatedRecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setAdapter(componentAdapter);
        recyclerView.setLayoutManager(mLayoutManager);

        componentAdapter.notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();



    }}

