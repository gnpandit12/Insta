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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.insta.databinding.FragmentFollowingBinding;
import com.example.insta.model.adapter.UserRecyclerViewAdapter;
import com.example.insta.model.data.UsersList;
import com.example.insta.model.listener.OnUserClickedListener;
import com.example.insta.viewModel.FollowingViewModel;

public class FollowingFragment extends Fragment implements OnUserClickedListener {

    public static final String TAG = "FollowingFragment";
    private FragmentFollowingBinding fragmentFollowingBinding;
    private FollowingViewModel followingViewModel;
    private RecyclerView userRecyclerView;
    private ProgressBar progressBar;
    private String username, cursor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        followingViewModel = new ViewModelProvider(requireActivity()).get(FollowingViewModel.class);

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        fragmentFollowingBinding = FragmentFollowingBinding.inflate(inflater, container, false);

        if (getArguments() != null) {
            username = getArguments().getString("username");
            cursor = getArguments().getString("cursor");
        } else {
            username = "";
            cursor = "";
        }

        return fragmentFollowingBinding.getRoot();

    }


    @Override
    public void onStart() {
        super.onStart();
        userRecyclerView = fragmentFollowingBinding.followingRecyclerView;
        progressBar = fragmentFollowingBinding.followingProgressBar;


        progressBar.setVisibility(View.VISIBLE);
        getFollowings(username, cursor);
    }

    @Override
    public void onResume() {
        super.onResume();
        getFollowings(username, cursor);
    }

    private void getFollowings(String username, String cursor) {

        followingViewModel.getIsLoading().observe(this, aBoolean -> {
            if (aBoolean) {
                progressBar.setVisibility(View.VISIBLE);
            } else {
                progressBar.setVisibility(View.INVISIBLE);
            }
        });

        followingViewModel.getFollowingMutableLiveData(username, cursor).
                observe(this, usersList -> {
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
        UserRecyclerViewAdapter userRecyclerViewAdapter = new UserRecyclerViewAdapter(getContext(),  usersList);
        userRecyclerView.setAdapter(userRecyclerViewAdapter);
        userRecyclerViewAdapter.setOnInstagramUserClickListener(this);
        userRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void onUserClicked(String userName) {
        Uri uri = Uri.parse("http://instagram.com/_u/"+userName);


        Intent i= new Intent(Intent.ACTION_VIEW,uri);

        i.setPackage("com.instagram.android");

        try {
            startActivity(i);
        } catch (ActivityNotFoundException e) {

            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://instagram.com/xxx")));
        }
    }


}
