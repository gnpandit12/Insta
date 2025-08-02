package com.example.insta.view;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.insta.databinding.ActivityMainBinding;
import com.example.insta.view.fragments.FollowersFragment;
import com.example.insta.view.fragments.FollowingFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    ActivityMainBinding activityMainBinding;
    private final String[] tabLabels = new String[]{"Followers", "Following"};
    private EditText usernameSearchEditText;

    public static Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());

        ViewPager2 viewPager2 = activityMainBinding.viewPager;
        viewPager2.setAdapter(new ViewPagerAdapter(this, null));
        viewPager2.setUserInputEnabled(false);

        TabLayout tabLayout = activityMainBinding.tabLayout;
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#FFFFFF"));
        new TabLayoutMediator(tabLayout, viewPager2, (tab, i) -> tab.setText(tabLabels[i])).attach();

        usernameSearchEditText = activityMainBinding.userNameSearchEditText;


        usernameSearchEditText.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (i == EditorInfo.IME_ACTION_DONE) {
                if (!usernameSearchEditText.getText().toString().trim().isEmpty()) {
                    bundle = new Bundle();
                    bundle.putString("username", usernameSearchEditText.getText().toString().trim());
                    bundle.putString("cursor", "" );
                    viewPager2.setAdapter(new ViewPagerAdapter(this, bundle));
                    viewPager2.setUserInputEnabled(false);
                } else {
                    Log.d(TAG, "Search null");
                }
            }
            return false;
        });


    }


    public static class ViewPagerAdapter extends FragmentStateAdapter {

        private Bundle mBundle;
        public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity, Bundle bundle) {
            super(fragmentActivity);
            this.mBundle = bundle;
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:
                    FollowersFragment followersFragment = new FollowersFragment();
                    followersFragment.setArguments(mBundle);
                    return followersFragment;
                case 1:
                    FollowingFragment followingFragment = new FollowingFragment();
                    followingFragment.setArguments(mBundle);
                    return followingFragment;
            }
            FollowersFragment followersFragment = new FollowersFragment();
            followersFragment.setArguments(mBundle);
            return followersFragment;
        }

        @Override
        public int getItemCount() {
            return 2;
        }
    }


}