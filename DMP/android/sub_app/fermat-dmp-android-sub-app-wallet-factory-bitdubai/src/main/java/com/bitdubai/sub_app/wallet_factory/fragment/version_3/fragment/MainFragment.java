package com.bitdubai.sub_app.wallet_factory.fragment.version_3.fragment;

import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.SubAppsSession;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatScreenSwapper;
import com.bitdubai.sub_app.wallet_factory.R;
import com.bitdubai.sub_app.wallet_factory.session.WalletFactorySubAppSession;
/**
 * Created by natalia on 09/07/15.
 */
public class MainFragment extends FermatFragment {

    private static final String ARG_POSITION = "position";

    private WalletFactorySubAppSession subAppSession;

    Typeface tf;
    public static MainFragment newInstance(int position,SubAppsSession subAppSession) {
        MainFragment f = new MainFragment();
        f.setSubAppSession((WalletFactorySubAppSession)subAppSession);
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //subAppSession.getWalletFactoryProjectManager().
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);



        return inflater.inflate(R.layout.wallet_factory_main_fragment, container, false);



    }


    public void setSubAppSession(WalletFactorySubAppSession subAppSession) {
        this.subAppSession = subAppSession;
    }
}
