package com.bitdubai.sub_app.intra_user_identity.fragments;

/**
 * Created by Andres Abreu aabreu1 on 21/06/16.
 */

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_ccp_api.all_definition.enums.Frequency;
import com.bitdubai.sub_app.intra_user_identity.R;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user_identity.exceptions.CantGetIntraUserIdentityException;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user_identity.interfaces.IntraUserIdentityModuleManager;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_user.interfaces.IntraWalletUserIdentity;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.sub_app.intra_user_identity.common.popup.PresentationGeolocationIntraUserIdentityDialog;

import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.makeText;


public class GeolocationIntraUserIdentityFragment extends AbstractFermatFragment<ReferenceAppFermatSession<IntraUserIdentityModuleManager>, SubAppResourcesProviderManager>{

    IntraUserIdentityModuleManager moduleManager;
    ErrorManager errorManager;
    EditText accuracy;
    Spinner frequency;
    Toolbar toolbar;
    long acurracydata;
    Frequency frecuencydata;
    IntraWalletUserIdentity identity;




    public static GeolocationIntraUserIdentityFragment newInstance() {
        return new GeolocationIntraUserIdentityFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            moduleManager = appSession.getModuleManager();
            errorManager = appSession.getErrorManager();


            checkIdentity();

        } catch (Exception e) {
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
        }
        toolbar = getToolbar();

        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ccp_ic_back_buttom));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // getActivity().onBackPressed();
                saveAndGoBack();
                changeActivity(Activities.CCP_SUB_APP_INTRA_IDENTITY_CREATE_IDENTITY, appSession.getAppPublicKey());
            }
        });
        toolbar.setBackgroundColor(Color.parseColor("#21386D"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootLayout = inflater.inflate(R.layout.fragment_intra_user_identity_geolocation, container, false);
        initViews(rootLayout);
        return rootLayout;
    }

    private void checkIdentity(){
        //Check if a default identity is configured
        if(identity==null){
            try{
                identity = moduleManager.getIntraWalletUsers();
            }catch (Exception e){
                errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
            }
        }
    }

    /**
     * Initializes the views of this Fragment
     *
     * @param layout layout of this Fragment contta aining the views
     */

    private void initViews(View layout) {
        // Spinner Drop down elements
        List<Frequency> dataspinner = new ArrayList<Frequency>();
        dataspinner.add(Frequency.NONE);
        dataspinner.add(Frequency.LOW);
        dataspinner.add(Frequency.NORMAL);
        dataspinner.add(Frequency.HIGH);

        // Spinner element
        accuracy = (EditText) layout.findViewById(R.id.accuracy);
        frequency = (Spinner) layout.findViewById(R.id.spinner_frequency);
        frequency.setBackgroundColor(Color.parseColor("#00000000"));

        try {
            ArrayAdapter<Frequency> dataAdapter = new ArrayAdapter<Frequency>(getActivity(),
                    R.layout.frecuency_iden_spinner_item, dataspinner);
            //android.R.layout.simple_spinner_item, dataspinner);
            dataAdapter.setDropDownViewResource(R.layout.frecuency_iden_spinner_item);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            frequency.setAdapter(dataAdapter);

            setValues(frequency, accuracy, dataAdapter);
            // frequency.setBackgroundColor(new Color.parseColor("#d1d1d1"));
            frequency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    try {
                        frecuencydata = Frequency.getByCode(parent.getItemAtPosition(position).toString().toLowerCase());
             //           ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#616161"));
               //         (parent.getChildAt(0)).setBackgroundColor(Color.parseColor("#F9f9f9"));
                    } catch (InvalidParameterException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        } catch (CantGetIntraUserIdentityException e) {
            e.printStackTrace();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onCreateOptionsMenu(final Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {
            int id = item.getItemId();
            if (id == 1)
                showDialog();

        } catch (Exception e) {
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
            makeText(getActivity(), "Oooops! recovering from system error",
                    LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }

    public void saveAndGoBack(){
        try {
            if(ExistIdentity()){
                saveIdentityGeolocation("onBack");
            }
        } catch (FermatException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed(){
        saveAndGoBack();
        changeActivity(Activities.CCP_SUB_APP_INTRA_IDENTITY_CREATE_IDENTITY, appSession.getAppPublicKey());
      // super.onBackPressed();
    }

    private void saveIdentityGeolocation(String donde) throws CantGetIntraUserIdentityException {

        try {
            if (accuracy.getText().length() == 0) {
                Toast.makeText(getActivity(), "Accuracy is empty, please add a value", Toast.LENGTH_SHORT).show();
            } else {
                acurracydata = Long.parseLong(accuracy.getText().toString());
                moduleManager.updateIntraUserIdentity(identity.getPublicKey(), identity.getAlias(),identity.getPhrase(),
                        identity.getImage(),acurracydata,frecuencydata , moduleManager.getLocationManager());

                Toast.makeText(getActivity(), "Wallet User Identity Geolocation Updated OK.", Toast.LENGTH_LONG).show();

            }
        }catch(Exception e){
            Toast.makeText(getActivity(), "Wallet User Identity Geolocation Updated Error.", Toast.LENGTH_LONG).show();

            if(errorManager != null)
                errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
        }
    }

    public boolean ExistIdentity() throws FermatException {
        try {
            if (!identity.getAlias().isEmpty()) {
                Log.i("CCP EXIST IDENTITY", "TRUE");
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        Log.i("CPP EXIST IDENTITY", "FALSE");
        return false;
    }

    public void setValues(Spinner frequency, EditText accuracy, ArrayAdapter<Frequency> dataAdapter) throws CantGetIntraUserIdentityException {
        checkIdentity();
        if(identity!=null){
            accuracy.setText(""+identity.getAccuracy());
            if (!identity.getFrequency().equals(null)) {
                int spinnerPosition = dataAdapter.getPosition(identity.getFrequency());
                frequency.setSelection(spinnerPosition);
            }
        }
    }

    public void showDialog(){
        if(getActivity()!=null) {
            PresentationGeolocationIntraUserIdentityDialog presentation = new PresentationGeolocationIntraUserIdentityDialog(getActivity(), appSession, null, appSession.getModuleManager());
            presentation.show();
        }
    }


}
