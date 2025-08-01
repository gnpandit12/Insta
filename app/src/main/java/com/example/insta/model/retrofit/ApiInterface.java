package com.example.insta.model.retrofit;

import com.example.insta.model.RequestBody;
import com.example.insta.model.data.UsersList;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @Author Gaurav Naresh Pandit
 * @Since 20/05/24
 **/
public interface ApiInterface {

    @POST("/api/ins/v1/free/get-followers")
    Call<UsersList> getFollowers(
            @Body RequestBody body
    );

    @POST("/api/ins/v1/free/get-following")
    Call<UsersList> getFollowing(
            @Body RequestBody body
    );

}
