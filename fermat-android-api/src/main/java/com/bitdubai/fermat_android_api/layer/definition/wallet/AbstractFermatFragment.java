package com.bitdubai.fermat_android_api.layer.definition.wallet;


import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.bitdubai.fermat_android_api.engine.PaintActivityFeatures;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.FermatActivityManager;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.FermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.SubAppsSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.WizardConfiguration;
import com.bitdubai.fermat_android_api.ui.inflater.ViewInflater;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatWizardActivity;
import com.bitdubai.fermat_api.FermatStates;
import com.bitdubai.fermat_api.layer.all_definition.common.system.enums.NetworkStatus;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantGetBitcoinNetworkStatusException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantGetCommunicationNetworkStatusException;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.Engine;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.DesktopAppSelector;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatRuntime;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatScreenSwapper;
import com.bitdubai.fermat_api.layer.dmp_module.InstalledApp;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.FermatBundle;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;

/**
 * Created by Matias Furszyfer on 2015.11.21..
 */
public abstract class AbstractFermatFragment<S extends FermatSession,R extends ResourceProviderManager> extends Fragment implements AbstractFermatFragmentInterface<S,R>{

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

    enum ScreenSize{
        LARGE,NORMAL, UNDEFINED, SMALL
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        try {
            context = (WizardConfiguration) getActivity();
            viewInflater = new ViewInflater(getActivity(), appResourcesProviderManager);
        } catch (Exception ex) {
            throw new ClassCastException("cannot convert the current context to WizardConfiguration");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
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


    protected void selectApp(InstalledApp installedSubApp) throws Exception {
        destroy();
        getDesktopAppSelector().selectApp(installedSubApp);
    }

    private DesktopAppSelector getDesktopAppSelector() throws Exception {
        if(getActivity() instanceof DesktopAppSelector){
            return (DesktopAppSelector) getActivity();
        }
        throw new Exception("big problem occur");
    }

    /**
     * Method used to go to home desktop
     */
    protected void home(){
        ((FermatActivityManager)getActivity()).goHome();
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

    protected void changeApp(String fermatAppToConnectPublicKey, Object[] objects) {
        getFermatScreenSwapper().connectWithOtherApp(fermatAppToConnectPublicKey, objects);
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


    protected <S extends SubAppsSession> void destroy(){
        onDestroy();
        System.gc();
    }

    protected void sendErrorReport(String userTo) throws Exception {
        ((FermatActivityManager)getActivity()).reportError(userTo);
    }

    protected void sendMail(String userTo, String bodyText) throws Exception {
        ((FermatActivityManager)getActivity()).sendMailExternal(userTo,bodyText);
    }

    protected final void onBack(String activityCodeBack){
        getFermatScreenSwapper().onControlledActivityBack(activityCodeBack);
    }

    protected final void setChangeBackActivity(Activities backActivity){
        getFermatScreenSwapper().setChangeBackActivity(backActivity);
    }

    protected final FermatRuntime getRuntimeManager(){
        return ((FermatActivityManager)getActivity()).getRuntimeManager();
    }

    protected final FermatActivityManager getFermatActivityManager(){
        return ((FermatActivityManager)getActivity());
    }

    protected final NetworkStatus getFermatNetworkStatus() throws CantGetCommunicationNetworkStatusException {
        return getFermatStates().getFermatNetworkStatus();
    }

    protected final NetworkStatus getBitcoinNetworkStatus(BlockchainNetworkType blockchainNetworkType) throws CantGetBitcoinNetworkStatusException {
        return getFermatStates().getBitcoinNetworkStatus(blockchainNetworkType);
    }

    protected final FermatStates getFermatStates(){
        return  ((FermatStates)getActivity());
    }


    public final void onUpdateViewHandler(final String appCode,final String code){
        if(appSession.getAppPublicKey().equals(appCode)){
            onUpdateView(code);
        }

    }

    public final void onUpdateViewUIThred(final String code){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                onUpdateViewOnUIThread(code);
            }
        });
    }

    /**
     * This class have to be ovverride if someone wants to get broadcast
     *
     * @param code is a code for update some part of the fragment or everything
     */
    public void onUpdateView(String code) {
        return;
    }

    /**
     * This class have to be ovverride if someone wants to get broadcast on UI Thread
     * ONLY FOR VIEW UPDATE
     *
     * @param code is a code for update some part of the fragment or everything
     */


    public void onUpdateViewOnUIThread(String code) {
        return;
    }

    public void onUpdateView(FermatBundle bundle) {

    }
    public void onUpdateViewUIThred(FermatBundle bundle) {

    }
    /**
     * This method will be called when the user press the back button
     */
    public void onBackPressed() {

    }


    /**
     *  This method will be called when the user open the drawer if exist
     */
    public void onDrawerOpen() {

    }

    /**
     *  This method will be called when the user close the drawer if exist
     */
    public void onDrawerClose() {

    }

    public void onDrawerSlide(View drawerView, float offset) {

    }


    private ScreenSize getScreenSize(){
        int screenSize = getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK;
        ScreenSize screenSizeType = null;
        switch(screenSize) {
            case Configuration.SCREENLAYOUT_SIZE_LARGE:
                screenSizeType = ScreenSize.LARGE;
                break;
            case Configuration.SCREENLAYOUT_SIZE_NORMAL:
                screenSizeType = ScreenSize.NORMAL;
                break;
            case Configuration.SCREENLAYOUT_SIZE_SMALL:
                screenSizeType = ScreenSize.SMALL;
                break;
            case Configuration.SCREENLAYOUT_SIZE_UNDEFINED:
                screenSizeType = ScreenSize.UNDEFINED;
                break;
            default:
                screenSizeType = ScreenSize.UNDEFINED;
        }
        return screenSizeType;
    }
}
