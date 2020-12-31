package com.runn.ourchat.ui.chatscreen;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.runn.ourchat.R;
import com.runn.ourchat.ui.models.Message;

public class MessageViewHolder extends RecyclerView.ViewHolder {


    public ImageView chatIV;
    public TextView chatTV;
    public TextView timeTV;

    public MessageViewHolder(View itemView) {
        super(itemView);

        chatIV = (ImageView)itemView.findViewById( R.id.chatIV );
        chatTV = (TextView)itemView.findViewById( R.id.chatTV );
        timeTV = (TextView)itemView.findViewById( R.id.timeTV );
    }

    public void bindToMessage(Message message, View.OnClickListener starClickListener) {

//        if (!TextUtils.isEmpty(user.getProfilePicUrl())) {
//            Picasso.with(userIV.getContext()).load(user.getProfilePicUrl()).into(userIV);
//        }
        chatTV.setText(message.getData());
    }

}
