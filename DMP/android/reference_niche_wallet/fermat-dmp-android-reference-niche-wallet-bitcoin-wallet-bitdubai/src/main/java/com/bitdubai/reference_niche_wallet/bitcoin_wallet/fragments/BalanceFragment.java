package com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments;


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

    String[] balances;

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


        cryptoWalletManager = platform.getCryptoWalletManager();

        //try{
           // cryptoWallet = cryptoWalletManager.getCryptoWallet();
        //}
        //catch (CantGetCryptoWalletException e) {
           // e.printStackTrace();
        //}

        //TODO falta el BitcoinWalletManager para poder consultar el balancce
     //   try {
           // long balance = cryptoWallet.getBalance(wallet_id);
      //  } catch (CantGetBalanceException e) {
      //  e.printStackTrace();

      //  }

        balances = new String[]{"BTC 0.0049"};

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
        tv.setText(balances[0]);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);




    }



}

