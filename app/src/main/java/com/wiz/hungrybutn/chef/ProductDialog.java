package com.wiz.hungrybutn.chef;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.skydoves.transformationlayout.TransformationLayout;
import com.skydoves.transformationlayout.TransitionExtensionKt;
import com.wiz.hungrybutn.R;
import com.wiz.hungrybutn.main.Dashboard;
import com.wiz.hungrybutn.main.WhereEat;
import com.wiz.hungrybutn.menu.Category;
import com.wiz.hungrybutn.network.Config;
import com.wiz.hungrybutn.network.SharedPreferanceHandler;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class ProductDialog extends DialogFragment {
    private RecyclerView mRecyclerView;
    private RecyclerView mRecyclerView2;
    private ProductDialog adapter;

    Button back, done;
    TextView textView;
    String type;
    TransformationLayout rootTrans ;

    // this method create view for your Dialog
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //inflate layout with recycler view

        TransformationLayout.Params params = getArguments().getParcelable("TransformationParams");
        TransitionExtensionKt.onTransformationEndContainer(this, params);

        View v = inflater.inflate(R.layout.product_dialog_layout, container);
        back = v.findViewById(R.id.cancel_Buttun);
        done = v.findViewById(R.id.done_buttun);
        textView = v.findViewById(R.id.option_tv);
        rootTrans = v.findViewById(R.id.root_trans);
        type = getArguments().getString("type");

        if (SharedPreferanceHandler.getInstance(getActivity()).getString(Config.Key_LANGUAGE).equalsIgnoreCase(Config.LANGUAGE_AR)) {
            if (type.equalsIgnoreCase("done")) {
                done.setText(getString(R.string.sure_ar));
                back.setText(getString(R.string.no_ar));
                textView.setText(getString(R.string.done_confirmation_ar));
            } else {
                done.setText(getString(R.string.exit_ar));
                back.setText(getString(R.string.cancel_ar));
                textView.setText(getString(R.string.cancel_confirmation_ar));
            }
        } else {
            if (type.equalsIgnoreCase("done")) {
                done.setText(getString(R.string.yes));
                back.setText(getString(R.string.no));
                textView.setText(getString(R.string.done_confirmation));


            } else {
                done.setText(getString(R.string.exit));
                back.setText(getString(R.string.cancel));
                textView.setText(getString(R.string.cancel_confirmation));
            }
        }


        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setStyle(DialogFragment.STYLE_NO_FRAME, android.R.style.Theme);
        TransformationLayout.Params params = getArguments().getParcelable("TransformationParams");
        TransitionExtensionKt.onTransformationEndContainer(this, params);
        done = view.findViewById(R.id.done_buttun);
        back = view.findViewById(R.id.cancel_Buttun);
        rootTrans = view.findViewById(R.id.root_trans);
        rootTrans.setTransitionName("myTransitionName");
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (type.equalsIgnoreCase("done")) {
                    startActivity(new Intent(getActivity(), InvoiceActivity.class));
                } else {
                    startActivity(new Intent(getActivity(), Dashboard.class));
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }


}