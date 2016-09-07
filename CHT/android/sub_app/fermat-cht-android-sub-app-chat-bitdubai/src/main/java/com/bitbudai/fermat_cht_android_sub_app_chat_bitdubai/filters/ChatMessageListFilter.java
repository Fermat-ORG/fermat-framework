package com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.filters;

import android.widget.Filter;

import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.adapters.ChatMessageListAdapter;
import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.models.ChatMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * ChatMessageListFilter
 *
 * @author Jose Cardozo josejcb (josejcb89@gmail.com) on 19/04/16.
 * @version 1.0
 */
public class ChatMessageListFilter extends Filter {

    private List<ChatMessage> data;
    private ChatMessageListAdapter adapter;

    public ChatMessageListFilter(List<ChatMessage> data, ChatMessageListAdapter adapter) {
        this.data = data;
        this.adapter = adapter;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        String filterString = constraint.toString().toLowerCase();
        adapter.setFilterString(filterString);

        FilterResults results = new FilterResults();

        final List<ChatMessage> list = data;

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
        adapter.changeDataSet((ArrayList<ChatMessage>) filterResults.values);
    }
}
