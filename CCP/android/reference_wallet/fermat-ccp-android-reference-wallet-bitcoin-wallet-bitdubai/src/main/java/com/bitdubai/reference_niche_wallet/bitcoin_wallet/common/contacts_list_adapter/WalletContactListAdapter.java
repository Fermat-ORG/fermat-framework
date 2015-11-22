package com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.contacts_list_adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bitdubai.android_fermat_ccp_wallet_bitcoin.R;
import com.bitdubai.fermat_android_api.layer.definition.wallet.utils.ImagesUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lnacosta on 2015.07.07..
 */
public class WalletContactListAdapter extends ArrayAdapter<WalletContact> {

    private List<WalletContact> originalData = null;
    private List<WalletContact> filteredData = null;

    Typeface tf ;

    public WalletContactListAdapter(Context context, int resource, List<WalletContact> items) {
        super(context, resource, items);
        tf=Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Regular.ttf");
        this.filteredData = items;
        this.originalData = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.wallets_bitcoin_fragment_contacts_list_item, null);
        }

        WalletContact contact = getItem(position);

        if (contact != null) {
            TextView contact_name = (TextView) v.findViewById(R.id.contact_name);
            contact_name.setText(contact.name);
            ImageView contact_profile_image = (ImageView) v.findViewById(R.id.contact_profile_image);
            try {
                if (contact.profileImage != null) {
                    if (contact.profileImage.length > 0) {
                        contact_profile_image.setImageDrawable(ImagesUtils.getRoundedBitmap(getContext().getResources(), contact.profileImage));
                    } else
                        contact_profile_image.setImageDrawable(ImagesUtils.getRoundedBitmap(getContext().getResources(), R.drawable.profile_image));
                } else
                    contact_profile_image.setImageDrawable(ImagesUtils.getRoundedBitmap(getContext().getResources(), R.drawable.profile_image));
            }catch (Exception e){
                contact_profile_image.setImageDrawable(ImagesUtils.getRoundedBitmap(getContext().getResources(), R.drawable.profile_image));
            }
        }
        return v;
    }

    public int getCount() {
        return filteredData.size();
    }

    public WalletContact getItem(int position) {
        return filteredData.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                String filterString = "";

                if (constraint !=null)
                    filterString = constraint.toString().toLowerCase();

                FilterResults results = new FilterResults();

                final List<WalletContact> list = originalData;

                int count = list.size();
                final ArrayList<WalletContact> nlist = new ArrayList<>(count);

                WalletContact filterableString ;

                for (int i = 0; i < count; i++) {
                    filterableString = list.get(i);
                    if (filterableString.name.toLowerCase().contains(filterString)) {
                        nlist.add(filterableString);
                    }
                }

                results.values = nlist;
                results.count = nlist.size();

                return results;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredData = (ArrayList<WalletContact>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}