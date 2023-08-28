package com.wiz.hungrybutn.menu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wiz.hungrybutn.R;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private final int itemCount = 50;
    private int[] backgrounds = new int[2];
    private List<Product> Product;
    private LayoutInflater inflater ;
    private static ProductAdapter.ItemClickListener mClickListener;
   Context context ;
    public ProductAdapter(Context context , List<Product> components) {

        this.inflater = LayoutInflater.from(context);
        this.Product = Product;
        this.context = context ;


        //  backgrounds[0] = R.drawable.background_primary_light;
        //  backgrounds[1] = R.drawable.background_primary_dark;
        //  backgrounds[2] = R.drawable.background_primary_accent;
    }

    @NonNull
    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.dialog_item,parent,false);
        return new ProductAdapter.ViewHolder(view , 1 );
//        int index = (int) (Math.random() * 3);
//        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_item, parent, false), index);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        MenuItem component = MenuItem.get(position);


//        Toast.makeText(context,"ffffffffff" + position , Toast.LENGTH_SHORT).show();
    }



    @Override
    public int getItemCount() {
        return 1 ;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView cmName ;
        TextView cmDes ;
        ImageView cmImg ;
        Button Btn ;

        private FrameLayout view;
        private int backgroundIndex = 0;

        ViewHolder(View itemView, int backgroundIndex) {
            super(itemView);
//            this.backgroundIndex = backgroundIndex;
            view = (FrameLayout) itemView.findViewById(R.id.item);

            // ----------
            cmName = itemView.findViewById(R.id.item_name);
            cmName = itemView.findViewById(R.id.item_des);
            cmImg = itemView.findViewById(R.id.img);
            Btn = itemView.findViewById(R.id.destailes_btn);
            itemView.setOnClickListener(this);




        }

        @Override
        public void onClick(View v) {
//            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
//           layout.setVisibility(View.VISIBLE);

        }
    }

    static  void setClickListener(ProductAdapter.ItemClickListener itemClickListener) {
        mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

}
