package com.example.theeagle.e_library.adapter;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.theeagle.e_library.R;
import com.example.theeagle.e_library.data.Book;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.viewHolder> {

    private ArrayList<Book> books;

    public Adapter(ArrayList<Book> books) {
        this.books = books;
    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new viewHolder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.book_item, parent, false));
    }

    @Override
    public void onBindViewHolder(viewHolder holder, int position) {
        final Book book = books.get(holder.getAdapterPosition());
        holder.title_tv.setText(book.getTitle());
        holder.price_tv.setText(String.valueOf(book.getPrice()));
        holder.describe_tv.setText(book.getDescription());
        Glide.with(holder.itemView.getContext()).load(book.getImageUrl()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    class viewHolder extends RecyclerView.ViewHolder {
        View view;
        ImageView imageView;
        TextView price_tv, title_tv, describe_tv;

        private viewHolder(View itemView) {
            super(itemView);
            view = itemView;
            imageView = view.findViewById(R.id.book_iv);
            price_tv = view.findViewById(R.id.price_tv);
            title_tv = view.findViewById(R.id.book_title_tv);
            describe_tv = view.findViewById(R.id.discrip_tv);

        }
    }

    public void addBook(Book book){
        this.books.add(book);
        notifyDataSetChanged();
    }
}

