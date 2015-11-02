package com.bitdubai.fermat_pip_plugin.layer.engine.app_runtime.developer.bitdubai.version_1;


import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Activity;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Fragment;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.MainMenu;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.MenuItem;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.SideMenu;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.StatusBar;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Tab;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.TabStrip;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.TitleBar;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.pip_engine.desktop_runtime.DesktopObject;
import com.bitdubai.fermat_api.layer.pip_engine.desktop_runtime.DesktopRuntimeManager;
import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_pip_plugin.layer.engine.app_runtime.developer.bitdubai.version_1.exceptions.CantFactoryResetException;
import com.bitdubai.fermat_pip_plugin.layer.engine.app_runtime.developer.bitdubai.version_1.structure.RuntimeDesktopObject;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.DealsWithEvents;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


/**
 * Created by Matias Furszyfer 16/9/2015
 */


public class DesktopRuntimeEnginePluginRoot implements Service, DesktopRuntimeManager, DealsWithEvents, DealsWithErrors, DealsWithPluginFileSystem, Plugin {

    /**
     * Service Interface member variables.
     */
    ServiceStatus serviceStatus = ServiceStatus.CREATED;

    /**
     * SubAppRuntimeManager Interface member variables.
     */

    List<FermatEventListener> listenersAdded = new ArrayList<>();

    /**
     * MAp of desktop identifier + runtimeDesktopObject
     */

    List<DesktopObject> lstDesktops = new ArrayList<DesktopObject>();

    /**
     * Last desktop-object
     */
    String lastDesktopObject;

    /**
     * Start desktop object
     */
    String startDesktopObject;

    //SubApps lastSubapp;

    //RuntimeSubApp homeScreen;

    /**
     * UsesFileSystem Interface member variables.
     */
    PluginFileSystem pluginFileSystem;


    /**
     * DealsWithErrors Interface member variables.
     */
    ErrorManager errorManager;

    /**
     * DealWithEvents Interface member variables.
     */
    EventManager eventManager;

    /**
     * Plugin Interface member variables.
     */
    UUID pluginId;


    public void addToNavigationStructure(/*String NavigationStructure, WalletModule*/) {

        /*
        PlatformEvent platformEvent = eventManager.getNewEvent(EventType.NAVIGATION_STRUCTURE_UPDATED);
        ((NavigationStructureUpdatedEvent) platformEvent).----------(this.-----);
        eventManager.raiseEvent(platformEvent);
        */
    }
    
    /*
    PlatformEvent platformEvent = eventManager.getNewEvent(EventType.NAVIGATION_STRUCTURE_UPDATED);
    ((NavigationStructureUpdatedEvent) platformEvent).--------(this.-------);
    eventManager.raiseEvent(platformEvent);
*/


