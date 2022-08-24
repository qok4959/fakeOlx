package com.example.olx.adapters;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.olx.R;
import com.example.olx.advertisement.AdvertisementDetails;
import com.example.olx.usefulClasses.AdvertisementData;
import com.example.olx.usefulClasses.ObjConversion;

import java.util.ArrayList;

public class AdvertisementFullAdapter extends RecyclerView.Adapter {

    ArrayList<AdvertisementData> arrData;
    Context context;

    public AdvertisementFullAdapter(ArrayList<AdvertisementData> arrData2, Context context) {
        arrData = arrData2;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_advertisement_full, parent,false);
        AdvertisementFullAdapter.ViewHolder viewHolder = new AdvertisementFullAdapter.ViewHolder(v);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {


        AdvertisementFullAdapter.ViewHolder myViewHolder = (AdvertisementFullAdapter.ViewHolder)holder;
        myViewHolder.title.setText(arrData.get(position).getDescription());
        myViewHolder.price.setText(arrData.get(position).getPrice());
        myViewHolder.location.setText(arrData.get(position).getLocation());
        myViewHolder.date.setText(arrData.get(position).getDate());


        String tempLink="https://upload.wikimedia.org/wikipedia/commons/thumb/a/ac/No_image_available.svg/1024px-No_image_available.svg.png";
        if (arrData.get(position).getLinks().size()==0)
            arrData.get(position).getLinks().add(tempLink);
        Glide.with(context).load(arrData.get(position).getLinks().get(0)).into(myViewHolder.img);
//        myViewHolder.itemView.set


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(context, AdvertisementDetails.class);

                ObjConversion androidPacket = new ObjConversion(arrData.get(position));
                String objAsJson = androidPacket.toJson();
                i.putExtra("my_obj", objAsJson);

                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title, price,location;
        ImageView img;
        TextView date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textViewItemTitle2);
            price = itemView.findViewById(R.id.textViewItemPrice2);
            img = itemView.findViewById(R.id.imageViewEachPhoto2);
            location = itemView.findViewById(R.id.textViewFullItemLocation);
            date = itemView.findViewById(R.id.textViewFullItemDate);
        }
    }

}
