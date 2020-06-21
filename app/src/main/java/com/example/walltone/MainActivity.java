package com.example.walltone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.walltone.Model.Post;
import com.example.walltone.network.APIClient;
import com.example.walltone.network.Api;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.walltone.R.id.fullscreenImageViewId;
import static com.example.walltone.R.id.gridViewId;
import static com.example.walltone.R.id.list_item;

public class MainActivity extends AppCompatActivity {

    private GridView gridView;
    //private List<Post> wallpaper;
    private CustomAdapter customAdapter;

    private BottomNavigationView bottomNavigationView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView= findViewById(R.id.Bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.wallpaperId);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.ringtoneId:
                        startActivity(new Intent(getApplicationContext(), RingtoneActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.wallpaperId:
                        return true;

                    case R.id.settingsId:
                        startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }

                return false;
            }
        });

        //uses of gridView's content
        gridView= findViewById(gridViewId);
        //CustomAdapter adapter= new CustomAdapter(this, wallpaper);
        //gridView.setAdapter(adapter);

        // Uses of retrofit
        /*Retrofit retrofit= new Retrofit.Builder(). baseUrl("http://52.77.133.170/all/walton/api/")
                .addConverterFactory(GsonConverterFactory.create()).build();

        Api api= retrofit.create(Api.class); */

        // setting onClickListener

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String a = String.valueOf(i);
                //Toast.makeText(getApplicationContext(), a, Toast.LENGTH_SHORT).show();





            }
        });

        Call<List<Post>> call= APIClient.apiinterface().getPosts();

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {

              if(response.isSuccessful()){

                  customAdapter= new CustomAdapter(MainActivity.this, response.body());
                  gridView.setAdapter(customAdapter);


              }
              else{
                  Toast.makeText(getApplicationContext(), "An error occured",Toast.LENGTH_SHORT).show();
              }

            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {

                Toast.makeText(getApplicationContext(), "An error occured"+ t.getLocalizedMessage(),Toast.LENGTH_SHORT).show();

            }
        });

    }



    public class CustomAdapter extends BaseAdapter{


        public Context context;
        public List<Post> wallpaper;
        public LayoutInflater inflater;

        public CustomAdapter(Context context, List<Post> wallpaper) {
            this.context = context;
            this.wallpaper = wallpaper;
        }

        @Override
        public int getCount() {
            return wallpaper.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {


            View view1 = LayoutInflater.from(context).inflate(R.layout.sample_view, null);

            //set data
            ImageView imageView= view1.findViewById(R.id.ImageViewId);
            TextView textView = view1.findViewById(R.id.textViewId);


            textView.setText(wallpaper.get(i).getName()); //get data

            Glide.with(context).load(wallpaper.get(i).getImageURL()).into(imageView); //get Image


            Log.d("TAG","ID: "+wallpaper.get(i).getId());
            view1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    context.startActivity(new Intent(context,SecondActivity_wallpaper.class)
                    .putExtra("ID",wallpaper.get(i).getId()));



                }
            });




            return view1;





        }



    }
}
