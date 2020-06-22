package com.example.walltone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.walltone.Model.Post2;
import com.example.walltone.network.APIClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SecondActivity_wallpaper extends AppCompatActivity {

    public String url = APIClient.base_url;
    public static int id;
    private int limit;
    private int index;
    private GridView chhildGrid;
    private CustomAdapter customAdapter;
    private String GridItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_wallpaper);



        id = getIntent().getIntExtra("ID", 0);

        Log.d("SECOND", "ID: " + id);

        chhildGrid = findViewById(R.id.gridViewId2);

        limit = 200;
        index = 0;

        Log.d("TAGG",url + "getdata_by_category/" + id + "/" + limit + "/" + index);

        final Call<List<Post2>> call = APIClient.apiinterface().getPost2(url + "getdata_by_category/" + id + "/" + limit + "/" + index);
        call.enqueue(new Callback<List<Post2>> () {
            @Override
            public void onResponse(Call<List<Post2>>  call, Response<List<Post2>>  response) {

                if (response.isSuccessful()){

                    Log.d("onResponse", response.body().toString());
                    Log.d("Image", "Name: " + response.body().get(0).getName());
                    Log.d("Content","Content: "+response.body().get(0).getContent());

                    List<Post2.Content> contents = response.body().get(0).getContent();
                    customAdapter = new CustomAdapter(SecondActivity_wallpaper.this, contents, id);
                    chhildGrid.setAdapter(customAdapter);

                } else {
                    Log.d("Error",""+response.headers());
                    Log.d("Error",""+response.code());
                    Log.d("Error",""+response.message());
                    Log.d("Error",""+response.errorBody());
                    Log.d("Error",""+response.raw());
                }

            }

            @Override
            public void onFailure(Call<List<Post2>>  call, Throwable t) {
                Log.d("onFailure", t.getLocalizedMessage());
            }
        });

        /*chhildGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //String a= String.valueOf(i);




                Intent intent =new Intent(getApplicationContext(), FullScreenActivity.class);
                intent.putExtra("ID", id);
                //intent.putExtra("url", );
                startActivity(intent);
            }
        });*/

    }




}
