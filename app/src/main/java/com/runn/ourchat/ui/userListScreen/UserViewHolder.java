package com.runn.ourchat.ui.userListScreen;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.runn.ourchat.R;
import com.runn.ourchat.ui.models.User;
import com.squareup.picasso.Picasso;

/**
 * Created by vihaan on 18/06/17.
 */

public class UserViewHolder extends RecyclerView.ViewHolder {

    public ImageView userIV;
    public TextView usernameTV;
    public TextView statusTV;

    public UserViewHolder(View itemView) {
        super(itemView);

        userIV = (ImageView) itemView.findViewById(R.id.userIV);
        usernameTV = (TextView) itemView.findViewById(R.id.userNameTV);
        statusTV = (TextView) itemView.findViewById(R.id.statusTV);
    }

    public void bindToUser(User user, View.OnClickListener starClickListener) {

        if(!TextUtils.isEmpty(user.getProfilePicUrl()))
        {
            Picasso.with(userIV.getContext()).load(user.getProfilePicUrl()).into(userIV);
        }
        usernameTV.setText(user.getName());
        statusTV.setText(user.getStatus());
    }
}
