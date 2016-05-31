package com.bitdubai.sub_app.wallet_factory.workers;

import android.app.Activity;

import com.bitdubai.fermat_android_api.ui.interfaces.FermatWorkerCallBack;
import com.bitdubai.fermat_android_api.ui.util.FermatWorker;
import com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_factory.interfaces.WalletFactoryManager;

/**
 * Get Available Projects
 *
 * @author Francisco Vasquez
 * @version 1.0
 */
public class GetAvailableProjectsAsync extends FermatWorker {

    private WalletFactoryManager manager;

    public GetAvailableProjectsAsync(Activity context, FermatWorkerCallBack callBack, WalletFactoryManager manager) {
        super(context, callBack);
        this.manager = manager;
    }

    @Override
    protected Object doInBackground() throws Exception {
        if (manager == null)
            throw new NullPointerException("Wallet Factory Module Manager is null");
        return manager.getAvailableProjects();
    }
}
