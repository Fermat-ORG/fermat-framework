package com.bitdubai.sub_app.intra_user.common;

import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_store.enums.InstallationStatus;

import static com.bitdubai.fermat_api.layer.dmp_middleware.wallet_store.enums.InstallationStatus.INSTALLED;
import static com.bitdubai.fermat_api.layer.dmp_middleware.wallet_store.enums.InstallationStatus.NOT_UNINSTALLED;
import static com.bitdubai.fermat_api.layer.dmp_middleware.wallet_store.enums.InstallationStatus.UPGRADE_AVAILABLE;
import com.intra_user.bitdubai.R;
/**
 * Created by nelson on 28/08/15.
 */
public enum UtilsFuncs {
    INSTANCE;

    public int getInstallationStatusStringResource(InstallationStatus installationStatus) {
        if (installationStatus == INSTALLED || installationStatus == NOT_UNINSTALLED)
            return R.string.wallet_status_installed;
        if (installationStatus == UPGRADE_AVAILABLE)
            return R.string.wallet_status_upgrade;

        return R.string.wallet_status_install;
    }
}
