package com.bitdubai.fermat_android_api.layer.definition.wallet;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.FermatSession;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.DesktopAppSelector;
import com.bitdubai.fermat_api.layer.dmp_module.InstalledApp;
import com.bitdubai.fermat_api.layer.dmp_module.sub_app_manager.InstalledSubApp;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.InstalledWallet;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.FermatBundle;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;

/**
 * Created by Matias Furszyfer on 2016.03.09..
 */
public class AbstractDesktopFragment<S extends FermatSession, R extends ResourceProviderManager> extends AbstractFermatFragment<S, R> {


    protected void selectSubApp(InstalledSubApp installedSubApp) throws Exception {
        destroy();
        getDesktopAppSelector().selectSubApp(installedSubApp);
    }

    protected void selectApp(InstalledApp installedSubApp) throws Exception {
        destroy();
        getDesktopAppSelector().selectApp(installedSubApp);
    }

    protected void selectWallet(InstalledWallet installedWallet) throws Exception {
        destroy();
        getDesktopAppSelector().selectWallet(installedWallet);
    }

    private DesktopAppSelector getDesktopAppSelector() throws Exception {
        if (getActivity() instanceof DesktopAppSelector) {
            return (DesktopAppSelector) getActivity();
        }
        throw new Exception("big problem occur");
    }

    /**
     * This method is called when the fragment is on user's focus
     */
    @Override
    public void onFragmentFocus() {

    }


    @Override
    public void onUpdateViewOnUIThread(FermatBundle code) {

    }
}
