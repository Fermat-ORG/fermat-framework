package com.bitdubai.sub_app.wallet_store.common.workers;

import android.app.Activity;
import android.app.ProgressDialog;
import android.view.View;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatButton;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatWorkerCallBack;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedSubAppExceptionSeverity;
import com.wallet_store.bitdubai.R;

/**
 * Created by nelson on 31/08/15.
 */
public class InstallWalletWorkerCallback implements FermatWorkerCallBack {
    private final Activity activity;
    private final ErrorManager errorManager;
    private ProgressDialog dialog;
    private FermatButton installButton;
    private FermatButton uninstallButton;

    public InstallWalletWorkerCallback(Activity activity, ErrorManager errorManager, ProgressDialog dialog) {
        this.activity = activity;
        this.errorManager = errorManager;
        this.dialog = dialog;
    }

    public InstallWalletWorkerCallback(Activity activity, ErrorManager errorManager, ProgressDialog dialog, FermatButton installButton, FermatButton uninstallButton) {
        this.activity = activity;
        this.errorManager = errorManager;
        this.dialog = dialog;
        this.installButton = installButton;
        this.uninstallButton = uninstallButton;
    }

    @Override
    public void onPostExecute(Object... result) {
        dialog.dismiss();
        Toast.makeText(activity, R.string.wallet_installed_message, Toast.LENGTH_SHORT).show();

        if (uninstallButton != null)
            uninstallButton.setVisibility(View.VISIBLE);
        if (installButton != null)
            installButton.setText(R.string.wallet_status_open);
    }

    @Override
    public void onErrorOccurred(Exception ex) {
        errorManager.reportUnexpectedSubAppException(SubApps.CWP_WALLET_STORE,
                UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, ex);

        dialog.dismiss();
        Toast.makeText(activity, R.string.wallet_not_installed_message, Toast.LENGTH_SHORT).show();
    }
}
