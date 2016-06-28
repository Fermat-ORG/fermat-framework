package com.bitdubai.sub_app.crypto_broker_community.common.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedSubAppExceptionSeverity;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.interfaces.ExtendedCity;
import com.bitdubai.sub_app.crypto_broker_community.R;
import com.bitdubai.sub_app.crypto_broker_community.common.dialogs.GeolocationDialog;

import java.util.List;

/**
 * Created by roy on 13/06/16.
 * Update by Jose Cardozo josejcb (josejcb89@gmail.com) on 27/06/16.
 */
public class GeolocationAdapter extends ArrayAdapter {

    protected List<ExtendedCity> dataSet;
    private ErrorManager errorManager;
    private AdapterCallback mAdapterCallback;
    private GeolocationDialog locationDialog;

    public GeolocationAdapter(Context context, List<ExtendedCity> dataSet, ErrorManager errorManager,
                              AdapterCallback mAdapterCallback, GeolocationDialog locationDialog){
        super(context, R.layout.cbc_geolocation_item, dataSet);
        this.dataSet = dataSet;
        this.errorManager = errorManager;
        this.mAdapterCallback = mAdapterCallback;
        this.locationDialog = locationDialog;
    }

    public static interface AdapterCallback {
        void onMethodCallback(ExtendedCity cityFromList);
    }

    public void refreshEvents(List<ExtendedCity> dataSet) {
        this.dataSet = dataSet;
        notifyDataSetChanged();
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View item = inflater.inflate(R.layout.cbc_geolocation_item, null, true);
        try {
            TextView Country = (TextView) item.findViewById(R.id.country_search2);
            TextView State = (TextView) item.findViewById(R.id.state_search2);
            Country.setText(dataSet.get(position).getCountryName());
            State.setText(dataSet.get(position).getName());
            final int pos=position;
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mAdapterCallback.onMethodCallback(dataSet.get(pos));
                    locationDialog.dismiss();
                }
            });
        } catch (Exception e) {
            errorManager.reportUnexpectedSubAppException(SubApps.CBP_CRYPTO_BROKER_COMMUNITY,
                    UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
        }
        return item;
    }
}
