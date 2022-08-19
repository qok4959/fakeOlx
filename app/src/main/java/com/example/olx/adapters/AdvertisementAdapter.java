package com.example.olx.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
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
import com.example.olx.usefulClasses.AdvertisementData;

import java.util.ArrayList;

public class AdvertisementAdapter extends RecyclerView.Adapter {

    ArrayList<AdvertisementData> arrData;
    Context context;

    public AdvertisementAdapter(ArrayList<AdvertisementData> arrData2, Context context) {
        arrData = arrData2;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_advertisement, parent,false);
        AdvertisementAdapter.ViewHolder viewHolder = new AdvertisementAdapter.ViewHolder(v);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {


        AdvertisementAdapter.ViewHolder myViewHolder = (AdvertisementAdapter.ViewHolder)holder;
        myViewHolder.description.setText(arrData.get(position).getDescription());
        myViewHolder.price.setText(arrData.get(position).getPrice());

        String tempLink="https://upload.wikimedia.org/wikipedia/commons/thumb/a/ac/No_image_available.svg/1024px-No_image_available.svg.png";
        if (arrData.get(position).getLinks().size()==0)
            arrData.get(position).getLinks().add(tempLink);
        Glide.with(context).load(arrData.get(position).getLinks().get(0)).into(myViewHolder.img);
//        myViewHolder.itemView.set
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),
                        arrData.get(position).getCategory(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView description, price;
        ImageView img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            description = itemView.findViewById(R.id.textViewItemDescription);
            price = itemView.findViewById(R.id.textViewItemPrice);
            img = itemView.findViewById(R.id.imageViewEachPhoto);
        }
    }

}
