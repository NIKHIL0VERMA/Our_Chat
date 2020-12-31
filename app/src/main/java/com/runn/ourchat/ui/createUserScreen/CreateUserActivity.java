package com.runn.ourchat.ui.createUserScreen;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.runn.ourchat.R;
import com.runn.ourchat.ui.Util;
import com.runn.ourchat.ui.homescreen.MainActivity;
import com.runn.ourchat.ui.models.User;

public class CreateUserActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String tag = CreateUserActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);
        findViewById(R.id.nextBtn).setOnClickListener(this);
        initViews();
    }


    private Button mNextButton;
    private EditText mUsernameET;
    private EditText mExistingUserET, mPasswordET;

    private void initViews() {
        mNextButton = (Button) findViewById(R.id.nextBtn);
        mNextButton.setOnClickListener(this);
        mUsernameET = (EditText) findViewById(R.id.userNameET);

        mExistingUserET = (EditText) findViewById(R.id.existingUserET);
        mPasswordET = (EditText) findViewById(R.id.passwordET);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.nextBtn:
                onNextButtonClicked();

//                Intent intent = new Intent(this, MainActivity.class);
//                startActivity(intent);
                break;
        }

    }

    String mUserName;

    private void onNextButtonClicked() {

        if(mExistingUserET.getText().toString().length() > 0)
        {
            loginExistingUser();
            return;
        }

        mUserName = mUsernameET.getText().toString();
        if (!TextUtils.isEmpty(mUserName)) {
            createUser();
        } else {
            Toast.makeText(this, "User name can not be blank", Toast.LENGTH_LONG).show();
        }
        showProgressDialog();
    }

    private void loginExistingUser()
    {
        String user = mExistingUserET.getText().toString();
        String password = mPasswordET.getText().toString();

        FirebaseAuth.getInstance().signInWithEmailAndPassword(user, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                Log.d(tag, "signInUser:oncomplete:" + task.isSuccessful());

                hideProgressDialog();


                if (task.isSuccessful()) {
                    onAuthSuccess(task.getResult().getUser());
                    Util.updateToken();
                } else {
                    try {
                        Exception e = task.getException();
                        e.printStackTrace();
                        Log.d("exception:", e.getMessage());
                        Toast.makeText(CreateUserActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.d("exception:", e.getMessage());
                    }
                }

            }
        });
    }

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    private void createUser() {
        mAuth = FirebaseAuth.getInstance();
        String email = mUserName + "@gmail.com";

        mAuth.createUserWithEmailAndPassword(email, mUserName)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(tag, "createUser:oncomplete:" + task.isSuccessful());

                        hideProgressDialog();


                        if (task.isSuccessful()) {
                            onAuthSuccess(task.getResult().getUser());
                            Util.updateToken();
                        } else {
                            try {
                                Exception e = task.getException();
                                e.printStackTrace();
                                Log.d("exception:", e.getMessage());
                                Toast.makeText(CreateUserActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            } catch (Exception e) {
                                e.printStackTrace();
                                Log.d("exception:", e.getMessage());
                            }
                        }
                    }
                });

    }


    private void onAuthSuccess(FirebaseUser firebaseUser) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        User user = new User();
        user.setName(usernameFromEmail(firebaseUser.getEmail()));
        user.setUid(firebaseUser.getUid());
        mDatabase.child("users").child(firebaseUser.getUid()).setValue(user);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private ProgressDialog mProgressDialog;

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setCancelable(false);
            mProgressDialog.setMessage("Loading...");
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    private String usernameFromEmail(String email) {
        if (email.contains("@")) {
            return email.split("@")[0];
        } else {
            return email;
        }
    }


}
