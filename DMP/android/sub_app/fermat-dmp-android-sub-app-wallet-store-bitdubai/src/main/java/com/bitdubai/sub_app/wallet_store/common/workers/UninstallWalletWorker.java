package com.bitdubai.sub_app.wallet_store.common.workers;

import android.app.Activity;

import com.bitdubai.fermat_android_api.ui.interfaces.FermatWorkerCallBack;
import com.bitdubai.fermat_android_api.ui.util.FermatWorker;
import com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_store.interfaces.WalletStoreModuleManager;

import java.util.UUID;

/**
 * Created by nelson on 31/08/15.
 */
public class UninstallWalletWorker extends FermatWorker {
    private final UUID catalogueId;
    private final WalletStoreModuleManager moduleManager;

    public UninstallWalletWorker(Activity context, FermatWorkerCallBack callBack, WalletStoreModuleManager moduleManager, UUID catalogueId) {
        super(context, callBack);
        this.moduleManager = moduleManager;
        this.catalogueId = catalogueId;
    }

    @Override
    protected Object doInBackground() throws Exception {
        moduleManager.uninstallWallet(catalogueId);
        return true;
    }
}
