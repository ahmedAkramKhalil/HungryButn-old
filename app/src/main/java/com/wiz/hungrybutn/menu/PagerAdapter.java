package com.wiz.hungrybutn.menu;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.collection.SparseArrayCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import java.lang.ref.WeakReference;


    public class PagerAdapter extends FragmentPagerAdapter {

        private final FragmentPagerItems pages;
        private final SparseArrayCompat<WeakReference<Fragment>> holder;

        public PagerAdapter(FragmentManager fm, FragmentPagerItems pages) {
            super(fm);
            this.pages = pages;
            this.holder = new SparseArrayCompat<>(pages.size());
        }

        @Override
        public int getCount() {
            return pages.size();
        }

        @Override
        public Fragment getItem(int position) {
            return getPagerItem(position).instantiate(pages.getContext(), position);
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            Object item = super.instantiateItem(container, position);
            if (item instanceof Fragment) {
                holder.put(position, new WeakReference<Fragment>((Fragment) item));
            }
            return item;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            holder.remove(position);
            super.destroyItem(container, position, object);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return getPagerItem(position).getTitle();
        }

        @Override
        public float getPageWidth(int position) {

//             return (0.95f);
            return super.getPageWidth(position);
        }

        public Fragment getPage(int position) {
            final WeakReference<Fragment> weakRefItem = holder.get(position);
            return (weakRefItem != null) ? weakRefItem.get() : null;
        }

        protected FragmentPagerItem getPagerItem(int position) {
            return pages.get(position);
        }

    }

