package com.example.olx.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.olx.R;
import com.example.olx.usefulClasses.AdvertisementData;

import java.util.ArrayList;
import java.util.List;

public class TextAdapter extends RecyclerView.Adapter {

    List links;
    Context context;

    public TextAdapter(ArrayList<String> list, Context context2) {
        links = list;
        context = context2;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        ViewHolder myViewHolder = (ViewHolder) holder;
        myViewHolder.link.setText(links.get(position).toString());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), links.get(position).toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return links.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView link;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            link = itemView.findViewById(R.id.textViewItem);
        }
    }
}
