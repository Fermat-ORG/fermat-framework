package com.bitdubai.sub_app.intra_user_community.common;

import android.os.AsyncTask;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.SubAppsSession;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserModuleManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.sub_app.intra_user_community.common.models.WalletStoreListItem;



/**
 * Created by nelson on 29/08/15.
 * Tarea asincrona para obtener el detalle del catalog Item, asi como guardar dicha informacion en la sesion de la SubApp
 */
public class DetailedCatalogItemLoader extends AsyncTask<Void, Void, Boolean> {
    private final IntraUserModuleManager moduleManager;
    private final SubAppsSession subAppsSession;
    private final WalletStoreListItem data;
    private final DetailedCatalogItemLoaderListener listener;

    /**
     * Cre un objeto DetailedCatalogItemLoader
     *
     * @param moduleManager  objeto module manager que contiene los metodos para traer el detalle del catalog item
     * @param subAppsSession la sesion de la subapp donde se va a almacenar la informacion del detalle
     * @param data           la data basica de Catalog Item, entre ellas su UUID
     * @param listener       listener que se ejecuta cuando termine el procesamiento, permite ejecutar instrucciones en el UI MyThread
     */
    public DetailedCatalogItemLoader(final IntraUserModuleManager moduleManager,
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

//        try {
//            DetailedCatalogItem catalogItemDetails = moduleManager.getCatalogItemDetails(data.getId());
//            DeveloperIdentity developer = catalogItemDetails.getDeveloper();
//            String developerAlias = developer.getAlias();
//
//            ArrayList<Drawable> previewImageDrawableList = null;
//            Skin skin = catalogItemDetails.getDefaultSkin();
//            if (skin != null) {
//                List<byte[]> previewImageList = skin.getPreviewImageList();
//                if (previewImageList != null) {
//                    previewImageDrawableList = new ArrayList<>();
//
//                    for (int i = 0; i < previewImageList.size(); i++) {
//                        byte[] previewImgBytes = previewImageList.get(i);
//                        ByteArrayInputStream inputStream = new ByteArrayInputStream(previewImgBytes);
//                        Drawable img = Drawable.createFromStream(inputStream, "preview_" + i);
//                        previewImageDrawableList.add(img);
//                    }
//                }
//            }
//
//            subAppsSession.setData(IntraUserSubAppSession.BASIC_DATA, data);
//            subAppsSession.setData(IntraUserSubAppSession.DEVELOPER_NAME, developerAlias);
//            subAppsSession.setData(IntraUserSubAppSession.PREVIEW_IMGS, previewImageDrawableList);
//
//        } catch (Exception e) {
//            errorManager.reportUnexpectedSubAppException(SubApps.CWP_WALLET_STORE,
//                    UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
//
//            return false;
//        }
        return true;
    }

    @Override
    protected void onPostExecute(Boolean processComplete) {
        super.onPostExecute(processComplete);
        listener.onPostExecute(processComplete);
    }
}
