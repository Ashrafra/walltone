package com.example.walltone;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.walltone.Model.Post;
import com.example.walltone.Model.Post2;

import java.util.List;

import static com.example.walltone.R.id.ImageViewId;
import static com.example.walltone.R.id.textViewId;

public class CustomAdapter extends BaseAdapter {
    private Context context;
    public List<Post2.Content> wallpaper2; // after creating the model class "Post2"
    private LayoutInflater inflater;
    private int id;


     public CustomAdapter(Context context, List<Post2.Content> wallpaper, int id) {
        this.context = context;
        this.wallpaper2 = wallpaper;
        this.id= id;

    }

    @Override
    public int getCount() {
        return wallpaper2.size();
    }

    @Override
    public Object getItem(int i) {
        return wallpaper2.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

       if(view==null){
           inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
           view =inflater.inflate(R.layout.sample_view, viewGroup, false);
       }


       //get data
        ImageView imageView= view.findViewById(ImageViewId);
        TextView textView= view.findViewById(textViewId);

        textView.setVisibility(View.GONE);

        // set data
//        textView.setText(wallpaper.get(i).getName());

        Glide.with(context).load(wallpaper2.get(i).getJpgImage()).into(imageView);

         view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, FullScreenActivity.class);
                intent.putExtra("ID", id);
                intent.putExtra("Imgurl", wallpaper2.get(i).getJpgImage());
                context.startActivity(intent);
            }
        });

        return view;
    }
}