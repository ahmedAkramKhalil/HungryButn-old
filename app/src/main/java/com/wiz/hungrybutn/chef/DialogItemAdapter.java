package com.wiz.hungrybutn.chef;

import android.content.Context;
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
import com.wiz.hungrybutn.menu.Product;

import java.util.List;

public class DialogItemAdapter extends RecyclerView.Adapter<DialogItemAdapter.ViewHolder> {

    private final int itemCount = 50;
    private int[] backgrounds = new int[2];
    private List<Product> components;
    private LayoutInflater inflater;
    private static DialogItemAdapter.ItemClickListener mClickListener;
    boolean clicked = false ;
    public DialogItemAdapter(Context context, List<Product> components) {

        this.inflater = LayoutInflater.from(context);
        this.components = components;


    }

    @NonNull
    @Override
    public DialogItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.dialog_item, parent, false);
        return new DialogItemAdapter.ViewHolder(view, 1);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final Product product = components.get(position);






    }



    @Override
    public int getItemCount() {
        return components.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView cmName;
        ImageView cmImg;

        private FrameLayout view;
        private int backgroundIndex = 0;

        ViewHolder(View itemView, int backgroundIndex) {
            super(itemView);
            view = (FrameLayout) itemView.findViewById(R.id.item);

            // ----------
            cmName = itemView.findViewById(R.id.item_name);
            cmImg = itemView.findViewById(R.id.item_img);


            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {


                if (mClickListener != null)
                    mClickListener.onItemClick(view, components.get(getAdapterPosition()).getId());


        }


    }

    static void setClickListener(DialogItemAdapter.ItemClickListener itemClickListener) {
        mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, Integer id );
    }


}
