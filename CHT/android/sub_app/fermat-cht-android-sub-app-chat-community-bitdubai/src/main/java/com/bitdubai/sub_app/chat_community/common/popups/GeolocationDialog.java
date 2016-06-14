package com.bitdubai.sub_app.chat_community.common.popups;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.FermatSession;
import com.bitdubai.fermat_android_api.ui.dialogs.FermatDialog;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.SearchView;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.sub_app.chat_community.R;
/**
 * Created by roy on 11/06/16.
 */
public class GeolocationDialog extends FermatDialog implements View.OnClickListener {

    private TextView CountryPlace;
    private TextView StatePlace;
    private SearchView SeachInput;

    String Country;
    String State;
    String Input;



    public GeolocationDialog (Context activity, FermatSession referenceAppFermatSession, ResourceProviderManager resources){
        super(activity, referenceAppFermatSession, resources);
    }

    public void setCountryPlace(String country){
        Country = country;
    }

    public void setStatePlace (String state){
        State = state;
    }

    public void setSeachInput (String input){
        Input = input;
    }

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        CountryPlace = (TextView) this.findViewById(R.id.country_search);
        StatePlace = (TextView) this.findViewById(R.id.state_search);

    }

    public void onClick(View v) {
        int i = v.getId();

    }

    protected int setLayoutId() {
        return R.layout.cht_comm_geolocation_dialog;
    }

    protected int setWindowFeature() {
        return Window.FEATURE_NO_TITLE;
    }


}
