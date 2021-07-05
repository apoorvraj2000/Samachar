package com.example.samachar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    ArrayList<News> items=new ArrayList<>();
    NewsItemClicked listener;
    public  MyAdapter(NewsItemClicked listener){
    this.listener=listener;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news,parent,false);
        MyViewHolder viewHolder=new MyViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClicked(items.get(viewHolder.getAdapterPosition()));
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
    News currentItem=items.get(position);
    holder.textView.setText(currentItem.title);
    holder.authorName.setText(currentItem.author);
    Glide.with(holder.itemView.getContext()).load(currentItem.imageUrl).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        //return 0;
        return  items.size();
    }
    public  void updateNews(ArrayList<News> updatedNews){
        items.clear();
        items.addAll(updatedNews);
        notifyDataSetChanged();
    }
    //Inner class
    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;
        TextView authorName;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView=(TextView)itemView.findViewById(R.id.title);
            imageView=(ImageView)itemView.findViewById(R.id.image);
            authorName=(TextView)itemView.findViewById(R.id.author);
        }
    }

}

interface NewsItemClicked{
    void onItemClicked(News items);
}