package com.example.olx.adapters;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.olx.R;
import com.example.olx.advertisement.AdvertisementDetails;
import com.example.olx.model.UserModel;
import com.example.olx.usefulClasses.AdvertisementData;
import com.example.olx.usefulClasses.ObjConversion;

import java.util.ArrayList;
import java.util.Optional;

public class AdvertisementFullAdapter extends RecyclerView.Adapter {

    public UserModel userModel;
    ArrayList<AdvertisementData> arrData;
    Context context;


    public AdvertisementFullAdapter(ArrayList<AdvertisementData> arrData2, UserModel uModel, Context context) {
        arrData = arrData2;
        userModel = uModel;
        this.context = context;
    }

    public AdvertisementFullAdapter(ArrayList<AdvertisementData> arrData2, Context context) {
        arrData = arrData2;
        UserModel userModel= new UserModel();
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("invokeTest", "onCreateViewHolder");
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_advertisement_full, parent, false);
        AdvertisementFullAdapter.ViewHolder viewHolder = new AdvertisementFullAdapter.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        Log.d("invokeTest", "onBindViewHolder");

        AdvertisementFullAdapter.ViewHolder myViewHolder = (AdvertisementFullAdapter.ViewHolder) holder;
        myViewHolder.title.setText(arrData.get(position).getDescription());
        myViewHolder.price.setText(arrData.get(position).getPrice());
        myViewHolder.location.setText(arrData.get(position).getLocation());
        myViewHolder.date.setText(arrData.get(position).getDate());


        String tempLink = "https://upload.wikimedia.org/wikipedia/commons/thumb/a/ac/No_image_available.svg/1024px-No_image_available.svg.png";
        if (arrData.get(position).getLinks().size() == 0)
            arrData.get(position).getLinks().add(tempLink);
        Glide.with(context).load(arrData.get(position).getLinks().get(0)).into(myViewHolder.img);
//        myViewHolder.itemView.set


//        Log.d("arraySize", String.valueOf(userModel.getFavorites().size()));

        Optional tempFavorites = userModel.getFavorites()
                .stream()
                .filter(x -> {
                    Log.d("equality", x + "==" + arrData.get(position).getId());
                    return x.equals(arrData.get(position).getId());
                })
                .findAny();

        if (tempFavorites.isPresent())
            myViewHolder.imgFavorite.setColorFilter(context.getResources().getColor(R.color.red), PorterDuff.Mode.SRC_IN);
        else
            myViewHolder.imgFavorite.setColorFilter(context.getResources().getColor(R.color.black), PorterDuff.Mode.SRC_IN);


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

        myViewHolder.imgFavorite.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {


                Optional tempFavorites = userModel.getFavorites()
                        .stream()
                        .filter(x -> {
                            Log.d("equality", x + "==" + arrData.get(position).getId());
                            return x.equals(arrData.get(position).getId());
                        })
                        .findAny();

                if (tempFavorites.isPresent())
                    myViewHolder.imgFavorite.setColorFilter(context.getResources().getColor(R.color.black), PorterDuff.Mode.SRC_IN);
                else
                    myViewHolder.imgFavorite.setColorFilter(context.getResources().getColor(R.color.red), PorterDuff.Mode.SRC_IN);

                userModel.toggleFavorite(arrData.get(position).id);

                Toast.makeText(context, "favorite clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title, price, location;
        ImageView img, imgFavorite;
        TextView date;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textViewItemTitle2);
            price = itemView.findViewById(R.id.textViewItemPrice2);
            img = itemView.findViewById(R.id.imageViewEachPhoto2);
            location = itemView.findViewById(R.id.textViewFullItemLocation);
            date = itemView.findViewById(R.id.textViewFullItemDate);
            imgFavorite = itemView.findViewById(R.id.imageViewFavoriteAdvertisement);
        }
    }

}
