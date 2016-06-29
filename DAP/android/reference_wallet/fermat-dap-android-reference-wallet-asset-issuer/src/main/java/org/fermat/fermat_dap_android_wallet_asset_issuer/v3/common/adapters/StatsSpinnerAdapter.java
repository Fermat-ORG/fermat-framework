package org.fermat.fermat_dap_android_wallet_asset_issuer.v3.common.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.R;

import java.util.List;

/**
 * Created by Frank Contreras (contrerasfrank@gmail.com) on 5/2/16.
 */
public class StatsSpinnerAdapter extends ArrayAdapter<String> {

    private List<String> objects;
    private Context context;

    public StatsSpinnerAdapter(Context context, int resourceId,
                               List<String> objects) {
        super(context, resourceId, objects);
        this.objects = objects;
        this.context = context;
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        return getCustomView(position, convertView, parent, true);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent, false);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent, boolean dropDown) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        int res = (dropDown) ? R.layout.dap_wallet_asset_issuer_stats_spinner_item : R.layout.dap_wallet_asset_issuer_stats_spinner;
        View row = inflater.inflate(res, parent, false);
        TextView label = (TextView) row.findViewById(R.id.itemText);
        label.setText(objects.get(position));

//        if (position == 0) {//Special style for dropdown header
//            label.setTextColor(context.getResources().getColor(R.color.text_hint_color));
//        }

        return row;
    }

}
