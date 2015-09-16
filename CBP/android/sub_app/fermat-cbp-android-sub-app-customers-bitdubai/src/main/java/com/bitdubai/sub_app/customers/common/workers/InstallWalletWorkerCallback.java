package com.bitdubai.sub_app.customers.common.workers;

import android.app.Activity;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.ui.interfaces.FermatWorkerCallBack;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedSubAppExceptionSeverity;

/**
 * Created by nelson on 31/08/15.
 */
public class InstallWalletWorkerCallback implements FermatWorkerCallBack {
    private final Activity activity;
    private final ErrorManager errorManager;

    public InstallWalletWorkerCallback(Activity activity, ErrorManager errorManager) {
        this.activity = activity;
        this.errorManager = errorManager;
    }

    @Override
    public void onPostExecute(Object... result) {
        Toast.makeText(activity, "Se a instalado la billetera", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onErrorOccurred(Exception ex) {
        errorManager.reportUnexpectedSubAppException(SubApps.CWP_WALLET_STORE,
                UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, ex);

        Toast.makeText(activity, "Disculpe, no se pudo instalar la billetera", Toast.LENGTH_SHORT).show();
    }
}
