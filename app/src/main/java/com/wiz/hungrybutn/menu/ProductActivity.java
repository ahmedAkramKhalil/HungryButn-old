package com.wiz.hungrybutn.menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.wiz.hungrybutn.R;

import java.util.ArrayList;
import java.util.List;

public class ProductActivity extends AppCompatActivity implements ProductAdapter.ItemClickListener {

    private PagerContainer mContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_item);

        final List<String> strings = new ArrayList<>();
        strings.add("name of");
        strings.add("name of");
        strings.add("name of");
        strings.add("name of");
        strings.add("name of");


        mContainer = (PagerContainer) findViewById(R.id.pager_container);

        final ViewPager viewPager = mContainer.getViewPager();;
//        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);

        ViewGroup tab = (ViewGroup) findViewById(R.id.tab);
        tab.addView(LayoutInflater.from(this).inflate(R.layout.menu_item_custom_tab, tab, false));

        final SmartTabLayout viewPagerTab = (SmartTabLayout) findViewById(R.id.viewpagertab);

//        viewPagerTab.setSelectedIndicatorColors(R.color.colorTindicatorAlpha);
        setup(viewPagerTab);



        FragmentPagerItems pages = new FragmentPagerItems(this);

        for (String titleResId : strings) {
            pages.add(FragmentPagerItem.of(titleResId, ProductFragment.class));
        }
//        for (String title : strings) {
//            pages.add(FragmentPagerItem.of(title, MenuItemFragment.class));
//
//        }
        Bundle bundle = new Bundle();
        Product product = new Product();
        List<Product> menuItems1 = new ArrayList<>();
        menuItems1.add(product);
        menuItems1.add(product);
        menuItems1.add(product);
        menuItems1.add(product);
        List<Product> menuItems2 = new ArrayList<>();
        menuItems2.add(product);
        menuItems2.add(product);
        List<List> lists = new ArrayList<>();
        lists.add(menuItems1);
        lists.add(menuItems2);
//        bundle.putParcelable("content", (Parcelable) lists);
        PagerAdapter adapter = new PagerAdapter(
                getSupportFragmentManager(), pages);
        viewPager.setAdapter(adapter);
        viewPagerTab.setViewPager(viewPager);
        ProductAdapter.setClickListener(ProductActivity.this);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
//                if (position == 0 ){
//                    viewPager.setPageMargin(10);
//
//
//                }else if (position == strings.size() ){
//                    viewPager.setPageMargin(10);
//
//                }else {
//                    viewPager.setPageMargin(15);
//
//                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        viewPager.setOffscreenPageLimit(adapter.getCount());
        //A little space between pages
        viewPager.setPageMargin(10);

        //If hardware acceleration is enabled, you should also remove
        // clipping on the pager for its children.
        viewPager.setClipChildren(false);

    }


    public int[] tabs() {
        return new int[]{
                R.string.bun_tab_title,
                R.string.meal_tab_title,
                R.string.tomato_tab_title,
                R.string.egg_tab_title,
                R.string.sauce_tab_title,
                R.string.cooker_tab_title
        };
    }

    public void setup(SmartTabLayout layout) {
        Context context = layout.getContext() ;
        final LayoutInflater inflater = LayoutInflater.from(context);
        final Resources res = layout.getContext().getResources();

//        layout.setCustomTabView(new SmartTabLayout.TabProvider() {
//            @Override
//            public View createTabView(ViewGroup container, int position, PagerAdapter adapter) {
//                ImageView icon = (ImageView) inflater.inflate(R.layout.custom_tab_icon2, container,
//                        false);
//                switch (position) {
//                    case 0:
//                        icon.setImageDrawable(res.getDrawable(R.drawable.bun_icon));
//                        break;
//                    case 1:
//                        icon.setImageDrawable(res.getDrawable(R.drawable.meal_icon));
//                        break;
//                    case 2:
//                        icon.setImageDrawable(res.getDrawable(R.drawable.tomato));
//                        break;
//                    case 3:
//                        icon.setImageDrawable(res.getDrawable(R.drawable.egg));
//                        break;
//                    case 4:
//                        icon.setImageDrawable(res.getDrawable(R.drawable.sous));
//                        break;
//                    case 5:
//                        icon.setImageDrawable(res.getDrawable(R.drawable.cooker));
//                        break;
//                    default:
//                        throw new IllegalStateException("Invalid position: " + position);
//                }
//                return icon;
//            }
//        });
    }
    @Override
    public void onItemClick(View view, int position) {

    }
    public int[] tabbs() {
        return new int[] {
                R.string.demo_tab_like_a_medium_top,
                R.string.demo_tab_like_a_medium_latest
        };
    }
}
