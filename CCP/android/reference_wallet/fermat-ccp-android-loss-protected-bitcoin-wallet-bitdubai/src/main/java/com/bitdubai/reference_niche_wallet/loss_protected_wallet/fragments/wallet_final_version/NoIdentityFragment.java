package com.bitdubai.reference_niche_wallet.loss_protected_wallet.fragments.wallet_final_version;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bitdubai.android_fermat_ccp_loss_protected_wallet_bitcoin.R;
import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserModuleManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.session.LossProtectedWalletSession;

/**
 * Created by mati on 2015.11.25..
 */
public class NoIdentityFragment extends AbstractFermatFragment {


    /**
     * Plaform reference
     */
    private LossProtectedWalletSession referenceWalletSession;
    private IntraUserModuleManager intraUserModuleManager;

    /**
     * UI
     */
    private View rootView;


    public static NoIdentityFragment newInstance() {
        return new NoIdentityFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            referenceWalletSession = (LossProtectedWalletSession) appSession;

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        try {
            rootView = inflater.inflate(R.layout.no_odentity_main, container, false);
            setUpUI();
            return rootView;
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();
            referenceWalletSession.getErrorManager().reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.CRASH, e);
        }

        return null;
    }


    private void setUpUI() {
        rootView.findViewById(R.id.btn_create).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Object[] object = new Object[2];
                    //changeApp(Engine.BITCOIN_WALLET_CALL_INTRA_USER_IDENTITY, ((ReferenceWalletSession)appSession).getIdentityConnection(), object);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


}

