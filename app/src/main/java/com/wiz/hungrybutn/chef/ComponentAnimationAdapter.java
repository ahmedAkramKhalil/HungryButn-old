package com.wiz.hungrybutn.chef;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieComposition;
import com.google.android.material.datepicker.SingleDateSelector;
import com.wiz.hungrybutn.R;
import com.wiz.hungrybutn.main.Dashboard;

import java.util.List;

public class ComponentAnimationAdapter extends RecyclerView.Adapter<ComponentAnimationAdapter.ViewHolder> {

    private static final float ANIM_START = 0.5F;
    private static final float ANIM_END = 0.9F;
    private final int itemCount = 50;
    private int[] backgrounds = new int[2];
    private List<ComponentAnimation> components;
    private LayoutInflater inflater;
    int pos;
    int aPosition;
    Context context ;

    public ComponentAnimationAdapter(Context context, List<ComponentAnimation> components) {
        this.inflater = LayoutInflater.from(context);
        this.components = components;
//        this.pos = position;
        this.context = context ;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.anim_item, parent, false);
//        View tView = LayoutInflater.from(parent.getContext()).inflate(R.layout.anim_item, parent, false);
//        ViewHolder trackViewHolder = new ViewHolder(tView , 1);
//        int position = trackViewHolder.getAdapterPosition();
//        if (position>0) {
//            final ComponentAnimation component = components.get(position);
//
//            startAnimation(component, trackViewHolder.view);
//        }
        return  new ViewHolder(view,0) ;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final ComponentAnimation component = components.get(position);
        Log.e("component" , "onBindViewHolder") ;
        if (!component.isPlayed())
        startAnimation(component,holder.view);

    }

    @Override
    public int getItemCount() {
        return components.size();
    }

    public void addItems(List<ComponentAnimation> newItems) {
        this.components.addAll(newItems);
        notifyItemRangeInserted(this.components.size(), newItems.size());
    }
    public void removeLastItem() {
        this.components.remove(components.size() - 1);
        notifyItemRemoved(components.size());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private LottieAnimationView view;
        private int backgroundIndex = 0;

        ViewHolder(View itemView, int backgroundIndex) {
            super(itemView);
//            this.backgroundIndex = backgroundIndex;
            view =  itemView.findViewById(R.id.anim_view);


        }
    }

    public void startAnimation(ComponentAnimation component , final LottieAnimationView view ){
        try {


//           view.setAnimation(component.json);
//            ValueAnimator animator = ValueAnimator.ofFloat(ANIM_START, ANIM_END).setDuration(500);
//        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                view.setProgress((float) animation.getAnimatedValue());
//            }
//        });
//            animator.start();
//            view.playAnimation();
//
////            view.setAnimation(component.json);
////            view.loop(true);
////            view.playAnimation();
//
            Log.e("component", "startAnimation");
            view.setAnimation(component.getJson());
//            view.setAnimation(component.json);

            view.playAnimation();

            component.setPlayed(true);
        }catch (Exception e ){
            e.printStackTrace();
        }
    }
    public void addComponent(ComponentAnimation component , int index){
        this.components.add(index,component);
        notifyItemChanged(index);
    }
    public void removeComponent(int index){
        this.components.remove(index);
        notifyItemRemoved(index);
    }

    public void setList(List<ComponentAnimation> components, int position) {
        this.components = components;
        this.pos = position;
    }


}
