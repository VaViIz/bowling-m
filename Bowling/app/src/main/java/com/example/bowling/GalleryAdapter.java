package com.example.bowling;

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

import java.util.ArrayList;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {
    private ArrayList<ImgCard> imgCardData;
    private ArrayList<ImgCard> imgCardDataAll;
    private Context context;
    private int lastPosition = -1;

    GalleryAdapter(Context context, ArrayList<ImgCard> imgCardList){
        this.imgCardData = imgCardList;
        this.imgCardDataAll = imgCardList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.gallery_cards, parent, false));
    }

    @Override
    public void onBindViewHolder(GalleryAdapter.ViewHolder holder, int position) {
        ImgCard currentCard = imgCardData.get(position);
        holder.bindTo(currentCard);
    }

    @Override
    public int getItemCount() {
        return imgCardData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView imgInfo;
        private ImageView img;

        public ViewHolder(View itemView) {
            super(itemView);

            imgInfo = itemView.findViewById(R.id.cardInfo);
            img = itemView.findViewById(R.id.cardImage);

            itemView.findViewById(R.id.goBooking).setOnClickListener(view -> ((GalleryActivity)context).cancel(view));
        }

        public void bindTo(ImgCard currentCard) {
            imgInfo.setText(currentCard.getInfo());

            Glide.with(context).load(currentCard.getImageResource()).into(img);
            itemView.findViewById(R.id.delete).setOnClickListener(view -> ((GalleryActivity)context).deleteImgCard(currentCard));
        }
    }
}
