package com.bitdubai.reference_wallet.fan_wallet.common.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bitdubai.reference_wallet.fan_wallet.R;
import com.bitdubai.reference_wallet.fan_wallet.common.models.FollowingItems;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by Miguel Payarez on 16/03/16.
 */
public class FollowingAdapter extends RecyclerView.Adapter<FollowingAdapter.ViewHolder> {

    private List<FollowingItems> item=new ArrayList<>();
    private RecyclerView mRecyclerView;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imagen;
        TextView artistname;
        TextView artisturl;

        public ViewHolder(View v){
            super(v);
            imagen=(ImageView)v.findViewById(R.id.imagen);
            artisturl=(TextView)v.findViewById(R.id.url);
            artistname=(TextView)v.findViewById(R.id.artistname);

        }

    }


    public FollowingAdapter(List<FollowingItems> item){this.item.addAll(item);}

    @Override
    public int getItemCount() {
        return item.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.tky_fan_wallet_following_fragment_cardview,parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int i) {

        holder.imagen.setImageBitmap(item.get(i).getImagen());
        holder.artistname.setText(item.get(i).getArtist_name());
        holder.artisturl.setText(item.get(i).getArtist_url());
    }

    public void setFilter(List<FollowingItems> newitems) {
        item.clear();
        item.addAll(newitems);
        notifyDataSetChanged();
    }
}
