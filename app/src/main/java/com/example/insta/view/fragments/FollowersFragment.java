package com.example.insta.view.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        followersViewModel = new ViewModelProvider(requireActivity()).get(FollowersViewModel.class);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentFollowersBinding = FragmentFollowersBinding.inflate(inflater, container, false);

        if (getArguments() != null) {
            username = getArguments().getString("username");
            cursor = getArguments().getString("cursor");
        } else {
            username = "__omkar__2006";
            cursor = "";
        }


        return fragmentFollowersBinding.getRoot();


    }

    @Override
    public void onStart() {
        super.onStart();

        followersRecyclerView = fragmentFollowersBinding.followersRecyclerView;
        progressBar = fragmentFollowersBinding.followersProgressBar;
        progressBar.setVisibility(View.VISIBLE);

        getFollowers("__omkar__2006", "");

    }

    @Override
    public void onResume() {
        super.onResume();
        getFollowers(username, cursor);
    }

    @SuppressLint("NotifyDataSetChanged")
    private void getFollowers(String username, String cursor) {

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

    }
}
