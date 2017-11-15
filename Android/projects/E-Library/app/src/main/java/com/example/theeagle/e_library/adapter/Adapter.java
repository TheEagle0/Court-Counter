package com.example.theeagle.e_library.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.example.theeagle.e_library.R;

public class Adapter extends RecyclerView.Adapter<Adapter.viewHolder> {


    Context context;

    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new viewHolder(LayoutInflater.from(context).inflate(R.layout.book_item,parent,false));
    }

    @Override
    public void onBindViewHolder(viewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

     class viewHolder extends RecyclerView.ViewHolder{
        View view;

        private viewHolder(View itemView) {
            super(itemView);
            view=itemView;
            ImageView imageView;
            imageView=view.findViewById(R.id.book_iv);

        }
    }
}

