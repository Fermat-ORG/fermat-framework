package com.bitdubai.fermat_android_api.ui.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.FermatSession;
import com.bitdubai.fermat_android_api.ui.inflater.ViewInflater;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatScreenSwapper;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;

/**
 * Created by Matias Furszyfer on 2015.10.18..
 */
public abstract class FermatDialog<S extends FermatSession, R extends ResourceProviderManager> extends Dialog {


    private final Context activity;
    private S fermatSession;

    private R resources;


    private ViewInflater viewInflater;


    /**
     * Constructor using Session and Resources
     *
     * @param activity
     * @param fermatSession parent class of walletSession and SubAppSession
     * @param resources     parent class of WalletResources and SubAppResources
     */
    public FermatDialog(Context activity, S fermatSession, R resources) {
        super(activity);
        this.activity = activity;
        this.fermatSession = fermatSession;
        this.resources = resources;
    }

    public FermatDialog(Context activity, int themeResId, S fermatSession, R resources) {
        super(activity, themeResId);
        this.activity = activity;
        this.fermatSession = fermatSession;
        this.resources = resources;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            requestWindowFeature(setWindowFeature());
            setContentView(setLayoutId());
        } catch (Exception e) {
            getErrorManager().reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.CRASH, e);
            Toast.makeText(getOwnerActivity(), new StringBuilder().append("Oooops! recovering from system error - ").append(e.getMessage()).toString(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Send local broadcast
     *
     * @param broadcast Intent broadcast with channel and extras
     */
    public void sendLocalBroadcast(Intent broadcast) {
        LocalBroadcastManager.getInstance(getContext())
                .sendBroadcast(broadcast);
    }

    /**
     * Set the layout content view
     *
     * @return
     */
    protected abstract int setLayoutId();

    /**
     * Window feacture
     *
     * @return
     */
    protected abstract int setWindowFeature();

    /**
     * @return Session
     */
    public S getSession() {
        return fermatSession;
    }

    /**
     * @return resoruces instance
     */

    public R getResources() {
        return resources;
    }

    /**
     * @return error manager from session
     */
    public ErrorManager getErrorManager() {
        return fermatSession != null ? fermatSession.getErrorManager() : null;
    }

    protected void changeApp(String fermatAppToConnectPublicKey, Object[] objects) {
        getFermatScreenSwapper().connectWithOtherApp(fermatAppToConnectPublicKey, objects);
    }

    protected FermatScreenSwapper getFermatScreenSwapper() {
        return (FermatScreenSwapper) activity;
    }

    protected Context getActivity() {
        return activity;
    }

    protected void toastDefaultError() {
        Toast.makeText(getContext(), "Oooops! recovering from system error - ", Toast.LENGTH_SHORT).show();
    }


}
