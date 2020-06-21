package com.example.walltone.network;

import com.example.walltone.Model.Post;
import com.example.walltone.Model.Post2;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface Api {
    @GET("getcategory")
    Call<List<Post>> getPosts();
    @GET
    Call<List<Post2>> getPost2(@Url String url);
}
