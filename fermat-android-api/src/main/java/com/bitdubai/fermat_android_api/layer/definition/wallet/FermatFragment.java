//package com.bitdubai.fermat_android_api.layer.definition.wallet;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v4.content.LocalBroadcastManager;
//import android.view.Menu;
//import android.view.MenuInflater;
//import android.view.View;
//import android.widget.RelativeLayout;
//
//import com.bitdubai.fermat_android_api.engine.PaintActivityFeatures;
//import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.SubAppsSession;
//import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.WizardConfiguration;
//import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
//import com.bitdubai.fermat_android_api.ui.inflater.ViewInflater;
//import com.bitdubai.fermat_api.layer.all_definition.enums.Engine;
//import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatScreenSwapper;
//import com.bitdubai.fermat_api.layer.dmp_module.sub_app_manager.InstalledSubApp;
//import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.InstalledWallet;
//import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;
//
///**
// * Common Android Fragment Base
// *
// * @author Matias Furszy
// * @author Francisco Vasquez
// * @version 1.1
// */
//public abstract class fermatFragment extends fermatFragment {
//
//
//    /**
//     * REFERENCES
//     */
//    protected WizardConfiguration context;
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        try {
//            context = (WizardConfiguration) getActivity();
//            viewInflater = new ViewInflater(getActivity(), appResourcesProviderManager);
//        } catch (Exception ex) {
//            throw new ClassCastException("cannot convert the current context to WizardConfiguration");
//        }
//    }
//
//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        menu.clear();
//    }
//
//    /**
//     * Setting up SubApp Session
//     *
//     * @param subAppsSession SubAppSession object
//     */
//    public void setSubAppsSession(SubAppsSession subAppsSession) {
//        this.appSession = subAppsSession;
//    }
//
//    /**
//     * Setting up SubAppResourcesProviderManager
//     *
//     * @param subAppResourcesProviderManager SubAppResourcesProviderManager object
//     */
//    public void setSubAppResourcesProviderManager(SubAppResourcesProviderManager subAppResourcesProviderManager) {
//        this.appResourcesProviderManager = subAppResourcesProviderManager;
//    }
//
//
//    protected PaintActivityFeatures getPaintActivtyFeactures(){
//        return ((PaintActivityFeatures)getActivity());
//    }
//
//    protected void changeApp(Engine emgine,Object[] objects){
//        //getFermatScreenSwapper().connectWithOtherApp(emgine, objects);
//    }
//
//    protected void selectSubApp(InstalledSubApp installedSubApp){
//        destroy();
//        getFermatScreenSwapper().selectSubApp(installedSubApp);
//    }
//
//    protected void selectWallet(InstalledWallet installedWallet){
//        destroy();
//        getFermatScreenSwapper().selectWallet(installedWallet);
//    }
//
//    protected FermatScreenSwapper getFermatScreenSwapper(){
//        return (FermatScreenSwapper) getActivity();
//    }
//
//
//    private void destroy(){
//        onDestroy();
//        System.gc();
//    }
//
//    /**
//     * Send local broadcast
//     *
//     * @param broadcast Intent broadcast with channel and extras
//     */
//    public void sendLocalBroadcast(Intent broadcast) {
//        LocalBroadcastManager.getInstance(getActivity())
//                .sendBroadcast(broadcast);
//    }
//}
//
