package com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;


import android.widget.TextView;
import com.bitdubai.android_fermat_dmp_wallet_bitcoin.R;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.exceptions.CantGetAllWalletContactsException;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.exceptions.CantGetBalanceException;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.exceptions.CantGetCryptoWalletException;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.interfaces.CryptoWallet;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.interfaces.CryptoWalletManager;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.Platform;

import java.util.UUID;

/**
 * Created by Natalia on 02/06/2015.
 */
public class BalanceFragment extends  Fragment {
    View rootView;

    String balance;

    private static final String ARG_POSITION = "position";
    private int position;

    UUID wallet_id = UUID.fromString("25428311-deb3-4064-93b2-69093e859871");

    /**
     * DealsWithNicheWalletTypeCryptoWallet Interface member variables.
     */
    private static CryptoWalletManager cryptoWalletManager;
    private static Platform platform = new Platform();
    CryptoWallet cryptoWallet;

    public static BalanceFragment newInstance(int position) {
        BalanceFragment f = new BalanceFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }


    @Override
    public void onPrepareOptionsMenu(Menu menu) {

        /*for(int i=0;i<menu.size();i++){
            menu.getItem(i).setVisible(false);
        }
        menu.clear();
*/
        super.onPrepareOptionsMenu(menu);
        for(int i=0;i<menu.size();i++){
            menu.getItem(i).setVisible(false);
        }
        //MenuItem item3  = menu.findItem(R.id.action);
        //item3.setVisible(false);
    }



    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try{

         balance = "0 bits";
        cryptoWalletManager = platform.getCryptoWalletManager();

        try {
            cryptoWallet = cryptoWalletManager.getCryptoWallet();
        }
        catch (CantGetCryptoWalletException e) {
            showMessage("CantGetCryptoWalletException- " + e.getMessage());
           e.printStackTrace();
        }

        try {
            long lngBalance = cryptoWallet.getBalance(wallet_id);

            balance = (int) (lngBalance / 100) + " bits";

            //String.valueOf
        } catch (CantGetBalanceException e) {
            showMessage("CantGetBalanceException- " + e.getMessage());
            e.printStackTrace();

        }
    }
    catch(Exception ex) {
        showMessage("Unexpected error getting the balance - " + ex.getMessage());
        ex.printStackTrace();
    }



      //  MyApplication.changeColor(Color.parseColor("#F0E173"), super.getActivity().getResources());

    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
        //MenuItem searchItem = menu.findItem(R.id.action_search);
        //mSearchView = (SearchView) MenuItemCompat.getActionView(searchItem);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        //if(id == R.id.action_search){
        //    Toast.makeText(getActivity(), "holaa", Toast.LENGTH_LONG);
        //}

        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.wallets_bitcoin_fragment_balance, container, false);
        TextView tv = ((TextView)rootView.findViewById(R.id.balance));
        tv.setText(balance);


        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);




    }
    private void showMessage(String text){
        AlertDialog alertDialog = new AlertDialog.Builder(this.getActivity()).create();
        alertDialog.setTitle("Warning");
        alertDialog.setMessage(text);
        alertDialog.setButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // aquí puedes añadir funciones
            }
        });
       // alertDialog.setIcon(R.drawable.icon);
        alertDialog.show();
    }






}

