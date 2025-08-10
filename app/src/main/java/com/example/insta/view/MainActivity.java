package com.example.insta.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.insta.R;
import com.example.insta.databinding.ActivityMainBinding;
import com.example.insta.view.fragments.FollowersFragment;
import com.example.insta.view.fragments.FollowingFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    ActivityMainBinding activityMainBinding;
    private final String[] tabLabels = new String[]{"Following", "Followers"};

    public static Bundle bundle;
    public ViewPager2 viewPager2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#c13584")));

        viewPager2 = activityMainBinding.viewPager;
        viewPager2.setAdapter(new ViewPagerAdapter(this, null));
        viewPager2.setUserInputEnabled(false);

        TabLayout tabLayout = activityMainBinding.tabLayout;
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#FFFFFF"));
        new TabLayoutMediator(tabLayout, viewPager2, (tab, i) -> tab.setText(tabLabels[i])).attach();


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);

        FragmentActivity fragmentActivity = this;

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        // Configure the SearchView (optional)
        if (searchView != null) {
            searchView.setQueryHint("Search...");
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    // Handle search submission (e.g., perform a search)
                    bundle = new Bundle();
                    bundle.putString("username", query);
                    bundle.putString("cursor", "" );
                    viewPager2.setAdapter(new ViewPagerAdapter(fragmentActivity, bundle));
                    viewPager2.setUserInputEnabled(false);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    // Handle text changes (e.g., filter a list)
                    return false;
                }
            });
        }

        return true;
    }


    public static class ViewPagerAdapter extends FragmentStateAdapter {

        private final Bundle mBundle;
        public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity, Bundle bundle) {
            super(fragmentActivity);
            this.mBundle = bundle;
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:
                    FollowingFragment followingFragment = new FollowingFragment();
                    followingFragment.setArguments(mBundle);
                    return followingFragment;
                case 1:
                    FollowersFragment followersFragment = new FollowersFragment();
                    followersFragment.setArguments(mBundle);
                    return followersFragment;
            }
            FollowingFragment followingFragment = new FollowingFragment();
            followingFragment.setArguments(mBundle);
            return followingFragment;
        }

        @Override
        public int getItemCount() {
            return 2;
        }
    }

}