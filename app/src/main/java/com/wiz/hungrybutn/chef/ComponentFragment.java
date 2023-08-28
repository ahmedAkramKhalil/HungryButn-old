package com.wiz.hungrybutn.chef;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mlsdev.animatedrv.AnimatedRecyclerView;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;
import com.wiz.hungrybutn.R;
import com.wiz.hungrybutn.Utils;
import com.wiz.hungrybutn.menu.Category;
import com.wiz.hungrybutn.menu.ComponentCategory;

import java.util.ArrayList;
import java.util.List;


public class ComponentFragment extends Fragment implements ComponentAdapter.ItemClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chef, container, false);
    }
    ComponentAdapter componentAdapter ;
    RecyclerView recyclerView ;
    int position ;
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
         position = FragmentPagerItem.getPosition(getArguments());
        ChefActivity chefActivity = (ChefActivity) getActivity();
        List<ComponentCategory> componentCategories = ChefActivity.categories ;

        componentAdapter = new ComponentAdapter(getContext(), componentCategories.get(position).getComponents(), position);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2);

         recyclerView = (AnimatedRecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setAdapter(componentAdapter);
        recyclerView.setLayoutManager(mLayoutManager);

        componentAdapter.notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();



    }

    @Override
    public void onItemClick(View view, int position, int pos, int action) {
//        componentAdapter = new ComponentAdapter(getContext(), ChefActivity.componentMap.get(ChefActivity.componentMapPosition.get(position)) , position);
//        recyclerView.setAdapter(componentAdapter);
//
//
//        componentAdapter.notifyDataSetChanged();
//        componentAdapter.notify();
    }

    public void notifying(){
        componentAdapter.setList(ChefActivity.componentMap.get(ChefActivity.componentMapPosition.get(position)) , position);
        componentAdapter.notifyDataSetChanged();
        componentAdapter.notify();

    }
}

