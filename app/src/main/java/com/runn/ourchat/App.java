package com.runn.ourchat;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init()
    {

        initFirebase();
    }

    private void initFirebase()
    {
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
