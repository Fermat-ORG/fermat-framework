package org.fermat.fermat_dap_android_sub_app_asset_factory.v3.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bitdubai.fermat_api.layer.all_definition.util.BitcoinConverter.Currency;
import com.bitdubai.fermat_dap_android_sub_app_asset_factory_bitdubai.R;

/**
 * Created by Frank Contreras (contrerasfrank@gmail.com) on 1/25/16.
 */
public class AssetValueSpinnerAdapter extends ArrayAdapter<Currency> {
    private Activity context;
    Currency[] data = null;

    public AssetValueSpinnerAdapter(Activity context, int resource, Currency[] data) {
        super(context, resource, data);
        this.context = context;
        this.data = data;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if (row == null) {
            //inflate your customlayout for the textview
            LayoutInflater inflater = context.getLayoutInflater();
            row = inflater.inflate(R.layout.dap_v3_factory_wizard_crypto_asset_value_spinner_item, parent, false);
        }
        //put the data in it
        String item = data[position].name();
        if (item != null) {
            TextView text1 = (TextView) row.findViewById(R.id.textItem);
//            text1.setTextColor(Color.WHITE);
            text1.setText(item);
        }
        return row;
    }
}
