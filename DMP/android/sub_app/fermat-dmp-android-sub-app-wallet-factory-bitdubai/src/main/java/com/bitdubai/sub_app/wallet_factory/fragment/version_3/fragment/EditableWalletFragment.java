package com.bitdubai.sub_app.wallet_factory.fragment.version_3.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.SubAppsSession;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Fragment;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatScreenSwapper;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.interfaces.InstalledWallet;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_factory.interfaces.WalletFactoryManager;
import com.bitdubai.sub_app.wallet_factory.R;
import com.bitdubai.sub_app.wallet_factory.session.WalletFactorySubAppSession;

/**
 * Created by Matias Furszyfer on 2015.08.15..
 */

//TODO: Fransisco, no se como pensas abrir la wallet. esto es solo un testeo, hay que hablar de q es lo que queda

public class EditableWalletFragment extends FermatFragment {

    /**
     * Flag used if the user create a new navigation structure
     */
    private boolean withNewNavigationStructure;
    /**
     * Manager
     */
    private WalletFactoryManager manager;

    public static EditableWalletFragment newInstance(boolean withNewNavigationStructure) {
        EditableWalletFragment f = new EditableWalletFragment();
        f.setWithNewNavigationStructure(withNewNavigationStructure);
        Bundle b = new Bundle();
        f.setArguments(b);
        return f;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            /*setting up manager*/
            manager = ((WalletFactorySubAppSession) subAppsSession).getWalletFactoryManager();
        } catch (Exception ex) {
            Log.getStackTraceString(ex);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.edit_test, container, false);

        Button btnEditWallet = (Button) rootView.findViewById(R.id.btnEditWallet);

        btnEditWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((FermatScreenSwapper) getActivity()).changeActivity(Activities.CWP_WALLET_FACTORY_EDIT_WALLET.getCode());
            }
        });

        return rootView;
    }


    public void setWithNewNavigationStructure(boolean withNewNavigationStructure) {
        this.withNewNavigationStructure = withNewNavigationStructure;
    }
}
