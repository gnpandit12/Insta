package com.example.insta.model.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.insta.model.RequestBody;
import com.example.insta.model.data.UsersList;
import com.example.insta.model.retrofit.ApiInterface;
import com.example.insta.model.retrofit.RetrofitService;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @Author Gaurav Naresh Pandit
 * @Since 06/06/24
 **/
public class FollowerRepository {

    public static final String TAG = "FollowerRepository";
    private static ApiInterface apiInterface;
    private final MutableLiveData<UsersList> usersListMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private static FollowerRepository followerRepository;
    public static FollowerRepository getInstance() {
        if (followerRepository == null){
            followerRepository = new FollowerRepository();
        }
        return followerRepository;
    }

    public FollowerRepository(){
        apiInterface = RetrofitService.getInterface();
    }

    public MutableLiveData<UsersList> getFollowersResponse(
            String username,
            String cursor
    ) {
        isLoading.setValue(true);
        Call<UsersList> call = apiInterface.getFollowers(
                new RequestBody(username, cursor)
        );

        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<UsersList> call, @NonNull Response<UsersList> response) {
                usersListMutableLiveData.setValue(response.body());
                isLoading.setValue(false);
                Log.d(TAG, "onResponse: "+ response);
            }

            @Override
            public void onFailure(Call<UsersList> call, Throwable t) {
                usersListMutableLiveData.setValue(null);
            }
        });

        return usersListMutableLiveData;
    }

    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }




}
