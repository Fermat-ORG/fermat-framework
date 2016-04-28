package com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.filters;

import android.widget.Filter;

import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.adapters.ContactListAdapter;

import java.util.ArrayList;

/**
 * ContactListFilter
 *
 * @author Jose Cardozo josejcb (josejcb89@gmail.com) on 19/04/16.
 * @version 1.0
 */
public class ContactListFilter extends Filter {

    private ArrayList<String> data;
    private ContactListAdapter adapter;

    public ContactListFilter(ArrayList<String> data, ContactListAdapter adapter) {
        this.data = data;
        this.adapter = adapter;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        String filterString = constraint.toString().toLowerCase();
        adapter.setFilterString(filterString);

        FilterResults results = new FilterResults();

        final ArrayList<String> list = data;

        int count = list.size();
        final ArrayList<String> nlist = new ArrayList<>(count);

        String filterableString;
        String resource;

        for (int i = 0; i < count; i++) {
            resource = list.get(i);
            filterableString = resource;
            if (filterableString.toLowerCase().contains(filterString)) {
                nlist.add(list.get(i));
            }
        }

        results.values = nlist;
        results.count = nlist.size();

        return results;
    }

    @Override
    protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
//        adapter.changeDataSet((List<DigitalAsset>) filterResults.values);
        adapter.setData((ArrayList<String>) filterResults.values);
        adapter.notifyDataSetChanged();
    }
}
