package com.bitdubai.reference_wallet.fan_wallet.common.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bitdubai.reference_wallet.fan_wallet.R;
import com.bitdubai.reference_wallet.fan_wallet.common.models.SongItems;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Miguel Payarez on 16/03/16.
 */
public class SongAdapter extends RecyclerView.Adapter<SongAdapter.ViewHolder> {
    private List<SongItems> items=new ArrayList<>();


    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView artistName;
        TextView songName;
        TextView status;
        ProgressBar progressBar;
        UUID song_id;
        public ViewHolder(View v){
            super(v);
            image =(ImageView)v.findViewById(R.id.imagen);
            songName =(TextView)v.findViewById(R.id.songname);
            artistName =(TextView)v.findViewById(R.id.artistname);
            status=(TextView)v.findViewById(R.id.status);
            progressBar=(ProgressBar)v.findViewById(R.id.progressBar2);
            progressBar.setMax(100);
            progressBar.setVisibility(View.INVISIBLE);
        }



    }

    public SongAdapter(List<SongItems> items){
        this.items.addAll(items);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.tky_fan_wallet_song_fragment_cardview,parent,false);

        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int i) {
        holder.image.setImageBitmap(items.get(i).getImagen());
        holder.songName.setText(items.get(i).getSong_name());
        holder.artistName.setText(items.get(i).getArtist_name());
        holder.status.setText(items.get(i).getStatus());
        holder.song_id=items.get(i).getSong_id();
        holder.progressBar.setProgress(items.get(i).getProgress());
        if(items.get(i).isProgressbarvissible()){
            holder.progressBar.setVisibility(View.VISIBLE);
        }else{
            holder.progressBar.setVisibility(View.INVISIBLE);
        }

    }

    //cases false new item
    //cases true update present item
    public void setFilter(SongItems newitems,boolean cases,int position) {
        if(cases) {
            items.set(position,newitems);
            notifyItemChanged(position);
        }else{
            items.add(newitems);
            notifyItemChanged(position);
            /*items.clear();
            items.addAll(newitems);
            notifyDataSetChanged();*/
        }

    }

}
