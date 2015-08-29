package com.bitdubai.sub_app.wallet_store.common;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v4.util.ArrayMap;

import com.bitdubai.fermat_android_api.ui.interfaces.FermatWorkerCallBack;
import com.bitdubai.fermat_android_api.ui.util.FermatWorker;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_store.interfaces.WalletStoreModuleManager;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.interfaces.DetailedCatalogItem;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.interfaces.Skin;
import com.bitdubai.fermat_api.layer.pip_Identity.developer.interfaces.DeveloperIdentity;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.bitdubai.sub_app.wallet_store.session.WalletStoreSubAppSession.DEVELOPER_NAME;
import static com.bitdubai.sub_app.wallet_store.session.WalletStoreSubAppSession.PREVIEW_IMGS;

/**
 * Created by nelson on 29/08/15.
 * Worker que se encarga de buscar el DetailCatalogItem de un CatalogItem y extraer la data contenida en ella
 */
public class DetailCatalogItemWorker extends FermatWorker {

    private WalletStoreModuleManager moduleManager;
    private UUID catalogItemId;


    public DetailCatalogItemWorker(Activity context, FermatWorkerCallBack callBack) {
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
        DetailedCatalogItem catalogItemDetails = moduleManager.getCatalogItemDetails(catalogItemId);
        DeveloperIdentity developer = catalogItemDetails.getDeveloper();
        String developerAlias = developer.getAlias();

        ArrayList<Bitmap> previewImageDrawableList = null;
        Skin skin = catalogItemDetails.getDefaultSkin();
        if (skin != null) {
            List<byte[]> previewImageList = skin.getPreviewImageList();
            if (previewImageList != null) {
                previewImageDrawableList = new ArrayList<>();

                for (int i = 0; i < previewImageList.size(); i++) {
                    byte[] previewImgBytes = previewImageList.get(i);
                    Bitmap img = BitmapFactory.decodeByteArray(previewImgBytes, 0, previewImgBytes.length);
                    previewImageDrawableList.add(img);
                }
            }
        }

        ArrayMap<String, Object> data = new ArrayMap<>();
        data.put(DEVELOPER_NAME, developerAlias);
        data.put(PREVIEW_IMGS, previewImageDrawableList);
        return data;
    }
}
