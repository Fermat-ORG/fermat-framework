package com.bitdubai.fermat_android_api.layer.definition.wallet;


import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.RelativeLayout;


import com.bitdubai.fermat_android_api.engine.PaintActivityFeatures;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.FermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.SubAppsSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.WizardConfiguration;
import com.bitdubai.fermat_android_api.ui.inflater.ViewInflater;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatWizardActivity;
import com.bitdubai.fermat_api.layer.all_definition.enums.Engine;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatScreenSwapper;
import com.bitdubai.fermat_api.layer.dmp_module.sub_app_manager.InstalledSubApp;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.InstalledWallet;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;

/**
 * Created by Matias Furszyfer on 2015.11.21..
 */
public abstract class AbstractFermatFragment<S extends FermatSession,R extends ResourceProviderManager> extends Fragment implements View.OnKeyListener {

    /**
     * FLAGS
     */
    protected boolean isAttached;

    /**
     * Platform
     */
    protected S appSession;
    protected R appResourcesProviderManager;


    /**
     * ViewInflater
     */
    protected ViewInflater viewInflater;
    private WizardConfiguration context;
    private String changeBackActivity;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            context = (WizardConfiguration) getActivity();
            viewInflater = new ViewInflater(getActivity(), appResourcesProviderManager);
            getView().setOnKeyListener(this);
        } catch (Exception ex) {
            throw new ClassCastException("cannot convert the current context to WizardConfiguration");
        }
    }


    /**
     * Start a configuration Wizard
     *
     * @param key  Enum Wizard registered type
     * @param args Object[] where you're be able to passing arguments like session, settings, resources, module, etc...
     */
    protected void startWizard(String key, Object... args) {
        if (context != null && isAttached) {
            context.showWizard(key, args);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        isAttached = true;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        isAttached = false;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
    }

    public void setAppSession(S appSession) {
        this.appSession = appSession;
    }

    public void setAppResourcesProviderManager(R appResourcesProviderManager) {
        this.appResourcesProviderManager = appResourcesProviderManager;
    }

    protected void wizardNext(){
        Activity activity = getActivity();
        if(activity instanceof FermatWizardActivity){
            ((FermatWizardActivity) activity).nextScreen();
        }
    }

    protected void wizardBack(){
        Activity activity = getActivity();
        if(activity instanceof FermatWizardActivity){
            ((FermatWizardActivity) activity).nextScreen();
        }
    }




    /**
     * Change activity
     */
    protected final void changeActivity(Activities activity, String appPublicKey) {
        destroy();
        getFermatScreenSwapper().changeActivity(activity.getCode(), appPublicKey);
    }

    /**
     * Change activity
     */
    protected final void changeActivity(Activities activity) {
        destroy();
        getFermatScreenSwapper().changeActivity(activity.getCode(), appSession.getAppPublicKey());
    }

    /**
     * Change activity
     */
    protected final void changeFragment(String fragment, int idContainer) {
        getFermatScreenSwapper().changeScreen(fragment, idContainer, null);
    }

    /**
     *
     */

    protected final RelativeLayout getToolbarHeader() {
        return getPaintActivtyFeactures().getToolbarHeader();
    }

    protected PaintActivityFeatures getPaintActivtyFeactures() {
        return ((PaintActivityFeatures) getActivity());
    }

    protected Toolbar getToolbar() {
        return getPaintActivtyFeactures().getToolbar();
    }

    protected void changeApp(Engine emgine,String fermatAppToConnectPublicKey, Object[] objects) {
        getFermatScreenSwapper().connectWithOtherApp(emgine,fermatAppToConnectPublicKey, objects);
    }

    protected FermatScreenSwapper getFermatScreenSwapper() {
        return (FermatScreenSwapper) getActivity();
    }

    /**
     * Change activity
     */
    protected final void changeActivity(String activityCode,String appPublicKey, Object... objectses) {
        destroy();
        ((FermatScreenSwapper) getActivity()).changeActivity(activityCode, appPublicKey, objectses);

    }
    /**
     * Change activity
     */
    @Deprecated
    protected final void changeActivity(String activityCode, Object... objectses) {
        destroy();
        ((FermatScreenSwapper) getActivity()).changeActivity(activityCode, null);
    }

    protected void changeApp(Engine emgine,Object[] objects){
        //getFermatScreenSwapper().connectWithOtherApp(emgine, objects);
    }

    protected void selectSubApp(InstalledSubApp installedSubApp){
        destroy();
        getFermatScreenSwapper().selectSubApp(installedSubApp);
    }

    protected void selectWallet(InstalledWallet installedWallet){
        destroy();
        getFermatScreenSwapper().selectWallet(installedWallet);
    }

    /**
     * Send local broadcast
     *
     * @param broadcast Intent broadcast with channel and extras
     */
    public void sendLocalBroadcast(Intent broadcast) {
        LocalBroadcastManager.getInstance(getActivity())
                .sendBroadcast(broadcast);
    }

    protected void invalidate(){
        getPaintActivtyFeactures().invalidate();
    }


    private <S extends SubAppsSession> void destroy(){
        onDestroy();
        System.gc();
    }

    protected final void onBack(String activityCodeBack){
        getFermatScreenSwapper().onControlledActivityBack(activityCodeBack);
    }

    protected final void setChangeBackActivity(String backActivity){
        this.changeBackActivity = backActivity;
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
            if( keyCode == KeyEvent.KEYCODE_BACK ){
                onBack(changeBackActivity);
                return true;
            }
            return false;
    }
}
