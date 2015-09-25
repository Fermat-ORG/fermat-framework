package com.bitdubai.sub_app.wallet_store.common.workers;

import android.app.Activity;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.SubAppsSession;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatWorkerCallBack;
import com.bitdubai.fermat_android_api.ui.util.FermatWorker;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletCategory;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.ccp_module.wallet_store.interfaces.WalletStoreModuleManager;
import com.bitdubai.sub_app.wallet_store.common.models.WalletStoreListItem;

import java.util.UUID;

import static com.bitdubai.sub_app.wallet_store.session.WalletStoreSubAppSession.BASIC_DATA;
import static com.bitdubai.sub_app.wallet_store.session.WalletStoreSubAppSession.LANGUAGE_ID;
import static com.bitdubai.sub_app.wallet_store.session.WalletStoreSubAppSession.SKIN_ID;
import static com.bitdubai.sub_app.wallet_store.session.WalletStoreSubAppSession.WALLET_VERSION;

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
