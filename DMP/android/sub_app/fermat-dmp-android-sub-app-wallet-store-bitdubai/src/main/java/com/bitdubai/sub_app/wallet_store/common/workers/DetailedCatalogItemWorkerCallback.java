package com.bitdubai.sub_app.wallet_store.common.workers;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.support.v4.util.ArrayMap;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.SubAppsSession;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatWorkerCallBack;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_settings.interfaces.SubAppSettings;
import com.bitdubai.fermat_pip_api.layer.pip_network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedSubAppExceptionSeverity;
import com.bitdubai.sub_app.wallet_store.common.models.WalletStoreListItem;
import com.bitdubai.sub_app.wallet_store.fragments.DetailsActivityFragment;
import com.wallet_store.bitdubai.R;

import static com.bitdubai.sub_app.wallet_store.session.WalletStoreSubAppSession.BASIC_DATA;
import static com.bitdubai.sub_app.wallet_store.session.WalletStoreSubAppSession.DEVELOPER_NAME;
import static com.bitdubai.sub_app.wallet_store.session.WalletStoreSubAppSession.PREVIEW_IMGS;

/**
 * Created by nelson on 29/08/15.
 * Clase CallBack para {@link DetailedCatalogItemWorker}
 */
public class DetailedCatalogItemWorkerCallback implements FermatWorkerCallBack {
    private final Activity activity;
    private final SubAppsSession session;
    private final SubAppSettings settings;
    private final SubAppResourcesProviderManager providerManager;
    private final WalletStoreListItem basicData;

    public DetailedCatalogItemWorkerCallback(WalletStoreListItem basicData, Activity activity,
                                             SubAppsSession session,
                                             SubAppSettings settings,
                                             SubAppResourcesProviderManager providerManager) {
        this.activity = activity;
        this.session = session;
        this.settings = settings;
        this.providerManager = providerManager;
        this.basicData = basicData;
    }

    @Override
    public void onPostExecute(Object... result) {
        ArrayMap data = (ArrayMap) result[0];

        session.setData(BASIC_DATA, basicData);
        session.setData(DEVELOPER_NAME, data.get(DEVELOPER_NAME));
        session.setData(PREVIEW_IMGS, data.get(PREVIEW_IMGS));

        final DetailsActivityFragment fragment = DetailsActivityFragment.newInstance();
        fragment.setSubAppsSession(session);
        fragment.setSubAppSettings(settings);
        fragment.setSubAppResourcesProviderManager(providerManager);

        final FragmentTransaction FT = activity.getFragmentManager().beginTransaction();
        FT.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        FT.replace(R.id.activity_container, fragment);
        FT.addToBackStack(null);
        FT.commit();
    }

    @Override
    public void onErrorOccurred(Exception ex) {
        final ErrorManager errorManager = session.getErrorManager();
        errorManager.reportUnexpectedSubAppException(SubApps.CWP_WALLET_STORE,
                UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, ex);

        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Hubo un problema");
        builder.setMessage("No se pudieron obtener los detalles de la wallet seleccionada");
        builder.setPositiveButton("OK", null);
        builder.show();
    }
}
