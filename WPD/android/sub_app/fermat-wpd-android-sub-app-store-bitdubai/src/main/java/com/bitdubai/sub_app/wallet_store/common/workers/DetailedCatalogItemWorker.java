package com.bitdubai.sub_app.wallet_store.common.workers;

import android.app.Activity;
import android.graphics.Bitmap;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.FermatSession;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatWorkerCallBack;
import com.bitdubai.fermat_android_api.ui.util.FermatWorker;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_store.interfaces.WalletStoreModuleManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.interfaces.DetailedCatalogItem;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.interfaces.Language;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.interfaces.Skin;
import com.bitdubai.fermat_wpd_api.layer.wpd_identity.developer.interfaces.DeveloperIdentity;
import com.bitdubai.sub_app.wallet_store.util.UtilsFuncs;
import com.bitdubai.sub_app.wallet_store.common.models.WalletStoreListItem;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.bitdubai.sub_app.wallet_store.session.WalletStoreSubAppSessionReferenceApp.BASIC_DATA;
import static com.bitdubai.sub_app.wallet_store.session.WalletStoreSubAppSessionReferenceApp.DEVELOPER_NAME;
import static com.bitdubai.sub_app.wallet_store.session.WalletStoreSubAppSessionReferenceApp.LANGUAGE_ID;
import static com.bitdubai.sub_app.wallet_store.session.WalletStoreSubAppSessionReferenceApp.PREVIEW_IMGS;
import static com.bitdubai.sub_app.wallet_store.session.WalletStoreSubAppSessionReferenceApp.SKIN_ID;
import static com.bitdubai.sub_app.wallet_store.session.WalletStoreSubAppSessionReferenceApp.WALLET_VERSION;

/**
 * Created by nelson on 29/08/15.
 * Worker que se encarga de buscar el DetailCatalogItem de un CatalogItem y extraer la data contenida en ella
 */
public class DetailedCatalogItemWorker extends FermatWorker {

    private WalletStoreModuleManager moduleManager;
    private UUID catalogItemId;
    private FermatSession session;


    public DetailedCatalogItemWorker(WalletStoreModuleManager moduleManager, FermatSession session, WalletStoreListItem item, Activity context, FermatWorkerCallBack callBack) {
        super(context, callBack);
        this.moduleManager = moduleManager;
        this.catalogItemId = item.getId();
        this.session = session;
        this.session.setData(BASIC_DATA, item);
    }

    @Override
    protected Object doInBackground() throws Exception {

        DetailedCatalogItem catalogItemDetails = moduleManager.getCatalogItemDetails(catalogItemId);

        DeveloperIdentity developer = catalogItemDetails.getDeveloper();
        if (developer != null) {
            String developerAlias = developer.getAlias();
            session.setData(DEVELOPER_NAME, developerAlias);
        }

        Language defaultLanguage = catalogItemDetails.getDefaultLanguage();
        if (defaultLanguage != null) {
            UUID languageId = defaultLanguage.getLanguageId();
            session.setData(LANGUAGE_ID, languageId);
        }

        Version version = catalogItemDetails.getVersion();
        if (version != null) {
            session.setData(WALLET_VERSION, version);
        }

        Skin defaultSkin = catalogItemDetails.getDefaultSkin();
        if (defaultSkin != null) {
            UUID skinId = defaultSkin.getSkinId();
            session.setData(SKIN_ID, skinId);

            List<byte[]> previewImageList = defaultSkin.getPreviewImageList();
            ArrayList<Bitmap> bitmapList = UtilsFuncs.INSTANCE.getBitmapList(previewImageList);
            session.setData(PREVIEW_IMGS, bitmapList);
        }

        return true;
    }


}
