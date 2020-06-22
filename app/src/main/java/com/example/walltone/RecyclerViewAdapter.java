package com.example.walltone;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.walltone.Model.Post2;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private List<Post2.Content> wallpaper;
    private Context context;



    public RecyclerViewAdapter(List<Post2.Content> wallpaper, Context context) {
        this.wallpaper = wallpaper;
        this.context = context;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.full_screen_image, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

       final Post2.Content data= wallpaper.get(position);



        Glide.with(context).load(data.getJpgImage()).into(holder.imageView);


        holder.downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveImage(data.getJpgImage());
            }
        });




    }

    @Override
    public int getItemCount() {
        return wallpaper.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        Button downloadButton;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            downloadButton = itemView.findViewById(R.id.buttonD);
            imageView=itemView.findViewById(R.id.fullscreenImageViewId);

        }
    }

    public void SaveImage(String url) {
        Glide.with(context).asBitmap().load(url).into(new CustomTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {
                try {
                    File mydir = new File(Environment.getExternalStorageDirectory() + "/walltone");
                    if (!mydir.exists()) {
                        mydir.mkdirs();
                    }

                    String fileUri = mydir.getAbsolutePath() + File.separator + System.currentTimeMillis() + ".jpg";
                    FileOutputStream outputStream = new FileOutputStream(fileUri);

                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                    outputStream.flush();
                    outputStream.close();
                } catch(IOException e) {
                    e.printStackTrace();
                }
                Toast.makeText(context, "Image Saved at "+Environment.getExternalStorageDirectory() + "/walltone" , Toast.LENGTH_LONG).show();
            }
            @Override
            public void onLoadCleared(Drawable placeholder) {
            }
        });
    }



}
