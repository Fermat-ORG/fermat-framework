package com.bitdubai.fermat_pip_plugin.layer.engine.desktop_runtime.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Activity;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.BottomNavigation;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.FermatRuntimeFragment;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.option_menu.OptionsMenu;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.MenuItem;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.SideMenu;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.StatusBar;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Tab;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.TabStrip;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.TitleBar;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Fragments;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatBottomNavigation;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatStructure;
import com.bitdubai.fermat_api.layer.all_definition.util.LibraryException;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;
import com.bitdubai.fermat_api.layer.core.PluginInfo;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantLoadFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_api.layer.pip_engine.desktop_runtime.DesktopObject;
import com.bitdubai.fermat_api.layer.pip_engine.desktop_runtime.DesktopRuntimeManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.EventManager;
import com.bitdubai.fermat_pip_plugin.layer.engine.desktop_runtime.developer.bitdubai.version_1.exceptions.CantFactoryResetException;
import com.bitdubai.fermat_pip_plugin.layer.engine.desktop_runtime.developer.bitdubai.version_1.structure.RuntimeDesktopObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * TODO this plugin DO.....
 * <p/>
 * Created by Matias Furszyfer 16/9/2015
 */
@PluginInfo(createdBy = "Matias Furszyfer", maintainerMail = "matiasfurszyfer@gmail.com", platform = Platforms.CRYPTO_CURRENCY_PLATFORM, layer = Layers.DESKTOP_MODULE, plugin = Plugins.WALLET_MANAGER)

public class DesktopRuntimeEnginePluginRoot extends AbstractPlugin implements DesktopRuntimeManager {

