package com.bitdubai.sub_app.developer.filters;

import android.widget.Filter;

import com.bitdubai.sub_app.developer.common.Loggers;
import com.bitdubai.sub_app.developer.common.Resource;
import com.bitdubai.sub_app.developer.fragment.DatabaseToolsFragment;
import com.bitdubai.sub_app.developer.fragment.LogToolsFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Frank Contreras (contrerasfrank@gmail.com) on 2/4/16.
 */
public class DeveloperLogFilter extends Filter {

    private List<Loggers> data;
    private LogToolsFragment.AppListAdapter adapter;

    public DeveloperLogFilter(List<Loggers> data, LogToolsFragment.AppListAdapter adapter) {
        this.data = data;
        this.adapter = adapter;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        String filterString = constraint.toString().toLowerCase();
        adapter.setFilterString(filterString);

        FilterResults results = new FilterResults();

        final List<Loggers> list = data;

        int count = list.size();
        final ArrayList<Loggers> nlist = new ArrayList<>(count);

        String filterableString;
        Loggers resource;

        for (int i = 0; i < count; i++) {
            resource = list.get(i);
            filterableString = resource.classHierarchyLevels.getLevel0();
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
        adapter.setData((List<Loggers>) filterResults.values);
        adapter.notifyDataSetChanged();
    }
}
