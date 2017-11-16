package com.example.theeagle.e_library.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.theeagle.e_library.R;
import com.example.theeagle.e_library.activities.MyBooksActivity;
import com.example.theeagle.e_library.data.Book;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.viewHolder> {

    private ArrayList<Book> books;
    private Context context;
    private Book book;

    public Adapter(ArrayList<Book> books, Context context) {
        this.books = books;
        this.context = context;
    }

    public Adapter(MyBooksActivity myBooksActivity, ArrayList<Book> books) {

    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new viewHolder(LayoutInflater.from(context).inflate(R.layout.book_item, parent, false));
    }

    @Override
    public void onBindViewHolder(viewHolder holder, int position) {
        holder.book = books.get(position);
        holder.title_tv.setText(holder.book.getTitle());
        holder.price_tv.setText(String.valueOf(holder.book.getPrice()));
        holder.describe_tv.setText(holder.book.getDescription());
        Glide.with(context).load(holder.book.getImageUrl()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    class viewHolder extends RecyclerView.ViewHolder {
        View view;
        ImageView imageView;
        TextView price_tv, title_tv, describe_tv;
        Book book;

        private viewHolder(View itemView) {
            super(itemView);
            view = itemView;
            imageView = view.findViewById(R.id.book_iv);
            price_tv = view.findViewById(R.id.price_tv);
            title_tv = view.findViewById(R.id.book_title_tv);
            describe_tv = view.findViewById(R.id.discrip_tv);

        }
    }
}

