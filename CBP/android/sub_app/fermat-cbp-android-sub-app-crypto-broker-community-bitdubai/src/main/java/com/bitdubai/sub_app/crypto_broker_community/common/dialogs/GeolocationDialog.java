package com.bitdubai.sub_app.crypto_broker_community.common.dialogs;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.ui.dialogs.FermatDialog;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.interfaces.CryptoBrokerCommunitySubAppModuleManager;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.exceptions.CantGetCitiesListException;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.interfaces.ExtendedCity;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.sub_app.crypto_broker_community.R;
import com.bitdubai.sub_app.crypto_broker_community.common.adapters.GeolocationAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by roy on 11/06/16.
 * Update by Jose Cardozo josejcb (josejcb89@gmail.com) on 27/06/16.
 */
public class GeolocationDialog
        extends FermatDialog<ReferenceAppFermatSession, SubAppResourcesProviderManager>
        implements View.OnClickListener {

    //ATTRIBUTES
    private EditText searchInput;
    private CryptoBrokerCommunitySubAppModuleManager mActorCommunityManager;
    private ListView mListView;
    private ReferenceAppFermatSession<CryptoBrokerCommunitySubAppModuleManager> appSession;
    private ImageView lupaButton;
    private ImageView closeButton;

    //THREAD ATTRIBUTES
    private boolean isRefreshing = false;
    private List<ExtendedCity> lstLocations = new ArrayList<>();
    private GeolocationAdapter adapter;
    private ErrorManager errorManager;
    private LinearLayout emptyView;
    private final Activity activity;
    TextView noDatalabel;
    private AdapterCallback mAdapterCallback;

    public GeolocationDialog (Activity activity, ReferenceAppFermatSession<CryptoBrokerCommunitySubAppModuleManager> appSession,
                              ResourceProviderManager resources, AdapterCallback mAdapterCallback){
        super(activity, appSession, null);
        this.appSession = appSession;
        this.activity = activity;
        this.mAdapterCallback = mAdapterCallback;
    }

    public void onClick(View v) {
        int id = v.getId();
    }

    public static interface AdapterCallback extends GeolocationAdapter.AdapterCallback {
        void onMethodCallback(ExtendedCity cityFromList);
    }

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        try {

            mActorCommunityManager = appSession.getModuleManager();
            errorManager = appSession.getErrorManager();
            mActorCommunityManager.setAppPublicKey(appSession.getAppPublicKey());

            mListView = (ListView) findViewById(R.id.geolocation_view);
            noDatalabel = (TextView) findViewById(R.id.nodatalabel_geo);
            searchInput = (EditText) findViewById(R.id.geolocation_input);
            emptyView = (LinearLayout) findViewById(R.id.empty_view_geo);
            closeButton = (ImageView) findViewById(R.id.close_geolocation_dialog);
            lupaButton = (ImageView) this.findViewById(R.id.lupita_button);

            closeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });

            adapter = new GeolocationAdapter(getActivity(), lstLocations, errorManager, mAdapterCallback, GeolocationDialog.this);
            mListView.setAdapter(adapter);
            lupaButton.setOnClickListener( new View.OnClickListener() {
                                               @Override
                                               public void onClick(View v) {
                                                   try {
                                                       lstLocations = mActorCommunityManager.getExtendedCitiesByFilter(searchInput.getText().toString());
                                                       adapter = new GeolocationAdapter(getActivity(), lstLocations, errorManager, mAdapterCallback, GeolocationDialog.this);
                                                       mListView.setAdapter(adapter);
                                                       adapter.refreshEvents(lstLocations);
                                                   }catch (CantGetCitiesListException e){
                                                       if (getActivity() != null)
                                                           errorManager.reportUnexpectedUIException(UISource.ACTIVITY,
                                                                   UnexpectedUIExceptionSeverity.CRASH, FermatException.wrapException(e));
                                                   }
                                               }
                                           }
            );
            showEmpty(true, emptyView);
        }catch (Exception e){
            System.out.println("Exception at Geolocation Dialog");
        }
    }

    protected int setLayoutId() {
        return R.layout.cbc_geolocation_dialog;
    }

    protected int setWindowFeature() {
        return Window.FEATURE_NO_TITLE;
    }

    public void showEmpty(boolean show, View emptyView) {
        if (show &&
                (noDatalabel.getVisibility() == View.GONE || noDatalabel.getVisibility() == View.INVISIBLE)) {
            noDatalabel.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.VISIBLE);
            if (adapter != null) {
                adapter.refreshEvents(null);
                mListView.setAdapter(adapter);
            }
        } else if (!show && noDatalabel.getVisibility() == View.VISIBLE) {
            noDatalabel.setVisibility(View.GONE);
            emptyView.setVisibility(View.GONE);
        }
    }
}