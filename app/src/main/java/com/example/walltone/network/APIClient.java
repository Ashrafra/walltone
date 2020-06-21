package com.example.walltone.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {

    public static String base_url= "http://52.77.133.170/all/walton/api/";

    public static Retrofit getClient(){

        Retrofit retrofit= new Retrofit.Builder(). baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create()).build();

        return retrofit;
    }
    public static Api apiinterface(){
        return getClient().create(Api.class);
    }
}
