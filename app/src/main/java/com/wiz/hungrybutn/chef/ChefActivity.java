package com.wiz.hungrybutn.chef;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hanks.htextview.evaporate.EvaporateTextView;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.skydoves.transformationlayout.TransformationLayout;
import com.skydoves.transformationlayout.TransitionExtensionKt;
import com.wiz.hungrybutn.R;
import com.wiz.hungrybutn.menu.Category;
import com.wiz.hungrybutn.menu.ComponentCategory;
import com.wiz.hungrybutn.network.Config;
import com.wiz.hungrybutn.network.SharedPreferanceHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class ChefActivity extends AppCompatActivity implements ComponentAdapter.ItemClickListener, DialogItemAdapter.ItemClickListener {

    static HashMap<String, List<Component>> componentMap = new LinkedHashMap<>();
    static HashMap<Integer, String> componentMapPosition = new LinkedHashMap<>();

    public static List<ComponentCategory> categories;
    TextView title;
    TextView totalPrice;
    EvaporateTextView price;
    Button doneBtn, skipBtn;
    FloatingActionButton nextBtn, backBtn;

    static boolean ar = false;
    public static float priceNum = 0;
    private int currentPage;
    static List<Integer> productIds;
    RecyclerView recyclerView;
    ComponentAnimationAdapter animationAdapter;
    List<ComponentAnimation> componentAnimations;
    TransformationLayout doneTrans, cancelTrans;
    FragmentPagerItems pages;
    LinearLayoutManager layoutManager;
    ViewPager viewPager;
    SmartTabLayout viewPagerTab;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TransitionExtensionKt.onTransformationStartContainer(this);
        setContentView(R.layout.activity_chef);

        title = findViewById(R.id.title_tv);
        totalPrice = findViewById(R.id.total_tv);
        price = findViewById(R.id.price_textView);
        doneBtn = findViewById(R.id.done_button);
        skipBtn = findViewById(R.id.skip_button);
        nextBtn = findViewById(R.id.next_button);
        backBtn = findViewById(R.id.back_button);
        doneTrans = findViewById(R.id.done_transformationLayout);
        cancelTrans = findViewById(R.id.cancel_transformationLayout);
        recyclerView = findViewById(R.id.anim_recycle);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPagerTab = (SmartTabLayout) findViewById(R.id.viewpagertab);
        doneTrans.setTransitionName("myTransitionName");
        cancelTrans.setTransitionName("myTransitionName");
        price.animateText("00.0");
        productIds = new ArrayList<>();
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        componentAnimations = new ArrayList<>();
        animationAdapter = new ComponentAnimationAdapter(this, componentAnimations);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new ItemDecorator(-100));
        recyclerView.setAdapter(animationAdapter);


        setup(viewPagerTab);
        pages = new FragmentPagerItems(ChefActivity.this);
        categories = init();
        boolean langFlag = SharedPreferanceHandler.getInstance(ChefActivity.this).getString(Config.Key_LANGUAGE).equalsIgnoreCase(Config.LANGUAGE_AR);
        int count = 0;
        if (langFlag) {
            ar = true;
            totalPrice.setText(R.string.total_price_ar);
            doneBtn.setText(R.string.done_ar);
            skipBtn.setText(R.string.cancel_ar);
            title.setText(categories.get(0).getName());
            for (ComponentCategory category : categories) {
                if (count < 5)
                    pages.add(FragmentPagerItem.of(category.getName(), ComponentFragment.class));
                count++;

            }
        } else {
            ar = false;
            totalPrice.setText(R.string.total_price);
            doneBtn.setText(R.string.done);
            skipBtn.setText(R.string.cancel);
            title.setText(categories.get(0).getName_en());
            for (ComponentCategory category : categories) {
                if (count < 6)
                    pages.add(FragmentPagerItem.of(category.getName_en(), ComponentFragment.class));
                count++;
            }
        }
        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(), pages);
        viewPager.setAdapter(adapter);
        viewPagerTab.setViewPager(viewPager);
        ComponentAdapter.setClickListener(ChefActivity.this);
        DialogItemAdapter.setClickListener(ChefActivity.this);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                currentPage = position;
                if (ar) {
                    title.setText(categories.get(position).getName());
                } else {
                    title.setText(categories.get(position).getName_en());
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });



        skipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCancelDialog();
            }
        });

        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentPage == 4) {
                    showDoneDialog();
                }
                if (currentPage < 4) {
                    viewPager.setCurrentItem(currentPage + 1);
                }

            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentPage > 0)
                    viewPager.setCurrentItem(currentPage - 1);
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (currentPage == 4) {
                    showDoneDialog();

//                    startActivity(new Intent(ChefActivity.this, InvoiceActivity.class));
                }

                if (currentPage < 4) {
                    viewPager.setCurrentItem(currentPage + 1);
                }


            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    public void setup(SmartTabLayout layout) {
        Context context = layout.getContext();
        final LayoutInflater inflater = LayoutInflater.from(context);
        final Resources res = layout.getContext().getResources();

        layout.setCustomTabView(new SmartTabLayout.TabProvider() {
            @Override
            public View createTabView(ViewGroup container, int position, PagerAdapter adapter) {
                ImageView icon = (ImageView) inflater.inflate(R.layout.custom_tab_icon2, container,
                        false);
                switch (position) {
                    case 0:
                        icon.setImageDrawable(res.getDrawable(R.drawable.bune));
                        break;
                    case 1:
                        icon.setImageDrawable(res.getDrawable(R.drawable.pattye));
                        break;
                    case 2:
                        icon.setImageDrawable(res.getDrawable(R.drawable.tomatoe));
                        break;
                    case 3:
                        icon.setImageDrawable(res.getDrawable(R.drawable.egge));
                        break;
                    case 4:
                        icon.setImageDrawable(res.getDrawable(R.drawable.sauce));
                        break;
                    case 5:
//                        showProductDialog();
                        break;
//                    default:
//                        throw new IllegalStateException("Invalid position: " + position);
                }
                return icon;
            }
        });
    }

    @Override
    public void onItemClick(View view, int position, int pos, int action) {


//        Toast.makeText(getApplicationContext(), "fff" + position + " - " + pos, Toast.LENGTH_SHORT).show();

        Log.d("pos", "pos " + pos + " pp " + position);
        Component component = categories.get(pos).getComponents().get(position);
        if (action == 1) {
            component.amount = component.amount + 1;
            price.animateText("" + getPrice(component, 1)); // animate

            try {
                ComponentAnimation componentAnimation = new ComponentAnimation(component, pos);

                if (componentAnimations.size() == 0) {
                    animationAdapter.addComponent(componentAnimation, 0);
                } else {
                    animationAdapter.addComponent(componentAnimation, componentAnimations.size());
                    recyclerView.scrollToPosition(animationAdapter.getItemCount() - 1);


                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        if (component.amount > 0) {
            if (action == 0) {
                component.amount = component.amount - 1;
                price.animateText("" + getPrice(component, 0)); // animate

                try {
                    for (ComponentAnimation componentAnimation : componentAnimations) {
                        if (componentAnimation.component == component) {
                            animationAdapter.removeComponent(componentAnimations.indexOf(componentAnimation));
                        }
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }

//        animationAdapter.addComponent(componentAnimation,0);
//        animationAdapter.addItems(componentAnimations);


    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
//        Toast.makeText(getApplicationContext(), "fff" + hasCapture, Toast.LENGTH_SHORT).show();

    }

    List<ComponentCategory> init() {
        Realm.init(getApplicationContext());
        Realm realm = Realm.getDefaultInstance();
        RealmResults<ComponentCategory> realmCities = realm.where(ComponentCategory.class).findAllAsync();
        List<ComponentCategory> categoryList = realm.copyFromRealm(realmCities);
//        realmCities.load();
        return categoryList;
    }

    float getPrice(Component component, int action) {
        if (action == 0) {
            priceNum = priceNum - (float) (component.getPrice());
        } else {
            if (component.getAmount() > 0) {
                priceNum = priceNum + (float) (component.getPrice());
            }
        }
        return priceNum;
    }


    private void showCancelDialog() {
        Bundle bundle = new Bundle();
        bundle.putString("type", "cancel");
        FragmentManager fm = getSupportFragmentManager();
        ProductDialog editNameDialogFragment = new ProductDialog();
        bundle.putParcelable("TransformationParams", cancelTrans.getParcelableParams());
        editNameDialogFragment.setArguments(bundle);
        editNameDialogFragment.show(fm, "fragment_edit_name");
    }

    private void showDoneDialog() {
        Bundle bundle = new Bundle();
        bundle.putString("type", "done");
        FragmentManager fm = getSupportFragmentManager();
        bundle.putParcelable("TransformationParams", doneTrans.getParcelableParams());

        ProductDialog editNameDialogFragment = new ProductDialog();
        editNameDialogFragment.setArguments(bundle);

        editNameDialogFragment.show(fm, "fragment_edit_name");


    }


    @Override
    public void onItemClick(View view, Integer id) {

//        if (productIds.contains(id)) {
//            boolean a = productIds.remove(id);
//            for (Category category : productCategories) {
//                for (Product product : category.getProducts()) {
//                    if (product.getId() == id) {
//                        price.animateText("" + getPrice(product, 0)); // animate
//                        break;
//                    }
//                }
//            }
//        } else {
//            productIds.add(id);
//            for (Category category : productCategories) {
//                for (Product product : category.getProducts()) {
//                    if (product.getId() == id) {
//                        price.animateText("" + getPrice(product, 1)); // animate
//                        break;
//                    }
//                }
//            }
//        }


        Log.d("dddd >", "id " + id);

    }


    List<ComponentCategory> getProductCategories() {


        Realm.init(getApplicationContext());
        Realm realm = Realm.getDefaultInstance();
        RealmResults<ComponentCategory> realmCities = realm.where(ComponentCategory.class).findAllAsync();
        List<ComponentCategory> categoryList = realm.copyFromRealm(realmCities);
        realmCities.load();
        if (realmCities != null && realmCities.size() > 0) {
            for (ComponentCategory category : realmCities) {


            }
            Log.e("realmCities", "" + realmCities.size());
        } else {

        }

        return categoryList;
    }

    public class ItemDecorator extends RecyclerView.ItemDecoration {
        private final int mSpace;

        public ItemDecorator(int space) {
            this.mSpace = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view);
            if (position != 0) {
                outRect.bottom = mSpace;
            }
        }
    }
}
