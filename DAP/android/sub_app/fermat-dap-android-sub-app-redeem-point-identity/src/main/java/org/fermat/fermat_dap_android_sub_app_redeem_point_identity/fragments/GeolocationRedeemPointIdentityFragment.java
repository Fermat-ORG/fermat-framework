package org.fermat.fermat_dap_android_sub_app_redeem_point_identity.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_dap_android_sub_app_redeem_point_identity_bitdubai.R;

import org.fermat.fermat_dap_android_sub_app_redeem_point_identity.session.SessionConstants;
import com.bitdubai.fermat_api.layer.all_definition.enums.GeoFrequency;
import org.fermat.fermat_dap_api.layer.dap_actor.redeem_point.exceptions.CantGetAssetRedeemPointActorsException;
import org.fermat.fermat_dap_api.layer.dap_identity.redeem_point.interfaces.RedeemPointIdentity;
import org.fermat.fermat_dap_api.layer.dap_sub_app_module.redeem_point_identity.RedeemPointIdentitySettings;
import org.fermat.fermat_dap_api.layer.dap_sub_app_module.redeem_point_identity.interfaces.RedeemPointIdentityModuleManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Jinmy Bohorquez on 26/06/16.
 */
public class GeolocationRedeemPointIdentityFragment
        extends AbstractFermatFragment<ReferenceAppFermatSession<RedeemPointIdentityModuleManager>, ResourceProviderManager>{
    EditText accuracy;
    Spinner frequency;
    Toolbar toolbar;
    int accuracyData;
    GeoFrequency frequencyData;


    public static GeolocationRedeemPointIdentityFragment newInstance() {
        return new GeolocationRedeemPointIdentityFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

//        toolbar = getToolbar();
//        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.arrow_left));
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getActivity().onBackPressed();
//            }
//        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootLayout = inflater.inflate(R.layout.fragment_dap_identity_redeem_point_geolocation, container, false);
        initViews(rootLayout);
        return rootLayout;
    }

    private void initViews(View layout) {
        // Spinner Drop down elements
        List<GeoFrequency> dataSpinner = new ArrayList<>();
        dataSpinner.addAll(Arrays.asList(GeoFrequency.values()));

        // Spinner element
        accuracy = (EditText) layout.findViewById(R.id.accuracy);
        accuracy.requestFocus();
        frequency = (Spinner) layout.findViewById(R.id.spinner_frequency);
        frequency.setBackgroundColor(Color.parseColor("#f9f9f9"));

        try {
            ArrayAdapter<GeoFrequency> dataAdapter = new ArrayAdapter<>(getActivity(), R.layout.dap_spinner_item, dataSpinner);
            dataAdapter.setDropDownViewResource(R.layout.dap_spinner_item);
            frequency.setAdapter(dataAdapter);

            setValues(frequency, accuracy, dataAdapter);

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
        } catch (CantGetAssetRedeemPointActorsException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onBackPressed() {
        if (accuracy.getText().length() == 0) {
            Toast.makeText(getActivity(), "Accuracy is empty, please add a value", Toast.LENGTH_SHORT).show();
        } else {
            accuracyData = Integer.parseInt(accuracy.getText().toString());

            appSession.setData(SessionConstants.FREQUENCY_DATA, frequencyData);
            appSession.setData(SessionConstants.ACCURACY_DATA, accuracyData);

        }
    }

    private void setValues(Spinner frequency, EditText accuracy, ArrayAdapter<GeoFrequency> dataAdapter) throws CantGetAssetRedeemPointActorsException {
        if(appSession.getData(SessionConstants.ACCURACY_DATA) != null) {
            int accuracyTemp = (int) appSession.getData(SessionConstants.ACCURACY_DATA);
            accuracy.setText(String.format("%s", Integer.toString(accuracyTemp)));
            int spinnerPosition = dataAdapter.getPosition((GeoFrequency) appSession.getData(SessionConstants.FREQUENCY_DATA));
            frequency.setSelection(spinnerPosition);
        } else {
            final RedeemPointIdentity identityInfo = (RedeemPointIdentity) appSession.getData(SessionConstants.IDENTITY_SELECTED);

            if (identityInfo != null) {
                final String accuracyString = Integer.toString(identityInfo.getAccuracy());
                accuracy.setText(accuracyString);

                if (!(identityInfo.getFrequency() == null)) {
                    int spinnerPosition = dataAdapter.getPosition(identityInfo.getFrequency());
                    frequency.setSelection(spinnerPosition);
                }
            } else {
                int accuracyTemp = appSession.getModuleManager().getAccuracyDataDefault();
                accuracy.setText(String.format("%s", Integer.toString(accuracyTemp)));

                int spinnerPosition = dataAdapter.getPosition(appSession.getModuleManager().getFrequencyDataDefault());
                frequency.setSelection(spinnerPosition);

            }
        }
    }
}
