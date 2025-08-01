package com.example.insta.viewModel;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.insta.model.data.UsersList;
import com.example.insta.model.repository.FollowerRepository;
import com.example.insta.model.repository.FollowingRepository;

import java.util.List;

/**
 * @Author Gaurav Naresh Pandit
 * @Since 06/06/24
 **/
public class FollowingViewModel extends AndroidViewModel {


    private final FollowingRepository followingRepository;
    public FollowingViewModel(@NonNull Application application) {
        super(application);
        followingRepository = FollowingRepository.getInstance();
    }

    public MutableLiveData<UsersList> getFollowingMutableLiveData(
            String username,
            String cursor
    ) {
        return followingRepository.getFollowingResponse(
                username,
                cursor
        );
    }

    public MutableLiveData<Boolean> getIsLoading() {
        return followingRepository.getIsLoading();
    }



}
