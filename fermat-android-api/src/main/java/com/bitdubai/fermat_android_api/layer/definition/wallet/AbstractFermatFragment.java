package com.bitdubai.fermat_android_api.layer.definition.wallet;


import android.app.Activity;
import android.app.Fragment;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.bitdubai.fermat_android_api.core.ResourceSearcher;
import com.bitdubai.fermat_android_api.engine.PaintActivityFeatures;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.FermatActivityManager;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.FermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.FrameworkHelpers;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.WizardConfiguration;
import com.bitdubai.fermat_android_api.ui.inflater.ViewInflater;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatWizardActivity;
import com.bitdubai.fermat_api.FermatBroadcastReceiver;
import com.bitdubai.fermat_api.FermatIntentFilter;
import com.bitdubai.fermat_api.FermatStates;
import com.bitdubai.fermat_api.layer.all_definition.common.system.enums.NetworkStatus;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantGetBitcoinNetworkStatusException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantGetCommunicationNetworkStatusException;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.Engine;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.FermatDrawable;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.FermatView;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.SourceLocation;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.DesktopAppSelector;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatFragment;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatRuntime;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatScreenSwapper;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.option_menu.OptionMenuItem;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.FermatBundle;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matias Furszyfer on 2015.11.21..
 */
public abstract class AbstractFermatFragment<S extends FermatSession, R extends ResourceProviderManager> extends Fragment implements AbstractFermatFragmentInterface<S, R> {

    private static final String TAG = "AbstractFermatFragment";
    /**
     * FLAGS
     */
    protected boolean isAttached;
    /**
     * If the fragment is visible for the user
     */
    protected boolean isVisible;

    /**
     * Platform
     */
    protected FermatFragment fermatFragmentType;
    protected S appSession;
    protected R appResourcesProviderManager;

    /**
     * Receivers
     */
    private List<FermatBroadcastReceiver> receivers;

    /**
     * OptionMenuListeners
     */
//    private Map<Integer,?> optionMenuListeners;

    /**
     * ViewInflater
     */
    protected ViewInflater viewInflater;
    private WizardConfiguration context;

