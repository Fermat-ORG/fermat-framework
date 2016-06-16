package org.fermat.fermat_dap_android_sub_app_asset_user_community.filters;

import android.widget.Filter;

import org.fermat.fermat_dap_android_sub_app_asset_user_community.adapters.UserCommunityAdapter;
import org.fermat.fermat_dap_android_sub_app_asset_user_community.models.Actor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Frank Contreras (contrerasfrank@gmail.com) on 6/8/16.
 */
public class UserCommunityAdapterFilter extends Filter {

    private List<Actor> data;
    private UserCommunityAdapter adapter;

    public UserCommunityAdapterFilter(List<Actor> data, UserCommunityAdapter adapter) {
        this.data = data;
        this.adapter = adapter;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        String filterString = constraint.toString().toLowerCase();

        FilterResults results = new FilterResults();

        if (data != null) {
            final List<Actor> list = data;

            int count = list.size();
            final ArrayList<Actor> nlist = new ArrayList<>(count);

            String filterableString;
            Actor actor;

            for (int i = 0; i < count; i++) {
                actor = list.get(i);
                filterableString = actor.getName();
                if (filterableString.toLowerCase().contains(filterString)) {
                    nlist.add(list.get(i));
                }
            }

            results.values = nlist;
            results.count = nlist.size();
        }

        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults filterResults) {
        adapter.changeDataSet((List<Actor>) filterResults.values);
    }
}
