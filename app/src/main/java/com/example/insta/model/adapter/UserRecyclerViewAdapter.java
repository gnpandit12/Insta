package com.example.insta.model.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.insta.R;
import com.example.insta.model.data.Item;
import com.example.insta.model.data.UsersList;
import com.example.insta.model.listener.OnUserClickedListener;

import java.util.ArrayList;

/**
 * @Author Gaurav Naresh Pandit
 * @Since 21/05/24
 **/
public class UserRecyclerViewAdapter extends RecyclerView.Adapter<UserRecyclerViewAdapter.NewsViewHolder> {

    private Context mContext;
    private UsersList mUsersList;
    private OnUserClickedListener mOnUserClickListener;
    public static final String TAG = "UserRecyclerViewAdapter";
    public UserRecyclerViewAdapter(Context context, UsersList usersLists) {
        this.mContext = context;
        this.mUsersList = usersLists;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.instagram_user_card_view, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {

        Item user = mUsersList.getData().getData().getItems().get(position);

        Log.d(TAG, "onBindViewHolder: "+ mUsersList.getData().getData().getItems().get(position).getProfilePicUrl());


        Glide
                .with(mContext)
                .load(user.getProfilePicUrl())
                .into(holder.userImage);

        holder.userName.setText(user.getUsername());
        holder.userFullName.setText(user.getFullName());

        holder.itemView.setOnClickListener(view -> mOnUserClickListener.onUserClicked(user.getUsername()));


    }

    @Override
    public int getItemCount() {
        return mUsersList.getData().getData().getItems().size();
    }

    public static class NewsViewHolder extends RecyclerView.ViewHolder {

        ImageView userImage;
        TextView userName;
        TextView userFullName;
        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);

            userImage = itemView.findViewById(R.id.instagram_user_image_view);
            userName = itemView.findViewById(R.id.instagram_user_name_text_view);
            userFullName = itemView.findViewById(R.id.instagram_full_name_text_view);

        }
    }

    public void setOnInstagramUserClickListener(OnUserClickedListener onUserClickListener) {
        this.mOnUserClickListener = onUserClickListener;
    }


}