    public enum ScreenSize {
        LARGE, NORMAL, UNDEFINED, SMALL
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //if(fermatFragmentType.getOptionsMenu()!=null)
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
    public void onAttach(Context activity) {
        super.onAttach(activity);
        isAttached = true;
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
    public void onPrepareOptionsMenu(Menu menu) {
        try {
            if (fermatFragmentType != null) {
                if (isVisible) {
                    if (fermatFragmentType.getOptionsMenu() != null) {
                        List<OptionMenuItem> optionsMenuItems = fermatFragmentType.getOptionsMenu().getMenuItems();
                        for (int i = 0; i < optionsMenuItems.size(); i++) {
                            OptionMenuItem menuItem = optionsMenuItems.get(i);
                            int id = menuItem.getId();
                            int groupId = menuItem.getGroupId();
                            int order = menuItem.getOrder();
                            int showAsAction = menuItem.getShowAsAction();
                            MenuItem oldMenu = menu.findItem(id);
                            if (oldMenu == null) {
                                MenuItem item = menu.add(groupId, id, order, menuItem.getLabel());
                                FermatDrawable icon = menuItem.getFermatDrawable();
                                if (icon != null) {
                                    int iconRes = obtainRes(ResourceSearcher.DRAWABLE_TYPE, icon.getId(), icon.getSourceLocation(), icon.getOwner().getOwnerAppPublicKey());
                                    item.setIcon(iconRes);//.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

                                }
                                if (showAsAction != -1)
                                    item.setShowAsAction(menuItem.getShowAsAction());
                                int actionViewClass = menuItem.getActionViewClass();
                                if (actionViewClass != -1) {
                                    View view = obtainFrameworkViewOptionMenuAvailable(actionViewClass, SourceLocation.FERMAT_FRAMEWORK);
                                    item.setActionView(view);
                                }
                            }
                        }
                    }
                }
            } else {
                if (appSession != null)
                    Log.e(TAG, new StringBuilder().append("FermatFragmentType null in fragment for app:").append(appSession.getAppPublicKey()).append(", contact furszy").toString());
            }

            onOptionMenuPrepared(menu);


        } catch (Exception e) {
            if (appSession != null)
                Log.e(TAG, new StringBuilder().append("Error loading optionsMenu, please check fragments for session: ").append(appSession.getAppPublicKey()).append(", if problem persist contact to Furszy").toString());
            e.printStackTrace();
        }
        super.onPrepareOptionsMenu(menu);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

//        try {
//            if(fermatFragmentType!=null) {
//                if (fermatFragmentType.getOptionsMenu() != null) {
//                    List<OptionMenuItem> optionsMenuItems = fermatFragmentType.getOptionsMenu().getMenuItems();
//                    for (int i = 0; i < optionsMenuItems.size(); i++) {
//                        OptionMenuItem menuItem = optionsMenuItems.get(i);
//                        int id = menuItem.getId();
//                        int groupId = menuItem.getGroupId();
//                        int order = menuItem.getOrder();
//                        int showAsAction = menuItem.getShowAsAction();
//                        MenuItem item = menu.add(groupId, id, order, menuItem.getLabel());
//                        FermatDrawable icon = menuItem.getFermatDrawable();
//                        if (icon != null) {
//                            int iconRes = obtainRes(icon.getId(), icon.getSourceLocation(), icon.getOwner().getOwnerAppPublicKey());
//                            item.setIcon(iconRes);//.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
//
//                        }
//                        if (showAsAction != -1) item.setShowAsAction(menuItem.getShowAsAction());
//                        int actionViewClass = menuItem.getActionViewClass();
//                        if (actionViewClass != -1) {
//                            item.setActionView(obtainFrameworkViewOptionMenuAvailable(actionViewClass, SourceLocation.FERMAT_FRAMEWORK));
//                        }
//                    }
//                }
//            }else{
//                if(appSession!=null)
//                Log.e(TAG,"FermatFragmentType null in fragment for app:"+appSession.getAppPublicKey()+", contact furszy");
//            }
//
//
//        } catch (Exception e) {
//            if(appSession!=null) Log.e(TAG,"Error loading optionsMenu, please check fragments for session:"+appSession.getAppPublicKey()+", if problem persist contact to Furszy");
//            e.printStackTrace();
//        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    /**
     * Method to obtain res from other apps
     */
    private final int obtainRes(int resType, int id, SourceLocation sourceLocation, String appOwnerPublicKey) {
        return getFrameworkHelpers().obtainRes(resType, id, sourceLocation, appOwnerPublicKey);
    }

    /**
     * Method to obtain view class from framework
     *
     * @return
     */
    private final View obtainFrameworkView(FermatView fermatView) {
        return getFrameworkHelpers().obtainClassView(fermatView);
    }

    private final View obtainFrameworkViewOptionMenuAvailable(int id, SourceLocation sourceLocation) {
        return getFrameworkHelpers().obtainFrameworkOptionMenuClassViewAvailable(id, sourceLocation);
    }

    private final View obtainFrameworkViewOptionMenuAvailableAndLoadListeners(int id, SourceLocation sourceLocation, Object[] listeners) {
        return getFrameworkHelpers().obtainFrameworkOptionMenuClassViewAvailable(id, sourceLocation, listeners);
    }


    private final FrameworkHelpers getFrameworkHelpers() {
        return (FrameworkHelpers) getActivity();
    }

    public final void setAppSession(S appSession) {
        this.appSession = appSession;
    }

    public final void setFragmentType(FermatFragment fermatFragmentType) {
        this.fermatFragmentType = fermatFragmentType;
    }

    public void setAppResourcesProviderManager(R appResourcesProviderManager) {
        this.appResourcesProviderManager = appResourcesProviderManager;
    }

    protected void wizardNext() {
        Activity activity = getActivity();
        if (activity instanceof FermatWizardActivity) {
            ((FermatWizardActivity) activity).nextScreen();
        }
    }

    protected void wizardBack() {
        Activity activity = getActivity();
        if (activity instanceof FermatWizardActivity) {
            ((FermatWizardActivity) activity).nextScreen();
        }
    }


    protected void selectApp(String appPublicKey) throws Exception {
        destroy();
        ((FermatActivityManager) getActivity()).selectApp(appPublicKey);
    }


    private DesktopAppSelector getDesktopAppSelector() throws Exception {
        if (getActivity() instanceof DesktopAppSelector) {
            return (DesktopAppSelector) getActivity();
        }
        throw new Exception("big problem occur");
    }

    /**
     * Method used to go to home desktop
     */
    protected void home() {
        ((FermatActivityManager) getActivity()).goHome();
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
    protected final void changeActivity(String activityCode, String appPublicKey, Object... objectses) {
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

    protected void changeApp(Engine emgine, Object[] objects) {
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

    protected void invalidate() {
        getPaintActivtyFeactures().invalidate();
    }


    protected void destroy() {
        unregisterAllReceivers();
        onDestroy();
        System.gc();
    }

    protected void sendErrorReport(String userTo) throws Exception {
        ((FermatActivityManager) getActivity()).reportError(userTo);
    }

    protected void sendMail(String userTo, String bodyText) throws Exception {
        ((FermatActivityManager) getActivity()).sendMailExternal(userTo, bodyText);
    }

    protected final void onBack(String activityCodeBack) {
        getFermatScreenSwapper().onControlledActivityBack(activityCodeBack);
    }

    protected final void setChangeBackActivity(Activities backActivity) {
        try {
            getFermatScreenSwapper().setChangeBackActivity(backActivity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected final FermatRuntime getRuntimeManager() {
        if (isAttached)
            return ((FermatActivityManager) getActivity()).getRuntimeManager();
        return null;
    }

    protected final void changeStartActivity(String activityCode) {
        ((FermatActivityManager) getActivity()).getRuntimeManager().changeStartActivity(appSession.getAppPublicKey(), activityCode);
    }

    public void changeTabNotification(String activityCode, int number) throws InvalidParameterException {
        ((FermatActivityManager) getActivity()).getRuntimeManager().changeTabNumber(appSession.getAppPublicKey(), activityCode, number);
    }

    protected final FermatActivityManager getFermatActivityManager() {
        return ((FermatActivityManager) getActivity());
    }

    protected final NetworkStatus getFermatNetworkStatus() throws CantGetCommunicationNetworkStatusException {
        return getFermatStates().getFermatNetworkStatus();
    }

    protected final NetworkStatus getBitcoinNetworkStatus(BlockchainNetworkType blockchainNetworkType) throws CantGetBitcoinNetworkStatusException {
        return getFermatStates().getBitcoinNetworkStatus(blockchainNetworkType);
    }

    protected final FermatStates getFermatStates() {
        return ((FermatStates) getActivity());
    }


    public final void onUpdateViewUIThred(final String code) {
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

    @Override
    public void onUpdateViewOnUIThread(FermatBundle code) {

    }

    /**
     * This method will be called when the user press the back button
     */
    public void onBackPressed() {

    }


    public final void setFragmentFocus(boolean isVisible) {
        this.isVisible = isVisible;
        if (isAttached) {
            onFragmentFocus();
        }
    }

    /**
     * This method is called when the fragment is on user's focus
     */
    public void onFragmentFocus() {

    }

    /**
     * This method will be called when the user open the drawer if exist
     */
    public void onDrawerOpen() {

    }

    /**
     * This method will be called when the user close the drawer if exist
     */
    public void onDrawerClose() {

    }

    public void onDrawerSlide(View drawerView, float offset) {

    }

    /**
     * Method to prepare optionMenu
     */
    public void onOptionMenuPrepared(Menu menu) {

    }

    public ScreenSize getScreenSize() {
        int screenSize = getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK;
        ScreenSize screenSizeType = null;
        switch (screenSize) {
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


    public boolean isActiveNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public void setToolbarTitleVisibility(boolean isVisible) {

    }

    public void pushNotification(Notification notification) {
        getPaintActivtyFeactures().pushNotification(appSession.getAppPublicKey(), notification);
    }

    public void cancelNotification(FermatBundle fermatBundle) {
        getPaintActivtyFeactures().cancelNotification(fermatBundle);
    }


    /**
     * Runtime Fragment methods
     * //TODO: esto pueda ser una transacción y cuando le da commit se hace y se cambia todo lo que se quiera cambiar en runtime del fragmento
     */

    /**
     * Change the optionMenuItem visibility for a fragment menuItem
     *
     * @param id
     * @param visibility
     * @throws InvalidParameterException
     */
    public void changeOptionMenuVisibility(int id, boolean visibility) throws InvalidParameterException {
        changeOptionMenuVisibility(id, visibility, false);
    }

    /**
     * Change the optionMenuItem visibility for a activity menuItem
     *
     * @param id
     * @param visibility
     * @param fromParent
     * @throws InvalidParameterException
     */
    public void changeOptionMenuVisibility(int id, boolean visibility, boolean fromParent) throws InvalidParameterException {
        if (!fromParent) fermatFragmentType.getOptionsMenu().getItem(id).setVisibility(visibility);
        else
            getPaintActivtyFeactures().changeOptionMenuVisibility(id, visibility, appSession.getAppPublicKey());
        getToolbar().getMenu().findItem(id).setVisible(visibility);
    }


    /**
     * Receivers
     */
    protected void registerReceiver(FermatIntentFilter fermatIntentFilter, FermatBroadcastReceiver fermatBroadcastReceiver) {
        if (receivers == null) receivers = new ArrayList<>();
        receivers.add(fermatBroadcastReceiver);
        getFrameworkHelpers().registerReceiver(fermatIntentFilter, fermatBroadcastReceiver, appSession.getAppPublicKey());
    }

    protected void unregisterReceiver(FermatBroadcastReceiver fermatBroadcastReceiver) {
        if (receivers != null) receivers.remove(fermatBroadcastReceiver);
        getFrameworkHelpers().unregisterReceiver(fermatBroadcastReceiver, appSession.getAppPublicKey());
    }

    protected void unregisterAllReceivers() {
        if (receivers != null) {
            for (FermatBroadcastReceiver receiver : receivers) {
                getFrameworkHelpers().unregisterReceiver(receiver, appSession.getAppPublicKey());
            }
        }
    }

    /**
     * Override this method if yo want to implement infinite scrolling or pagination.
     * Return a {@link RecyclerView.OnScrollListener} for the {@link RecyclerView} of this fragment.
     *
     * @return the {@link RecyclerView.OnScrollListener} for the {@link RecyclerView} of this fragment.
     * This return <code>null</code> by default
     */
    public RecyclerView.OnScrollListener getScrollListener() {
        return null;
    }


}
