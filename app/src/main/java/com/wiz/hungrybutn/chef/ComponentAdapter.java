package com.wiz.hungrybutn.chef;

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

import com.wiz.hungrybutn.R;
import com.wiz.hungrybutn.main.Dashboard;

import java.util.List;

public class ComponentAdapter extends RecyclerView.Adapter<ComponentAdapter.ViewHolder> {

    private final int itemCount = 50;
    private int[] backgrounds = new int[2];
    private List<Component> components;
    private LayoutInflater inflater;
    private static ItemClickListener mClickListener;
    int pos;
    int aPosition;
    Context context ;

    public ComponentAdapter(Context context, List<Component> components, int position) {

        this.inflater = LayoutInflater.from(context);
        this.components = components;
        this.pos = position;
        this.context = context ;


        //  backgrounds[0] = R.drawable.background_primary_light;
        //  backgrounds[1] = R.drawable.background_primary_dark;
        //  backgrounds[2] = R.drawable.background_primary_accent;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.layout_list_component, parent, false);
        return new ViewHolder(view, 1);
//        int index = (int) (Math.random() * 3);
//        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_item, parent, false), index);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final Component component = components.get(position);
        if (component.getAmount() > 0) {

            holder.button_layout.setVisibility(View.VISIBLE);
            holder.nameBtn.setText("" + component.getAmount());
        } else {
            holder.button_layout.setVisibility(View.GONE);

        }

        try{

            if (ChefActivity.ar){
                holder.cmName.setText(component.getName());
            }else {
                holder.cmName.setText(component.getName_en());

            }
            Log.d("fffffff" ,component.getName_en() ) ;
            holder.cmImg.setImageResource(Dashboard.componentImages.get(component.getName_en()));




            holder.minBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (mClickListener != null)
                        mClickListener.onItemClick(holder.view, holder.getAdapterPosition(), pos, 0);
                    holder.nameBtn.setText("" + ChefActivity.categories.get(pos).getComponents().get(position).getAmount());
                    if (ChefActivity.categories.get(pos).getComponents().get(position).getAmount() <= 0) {

                        holder.button_layout.setVisibility(View.GONE);
                    }
                    holder.nameBtn.setElevation(45);
                    holder.minBtn.setElevation(40);

                }
            });

            holder.addBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // ChefActivity.componentMap.get(ChefActivity.componentMapPosition.get(pos)).get(position).setAmount(1); ;

                    //  component.setAmount(1);
                    holder.nameBtn.setElevation(45);
                    holder.addBtn.setElevation(40);

                    if (mClickListener != null)
                        mClickListener.onItemClick(holder.view, holder.getAdapterPosition(), pos, 1);
                    holder.nameBtn.setText("" + ChefActivity.categories.get(pos).getComponents().get(position).getAmount());

                }
            });



        }catch (Exception e){
            e.printStackTrace();
        }

        //holder.
        // holder.view.setBackgroundResource(backgrounds[holder.backgroundIndex]);

//        holder.cmName.setText(component.getEnName());
//        holder


    }

    @Override
    public int getItemCount() {
        return components.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView cmName;
        ImageView cmImg;
        Button nameBtn, addBtn, minBtn;
        LinearLayout button_layout;

        private FrameLayout view;
        private int backgroundIndex = 0;

        ViewHolder(View itemView, int backgroundIndex) {
            super(itemView);
//            this.backgroundIndex = backgroundIndex;
            view = (FrameLayout) itemView.findViewById(R.id.item);

            // ----------
            cmName = itemView.findViewById(R.id.component_name);
            cmImg = itemView.findViewById(R.id.component_img);
            nameBtn = itemView.findViewById(R.id.name_button);
            addBtn = itemView.findViewById(R.id.add_button);
            minBtn = itemView.findViewById(R.id.mine_button);
            button_layout = itemView.findViewById(R.id.buttons_layout);



            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {

            if (ChefActivity.categories.get(pos).getComponents().get(getAdapterPosition()).getAmount() <= 0) {
                if (mClickListener != null)
                    mClickListener.onItemClick(view, getAdapterPosition(), pos, 1);
                int i = ChefActivity.categories.get(pos).getComponents().get(getAdapterPosition()).getAmount();
                nameBtn.setText("" + i);
            }
            button_layout.setVisibility(View.VISIBLE);


        }
    }

    static void setClickListener(ItemClickListener itemClickListener) {
        mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position, int pos, int action);
    }

    public void setList(List<Component> components, int position) {
        this.components = components;
        this.pos = position;
    }

}
