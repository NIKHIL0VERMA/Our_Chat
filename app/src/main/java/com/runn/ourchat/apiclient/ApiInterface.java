package com.runn.ourchat.apiclient;

import com.runn.ourchat.ui.models.Chat;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET("https://api.myjson.com/bins/1eh55t")
    Call<ArrayList<Chat>> getChats();
}
