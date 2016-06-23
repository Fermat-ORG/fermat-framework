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
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.interfaces.Cities;
import com.bitdubai.sub_app.chat_community.R;
import com.bitdubai.sub_app.chat_community.holders.CitiesListHolder;
import com.bitdubai.sub_app.chat_community.holders.ContactsListHolder;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by roy on 13/06/16.
 */
public class GeolocationAdapter extends ArrayAdapter<Cities> {

//    private List<Cities> cities = new ArrayList<>();
    protected List<Cities> dataSet;
    private ErrorManager errorManager;

    public GeolocationAdapter(Context context, List<Cities> citiesList){
        super(context, 0, citiesList );
    }

    public void changeDataSet(List<Cities> dataSet) {
        this.dataSet = dataSet;
        notifyDataSetChanged();
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        try {

            if(convertView == null){
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.cht_comm_geolocation_results_item, parent, false);
            }

            Cities cities = getItem(position);


            TextView Country = (TextView) convertView.findViewById(R.id.country_search);
            TextView State = (TextView) convertView.findViewById(R.id.state_search);

            Country.setText(cities.getCountryName());
            State.setText(cities.getName());

        } catch (Exception e) {
            errorManager.reportUnexpectedSubAppException(SubApps.CHT_COMMUNITY, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
        }
        return convertView;
    }



//    @Override
//    protected CitiesListHolder createHolder(View itemView, int type) {
//        return new CitiesListHolder(itemView);
//    }

//    @Override
//    protected int getCardViewResource() {
//        return R.layout.cht_comm_geolocation_results_item;
//    }

//    @Override
//    protected void bindHolder(CitiesListHolder holder, Cities data, int position) {
//        if (data.getCountryName() != null || data.getName() != null) {
//            holder.city.setText(data.getCountryName());
//            holder.state.setText(data.getName());
//        }
//    }

//    public int getSize() {
//        if (dataSet != null)
//            return dataSet.size();
//        return 0;
//    }
}
