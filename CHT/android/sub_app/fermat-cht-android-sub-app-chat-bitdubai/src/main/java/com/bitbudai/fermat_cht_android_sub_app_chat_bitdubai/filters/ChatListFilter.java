package com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.filters;

import android.widget.Filter;

import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.adapters.ChatListAdapter;
import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.models.Chat;

import java.util.ArrayList;
import java.util.List;

/**
 * ChatListFilter
 *<p/>
 * Created by Leon Acosta (laion.cj91@gmail.com) on 06/09/2016.
 *
 * @author  lnacosta
 * @version 1.0
 */
public class ChatListFilter extends Filter {

    private List<Chat> data;
    private ChatListAdapter adapter;

    public ChatListFilter(List<Chat> data, ChatListAdapter adapter) {
        this.data = data;
        this.adapter = adapter;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        String filterString = constraint.toString().toLowerCase();
        adapter.setFilterString(filterString);

        FilterResults results = new FilterResults();

        final List<Chat> list = data;

        int count = list.size();
        final ArrayList<Chat> nlist = new ArrayList<>(count);

        String filterableString;
        Chat resource;

        for (int i = 0; i < count; i++) {
            resource = list.get(i);
            filterableString = resource.getContactName();
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
        adapter.changeDataSet((ArrayList<Chat>) filterResults.values);
    }
}
