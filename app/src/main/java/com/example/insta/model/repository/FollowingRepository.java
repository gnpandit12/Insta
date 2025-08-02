package com.example.insta.model.repository;


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
public class FollowingRepository {

    private static ApiInterface apiInterface;
    private final MutableLiveData<UsersList> usersListMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private static FollowingRepository followingRepository;


    public static FollowingRepository getInstance() {
        if (followingRepository == null){
            followingRepository = new FollowingRepository();
        }
        return followingRepository;
    }

    public FollowingRepository() {
        apiInterface = RetrofitService.getInterface();
    }

    public MutableLiveData<UsersList> getFollowingResponse(
            String username,
            String cursor
    ) {
        isLoading.setValue(true);
        Call<UsersList> call = apiInterface.getFollowing(
                new RequestBody(username, cursor)
        );

        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<UsersList> call, @NonNull Response<UsersList> response) {
                usersListMutableLiveData.setValue(response.body());
                isLoading.setValue(false);
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
