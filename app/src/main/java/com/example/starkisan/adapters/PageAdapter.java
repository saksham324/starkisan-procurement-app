package com.example.starkisan.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import java.util.ArrayList;

public class PageAdapter extends FragmentStateAdapter {
    private ArrayList<Fragment> fragments;

    public PageAdapter(@NonNull FragmentManager fragmentManager, Lifecycle lifecycle, ArrayList<Fragment> fragments) {
        super(fragmentManager, lifecycle);
        this.fragments = fragments;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return (Fragment) this.fragments.get(position);
    }

    @Override
    public int getItemCount() {
        return this.fragments.size();
    }
}
