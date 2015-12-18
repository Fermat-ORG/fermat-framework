package com.bitdubai.reference_wallet.cash_money_wallet.fragments.setup;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatWalletFragment;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.reference_wallet.cash_money_wallet.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alejandro Bicelis on 12/18/2015.
 */
public class SetupFragment extends FermatWalletFragment implements View.OnClickListener, Spinner.OnItemSelectedListener {


    //DATA
    List<String> fiatCurrencies =  new ArrayList<>();


    //UI
    Spinner currencySpinner;
    ArrayAdapter<String> currencySpinnerAdapter;
    Button applyBtn;
    Button cancelBtn;


    public SetupFragment() {}

    public static SetupFragment newInstance() {return new SetupFragment();}


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.setup_page, container, false);

        applyBtn = (Button) layout.findViewById(R.id.csh_ctd_apply_transaction_btn);
        cancelBtn = (Button) layout.findViewById(R.id.csh_ctd_cancel_transaction_btn);
        applyBtn.setOnClickListener(this);



        fiatCurrencies.add("USD");
        fiatCurrencies.add("VEF");
        fiatCurrencies.add("ARS");

        currencySpinnerAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, fiatCurrencies);
        currencySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //currencySpinnerAdapter.notifyDataSetChanged();

        currencySpinner = (Spinner) layout.findViewById(R.id.csh_setup_currency_spinner);
        currencySpinner.setAdapter(currencySpinnerAdapter);
        currencySpinner.setOnItemSelectedListener(this);

        return layout;
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.csh_setup_ok_btn) {
            //TODO: Go back!
            changeActivity(Activities.WPD_DESKTOP);

        }else if( i == R.id.csh_setup_back_btn){
            changeActivity(Activities.CSH_CASH_MONEY_WALLET_HOME, appSession.getAppPublicKey());
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

        Toast.makeText(getActivity(), "Item #" + pos + " selected (" + fiatCurrencies.get(pos) + ")", Toast.LENGTH_SHORT).show();

        /*Addproject.this.mPos = pos;
        Addproject.this.mSelection = parent.getItemAtPosition(pos).toString();

        TextView spinnerresult = (TextView)findViewById(R.id.spinnertxt);
        spinnerresult.setText(Addproject.this.mSelection);

        Spinner spinner = (Spinner)findViewById(R.id.spinnertxt);
        spinner.setOnItemSelectedListener(this);*/

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        Toast.makeText(getActivity(), "Nothing selected", Toast.LENGTH_SHORT).show();

    }
}
