package com.bitdubai.sub_app.chat_community.filters;

import android.widget.Filter;

import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.interfaces.ChatActorCommunityInformation;
import com.bitdubai.sub_app.chat_community.adapters.CommunityListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * CommunityFilter
 *
 * @author Jose Cardozo josejcb (josejcb89@gmail.com) on 7/5/16.
 * @version 1.0
 */
public class CommunityFilter extends Filter {
    //
    private List<ChatActorCommunityInformation> data;
    private CommunityListAdapter adapter;
    ArrayList<ChatActorCommunityInformation> nlist = new ArrayList<>();

    public CommunityFilter(List<ChatActorCommunityInformation> data, CommunityListAdapter adapter) {
        this.data = data;
        this.adapter = adapter;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        String filterString = constraint.toString().toLowerCase();
        adapter.setFilterString(filterString);

        FilterResults results = new FilterResults();

        //final List<ChatActorCommunityInformation> list = data;

        int count = data.size();

        //final ArrayList<ChatActorCommunityInformation> nlist = new ArrayList<>();

        String filterableString;
        ChatActorCommunityInformation resource;

        for (int i = 0; i < count; i++) {
            resource = data.get(i);
            filterableString = resource.getAlias();
            if (filterableString.toLowerCase().contains(filterString)) {
                nlist.add(data.get(i));
            }
        }

        results.values = nlist;
        results.count = nlist.size();

        return results;
    }

    @Override
    protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
        //adapter.setData((List<ChatActorCommunityInformation>) filterResults.values);
        adapter.changeDataSet((List<ChatActorCommunityInformation>) filterResults.values);
        adapter.notifyDataSetChanged();
    }
}
