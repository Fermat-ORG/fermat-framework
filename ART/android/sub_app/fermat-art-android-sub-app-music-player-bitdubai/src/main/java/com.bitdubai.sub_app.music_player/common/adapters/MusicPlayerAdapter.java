package com.bitdubai.sub_app.music_player.common.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bitdubai.sub_app.music_player.R;
import com.bitdubai.sub_app.music_player.common.models.MusicPlayerItems;

import java.util.List;



/**
 * Created by Miguel Payarez on 16/03/16.
 */
public class MusicPlayerAdapter extends RecyclerView.Adapter<MusicPlayerAdapter.ViewHolder> {

   private List<MusicPlayerItems> items;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imagen;
        TextView texto;
        TextView artistname;

        public ViewHolder(View v){
            super(v);
            imagen=(ImageView)v.findViewById(R.id.imagen);
            texto=(TextView)v.findViewById(R.id.songname);
            artistname=(TextView)v.findViewById(R.id.artistname);

        }

    }

    public MusicPlayerAdapter(List<MusicPlayerItems> items){
        this.items=items;
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.art_music_player_cardview,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int i) {
        holder.imagen.setImageBitmap(items.get(i).getImagen());
        holder.texto.setText(items.get(i).getSong_name());
        holder.artistname.setText(items.get(i).getArtist_name());
    }

    public void setFilter(List<MusicPlayerItems> newitems) {

            items.clear();
            items.addAll(newitems);
            notifyDataSetChanged();

    }
}
