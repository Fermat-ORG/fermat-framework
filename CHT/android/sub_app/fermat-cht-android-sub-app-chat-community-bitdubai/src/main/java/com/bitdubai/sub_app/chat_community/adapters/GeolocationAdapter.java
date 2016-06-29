package com.bitdubai.sub_app.chat_community.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bitdubai.fermat_android_api.layer.definition.wallet.utils.ImagesUtils;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedSubAppExceptionSeverity;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_api.layer.modules.interfaces.ModuleManager;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.interfaces.ChatActorCommunityInformation;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.ultils.CitiesImpl;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.interfaces.ExtendedCity;
import com.bitdubai.sub_app.chat_community.R;
import com.bitdubai.sub_app.chat_community.common.popups.GeolocationDialog;
import com.bitdubai.sub_app.chat_community.holders.CitiesListHolder;
import com.bitdubai.sub_app.chat_community.holders.ContactsListHolder;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by roy on 13/06/16.
 */
public class GeolocationAdapter extends ArrayAdapter {

    protected List<ExtendedCity> dataSet;
    private ErrorManager errorManager;
    private CitiesImpl cityFromList;
    private AdapterCallback mAdapterCallback;
    private GeolocationDialog locationDialog;

    public GeolocationAdapter(Context context, List<ExtendedCity> dataSet, ErrorManager errorManager,
                              AdapterCallback mAdapterCallback, GeolocationDialog locationDialog){
        super(context, R.layout.cht_comm_geolocation_item, dataSet);
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
        View item = inflater.inflate(R.layout.cht_comm_geolocation_item, null, true);
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
            errorManager.reportUnexpectedSubAppException(SubApps.CHT_COMMUNITY, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
        }
        return item;
    }
}
