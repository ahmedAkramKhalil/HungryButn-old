package com.wiz.hungrybutn.chef;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wiz.hungrybutn.R;
import com.wiz.hungrybutn.menu.Product;

import java.util.HashMap;
import java.util.List;

public class InvoiceAdapter extends RecyclerView.Adapter<InvoiceAdapter.ViewHolder> {

    private final int itemCount = 50;
    private int[] backgrounds = new int[2];
    List<String> names ;
    List<String> prices ;

    private LayoutInflater inflater;
    private static InvoiceAdapter.ItemClickListener mClickListener;
    boolean clicked = false ;
    public InvoiceAdapter(Context context, List<String> names , List<String> prices) {

        this.inflater = LayoutInflater.from(context);
        this.names = names;
        this.prices = prices;


    }

    @NonNull
    @Override
    public InvoiceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.invoice_item, parent, false);
        TextView name = view.findViewById(R.id.name);
        TextView price = view.findViewById(R.id.price);

//        name.setText(names.get());


        return new InvoiceAdapter.ViewHolder(view, 1);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

       holder.cmName.setText(names.get(position));
       holder.cmImg.setText(prices.get(position));





    }



    @Override
    public int getItemCount() {
        return names.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView cmName;
        TextView cmImg;

        private FrameLayout view;
        private int backgroundIndex = 0;

        ViewHolder(View itemView, int backgroundIndex) {
            super(itemView);

            // ----------
            cmName = itemView.findViewById(R.id.name);
            cmImg = itemView.findViewById(R.id.price);
            if (getAdapterPosition() > 0) {
                cmName.setText(names.get(getAdapterPosition()));
                cmImg.setText(prices.get(getAdapterPosition()));
            }
            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {





        }


    }

    static void setClickListener(InvoiceAdapter.ItemClickListener itemClickListener) {
        mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, Integer id);
    }


}
