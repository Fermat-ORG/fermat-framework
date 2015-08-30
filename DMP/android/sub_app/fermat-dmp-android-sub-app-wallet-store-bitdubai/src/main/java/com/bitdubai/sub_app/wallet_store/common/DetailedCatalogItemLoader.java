package com.bitdubai.sub_app.wallet_store.common;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.SubAppsSession;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_store.interfaces.WalletStoreModuleManager;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.interfaces.DetailedCatalogItem;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.interfaces.Skin;
import com.bitdubai.fermat_api.layer.pip_Identity.developer.interfaces.DeveloperIdentity;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedSubAppExceptionSeverity;
import com.bitdubai.sub_app.wallet_store.common.models.WalletStoreListItem;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import static com.bitdubai.sub_app.wallet_store.session.WalletStoreSubAppSession.BASIC_DATA;
import static com.bitdubai.sub_app.wallet_store.session.WalletStoreSubAppSession.DEVELOPER_NAME;
import static com.bitdubai.sub_app.wallet_store.session.WalletStoreSubAppSession.PREVIEW_IMGS;

/**
 * Created by nelson on 29/08/15.
 * Tarea asincrona para obtener el detalle del catalog Item, asi como guardar dicha informacion en la sesion de la SubApp
 */
public class DetailedCatalogItemLoader extends AsyncTask<Void, Void, Boolean> {
    private final WalletStoreModuleManager moduleManager;
    private final SubAppsSession subAppsSession;
    private final WalletStoreListItem data;
    private final DetailedCatalogItemLoaderListener listener;

    /**
     * Cre un objeto DetailedCatalogItemLoader
     *
     * @param moduleManager  objeto module manager que contiene los metodos para traer el detalle del catalog item
     * @param subAppsSession la sesion de la subapp donde se va a almacenar la informacion del detalle
     * @param data           la data basica de Catalog Item, entre ellas su UUID
     * @param listener       listener que se ejecuta cuando termine el procesamiento, permite ejecutar instrucciones en el UI Thread
     */
    public DetailedCatalogItemLoader(final WalletStoreModuleManager moduleManager,
                                     final SubAppsSession subAppsSession,
                                     final WalletStoreListItem data,
                                     final DetailedCatalogItemLoaderListener listener) {

        this.moduleManager = moduleManager;
        this.subAppsSession = subAppsSession;
        this.data = data;
        this.listener = listener;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        ErrorManager errorManager = subAppsSession.getErrorManager();

        try {
            DetailedCatalogItem catalogItemDetails = moduleManager.getCatalogItemDetails(data.getId());
            DeveloperIdentity developer = catalogItemDetails.getDeveloper();
            String developerAlias = developer.getAlias();

            ArrayList<Drawable> previewImageDrawableList = null;
            Skin skin = catalogItemDetails.getDefaultSkin();
            if (skin != null) {
                List<byte[]> previewImageList = skin.getPreviewImageList();
                if (previewImageList != null) {
                    previewImageDrawableList = new ArrayList<>();

                    for (int i = 0; i < previewImageList.size(); i++) {
                        byte[] previewImgBytes = previewImageList.get(i);
                        ByteArrayInputStream inputStream = new ByteArrayInputStream(previewImgBytes);
                        Drawable img = Drawable.createFromStream(inputStream, "preview_" + i);
                        previewImageDrawableList.add(img);
                    }
                }
            }

            subAppsSession.setData(BASIC_DATA, data);
            subAppsSession.setData(DEVELOPER_NAME, developerAlias);
            subAppsSession.setData(PREVIEW_IMGS, previewImageDrawableList);

        } catch (Exception e) {
            errorManager.reportUnexpectedSubAppException(SubApps.CWP_WALLET_STORE,
                    UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);

            return false;
        }
        return true;
    }

    @Override
    protected void onPostExecute(Boolean processComplete) {
        super.onPostExecute(processComplete);
        listener.onPostExecute(processComplete);
    }
}
