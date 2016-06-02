package com.bitdubai.reference_wallet.fan_wallet.common.adapters;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
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

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView artistName;
        TextView artistUrl;

        public ViewHolder(View v){
            super(v);
            image =(ImageView)v.findViewById(R.id.imagen);
            artistUrl =(TextView)v.findViewById(R.id.url);
            artistName =(TextView)v.findViewById(R.id.artistname);

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

        holder.image.setImageBitmap(item.get(i).getImage());
        holder.artistName.setText(item.get(i).getUsername());
        //Description html
        //holder.artistUrl.setText(item.get(i).getURL());
        Spanned spanned = Html.fromHtml(item.get(i).getDescriptionHTML());
        holder.artistUrl.setText(spanned);
    }

    public void setFilter(List<FollowingItems> newItems) {
        item.clear();
        item.addAll(newItems);
        notifyDataSetChanged();
    }
}