    @Override
    public void start() throws CantStartPluginException {
        try {
            /**
             * I will initialize the handling of com.bitdubai.platform events.
             */
//            EventListener eventListener;
//            EventHandler eventHandler;
//            eventListener = eventManager.getNewListener(EventType.WALLET_RESOURCES_INSTALLED);
//            eventHandler = new WalletResourcesInstalledEventHandler();
//            ((WalletResourcesInstalledEventHandler) eventHandler).setSubAppRuntimeManager(this);
//            eventListener.setEventHandler(eventHandler);
//            eventManager.addListener(eventListener);
//            listenersAdded.add(eventListener);

            /**
             * At this time the only thing I can do is a factory reset. Once there should be a possibility to add
             * functionality based on wallets downloaded by users this wont be an option.
             * * *
             */
            factoryReset();

            this.serviceStatus = ServiceStatus.STARTED;
        } catch (CantFactoryResetException ex) {
            String message = CantStartPluginException.DEFAULT_MESSAGE;
            FermatException cause = ex;
            String context = "App Runtime Start";
            String possibleReason = "Some null definition";
            throw new CantStartPluginException(message, cause, context, possibleReason);
        } catch (Exception exception) {
            throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, "Unchecked Exception occurred, check the cause");
        }
    }

    @Override
    public void pause() {
        this.serviceStatus = ServiceStatus.PAUSED;
    }

    @Override
    public void resume() {

        this.serviceStatus = ServiceStatus.STARTED;

    }

    @Override
    public void stop() {
        /**
         * I will remove all the listeners registered with the event manager. 
         */
        for (FermatEventListener eventListener : listenersAdded) {
            eventManager.removeListener(eventListener);
        }

        listenersAdded.clear();

        this.serviceStatus = ServiceStatus.STOPPED;

    }

    @Override
    public ServiceStatus getStatus() {
        return this.serviceStatus;
    }


    /**
     * AppRuntime Interface implementation.
     */


    @Override
    public DesktopObject getLastDesktopObject() {
        if (lastDesktopObject != null) {
            //return mapDesktops.get(lastDesktopObject);
        }
        return  null;
        //return mapDesktops.get(startDesktopObject);
    }

    @Override
    public DesktopObject getDesktopObject(String desktopObjectType) {
        //DesktopObject desktopObject = mapDesktops.get(desktopObjectType);
//        if (desktopObject != null) {
//            lastDesktopObject = desktopObjectType;
//            return desktopObject;
//        }
        //TODO METODO CON RETURN NULL - OJO: solo INFORMATIVO de ayuda VISUAL para DEBUG - Eliminar si molesta
        return null;
    }

    @Override
    public List<DesktopObject> listDesktops() {
        return lstDesktops;
    }

    /**
     * UsesFileSystem Interface implementation.
     */

    @Override
    public void setPluginFileSystem(PluginFileSystem pluginFileSystem) {
        this.pluginFileSystem = pluginFileSystem;
    }

    /**
     * DealWithEvents Interface implementation.
     */

    @Override
    public void setEventManager(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    /**
     * DealWithErrors Interface implementation.
     */

    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }

    /**
     * DealsWithPluginIdentity methods implementation.
     */

    @Override
    public void setId(UUID pluginId) {
        this.pluginId = pluginId;
    }


    /**
     * The first time this plugins runs, it will setup the initial structure for the App, subApp and so on through the local
     * interfaces of the classes involved,
     */
    private void firstRunCheck() {

        /**
         * First I check weather this a structure already created, if not I create the "Factory" structure.
         */


    }


    /**
     * Here is where I actually generate the factory structure of the APP. This method is also useful to reset to the
     * factory structure.
     */
    private void factoryReset() throws CantFactoryResetException {

        //loadHomeScreen();

        try {

            RuntimeDesktopObject runtimeDesktopObject;// = new RuntimeDesktopObject();

            Activity runtimeActivity; //= new Activity();
            //runtimeActivity.setType(Activities.CWP_SHELL_LOGIN);
            //runtimeSubApp.addActivity(runtimeActivity);

            Fragment runtimeFragment; //= new Fragment();
            //runtimeFragment.setType(Fragments.CWP_SHELL_LOGIN.getKey());
            //runtimeActivity.addFragment(Fragments.CWP_SHELL_LOGIN.getKey(), runtimeFragment);

            TitleBar runtimeTitleBar;
            SideMenu runtimeSideMenu;
            MainMenu runtimeMainMenu;
            MenuItem runtimeMenuItem;
            TabStrip runtimeTabStrip;
            StatusBar statusBar;
            Tab runtimeTab;

            /**
             * Desktop CCP
             */

            runtimeDesktopObject = new RuntimeDesktopObject();
            runtimeDesktopObject.setType("DCCP");
            lstDesktops.add(runtimeDesktopObject);
            runtimeDesktopObject.setStartActivity(Activities.CCP_DESKTOP);

            Activity activity = new Activity();
            /**
             * set type home
             */
            //activity.setType(Activities.CWP_WALLET_MANAGER_MAIN);
            //activity.setType(Activities.dmp_DESKTOP_HOME);
            activity.setActivityType("CCPDHA");
            Fragment fragment = new Fragment();

            /**
             * Add WalletManager fragment
             */

            // dmp_WALLET_MANAGER_FRAGMENT
            fragment.setType("CCPWMF");
            activity.addFragment("CCPWMF",fragment);

            /**
             * Add home subApps fragment
             */

            fragment = new Fragment();
            // dmp_SUB_APP_MANAGER_FRAGMENT
            fragment.setType("CCPSAMF");
            activity.addFragment("CCPSAMF", fragment);




            /**
             * End Desktop CCP
             */


            /**
             * Desktop WPD
             */

            runtimeDesktopObject = new RuntimeDesktopObject();
            runtimeDesktopObject.setType("WPD");
            lstDesktops.add(runtimeDesktopObject);
            runtimeDesktopObject.setStartActivity(Activities.WPD_DESKTOP);

            activity = new Activity();
            /**
             * set type home
             */
            //activity.setType(Activities.CWP_WALLET_MANAGER_MAIN);
            //activity.setType(Activities.dmp_DESKTOP_HOME);
            activity.setActivityType("WPD");
            fragment = new Fragment();

            /**
             * Add home subApps fragment
             */

            fragment = new Fragment();
            // dmp_SUB_APP_MANAGER_FRAGMENT
            fragment.setType("CCPSAMF");
            activity.addFragment("CCPSAMF", fragment);



            /**
             * End Desktop WPD
             */


            /**
             * Desktop DAP
             */

            runtimeDesktopObject = new RuntimeDesktopObject();
            runtimeDesktopObject.setType("DDAP");
            lstDesktops.add(runtimeDesktopObject);
            runtimeDesktopObject.setStartActivity(Activities.DAP_DESKTOP);

            activity = new Activity();
            /**
             * set type home
             */
            //activity.setType(Activities.CWP_WALLET_MANAGER_MAIN);
            //activity.setType(Activities.dmp_DESKTOP_HOME);
            activity.setActivityType("DAPDHA");
            fragment = new Fragment();

            /**
             * Add WalletManager fragment
             */

            // dmp_WALLET_MANAGER_FRAGMENT
            fragment.setType("DAPWMF");
            activity.addFragment("DAPWMF",fragment);

            /**
             * Add home subApps fragment
             */

            fragment = new Fragment();
            // dmp_SUB_APP_MANAGER_FRAGMENT
            fragment.setType("DAPSAMF");
            activity.addFragment("DAPSAMF", fragment);

            /**
             * End Desktop DAP
             */

            /**
             * Desktop CBP
             */

            runtimeDesktopObject = new RuntimeDesktopObject();
            runtimeDesktopObject.setType("DCBP");
            lstDesktops.add(runtimeDesktopObject);
            runtimeDesktopObject.setStartActivity(Activities.CBP_DESKTOP);

            activity = new Activity();
            /**
             * set type home
             */
            //activity.setType(Activities.CWP_WALLET_MANAGER_MAIN);
            //activity.setType(Activities.dmp_DESKTOP_HOME);
            activity.setActivityType("CBPDHA");
            fragment = new Fragment();

            /**
             * Add WalletManager fragment
             */

            // dmp_WALLET_MANAGER_FRAGMENT
            fragment.setType("CBPWMF");
            activity.addFragment("CBPWMF",fragment);

            /**
             * Add home subApps fragment
             */

            fragment = new Fragment();
            // dmp_SUB_APP_MANAGER_FRAGMENT
            fragment.setType("CBPSAMF");
            activity.addFragment("CBPSAMF", fragment);

            /**
             * End Desktop CBP
             */


        } catch (Exception e) {
            String message = CantFactoryResetException.DEFAULT_MESSAGE;
            FermatException cause = FermatException.wrapException(e);
            String context = "Error on method Factory Reset, setting the structure of the apps";
            String possibleReason = "some null definition";
            throw new CantFactoryResetException(message, cause, context, possibleReason);

        }

    }



}
