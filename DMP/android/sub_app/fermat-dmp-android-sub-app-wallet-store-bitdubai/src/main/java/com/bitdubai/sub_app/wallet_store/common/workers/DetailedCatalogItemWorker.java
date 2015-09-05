package com.bitdubai.sub_app.wallet_store.common.workers;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v4.util.ArrayMap;

import com.bitdubai.fermat_android_api.ui.interfaces.FermatWorkerCallBack;
import com.bitdubai.fermat_android_api.ui.util.FermatWorker;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_store.interfaces.WalletStoreModuleManager;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.interfaces.DetailedCatalogItem;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.interfaces.Language;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.interfaces.Skin;
import com.bitdubai.fermat_api.layer.pip_Identity.developer.interfaces.DeveloperIdentity;
import com.bitdubai.sub_app.wallet_store.common.UtilsFuncs;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.bitdubai.sub_app.wallet_store.session.WalletStoreSubAppSession.DEVELOPER_NAME;
import static com.bitdubai.sub_app.wallet_store.session.WalletStoreSubAppSession.LANGUAGE_ID;
import static com.bitdubai.sub_app.wallet_store.session.WalletStoreSubAppSession.PREVIEW_IMGS;
import static com.bitdubai.sub_app.wallet_store.session.WalletStoreSubAppSession.SKIN_ID;
import static com.bitdubai.sub_app.wallet_store.session.WalletStoreSubAppSession.WALLET_VERSION;

/**
 * Created by nelson on 29/08/15.
 * Worker que se encarga de buscar el DetailCatalogItem de un CatalogItem y extraer la data contenida en ella
 */
public class DetailedCatalogItemWorker extends FermatWorker {

    private WalletStoreModuleManager moduleManager;
    private UUID catalogItemId;


    public DetailedCatalogItemWorker(Activity context, FermatWorkerCallBack callBack) {
        super(context, callBack);
    }

    public void setModuleManager(final WalletStoreModuleManager moduleManager) {
        this.moduleManager = moduleManager;
    }

    public void setCatalogItemId(final UUID catalogItemId) {
        this.catalogItemId = catalogItemId;
    }

    @Override
    protected Object doInBackground() throws Exception {
        ArrayMap<String, Object> data = new ArrayMap<>();

        DetailedCatalogItem catalogItemDetails = moduleManager.getCatalogItemDetails(catalogItemId);

        DeveloperIdentity developer = catalogItemDetails.getDeveloper();
        if (developer != null) {
            String developerAlias = developer.getAlias();
            data.put(DEVELOPER_NAME, developerAlias);
        }

        Language defaultLanguage = catalogItemDetails.getDefaultLanguage();
        if (defaultLanguage != null) {
            UUID languageId = defaultLanguage.getLanguageId();
            data.put(LANGUAGE_ID, languageId);
        }

        Version version = catalogItemDetails.getVersion();
        if(version != null){
            data.put(WALLET_VERSION, version);
        }

        Skin defaultSkin = catalogItemDetails.getDefaultSkin();
        if (defaultSkin != null) {
            UUID skinId = defaultSkin.getSkinId();
            data.put(SKIN_ID, skinId);

            List<byte[]> previewImageList = defaultSkin.getPreviewImageList();
            ArrayList<Bitmap> bitmapList = UtilsFuncs.INSTANCE.getBitmapList(previewImageList);
            data.put(PREVIEW_IMGS, bitmapList);
        }

        return data;
    }


}
