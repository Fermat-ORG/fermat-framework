package com.bitdubai.sub_app.wallet_store.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.SubAppsSession;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_store.interfaces.WalletStoreModuleManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.sub_app.wallet_store.session.WalletStoreSubAppSession;
import com.wallet_store.bitdubai.R;

/**
 * Fragment que luce como un Activity donde se muestra parte de los detalles de una Wallet seleccionada en el catalogo de MainActivityFragment
 *
 * @author Nelson Ramirez
 * @version 1.0
 */
public class DetailsActivityFragment extends FermatFragment {
    /**
     * STATIC
     */
    private static final String ARG_POSITION = "position";

    /**
     * MODULE
     */
    private WalletStoreModuleManager moduleManager;


    /**
     * Create a new instance of this fragment
     *
     * @param position tab position
     * @param session  WalletStoreSubAppSession instance object. This contains references to WalletStoreModuleManager and ErrorManager
     * @return InstalledFragment instance object
     */
    public static DetailsActivityFragment newInstance(int position) {
        DetailsActivityFragment f = new DetailsActivityFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_POSITION, position);
        f.setArguments(args);
        return f;
    }


    /**
     * Set module moduleManager
     *
     * @param moduleManager WalletStoreModuleManager object
     */
    public void setModuleManager(WalletStoreModuleManager moduleManager) {
        this.moduleManager = moduleManager;
    }

    @Override
    public void setSubAppsSession(SubAppsSession subAppsSession) {
        super.setSubAppsSession(subAppsSession);

        WalletStoreSubAppSession session = (WalletStoreSubAppSession) subAppsSession;
        moduleManager = session.getWalletStoreModuleManager();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.wallet_store_fragment_details_activity, container, false);
    }


}
