package com.example.walltone;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.walltone.Model.Post2;

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

       Post2.Content data= wallpaper.get(position);



        Glide.with(context).load(data.getJpgImage()).into(holder.imageView);







    }

    @Override
    public int getItemCount() {
        return wallpaper.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView=itemView.findViewById(R.id.fullscreenImageViewId);

        }
    }




}
