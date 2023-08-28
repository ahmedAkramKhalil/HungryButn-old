package com.wiz.hungrybutn.menu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wiz.hungrybutn.R;

import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {

    private final int itemCount = 50;
    private int[] backgrounds = new int[2];
    private List<Category> categories ;
    private LayoutInflater inflater ;
    private static ItemClickListener mClickListener;

    public MenuAdapter(Context context , List<Category> categories) {

        this.inflater = LayoutInflater.from(context);
        this.categories = categories ;



      //  backgrounds[0] = R.drawable.background_primary_light;
      //  backgrounds[1] = R.drawable.background_primary_dark;
      //  backgrounds[2] = R.drawable.background_primary_accent;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.layout_menu_item,parent,false);
       return new ViewHolder(view , 1 );
//        int index = (int) (Math.random() * 3);
//        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_item, parent, false), index);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
         Category component = categories.get(position);



         //holder.
       // holder.view.setBackgroundResource(backgrounds[holder.backgroundIndex]);

//        holder.cmName.setText(component.getEnName());
//        holder


    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        LinearLayout item ;


        ViewHolder(View itemView, int backgroundIndex) {
            super(itemView);
            item = (LinearLayout) itemView.findViewById(R.id.item);

              itemView.setOnClickListener(this);




        }

        @Override
        public void onClick(View v) {
            if (mClickListener != null) mClickListener.onItemClick(v, getAdapterPosition());

        }
    }

    static  void setClickListener(ItemClickListener itemClickListener) {
        mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

}
