package com.bitdubai.reference_wallet.fan_wallet.common.header;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bitdubai.fermat_android_api.engine.HeaderViewPainter;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_tky_api.layer.wallet_module.interfaces.FanWalletModule;
import com.bitdubai.reference_wallet.fan_wallet.R;
import com.bitdubai.reference_wallet.fan_wallet.session.FanWalletSession;

import java.lang.ref.WeakReference;


/**
 * Paint the header of fan wallet
 *
 * @author Miguel Payarez
 * @version 1.0
 * @since 11/05/2016
 */
public class FanWalletHeaderPainter implements HeaderViewPainter {
    private final String TAG = "FanrWalletHeader";

    private final FanWalletSession session;
    private final WeakReference<Context> activity;
    private ErrorManager errorManager;
    private FanWalletModule moduleManager;


    public FanWalletHeaderPainter(Context activity, FanWalletSession fullyLoadedSession) {
        this.activity = new WeakReference<>(activity);
        session = fullyLoadedSession;

        try {
            moduleManager = session.getModuleManager();
            errorManager = session.getErrorManager();
        } catch (Exception e) {
            if (errorManager != null)
                errorManager.reportUnexpectedWalletException(Wallets.TKY_FAN_WALLET,
                        UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
            else
                Log.e(TAG, e.getMessage(), e);
        }
    }

    @Override
    public void addExpandableHeader(ViewGroup viewGroup) {
       ((LayoutInflater) activity.get()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.tky_fan_wallet_header, viewGroup, true);


    }



}
