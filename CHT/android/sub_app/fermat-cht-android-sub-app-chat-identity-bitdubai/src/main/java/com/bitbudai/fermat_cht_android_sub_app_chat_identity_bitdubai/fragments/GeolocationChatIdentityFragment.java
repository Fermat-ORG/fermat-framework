package com.bitbudai.fermat_cht_android_sub_app_chat_identity_bitdubai.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bitbudai.fermat_cht_android_sub_app_chat_identity_bitdubai.util.GeolocationIdentityExecutor;
import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedSubAppExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_cht_android_sub_app_chat_identity_bitdubai.R;
import com.bitdubai.fermat_api.layer.all_definition.enums.GeoFrequency;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CHTException;
import com.bitdubai.fermat_cht_api.layer.identity.exceptions.CantGetChatIdentityException;
import com.bitdubai.fermat_cht_api.layer.identity.interfaces.ChatIdentity;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.identity.ChatIdentityModuleManager;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.identity.ChatIdentityPreferenceSettings;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.bitbudai.fermat_cht_android_sub_app_chat_identity_bitdubai.util.CreateChatIdentityExecutor.SUCCESS;

/**
 * FERMAT-ORG
 * Developed by Lozadaa on 04/04/16.
 * Updated by Jose Cardozo josejcb (josejcb89@gmail.com) on 16/06/16.
 */

public class GeolocationChatIdentityFragment  extends AbstractFermatFragment<ReferenceAppFermatSession<ChatIdentityModuleManager>, SubAppResourcesProviderManager> {

    ChatIdentityModuleManager moduleManager;
    ErrorManager errorManager;
    EditText accuracy;
    Spinner frequency;
    Toolbar toolbar;
    long acurracydata;
    GeoFrequency frequencyData;
    ChatIdentity identity;

    private ChatIdentityPreferenceSettings chatIdentitySettings;

    public static GeolocationChatIdentityFragment newInstance() {
        return new GeolocationChatIdentityFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            moduleManager = appSession.getModuleManager();
            errorManager = appSession.getErrorManager();
            chatIdentitySettings = null;
            try {
                chatIdentitySettings = moduleManager.loadAndGetSettings(appSession.getAppPublicKey());
            }catch(Exception e){
                chatIdentitySettings = null;
            }
            if (chatIdentitySettings == null) {
                try {
                    moduleManager.persistSettings(appSession.getAppPublicKey(), chatIdentitySettings);
                } catch (Exception e) {
                    errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                }
            }

            checkIdentity();
            
        } catch (Exception e) {
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
        }
//        toolbar = getToolbar();
//        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.cht_ic_back_buttom));
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //getActivity().onBackPressed();
//            }
//        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootLayout = inflater.inflate(R.layout.fragment_cht_identity_geolocation, container, false);
        initViews(rootLayout);
        return rootLayout;
    }

    private void checkIdentity(){
        //Check if a default identity is configured
        if(identity==null){
            try{
                identity = moduleManager.getIdentityChatUser();
            }catch (Exception e){
                errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
            }
        }
    }

    /**
     * Initializes the views of this Fragment
     *
     * @param layout layout of this Fragment containing the views
     */

    private void initViews(View layout) {
        // Spinner Drop down elements
        List<GeoFrequency> dataSpinner = new ArrayList<GeoFrequency>();
        dataSpinner.addAll(Arrays.asList(GeoFrequency.values()));

        // Spinner element
        accuracy = (EditText) layout.findViewById(R.id.accuracy);
        frequency = (Spinner) layout.findViewById(R.id.spinner_frequency);
        frequency.setBackgroundColor(Color.parseColor("#f9f9f9"));

        try {
            ArrayAdapter<GeoFrequency> dataAdapter = new ArrayAdapter<GeoFrequency>(getActivity(),
                    R.layout.cht_iden_spinner_item, dataSpinner);
            //android.R.layout.simple_spinner_item, dataspinner);
            dataAdapter.setDropDownViewResource(R.layout.cht_iden_spinner_item);
//        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            frequency.setAdapter(dataAdapter);

            setValues(frequency, accuracy, dataAdapter);
            // frequency.setBackgroundColor(new Color.parseColor("#d1d1d1"));
            frequency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    try {
//                        frequencyData = GeoFrequency.getByCode(parent.getItemAtPosition(position).toString());
                        frequencyData = (GeoFrequency) parent.getItemAtPosition(position);
                        frequencyData = GeoFrequency.getByCode(frequencyData.getCode());
                        ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#616161"));
                        (parent.getChildAt(0)).setBackgroundColor(Color.parseColor("#F9f9f9"));
                    } catch (InvalidParameterException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        } catch (CantGetChatIdentityException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onCreateOptionsMenu(final Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    public void saveAndGoBack(){
        try {
            if(ExistIdentity()){
                saveIdentityGeolocation("onBack");
            }
        } catch (CHTException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed(){
        saveAndGoBack();
        //changeActivity(Activities.CHT_CHAT_CREATE_IDENTITY, appSession.getAppPublicKey());
        //super.onBackPressed();
    }

    private void saveIdentityGeolocation(String donde) throws CantGetChatIdentityException {
        GeolocationIdentityExecutor executor = null;
        try {
            if (accuracy.getText().length() == 0) {
                Toast.makeText(getActivity(), "Accuracy is empty, please add a value", Toast.LENGTH_SHORT).show();
            } else {
                acurracydata = Long.parseLong(accuracy.getText().toString());
                executor = new GeolocationIdentityExecutor(appSession, identity.getPublicKey(), identity.getAlias(),
                        identity.getImage(), identity.getConnectionState(),
                        identity.getCountry(), identity.getState(),
                        identity.getCity(), frequencyData, acurracydata);
                int resultKey = executor.execute();
                switch (resultKey) {
                    case SUCCESS:
                        if (donde.equalsIgnoreCase("onClick")) {
                            Toast.makeText(getActivity(), "Chat Identity Geolocation Update.", Toast.LENGTH_LONG).show();
                            //getActivity().onBackPressed();
                        } else if (donde.equalsIgnoreCase("onBack")) {
                            Toast.makeText(getActivity(), "Chat Identity Geolocation Update.", Toast.LENGTH_LONG).show();
                        }
                        break;
                }
            }
        }catch(Exception e){
            if(errorManager != null)
                errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
        }
    }

    public boolean ExistIdentity() throws CHTException {
        try {
            if (!identity.getAlias().isEmpty()) {
                Log.i("CHT EXIST IDENTITY", "TRUE");
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        Log.i("CHT EXIST IDENTITY", "FALSE");
        return false;
    }

    public void setValues(Spinner frequency, EditText accuracy, ArrayAdapter<GeoFrequency> dataAdapter) throws CantGetChatIdentityException {
        checkIdentity();
        if(identity!=null){
            accuracy.setText(""+identity.getAccuracy());
            if (!identity.getFrecuency().equals(null)) {
                int spinnerPosition = dataAdapter.getPosition(identity.getFrecuency());
                frequency.setSelection(spinnerPosition);
            }
        }
    }
}