    final String NAVIGATION_STRUCTURE_FILE_PATH = "navigation_structure";

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.EVENT_MANAGER)
    private EventManager eventManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_FILE_SYSTEM)
    private PluginFileSystem pluginFileSystem;

    /**
     * SubAppRuntimeManager Interface member variables.
     */
    List<FermatEventListener> listenersAdded = new ArrayList<>();

    /**
     * MAp of desktop identifier + runtimeDesktopObject
     */

    Map<String, DesktopObject> lstDesktops = new HashMap<>();

    /**
     * Last desktop-object
     */
    String lastDesktopObject;

    public DesktopRuntimeEnginePluginRoot() {
        super(new PluginVersionReference(new Version()));
    }


    @Override
    public void start() throws CantStartPluginException {
        try {
            /**
             * I will initialize the handling of com.bitdubai.platform events.
             */

            /**
             * At this time the only thing I can do is a factory reset. Once there should be a possibility to add
             * functionality based on wallets downloaded by users this wont be an option.
             * * *
             */
            if (!loadConfig()) {
                factoryReset();
                saveFactory();
            }

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

    private void saveFactory() {
        try {
            PluginTextFile pluginTextFile = pluginFileSystem.createTextFile(pluginId, "config", "desktop_runtime_config", FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
            pluginTextFile.setContent("true");
            pluginTextFile.persistToMedia();
        } catch (CantCreateFileException e) {
            e.printStackTrace();
        } catch (CantPersistFileException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean loadConfig() {
        String dev = null;
        try {
            PluginTextFile pluginTextFile = pluginFileSystem.getTextFile(pluginId, "config", "desktop_runtime_config", FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
            pluginTextFile.loadFromMedia();
            dev = pluginTextFile.getContent();
        } catch (Exception e) {
            //e.printStackTrace();
            dev = "false";
        }
        return dev.equals("true");

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

    /**
     * AppRuntime Interface implementation.
     */
    @Override
    public DesktopObject getLastDesktopObject() {
        if (lastDesktopObject != null) {
            return lstDesktops.get(lastDesktopObject);
        } else {
            return lstDesktops.get("main_desktop");
        }
    }

    @Override
    public DesktopObject getDesktopObject(String desktopObjectPublicKey) {
        //DesktopObject desktopObject = mapDesktops.get(desktopObjectType);
//        if (desktopObject != null) {
//            lastDesktopObject = desktopObjectType;
//            return desktopObject;
//        }
        DesktopObject desktopObject = null;
        if (!lstDesktops.isEmpty()) {
            desktopObject = lstDesktops.get(desktopObjectPublicKey);
            ;
        } else {
            desktopObject = (DesktopObject) getAppByPublicKey(desktopObjectPublicKey);
        }
        return desktopObject;
    }

    @Override
    public Map<String, DesktopObject> listDesktops() {
        if (lstDesktops.size() < 2) {
            createToolsDesktop();
        }
        return lstDesktops;
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

            FermatRuntimeFragment runtimeFragment; //= new FermatRuntimeFragment();
            //runtimeFragment.setType(Fragments.CWP_SHELL_LOGIN.getKey());
            //runtimeActivity.addFragment(Fragments.CWP_SHELL_LOGIN.getKey(), runtimeFragment);

            TitleBar runtimeTitleBar;
            SideMenu runtimeSideMenu;
            OptionsMenu runtimeOptionsMenu;
            MenuItem runtimeMenuItem;
            TabStrip runtimeTabStrip;
            StatusBar statusBar;
            Tab runtimeTab;
            FermatBottomNavigation fermatBottomNavigation;

            /**
             * Desktop CCP
             */

            createDesktop();
            Activity activity;
            FermatRuntimeFragment fragment;


//            runtimeDesktopObject = new RuntimeDesktopObject();
//            /**
//             * Add WalletManager fragment
//             */
//
//            // dmp_WALLET_MANAGER_FRAGMENT
//            fragment.setFragmentCode("CCPWMF");
//            activity.addFragment("CCPWMF",fragment);
//            runtimeDesktopObject.setStartActivity(activity.getType());
//
//            /**
//             * Add home subApps fragment
//             */
//
//            fragment = new FermatRuntimeFragment();
//            // dmp_SUB_APP_MANAGER_FRAGMENT
//            fragment.setFragmentCode("CCPSAMF");
//            activity.addFragment("CCPSAMF", fragment);


            /**
             * End Desktop CCP
             */
            createToolsDesktop();


            /**
             * End Desktop WPD
             */


            /**
             * Desktop DAP
             */

            runtimeDesktopObject = new RuntimeDesktopObject();
            runtimeDesktopObject.setType("DDAP");
//            lstDesktops.add(runtimeDesktopObject);
            runtimeDesktopObject.setStartActivity(Activities.DAP_DESKTOP);

            activity = new Activity();
            /**
             * set type home
             */
            //activity.setType(Activities.CWP_WALLET_MANAGER_MAIN);
            //activity.setType(Activities.dmp_DESKTOP_HOME);
            activity.setActivityType("DAPDHA");
            fragment = new FermatRuntimeFragment();

            /**
             * Add WalletManager fragment
             */

            // dmp_WALLET_MANAGER_FRAGMENT
            fragment.setFragmentCode("DAPWMF");
            activity.addFragment("DAPWMF", fragment);
            runtimeDesktopObject.addActivity(activity);

            /**
             * Add home subApps fragment
             */

            fragment = new FermatRuntimeFragment();
            // dmp_SUB_APP_MANAGER_FRAGMENT
            fragment.setFragmentCode("DAPSAMF");
            activity.addFragment("DAPSAMF", fragment);
            runtimeDesktopObject.addActivity(activity);

            /**
             * End Desktop DAP
             */

            /**
             * Desktop CBP
             */

            runtimeDesktopObject = new RuntimeDesktopObject();
            runtimeDesktopObject.setType("DCBP");
//            lstDesktops.add(runtimeDesktopObject);
            runtimeDesktopObject.setStartActivity(Activities.CBP_DESKTOP);

            activity = new Activity();
            /**
             * set type home
             */
            //activity.setType(Activities.CWP_WALLET_MANAGER_MAIN);
            //activity.setType(Activities.dmp_DESKTOP_HOME);
            activity.setActivityType("CBPDHA");
            fragment = new FermatRuntimeFragment();
            runtimeDesktopObject.addActivity(activity);

            /**
             * Add WalletManager fragment
             */

            // dmp_WALLET_MANAGER_FRAGMENT
            fragment.setFragmentCode("CBPWMF");
            activity.addFragment("CBPWMF", fragment);
            runtimeDesktopObject.addActivity(activity);

            /**
             * Add home subApps fragment
             */

            fragment = new FermatRuntimeFragment();
            // dmp_SUB_APP_MANAGER_FRAGMENT
            fragment.setFragmentCode("CBPSAMF");
            activity.addFragment("CBPSAMF", fragment);
            runtimeDesktopObject.addActivity(activity);

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

    private void createDesktop() {
        RuntimeDesktopObject runtimeDesktopObject;
        TitleBar runtimeTitleBar;
        FermatRuntimeFragment runtimeFragment;
        String publicKey = "main_desktop";

        runtimeDesktopObject = new RuntimeDesktopObject();
        runtimeDesktopObject.setAppPublicKey(publicKey);
        runtimeDesktopObject.setType("DCCP");
        lastDesktopObject = publicKey;

        runtimeDesktopObject.setStartActivity(Activities.CCP_DESKTOP);

        Activity activity = new Activity();
        activity.setActivityType(Activities.CCP_DESKTOP.getCode());
        activity.setType(Activities.CCP_DESKTOP);
        activity.setFullScreen(true);
        activity.setBottomNavigationMenu(new BottomNavigation());

//            Wizard wizard = new Wizard();
//            WizardPage wizardPage = new WizardPage();
//            wizardPage.setFragment(Fragments.WELCOME_WIZARD_FIRST_SCREEN_FRAGMENT.getKey());
//            wizard.addPage(wizardPage);
//
//            wizardPage = new WizardPage();
//            wizardPage.setFragment(Fragments.WELCOME_WIZARD_SECOND_SCREEN_FRAGMENT.getKey());
//            wizard.addPage(wizardPage);
//
//            wizardPage = new WizardPage();
//            wizardPage.setFragment(Fragments.WELCOME_WIZARD_THIRD_SCREEN_FRAGMENT.getKey());
//            wizard.addPage(wizardPage);
//
//            wizardPage = new WizardPage();
//            wizardPage.setFragment(Fragments.WELCOME_WIZARD_FOURTH_SCREEN_FRAGMENT.getKey());
//            wizard.addPage(wizardPage);
//
//            activity.addWizard(WizardTypes.DESKTOP_WELCOME_WIZARD.getKey(),wizard);


        /**
         * set type home
         */
        //activity.setType(Activities.CWP_WALLET_MANAGER_MAIN);
        //activity.setType(Activities.dmp_DESKTOP_HOME);
        //   activity.setActivityType("CCPDHA");
        FermatRuntimeFragment fragment = new FermatRuntimeFragment();
        fragment.setFragmentCode(Fragments.DESKTOP_APPS_MAIN.getKey());
        activity.addFragment(Fragments.DESKTOP_APPS_MAIN.getKey(), fragment);
        activity.setStartFragment(Fragments.DESKTOP_APPS_MAIN.getKey());
        runtimeDesktopObject.addActivity(activity);
        activity.setColor("#fff");


        // activity
        fragment = new FermatRuntimeFragment();
        fragment.setFragmentCode(Fragments.DESKTOP_P2P_MAIN.getKey());
        activity.addFragment(Fragments.DESKTOP_P2P_MAIN.getKey(), fragment);

        fragment = new FermatRuntimeFragment();
        fragment.setFragmentCode(Fragments.DESKTOP_SOCIAL_MAIN.getKey());
        activity.addFragment(Fragments.DESKTOP_SOCIAL_MAIN.getKey(), fragment);


        //

        /**
         * Wizard
         */
        activity = new Activity();
        activity.setActivityType(Activities.DESKTOP_WIZZARD_WELCOME.getCode());
        activity.setType(Activities.DESKTOP_WIZZARD_WELCOME);
        activity.setFullScreen(true);
        activity.setBackgroundColor("#ffffff");
        activity.setStartFragment(Fragments.WELCOME_WIZARD_FIRST_SCREEN_FRAGMENT.getKey());
        runtimeDesktopObject.setStartActivity(Activities.DESKTOP_WIZZARD_WELCOME);

        fragment = new FermatRuntimeFragment();
        fragment.setFragmentCode(Fragments.WELCOME_WIZARD_FIRST_SCREEN_FRAGMENT.getKey());
        activity.addFragment(Fragments.WELCOME_WIZARD_FIRST_SCREEN_FRAGMENT.getKey(), fragment);
        runtimeDesktopObject.addActivity(activity);


        //settings bitcoin network
        activity = new Activity();
        activity.setBackgroundColor("#000000");
        activity.setActivityType(Activities.DESKTOP_SETTING_FERMAT_NETWORK.getCode());
        activity.setType(Activities.DESKTOP_SETTING_FERMAT_NETWORK);
        activity.setStartFragment(Fragments.DESKTOP_SETTINGS.getKey());
        activity.setBackActivity(Activities.CCP_DESKTOP);
        activity.setBackPublicKey(publicKey);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setColor("#000000");
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setLabel("Network Settings");
        runtimeTitleBar.setLabelSize(18);
        runtimeTitleBar.setTitleColor("#ffffff");
        activity.setTitleBar(runtimeTitleBar);


        runtimeFragment = new FermatRuntimeFragment();
        runtimeFragment.setFragmentCode(Fragments.DESKTOP_SETTINGS.getKey());
        runtimeFragment.setBackCode(Fragments.DESKTOP_APPS_MAIN.getKey());
        activity.addFragment(Fragments.DESKTOP_SETTINGS.getKey(), runtimeFragment);
        runtimeDesktopObject.addActivity(activity);

        //settings import key
        activity = new Activity();
        activity.setBackgroundColor("#000000");
        activity.setActivityType(Activities.DESKTOP_SETTING_IMPORT_KEY.getCode());
        activity.setType(Activities.DESKTOP_SETTING_IMPORT_KEY);
        activity.setStartFragment(Fragments.DESKTOP_SETTING_IMPORT_KEY.getKey());
        activity.setBackActivity(Activities.CCP_DESKTOP);
        activity.setBackPublicKey(publicKey);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setColor("#000000");
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setLabel("Import Key");
        runtimeTitleBar.setLabelSize(18);
        runtimeTitleBar.setTitleColor("#ffffff");
        activity.setTitleBar(runtimeTitleBar);


        runtimeFragment = new FermatRuntimeFragment();
        runtimeFragment.setFragmentCode(Fragments.DESKTOP_SETTING_IMPORT_KEY.getKey());
        runtimeFragment.setBackCode(Fragments.DESKTOP_APPS_MAIN.getKey());
        activity.addFragment(Fragments.DESKTOP_SETTING_IMPORT_KEY.getKey(), runtimeFragment);
        runtimeDesktopObject.addActivity(activity);

        //settings export key
        activity = new Activity();
        activity.setBackgroundColor("#011000");
        activity.setActivityType(Activities.DESKTOP_SETTING_EXPORT_KEY.getCode());
        activity.setType(Activities.DESKTOP_SETTING_EXPORT_KEY);
        activity.setStartFragment(Fragments.DESKTOP_SETTING_EXPORT_KEY.getKey());
        activity.setBackActivity(Activities.CCP_DESKTOP);
        activity.setBackPublicKey(publicKey);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setColor("#011000");
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setLabel("Export Key");
        runtimeTitleBar.setLabelSize(18);
        runtimeTitleBar.setTitleColor("#ffffff");
        activity.setTitleBar(runtimeTitleBar);


        runtimeFragment = new FermatRuntimeFragment();
        runtimeFragment.setFragmentCode(Fragments.DESKTOP_SETTING_EXPORT_KEY.getKey());
        runtimeFragment.setBackCode(Fragments.DESKTOP_APPS_MAIN.getKey());
        activity.addFragment(Fragments.DESKTOP_SETTING_EXPORT_KEY.getKey(), runtimeFragment);
        runtimeDesktopObject.addActivity(activity);

        //more settings
        activity = new Activity();
        activity.setBackgroundColor("#011000");
        activity.setActivityType(Activities.DESKTOP_MORE_SETTINGS.getCode());
        activity.setType(Activities.DESKTOP_MORE_SETTINGS);
        activity.setStartFragment(Fragments.DESKTOP_MORE_SETTINGS.getKey());
        activity.setBackActivity(Activities.CCP_DESKTOP);
        activity.setBackPublicKey(publicKey);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setColor("#011000");
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setLabel("Settings");
        runtimeTitleBar.setLabelSize(18);
        runtimeTitleBar.setTitleColor("#ffffff");
        activity.setTitleBar(runtimeTitleBar);


        runtimeFragment = new FermatRuntimeFragment();
        runtimeFragment.setFragmentCode(Fragments.DESKTOP_MORE_SETTINGS.getKey());
        runtimeFragment.setBackCode(Fragments.DESKTOP_APPS_MAIN.getKey());
        activity.addFragment(Fragments.DESKTOP_MORE_SETTINGS.getKey(), runtimeFragment);
        runtimeDesktopObject.addActivity(activity);

        // community
        activity = new Activity();
        activity.setActivityType(Activities.DESKTOP_COMMUNITY_ACTIVITY.getCode());
        activity.setType(Activities.DESKTOP_COMMUNITY_ACTIVITY);
        activity.setStartFragment(Fragments.COMMUNITIES_FRAGMENT.getKey());
        activity.setBackActivity(Activities.CCP_DESKTOP);
        activity.setBackPublicKey(publicKey);
        activity.setFullScreen(true);
        activity.setBackgroundColor("#ffffff");
        activity.setColor("#ffffff");


        runtimeFragment = new FermatRuntimeFragment();
        runtimeFragment.setFragmentCode(Fragments.COMMUNITIES_FRAGMENT.getKey());
        runtimeFragment.setBackCode(Fragments.DESKTOP_APPS_MAIN.getKey());
        activity.addFragment(Fragments.COMMUNITIES_FRAGMENT.getKey(), runtimeFragment);
        runtimeDesktopObject.addActivity(activity);


        lstDesktops.put(publicKey, runtimeDesktopObject);
    }

    private void createToolsDesktop() {
        RuntimeDesktopObject runtimeDesktopObject;
        Activity activity;
        FermatRuntimeFragment fragment; /**
         * Desktop WPD
         */

        runtimeDesktopObject = new RuntimeDesktopObject();
        runtimeDesktopObject.setType("WPD");
        lstDesktops.put("sub_desktop", runtimeDesktopObject);
        runtimeDesktopObject.setStartActivity(Activities.WPD_DESKTOP);

        activity = new Activity();
        /**
         * set type home
         */
        //activity.setType(Activities.CWP_WALLET_MANAGER_MAIN);
        //activity.setType(Activities.dmp_DESKTOP_HOME);
        activity.setActivityType("WPD");

        /**
         * Add home subApps fragment
         */

        fragment = new FermatRuntimeFragment();
        // dmp_SUB_APP_MANAGER_FRAGMENT
        fragment.setFragmentCode("CCPSAMF");
        activity.addFragment("CCPSAMF", fragment);
        runtimeDesktopObject.addActivity(activity);
    }


    @Override
    public void recordNAvigationStructure(FermatStructure fermatStructure) {
        String publiKey = fermatStructure.getPublicKey();
        try {
            String navigationStructureXml = parseNavigationStructureXml(fermatStructure);
            String navigationStructureName = publiKey + ".xml";
            try {
                PluginTextFile newFile = pluginFileSystem.createTextFile(pluginId, NAVIGATION_STRUCTURE_FILE_PATH, navigationStructureName, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
                newFile.setContent(navigationStructureXml);
                newFile.persistToMedia();
            } catch (CantPersistFileException e) {
                e.printStackTrace();
                //throw new CantSetWalletFactoryProjectNavigationStructureException(CantSetWalletFactoryProjectNavigationStructureException.DEFAULT_MESSAGE, e, "Can't create or overwrite navigation structure file.", "");
            } catch (CantCreateFileException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
            //throw new CantSetWalletFactoryProjectNavigationStructureException(CantSetWalletFactoryProjectNavigationStructureException.DEFAULT_MESSAGE, e, "Can't convert navigation structure to xml format", "");
        }
    }

    private String parseNavigationStructureXml(FermatStructure walletNavigationStructure) {
        String xml = null;
        if (walletNavigationStructure != null) {
            xml = XMLParser.parseObject(walletNavigationStructure);
        }
        return xml;
    }

    @Override
    public FermatStructure getLastApp() {
        FermatStructure fermatStructure = null;
        try {
            if (!lstDesktops.isEmpty()) {
                if (lastDesktopObject != null)
                    fermatStructure = lstDesktops.get(lastDesktopObject);
                else {
                    fermatStructure = lstDesktops.get("main_desktop");
                }
            } else {
                fermatStructure = getAppByPublicKey("main_desktop");
                if (fermatStructure != null) {
                    if (!lstDesktops.containsKey("main_desktop")) {
                        lstDesktops.put("main_desktop", (DesktopObject) fermatStructure);
                    }
                }
            }
        } catch (Exception e) {
            createDesktop();
            fermatStructure = lstDesktops.get("main_desktop");
        }
        return fermatStructure;
    }

    @Override
    public FermatStructure getAppByPublicKey(String appPublicKey) {
        DesktopObject fermatStructure = null;
        if (appPublicKey != null) {
            if (lstDesktops.containsKey(appPublicKey)) {
                fermatStructure = lstDesktops.get(appPublicKey);
            } else {
                String navigationStructureName = appPublicKey + ".xml";
                try {
                    PluginTextFile pluginTextFile = pluginFileSystem.getTextFile(pluginId, NAVIGATION_STRUCTURE_FILE_PATH, navigationStructureName, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
                    pluginTextFile.loadFromMedia();
                    String xml = pluginTextFile.getContent();
                    fermatStructure = (DesktopObject) XMLParser.parseXML(xml, fermatStructure);
                    lstDesktops.put(appPublicKey, fermatStructure);
                } catch (FileNotFoundException e) {
                    try {
                        PluginTextFile layoutFile = pluginFileSystem.createTextFile(pluginId, NAVIGATION_STRUCTURE_FILE_PATH, navigationStructureName, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
                        layoutFile.setContent("");

                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }

                    createDesktop();
                    return lstDesktops.get(appPublicKey);
                } catch (CantCreateFileException e) {
                    e.printStackTrace();
                } catch (CantLoadFileException e) {
                    e.printStackTrace();
                } catch (LibraryException e){
                    e.printStackTrace();
                    createDesktop();
                    createToolsDesktop();
                    fermatStructure = lstDesktops.get(appPublicKey);
                }
            }
        }
        return fermatStructure;
    }

    @Override
    public Set<String> getListOfAppsPublicKey() {
        return lstDesktops.keySet();
    }
}
