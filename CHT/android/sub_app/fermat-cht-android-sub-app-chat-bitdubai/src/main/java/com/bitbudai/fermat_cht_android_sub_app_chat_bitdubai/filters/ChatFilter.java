package com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.filters;

import android.widget.Filter;

import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.adapters.ChatAdapter;
import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.models.ChatMessage;

import java.util.ArrayList;

/**
 * ChatFilter
 *
 * @author Jose Cardozo josejcb (josejcb89@gmail.com) on 19/04/16.
 * @version 1.0
 */
public class ChatFilter extends
        Filter {

    private ArrayList<ChatMessage> data;
    private ChatAdapter adapter;

    public ChatFilter(ArrayList<ChatMessage> data, ChatAdapter adapter) {
        this.data = data;
        this.adapter = adapter;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        String filterString = constraint.toString().toLowerCase();
        adapter.setFilterString(filterString);

        FilterResults results = new FilterResults();

        final ArrayList<ChatMessage> list = data;

        int count = list.size();
        final ArrayList<ChatMessage> nlist = new ArrayList<>(count);

        String filterableString;
        ChatMessage resource;

        for (int i = 0; i < count; i++) {
            resource = list.get(i);
            filterableString = resource.getMessage();
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
        //adapter.setData((ArrayList<ChatMessage>) filterResults.values);
        adapter.changeDataSet((ArrayList<ChatMessage>) filterResults.values);
        adapter.notifyDataSetChanged();
        adapter.setFilterSetted(1);
    }
}
