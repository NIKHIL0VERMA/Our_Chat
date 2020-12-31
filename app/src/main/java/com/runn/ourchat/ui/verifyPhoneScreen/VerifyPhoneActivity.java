package com.runn.ourchat.ui.verifyPhoneScreen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.runn.ourchat.R;
import com.runn.ourchat.ui.createUserScreen.CreateUserActivity;

public class VerifyPhoneActivity extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_phone);
        findViewById(R.id.nextBtn).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        Intent intent = new Intent(this, CreateUserActivity.class);
        startActivity(intent);

    }
}


