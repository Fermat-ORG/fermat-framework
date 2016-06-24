package org.fermat.fermat_dap_android_sub_app_asset_issuer_community.filters;

import android.widget.Filter;

import org.fermat.fermat_dap_android_sub_app_asset_issuer_community.adapters.IssuerCommunityAdapter;
import org.fermat.fermat_dap_android_sub_app_asset_issuer_community.models.ActorIssuer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Frank Contreras (contrerasfrank@gmail.com) on 6/6/16.
 */
public class IssuerCommunityAdapterFilter extends Filter {
    private List<ActorIssuer> data;
    private IssuerCommunityAdapter adapter;

    public IssuerCommunityAdapterFilter(List<ActorIssuer> data, IssuerCommunityAdapter adapter) {
        this.data = data;
        this.adapter = adapter;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        String filterString = constraint.toString().toLowerCase();

        FilterResults results = new FilterResults();

        if (data != null) {
            final List<ActorIssuer> list = data;

            int count = list.size();
            final ArrayList<ActorIssuer> nlist = new ArrayList<>(count);

            String filterableString;
            ActorIssuer actorIssuer;

            for (int i = 0; i < count; i++) {
                actorIssuer = list.get(i);
                filterableString = actorIssuer.getRecord().getName();
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
        adapter.changeDataSet((List<ActorIssuer>) filterResults.values);
    }
}
