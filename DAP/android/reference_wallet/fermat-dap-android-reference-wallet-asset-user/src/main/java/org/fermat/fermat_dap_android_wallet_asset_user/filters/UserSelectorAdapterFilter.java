package org.fermat.fermat_dap_android_wallet_asset_user.filters;

import android.widget.Filter;

import org.fermat.fermat_dap_android_wallet_asset_user.adapters.UserSelectorAdapter;
import org.fermat.fermat_dap_android_wallet_asset_user.models.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jinmy Bohorquez on 24/03/16.
 */
public class UserSelectorAdapterFilter extends Filter {

    private List<User> data;
    private UserSelectorAdapter adapter;

    public UserSelectorAdapterFilter(List<User> data, UserSelectorAdapter adapter) {
        this.data = data;
        this.adapter = adapter;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        String filterString = constraint.toString().toLowerCase();

        FilterResults results = new FilterResults();

        final List<User> list = data;

        int count = list.size();
        final ArrayList<User> nlist = new ArrayList<>(count);

        String filterableString;
        User user;

        for (int i = 0; i < count; i++) {
            user = list.get(i);
            filterableString = user.getName();
            if (filterableString.toLowerCase().contains(filterString)) {
                nlist.add(list.get(i));
            }
        }

        results.values = nlist;
        results.count = nlist.size();

        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults filterResults) {
        adapter.changeDataSet((List<User>) filterResults.values);
    }
}
