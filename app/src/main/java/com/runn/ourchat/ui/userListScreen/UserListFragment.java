package com.runn.ourchat.ui.userListScreen;

import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.runn.ourchat.R;
import com.runn.ourchat.ui.Database;
import com.runn.ourchat.ui.chatscreen.ChatActivity;
import com.runn.ourchat.ui.models.User;


public class UserListFragment extends Fragment {

    public static UserListFragment newInstance() {

        Bundle args = new Bundle();

        UserListFragment fragment = new UserListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_user_list, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
        getUsers();
    }

    private void initViews()
    {
        initRecyclerView();

    }

    private RecyclerView mRecyclerView;
    private DatabaseReference mDatabase;
    private FirebaseRecyclerAdapter<User, UserViewHolder> mAdapter;
    private void initRecyclerView()
    {
        mRecyclerView= (RecyclerView) getView().findViewById(R.id.usersRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void getUsers() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        Query userQuery = mDatabase.child(Database.NODE_USERS);
        mAdapter = new FirebaseRecyclerAdapter<User, UserViewHolder>(User.class, R.layout.item_user_list,
                UserViewHolder.class, userQuery) {
            @Override
            protected void populateViewHolder(final UserViewHolder viewHolder, final User user, final int position) {
                final DatabaseReference postRef = getRef(position);

                // Set click listener for the whole post view
                final String postKey = postRef.getKey();
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Launch PostDetailActivity
                        Intent intent = new Intent(getActivity(), ChatActivity.class);
                        intent.putExtra(ChatActivity.EXTRAS_USER, user);
                        startActivity(intent);
                    }
                });



                // Bind Post to ViewHolder, setting OnClickListener for the star button
                viewHolder.bindToUser(user, new View.OnClickListener() {
                    @Override
                    public void onClick(View starView) {

                    }
                });
            }
        };

        mRecyclerView.setAdapter(mAdapter);
    }


}
