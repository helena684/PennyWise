package com.example.pennywise.Adapters;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.pennywise.Fragments.DailyFragments;
import com.example.pennywise.Fragments.NoteFragment;
import com.example.pennywise.Fragments.YearlyFragment;
import com.example.pennywise.R;

public class ViewPagerAdapter extends FragmentPagerAdapter {


    public ViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 0)
        {
            fragment = new NoteFragment();
        }
        else if (position == 1)
        {
            fragment = new DailyFragments();
        }
        else if (position == 2)
        {
            fragment = new com.example.pennywise.Fragments.MonthlyFragment();
        }
        else if (position == 3)
        {
            fragment = new YearlyFragment();
        }
        return fragment;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if (position == 1)
        {
            title = "DAILY";
        }
        else if (position == 2)
        {
            title = "MONTHLY";
        }
        else if (position == 3)
        {
            title = "YEARLY";
        }
        return title;
    }
    @Override
    public int getCount() {
        return 4;
    }
}