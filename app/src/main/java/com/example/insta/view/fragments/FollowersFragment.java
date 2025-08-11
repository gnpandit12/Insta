package com.example.insta.view.fragments;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.insta.databinding.FragmentFollowersBinding;
import com.example.insta.model.adapter.UserRecyclerViewAdapter;
import com.example.insta.model.data.UsersList;
import com.example.insta.model.listener.OnUserClickedListener;
import com.example.insta.viewModel.FollowersViewModel;

public class FollowersFragment extends Fragment implements OnUserClickedListener {

    public static final String TAG = "FollowersFragment";
    private FragmentFollowersBinding fragmentFollowersBinding;
    private RecyclerView followersRecyclerView;
    private ProgressBar progressBar;
    private UserRecyclerViewAdapter userRecyclerViewAdapter;
    private FollowersViewModel followersViewModel;
    private String username, cursor;
    private Boolean isSearched;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        followersViewModel = new ViewModelProvider(requireActivity()).get(FollowersViewModel.class);

        if (getArguments() != null) {
            isSearched = getArguments().getBoolean("IsSearched");
            username = getArguments().getString("username");
            cursor = getArguments().getString("cursor");
        }

        getFollowers(username, cursor, isSearched);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentFollowersBinding = FragmentFollowersBinding.inflate(inflater, container, false);
        followersRecyclerView = fragmentFollowersBinding.followersRecyclerView;
        progressBar = fragmentFollowersBinding.followersProgressBar;
        return fragmentFollowersBinding.getRoot();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void getFollowers(String username, String cursor, Boolean isSearched) {

        followersViewModel.getIsLoading().observe(this, aBoolean -> {
            if (aBoolean) {
                progressBar.setVisibility(View.VISIBLE);
            } else {
                progressBar.setVisibility(View.INVISIBLE);
            }
        });

        followersViewModel.getFollowerMutableLiveData(username, cursor)
                        .observe(this, usersList -> {
                            if (usersList != null) {
                                if (usersList.getData() != null) {
                                    showUsersList(usersList);
                                } else {
                                    Log.d(TAG, "Code: "+usersList.getCode().toString());
                                }
                            } else {
                                if (isSearched) {
                                    Toast.makeText(getContext(), "User is private or does not exits", Toast.LENGTH_SHORT).show();
                                }
                                Log.d(TAG, "Response null: ");
                            }

                        });
    }

    @SuppressLint("NotifyDataSetChanged")
    private void showUsersList(UsersList usersList) {
        userRecyclerViewAdapter = new UserRecyclerViewAdapter(getContext(), usersList);
        followersRecyclerView.setAdapter(userRecyclerViewAdapter);
        userRecyclerViewAdapter.setOnInstagramUserClickListener(this);
        progressBar.setVisibility(View.INVISIBLE);
        userRecyclerViewAdapter.notifyDataSetChanged();
    }


    @Override
    public void onUserClicked(String userName) {
        Uri uri = Uri.parse("http://instagram.com/_u/"+userName);
        Intent intent= new Intent(Intent.ACTION_VIEW,uri);
        intent.setPackage("com.instagram.android");
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://instagram.com/xxx")));
        }
    }
}
