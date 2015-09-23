package com.bitdubai.sub_app.wallet_factory.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatFragment;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.interfaces.InstalledWallet;
import com.bitdubai.sub_app.wallet_factory.R;
import com.bitdubai.sub_app.wallet_factory.models.Wallet;

import java.util.ArrayList;

/**
 * Available projects fragment to clone
 *
 * @author Francisco Vasquez
 * @author Matias Furszy
 * @version 1.0
 */
public class AvailableProjectsFragment extends FermatFragment {

    /**
     * Views
     */
    private View rootView;
    private ListView listView;

    private ArrayList<InstalledWallet> wallets;

    public static FermatFragment newInstance() {
        return new AvailableProjectsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        wallets = new ArrayList<>();
        Wallet wallet = new Wallet();
        wallets.add(wallet);
        wallets.add(wallet);
        wallets.add(wallet);
        wallets.add(wallet);
        wallets.add(wallet);
        wallets.add(wallet);
        wallets.add(wallet);
        wallets.add(wallet);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.factory_available_projects_fragment, container, false);

        listView = (ListView) rootView.findViewById(R.id.projects);
        ArrayAdapter<InstalledWallet> adapter = new ArrayAdapter<InstalledWallet>(getActivity(), android.R.layout.simple_list_item_1, wallets);
        listView.setAdapter(adapter);

        return rootView;
    }
}
