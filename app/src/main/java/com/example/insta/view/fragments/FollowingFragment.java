package com.example.insta.view.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
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
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.insta.databinding.FragmentFollowingBinding;
import com.example.insta.model.adapter.UserRecyclerViewAdapter;
import com.example.insta.model.data.UsersList;
import com.example.insta.model.listener.OnUserClickedListener;
import com.example.insta.viewModel.FollowingViewModel;
import com.google.gson.Gson;

import retrofit2.Response;

public class FollowingFragment extends Fragment implements OnUserClickedListener {

    public static final String TAG = "FollowingFragment";
    private FragmentFollowingBinding fragmentFollowingBinding;
    private FollowingViewModel followingViewModel;
    private RecyclerView userRecyclerView;
    private ProgressBar progressBar;
    private String username, cursor;

    private Activity activity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        followingViewModel = new ViewModelProvider(requireActivity()).get(FollowingViewModel.class);

        if (getArguments() != null) {
            username = getArguments().getString("username");
            cursor = getArguments().getString("cursor");
        } else {
            username = "";
            cursor = "";
        }

        activity = getActivity();
        getFollowings(username, cursor);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        fragmentFollowingBinding = FragmentFollowingBinding.inflate(inflater, container, false);
        userRecyclerView = fragmentFollowingBinding.followingRecyclerView;
        progressBar = fragmentFollowingBinding.followingProgressBar;
        return fragmentFollowingBinding.getRoot();

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
                        Toast.makeText(getContext(), "User is private or does not exits", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "Response null: ");
                    }

//                    activity.runOnUiThread(() -> {
//                        try {
//                            Gson gson = new Gson();
//                            String json = gson.toJson(usersList);
//                            Log.d(TAG, "Following Response: "+json);
//                        } catch (Exception e) {
//                            Log.d(TAG, "Exception: "+e.getMessage());
//                        }
//                    });

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
        Intent intent= new Intent(Intent.ACTION_VIEW,uri);
        intent.setPackage("com.instagram.android");
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://instagram.com/xxx")));
        }
    }


}
