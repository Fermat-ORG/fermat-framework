package com.bitdubai.sub_app.wallet_store.common.workers;

import android.app.Activity;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.FermatSession;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatWorkerCallBack;
import com.bitdubai.fermat_android_api.ui.util.FermatWorker;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletCategory;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_store.interfaces.WalletStoreModuleManager;
import com.bitdubai.sub_app.wallet_store.common.models.WalletStoreListItem;

import java.util.UUID;

import static com.bitdubai.sub_app.wallet_store.session.WalletStoreSubAppSessionReferenceApp.BASIC_DATA;
import static com.bitdubai.sub_app.wallet_store.session.WalletStoreSubAppSessionReferenceApp.LANGUAGE_ID;
import static com.bitdubai.sub_app.wallet_store.session.WalletStoreSubAppSessionReferenceApp.SKIN_ID;
import static com.bitdubai.sub_app.wallet_store.session.WalletStoreSubAppSessionReferenceApp.WALLET_VERSION;

/**
 * Created by nelson on 31/08/15.
 */
public class InstallWalletWorker extends FermatWorker {
    private final FermatSession session;
    private final WalletStoreModuleManager moduleManager;

    public InstallWalletWorker(Activity context, FermatWorkerCallBack callBack, WalletStoreModuleManager moduleManager, FermatSession session) {
        super(context, callBack);
        this.moduleManager = moduleManager;
        this.session = session;
    }

    @Override
    protected Object doInBackground() throws Exception {
        UUID languageId = (UUID) session.getData(LANGUAGE_ID);
        UUID skinId = (UUID) session.getData(SKIN_ID);
        Version version = (Version) session.getData(WALLET_VERSION);
        WalletStoreListItem catalogItem = (WalletStoreListItem) session.getData(BASIC_DATA);
        WalletCategory category = catalogItem.getCategory();
        UUID catalogueId = catalogItem.getId();

        moduleManager.installWallet(category, skinId, languageId, catalogueId, version);
        return true;
    }
}
