package com.bitdubai.desktop.wallet_manager.fragments;


import android.app.Fragment;
import android.app.Service;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bitdubai.desktop.wallet_manager.R;
import com.bitdubai.desktop.wallet_manager.fragments.provisory_classes.InstalledWallet;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletCategory;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletType;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatScreenSwapper;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.InstalledLanguage;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.InstalledSkin;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.WalletManager;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matias Furszyfer
 */


public class WalletDesktopFragment extends Fragment {

    private static final String ARG_POSITION = "position";
    private static final String CWP_WALLET_BASIC_ALL_MAIN = Activities.CWP_WALLET_BASIC_ALL_MAIN.getCode();

    Typeface tf;

    private WalletManager walletManager;

    private List<InstalledWallet> lstInstalledWallet;

    public static WalletDesktopFragment newInstance(int position) {
        WalletDesktopFragment f = new WalletDesktopFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        tf = Typeface.createFromAsset(getActivity().getAssets(), "fonts/CaviarDreams.ttf");

        lstInstalledWallet = new ArrayList<InstalledWallet>();

        // Harcoded para testear el circuito más arriba
        InstalledWallet installedWallet= new InstalledWallet(WalletCategory.REFERENCE_WALLET,
                WalletType.REFERENCE,
                new ArrayList<InstalledSkin>(),
                new ArrayList<InstalledLanguage>(),
                "crypto_broker",
                "crypto broker",
                "crypto_broker_wallet",
                "wallet_crypto_broker_platform_identifier",
                new Version(1,0,0));
        lstInstalledWallet.add(installedWallet);

        installedWallet= new InstalledWallet(WalletCategory.REFERENCE_WALLET,
                WalletType.REFERENCE,
                new ArrayList<InstalledSkin>(),
                new ArrayList<InstalledLanguage>(),
                "crypto_customer",
                "crypto customer",
                "crypto_customer_wallet",
                "wallet_crypto_customer_platform_identifier",
                new Version(1,0,0));
        lstInstalledWallet.add(installedWallet);

        GridView gridView = new GridView(getActivity());

        Configuration config = getResources().getConfiguration();
        if (config.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            gridView.setNumColumns(6);
        } else {
            gridView.setNumColumns(4);
        }

        AppListAdapter adapter = new AppListAdapter(getActivity(), R.layout.shell_wallet_desktop_front_grid_item, lstInstalledWallet);
        adapter.notifyDataSetChanged();
        gridView.setAdapter(adapter);


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(getActivity(),"mati",Toast.LENGTH_SHORT).show();
                return ;
            }
        });



        return gridView;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        //MenuItem searchItem = menu.findItem(R.id.action_search);
        //mSearchView = (SearchView) MenuItemCompat.getActionView(searchItem);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //if(id == R.id.action_search){
        //    Toast.makeText(getActivity(), "holaa", Toast.LENGTH_LONG);
        //}

        return super.onOptionsItemSelected(item);
    }

    /**
     * Set Wallet manager plugin
     *
     * @param walletManager
     */
    public void setWalletManager(WalletManager walletManager) {
        this.walletManager = walletManager;
    }


    public class AppListAdapter extends ArrayAdapter<InstalledWallet> {


        public AppListAdapter(Context context, int textViewResourceId, List<InstalledWallet> objects) {
            super(context, textViewResourceId, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            final InstalledWallet installedWallet = getItem(position);

            ViewHolder holder;
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Service.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.shell_wallet_desktop_front_grid_item, parent, false);
                holder = new ViewHolder();


                holder.imageView = (ImageView) convertView.findViewById(R.id.image_view);
                holder.companyTextView = (TextView) convertView.findViewById(R.id.company_text_view);


                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.companyTextView.setText(installedWallet.getWalletName());
            holder.companyTextView.setTypeface(tf, Typeface.BOLD);


            LinearLayout linearLayout = (LinearLayout) convertView.findViewById(R.id.wallet_3);

            //Hardcodeado hasta que esté el wallet resources
            switch (installedWallet.getWalletIcon()) {

                case "crypto_broker":
                    holder.imageView.setImageResource(R.drawable.wallet_1);
                    holder.imageView.setTag("WalletBitcoinActivity|4");
                    linearLayout.setTag("WalletBitcoinActivity|4");

                    linearLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            //set the next fragment and params
                            ((FermatScreenSwapper) getActivity()).selectWallet(installedWallet);

                        }
                    });

                    holder.imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            //set the next fragment and params
                            ((FermatScreenSwapper) getActivity()).selectWallet( installedWallet);

                        }
                    });

                    break;
                case "crypto_customer":
                    holder.imageView.setImageResource(R.drawable.wallet_2);
                    holder.imageView.setTag("WalletBitcoinActivity|4");
                    linearLayout.setTag("WalletBitcoinActivity|4");

                    linearLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            //set the next fragment and params
                            ((FermatScreenSwapper) getActivity()).selectWallet(installedWallet);

                        }
                    });

                    holder.imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            //set the next fragment and params
                            ((FermatScreenSwapper) getActivity()).selectWallet( installedWallet);

                        }
                    });

                    break;



            }


            return convertView;
        }

        /**
         * ViewHolder.
         */
        private class ViewHolder {


            public ImageView imageView;
            public TextView companyTextView;


        }

    }


}