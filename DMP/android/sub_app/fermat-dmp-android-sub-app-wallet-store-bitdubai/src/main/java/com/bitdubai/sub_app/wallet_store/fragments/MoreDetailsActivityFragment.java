package com.bitdubai.sub_app.wallet_store.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bitdubai.fermat_api.layer.dmp_module.wallet_store.interfaces.WalletStoreModuleManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.sub_app.wallet_store.session.WalletStoreSubAppSession;
import com.wallet_store.bitdubai.R;

/**
 * Fragment que luce como un Activity donde se muestra mas informacion sobre la Wallet mostrada en DetailsActivityFragment
 *
 * @author Nelson Ramirez
 * @version 1.0
 */
public class MoreDetailsActivityFragment extends Fragment {
    // STATIC
    private static final String ARG_POSITION = "position";

    // MANAGERS
    private WalletStoreModuleManager moduleManager;
    private ErrorManager errorManager;

     // SESSION
    private WalletStoreSubAppSession session;


    /**
     * Create a new instance of this fragment
     *
     * @param position tab position
     * @param session  WalletStoreSubAppSession instance object. This contains references to WalletStoreModuleManager and ErrorManager
     * @return InstalledFragment instance object
     */
    public static MoreDetailsActivityFragment newInstance(int position, WalletStoreSubAppSession session) {
        MoreDetailsActivityFragment f = new MoreDetailsActivityFragment();

        f.setSession(session);

        WalletStoreModuleManager moduleManager = session.getWalletStoreModuleManager();
        f.setModuleManager(moduleManager);

        ErrorManager errorManager = session.getErrorManager();
        f.setErrorManager(errorManager);

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

    /**
     * Set session of this subapp
     *
     * @param session WalletStoreSubAppSession object
     */
    public void setSession(WalletStoreSubAppSession session) {
        this.session = session;
    }

    /**
     * Set the error manager to handle the errors of this subapp
     *
     * @param errorManager ErrorManager object
     */
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.wallet_store_fragment_more_details_activity, container, false);
    }
}
