package com.example.walltone;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.DownloadListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.walltone.Model.Post;
import com.example.walltone.Model.Post2;
import com.example.walltone.network.APIClient;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.PriorityQueue;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.POST;

import static com.example.walltone.R.id.button2;
import static com.example.walltone.R.id.fullscreenImageViewId;
import static com.example.walltone.R.id.recylerViewId;
import static com.example.walltone.R.id.transition_position;
import static com.example.walltone.R.id.wallpaperId;

public class FullScreenActivity extends AppCompatActivity {

    private List<Post2.Content> wallpaper;

    private RecyclerView recyclerView;
    private int id;
    public String url = APIClient.base_url;
    private int limit;
    private int index;
    private int a;
    private Button downloadButton;
    private Bitmap bitmap;
    private ImageView imageView;
    public static final int PERMISSION_WRITE = 0;
    String fileUri;
    private String Imgurl;
    private int position;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen);



        imageView= findViewById(fullscreenImageViewId);
        downloadButton= findViewById(button2);

        limit= 200;
        index= 0;

        position = getIntent().getIntExtra("position",0);

        recyclerView=findViewById(recylerViewId);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);


        id = getIntent().getIntExtra("ID", 0);
        Imgurl= getIntent().getStringExtra("Imgurl");

        final Call<List<Post2>> call = APIClient.apiinterface().getPost2(url + "getdata_by_category/" + id + "/" + limit + "/" + index);
        call.enqueue(new Callback<List<Post2>>() {
            @Override
            public void onResponse(Call<List<Post2>> call, Response<List<Post2>> response) {

                if (response.isSuccessful()){

                    Log.d("onResponse", response.body().toString());
                    Log.d("Image", "Name: " + response.body().get(0).getName());
                    Log.d("Content","Content: "+response.body().get(0).getContent());

                    List<Post2.Content> contents = response.body().get(0).getContent();

                    RecyclerViewAdapter adapter = new RecyclerViewAdapter(contents, getApplicationContext());
                    recyclerView.smoothScrollToPosition(position);
                    recyclerView.setAdapter(adapter);

                } else {
                    Log.d("Error",""+response.headers());
                    Log.d("Error",""+response.code());
                    Log.d("Error",""+response.message());
                    Log.d("Error",""+response.errorBody());
                    Log.d("Error",""+response.raw());
                }


            }

            @Override
            public void onFailure(Call<List<Post2>> call, Throwable t) {
                Log.d("onFailure", t.getLocalizedMessage());

            }
        });



        checkPermission();

        downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //String aburl=
                //String Url= wallpaper.gpet(transition_position).jpgImage;
                //String URL="http://52.77.133.170/all/walton/public/uploads_images/1/jpg/1_jpg_1581583075.jpg";
                if (checkPermission()) {
                    SaveImage(Imgurl);
                }

            }
        });

    }

    public void SaveImage(String url) {
        Glide.with(this).asBitmap().load(url).into(new CustomTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {
                try {
                    File mydir = new File(Environment.getExternalStorageDirectory() + "/walltone");
                    if (!mydir.exists()) {
                        mydir.mkdirs();
                    }

                    fileUri = mydir.getAbsolutePath() + File.separator + System.currentTimeMillis() + ".jpg";
                    FileOutputStream outputStream = new FileOutputStream(fileUri);

                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                    outputStream.flush();
                    outputStream.close();
                } catch(IOException e) {
                    e.printStackTrace();
                }
                Toast.makeText(getApplicationContext(), "Image Saved at "+Environment.getExternalStorageDirectory() + "/walltone" , Toast.LENGTH_LONG).show();
            }
            @Override
            public void onLoadCleared(Drawable placeholder) {
            }
        });
    }

    //runtime storage permission
    public boolean checkPermission() {
        int READ_EXTERNAL_PERMISSION = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if((READ_EXTERNAL_PERMISSION != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_WRITE);
            return false;
        }
        return true;
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==PERMISSION_WRITE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            //do somethings
        }
    }










}
