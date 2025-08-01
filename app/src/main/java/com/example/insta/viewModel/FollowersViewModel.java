package com.example.insta.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.insta.model.data.UsersList;
import com.example.insta.model.repository.FollowerRepository;

/**
 * @Author Gaurav Naresh Pandit
 * @Since 06/06/24
 **/
public class FollowersViewModel extends AndroidViewModel {

    private final FollowerRepository followerRepository;

    public FollowersViewModel(@NonNull Application application) {
        super(application);
        followerRepository = FollowerRepository.getInstance();
    }

    public MutableLiveData<UsersList> getFollowerMutableLiveData(
            String username,
            String cursor
    ) {
        return followerRepository.getFollowersResponse(
                username,
                cursor
        );
    }

    public MutableLiveData<Boolean> getIsLoading() {
        return followerRepository.getIsLoading();
    }



}
