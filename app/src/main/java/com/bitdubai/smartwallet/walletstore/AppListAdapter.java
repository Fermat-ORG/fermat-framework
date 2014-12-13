package com.bitdubai.smartwallet.walletstore;

import java.util.List;

import android.app.Service;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bitdubai.smartwallet.R;

/**
 * GridView �� Adapter.
 */
public class AppListAdapter extends ArrayAdapter<App> {
    
    /**
     * �R���X�g���N�^.
     * @param context {@link Context}
     * @param textViewResourceId ���\�[�XID
     * @param objects ���X�g
     */
    public AppListAdapter(Context context, int textViewResourceId, List<App> objects) {
        super(context, textViewResourceId, objects);
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        
        App item = getItem(position);
        
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Service.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.wallets_teens_fragment_stores_item, parent, false);
            holder = new ViewHolder();
            holder.imageView = (ImageView) convertView.findViewById(R.id.image_view);
            holder.titleTextView = (TextView) convertView.findViewById(R.id.title_text_view);
            holder.companyTextView = (TextView) convertView.findViewById(R.id.company_text_view);
            //holder.ratingBar = (RatingBar) convertView.findViewById(R.id.rating_bar);
            holder.valueTextView = (TextView) convertView.findViewById(R.id.value_text_view);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        
        holder.titleTextView.setText(item.title);
        holder.companyTextView.setText(item.company);
        holder.ratingBar.setRating(item.rate);
        holder.valueTextView.setText("��" + item.value);
        
        return convertView;
    }
    
    /**
     * ViewHolder.
     */
    private class ViewHolder {
        
        /** �A�v���A�C�R��. */
        public ImageView imageView;
        /** �A�v����. */
        public TextView titleTextView;
        /** ������. */
        public TextView companyTextView;
        /** �]��. */
        public RatingBar ratingBar;
        /** ���i. */
        public TextView valueTextView;
        
    }

}
