package com.bitdubai.fermat_wpd_plugin.layer.engine.wallet_runtime.developer.bitdubai.version_1;

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
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletCategory;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletType;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Activity;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Fragment;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Header;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.MainMenu;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.MenuItem;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.SideMenu;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.StatusBar;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Tab;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.TabStrip;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.TitleBar;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.WalletNavigationStructure;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Wizard;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.WizardPage;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Fragments;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.WizardPageTypes;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.WizardTypes;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;
import com.bitdubai.fermat_api.layer.dmp_network_service.CantCheckResourcesException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantLoadFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.enums.EventType;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_engine.wallet_runtime.exceptions.CantRemoveWalletNavigationStructureException;
import com.bitdubai.fermat_wpd_api.layer.wpd_engine.wallet_runtime.exceptions.WalletRuntimeExceptions;
import com.bitdubai.fermat_wpd_api.layer.wpd_engine.wallet_runtime.interfaces.WalletRuntimeManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_engine.wallet_runtime.interfaces.XML;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_factory.exceptions.CantGetWalletFactoryProjectNavigationStructureException;
import com.bitdubai.fermat_wpd_plugin.layer.engine.wallet_runtime.developer.bitdubai.version_1.event_handlers.WalletClosedEventHandler;
import com.bitdubai.fermat_wpd_plugin.layer.engine.wallet_runtime.developer.bitdubai.version_1.event_handlers.WalletInstalledEventHandler;
import com.bitdubai.fermat_wpd_plugin.layer.engine.wallet_runtime.developer.bitdubai.version_1.event_handlers.WalletNavigationStructureDownloadedHandler;
import com.bitdubai.fermat_wpd_plugin.layer.engine.wallet_runtime.developer.bitdubai.version_1.event_handlers.WalletOpenedEventHandler;
import com.bitdubai.fermat_wpd_plugin.layer.engine.wallet_runtime.developer.bitdubai.version_1.event_handlers.WalletUnnInstalledEventHandler;
import com.bitdubai.fermat_wpd_plugin.layer.engine.wallet_runtime.developer.bitdubai.version_1.exceptions.CantFactoryReset;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


/**
 * Created by Matias Furszyfer on 23.07.15.
 */

public class WalletRuntimeEnginePluginRoot extends AbstractPlugin implements
        WalletRuntimeManager,
        XML {


    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_FILE_SYSTEM)
    private PluginFileSystem pluginFileSystem;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER)
    private ErrorManager errorManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.EVENT_MANAGER)
    private EventManager eventManager;


    /**
     * Path of xml files
     */
    final String NAVIGATION_STRUCTURE_FILE_PATH = "navigation_structure";

    List<FermatEventListener> listenersAdded = new ArrayList<>();

    /**
     * WalletRuntimeManager Interface member variables.
     */

    WalletNavigationStructure walletNavigationStructureOpen;

    public WalletRuntimeEnginePluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

    @Override
    public void start() throws CantStartPluginException {
        /**
         * I will initialize the handling of com.bitdubai.platform events.
         */


        FermatEventListener fermatEventListener;
        FermatEventHandler fermatEventHandler;

        fermatEventListener = eventManager.getNewListener(EventType.WALLET_OPENED);
        fermatEventHandler = new WalletOpenedEventHandler();
        ((WalletOpenedEventHandler) fermatEventHandler).setWalletRuntimeManager(this);
        fermatEventListener.setEventHandler(fermatEventHandler);
        eventManager.addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);

        fermatEventListener = eventManager.getNewListener(EventType.WALLET_CLOSED);
        fermatEventHandler = new WalletClosedEventHandler();
        ((WalletClosedEventHandler) fermatEventHandler).setWalletRuntimeManager(this);
        fermatEventListener.setEventHandler(fermatEventHandler);
        eventManager.addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);

        fermatEventListener = eventManager.getNewListener(EventType.WALLET_INSTALLED);
        fermatEventHandler = new WalletInstalledEventHandler();
        ((WalletInstalledEventHandler) fermatEventHandler).setWalletRuntimeManager(this);
        fermatEventListener.setEventHandler(fermatEventHandler);
        eventManager.addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);

        fermatEventListener = eventManager.getNewListener(EventType.WALLET_UNINSTALLED);
        fermatEventHandler = new WalletUnnInstalledEventHandler();
        ((WalletUnnInstalledEventHandler) fermatEventHandler).setWalletRuntimeManager(this);
        fermatEventListener.setEventHandler(fermatEventHandler);
        eventManager.addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);

        FermatEventListener fermatEventListenerStructureDownloaded = eventManager.getNewListener(EventType.WALLET_RESOURCES_NAVIGATION_STRUCTURE_DOWNLOADED);
        FermatEventHandler fermatEventHandlerStructureDownloaded = new WalletNavigationStructureDownloadedHandler(this);
        fermatEventListenerStructureDownloaded.setEventHandler(fermatEventHandlerStructureDownloaded);
        eventManager.addListener(fermatEventListenerStructureDownloaded);
        listenersAdded.add(fermatEventListenerStructureDownloaded);

        /**
         * At this time the only thing I can do is a factory reset. Once there should be a possibility to add
         * functionality based on wallets downloaded by users this wont be an option.
         * * *
         *
         */
        try {

            loadLastWalletNavigationStructure();
            factoryReset();

        } catch (CantFactoryReset ex) {
            String message = CantStartPluginException.DEFAULT_MESSAGE;
            FermatException cause = ex;
            String context = "WalletNavigationStructure Runtime Start";

            String possibleReason = "Some null definition";
            throw new CantStartPluginException(message, cause, context, possibleReason);
        }

        this.serviceStatus = ServiceStatus.STARTED;

    }

    @Override
    public void stop() {

        /**
         * I will remove all the event listeners registered with the event manager.
         */

        for (FermatEventListener fermatEventListener : listenersAdded) {
            eventManager.removeListener(fermatEventListener);
        }

        listenersAdded.clear();
    }

    /**
     * WalletRuntime Interface implementation.
     */

    @Override
    public void recordNavigationStructure(String xmlText, String linkToRepo, String name, UUID skinId, String walletPublicKey) throws CantCheckResourcesException {
        //TODO: pido el navigationStrucutre del network service que sea y lo mando ahí
        //setNavigationStructureXml(walletNavigationStructure);


        // For testing purpose

        WalletNavigationStructure walletNavigationStructure = new WalletNavigationStructure();

        //this.walletNavigationStructureOpen=(WalletNavigationStructure)XMLParser.parseXML(xmlText,walletNavigationStructure);

        PluginTextFile layoutFile = null;

        //String filename= skinId.toString()+"_"+name;
        String navigationStructureName = walletPublicKey + ".xml";

        try {

            layoutFile = pluginFileSystem.getTextFile(pluginId, NAVIGATION_STRUCTURE_FILE_PATH, navigationStructureName, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);

        } catch (CantCreateFileException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            try {

                layoutFile = pluginFileSystem.createTextFile(pluginId, NAVIGATION_STRUCTURE_FILE_PATH, navigationStructureName, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
                layoutFile.setContent(xmlText);
                layoutFile.persistToMedia();

            } catch (CantCreateFileException cantCreateFileException) {
                throw new CantCheckResourcesException("CAN'T CHECK REQUESTED RESOURCES", cantCreateFileException, "Error persist image file " + navigationStructureName, "");
            } catch (CantPersistFileException cantPersistFileException) {
                throw new CantCheckResourcesException("CAN'T CHECK REQUESTED RESOURCES", cantPersistFileException, "Error persist image file " + navigationStructureName, "");
            }
        }
    }

    @Override
    public boolean removeNavigationStructure(String publicKey) throws CantRemoveWalletNavigationStructureException {
        removeNavigationStructureXml(publicKey);
        return true;
    }

    @Override
    public WalletNavigationStructure getNavigationStructureFromWallet(String publicKey) {
        return getNavigationStructure(publicKey);
    }

    @Override
    public WalletNavigationStructure getLastWallet() {
        return walletNavigationStructureOpen;
    }


    @Override
    public WalletNavigationStructure getWallet(String publicKey) throws WalletRuntimeExceptions {
        //TODO: acá hay que poner una excepcion si no encuentra la wallet
        try {
            walletNavigationStructureOpen = getNavigationStructure(publicKey);
            return walletNavigationStructureOpen;
        } catch (Exception e) {
            throw new WalletRuntimeExceptions("WALLET RUNTIME GET WALLET", e, "wallet runtime not found the navigation structure for: " + publicKey, "");
        }

    }

    /**
     * Here is where I actually generate the factory structure of the APP. This method is also useful to reset to the
     * factory structure.
     */
    private void factoryReset() throws CantFactoryReset {

        Activity runtimeActivity;
        Fragment runtimeFragment;
        WalletNavigationStructure runtimeWalletNavigationStructure;
        TitleBar runtimeTitleBar;
        SideMenu runtimeSideMenu;
        MainMenu runtimeMainMenu;
        MenuItem runtimeMenuItem;
        TabStrip runtimeTabStrip;
        Header runtimeHeader;
        StatusBar runtimeStatusBar;

        Tab runtimeTab;

        String publicKey;


        /**
         * Asset issuer
         */

        runtimeWalletNavigationStructure = new WalletNavigationStructure();
        runtimeWalletNavigationStructure.setWalletCategory(WalletCategory.REFERENCE_WALLET.getCode());
        runtimeWalletNavigationStructure.setWalletType(WalletType.REFERENCE.getCode());
        publicKey = "asset_issuer";
        runtimeWalletNavigationStructure.setPublicKey(publicKey);
        //listWallets.put(publicKey, runtimeWalletNavigationStructure);


        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CWP_WALLET_RUNTIME_WALLET_BASIC_WALLET_BITDUBAI_VERSION_1_MAIN);
        runtimeActivity.setColor("#8bba9e");
        runtimeWalletNavigationStructure.addActivity(runtimeActivity);
        runtimeWalletNavigationStructure.setStartActivity(runtimeActivity.getType());

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("asset issuer wallet");
        runtimeTitleBar.setLabelSize(16);

        runtimeActivity.setTitleBar(runtimeTitleBar);
        runtimeActivity.setColor("#72af9c");

        runtimeStatusBar = new com.bitdubai.fermat_api.layer.all_definition.navigation_structure.StatusBar();
        runtimeStatusBar.setColor("#72af9c");

        runtimeActivity.setStatusBar(runtimeStatusBar);


        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.DAP_WALLET_ASSET_ISSUER_MAIN_ACTIVITY.getKey());
        runtimeActivity.addFragment(Fragments.DAP_WALLET_ASSET_ISSUER_MAIN_ACTIVITY.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment("DWAIMA");


        setNavigationStructureXml(runtimeWalletNavigationStructure);
        //WalletNavigationStructure walletNavigationStructure= getNavigationStructure(publicKey);

        /**
         * Asset User
         */
        runtimeWalletNavigationStructure = new WalletNavigationStructure();
        runtimeWalletNavigationStructure.setWalletCategory(WalletCategory.REFERENCE_WALLET.getCode());
        runtimeWalletNavigationStructure.setWalletType(WalletType.REFERENCE.getCode());
        publicKey = "asset_user";
        runtimeWalletNavigationStructure.setPublicKey(publicKey);
        //listWallets.put(publicKey, runtimeWalletNavigationStructure);


        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CWP_WALLET_RUNTIME_WALLET_BASIC_WALLET_BITDUBAI_VERSION_1_MAIN);
        runtimeActivity.setColor("#8bba9e");
        runtimeWalletNavigationStructure.addActivity(runtimeActivity);
        runtimeWalletNavigationStructure.setStartActivity(runtimeActivity.getType());

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("asset user wallet");
        runtimeTitleBar.setLabelSize(16);

        runtimeActivity.setTitleBar(runtimeTitleBar);
        runtimeActivity.setColor("#72af9c");

        runtimeStatusBar = new com.bitdubai.fermat_api.layer.all_definition.navigation_structure.StatusBar();
        runtimeStatusBar.setColor("#72af9c");

        runtimeActivity.setStatusBar(runtimeStatusBar);


        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.DAP_WALLET_ASSET_USER_MAIN_ACTIVITY.getKey());
        runtimeActivity.addFragment(Fragments.DAP_WALLET_ASSET_USER_MAIN_ACTIVITY.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment("DWUIMA");


        setNavigationStructureXml(runtimeWalletNavigationStructure);

        /**
         * Asset Redeem Point
         */
        runtimeWalletNavigationStructure = new WalletNavigationStructure();
        runtimeWalletNavigationStructure.setWalletCategory(WalletCategory.REFERENCE_WALLET.getCode());
        runtimeWalletNavigationStructure.setWalletType(WalletType.REFERENCE.getCode());
        publicKey = "redeem_point";
        runtimeWalletNavigationStructure.setPublicKey(publicKey);
        //listWallets.put(publicKey, runtimeWalletNavigationStructure);


        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CWP_WALLET_RUNTIME_WALLET_BASIC_WALLET_BITDUBAI_VERSION_1_MAIN);
        runtimeActivity.setColor("#8bba9e");
        runtimeWalletNavigationStructure.addActivity(runtimeActivity);
        runtimeWalletNavigationStructure.setStartActivity(runtimeActivity.getType());

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("asset redeem point wallet");
        runtimeTitleBar.setLabelSize(16);

        runtimeActivity.setTitleBar(runtimeTitleBar);
        runtimeActivity.setColor("#72af9c");

        runtimeStatusBar = new com.bitdubai.fermat_api.layer.all_definition.navigation_structure.StatusBar();
        runtimeStatusBar.setColor("#72af9c");

        runtimeActivity.setStatusBar(runtimeStatusBar);


        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.DAP_WALLET_REDEEM_POINT_MAIN_ACTIVITY.getKey());
        runtimeActivity.addFragment(Fragments.DAP_WALLET_REDEEM_POINT_MAIN_ACTIVITY.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment("DWRPMA");


        setNavigationStructureXml(runtimeWalletNavigationStructure);

        /**
         * CRYPTO BROKER WALLET
         */
        runtimeWalletNavigationStructure = createCryptoBrokerWalletNavigationStructure();
        setNavigationStructureXml(runtimeWalletNavigationStructure);

        /**
         * CRYPTO CUSTOMER WALLET
         */
        runtimeWalletNavigationStructure = createCryptoCustomerWalletNavigationStructure();
        setNavigationStructureXml(runtimeWalletNavigationStructure);

        /**
         * fin asset issuer
         */
        //try{

//            Activity runtimeActivity;
//            Fragment runtimeFragment;
//            WalletNavigationStructure runtimeWalletNavigationStructure;
//            TitleBar runtimeTitleBar;
//            SideMenu runtimeSideMenu;
//            MainMenu runtimeMainMenu;
//            MenuItem runtimeMenuItem;
//            TabStrip runtimeTabStrip;
//            StatusBar runtimeStatusBar;
//
//            Tab runtimeTab;
//
//            String publicKey;
//
//
//            /**
//             * WalletNavigationStructure Kids definition.
//             * */
//
//
//            runtimeWalletNavigationStructure = new WalletNavigationStructure();
//            //   runtimeSubApp.addWallet(runtimeWalletNavigationStructure);
//            publicKey="kids";
//            listWallets.put(publicKey, runtimeWalletNavigationStructure);
//            runtimeWalletNavigationStructure.setPublicKey(publicKey);
//
//            runtimeActivity= new Activity();
//            runtimeActivity.setType(Activities.CWP_WALLET_RUNTIME_WALLET_AGE_KIDS_ALL_BITDUBAI_VERSION_1_MAIN);
//            runtimeWalletNavigationStructure.addActivity(runtimeActivity);
//
//            runtimeTitleBar = new TitleBar();
//            runtimeTitleBar.setLabel("Kids Wallet");
//            runtimeActivity.setTitleBar(runtimeTitleBar);
//            runtimeActivity.setColor("#84DCF5");
//
//            runtimeSideMenu = new SideMenu();
//
//            runtimeMenuItem = new MenuItem();
//            runtimeMenuItem.setLabel("Menu item 1");
//            runtimeMenuItem.setLinkToActivity(Activities.CWP_WALLET_RUNTIME_WALLET_AGE_KIDS_ALL_BITDUBAI_VERSION_1_MAIN); // Solo es un ej.
//            runtimeSideMenu.addMenuItem(runtimeMenuItem);
//
//            runtimeActivity.setSideMenu(runtimeSideMenu);
//
//            runtimeTabStrip = new TabStrip();
//
//            runtimeTab = new Tab();
//            runtimeTab.setLabel("Profile");
//            runtimeTab.setFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_AGE_KIDS_ALL_BITDUBAI_PROFILE);
//            runtimeTabStrip.addTab(runtimeTab);
//
//            runtimeTab = new Tab();
//            runtimeTab.setLabel("Desktop");
//            runtimeTab.setFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_AGE_KIDS_ALL_BITDUBAI_DESKTOP);
//            runtimeTabStrip.addTab(runtimeTab);
//
//            runtimeTab = new Tab();
//            runtimeTab.setLabel("Contacts");
//            runtimeTab.setFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_AGE_KIDS_ALL_BITDUBAI_CONTACTS);
//            runtimeTabStrip.addTab(runtimeTab);
//
//            runtimeTab = new Tab();
//            runtimeTab.setLabel("Community");
//            runtimeTab.setFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_AGE_KIDS_ALL_BITDUBAI_COMMUNITY);
//            runtimeTabStrip.addTab(runtimeTab);
//
//
//            runtimeTabStrip.setDividerColor(0xFFFFFFFF);
//
//
//            runtimeActivity.setTabStrip(runtimeTabStrip);
//
//            runtimeFragment = new Fragment();
//            runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_AGE_KIDS_ALL_BITDUBAI_PROFILE);
//            runtimeActivity.addFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_AGE_KIDS_ALL_BITDUBAI_PROFILE,runtimeFragment);
//
//            runtimeFragment = new Fragment();
//            runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_AGE_KIDS_ALL_BITDUBAI_DESKTOP);
//            runtimeActivity.addFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_AGE_KIDS_ALL_BITDUBAI_DESKTOP,runtimeFragment);
//
//            runtimeFragment = new Fragment();
//            runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_AGE_KIDS_ALL_BITDUBAI_CONTACTS);
//            runtimeActivity.addFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_AGE_KIDS_ALL_BITDUBAI_CONTACTS,runtimeFragment);
//
//            runtimeFragment = new Fragment();
//            runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_AGE_KIDS_ALL_BITDUBAI_COMMUNITY);
//            runtimeActivity.addFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_AGE_KIDS_ALL_BITDUBAI_COMMUNITY,runtimeFragment);
//
//
//            /**
//             * End of WalletNavigationStructure Kids fragments.
//             * */
//
//            /**
//             * Fermat Bitcoin Reference Walletc definition.
//             *
//             * Structure:
//             *
//             * TYPE: CWP_WALLET_RUNTIME_WALLET_BASIC_WALLET_BITDUBAI_VERSION_1_MAIN
//             *
//             * TITLE: Fermat Bitcoin WalletNavigationStructure
//             *
//             * TAB STRIP:
//             *      *BALANCE - CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_BALANCE
//             *      *SEND - CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_SEND
//             *      *RECEIVE - CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_RECEIVE
//             *      *TRANSACTIONS - CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_TRANSACTIONS
//             *      *CONTACTS - CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_CONTACTS
//             *      *DATABASE TOOLS - CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_DATABASE_TOOLS
//             *      *LOG TOOLS - CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_LOG_TOOLS
//             */
//
//
//            runtimeWalletNavigationStructure = new WalletNavigationStructure();
//            publicKey="reference_wallet";
//            runtimeWalletNavigationStructure.setPublicKey(publicKey);
//            listWallets.put(publicKey, runtimeWalletNavigationStructure);
//
//            runtimeActivity= new Activity();
//            runtimeActivity.setType(Activities.CWP_WALLET_RUNTIME_WALLET_BASIC_WALLET_BITDUBAI_VERSION_1_MAIN);
//            runtimeActivity.setColor("#8bba9e");
//            runtimeWalletNavigationStructure.addActivity(runtimeActivity);
//            runtimeWalletNavigationStructure.setStartActivity(runtimeActivity.getType());
//
//            runtimeTitleBar = new TitleBar();
//            runtimeTitleBar.setLabel("Fermat Bitcoin Reference Wallet");
//
//            runtimeActivity.setTitleBar(runtimeTitleBar);
//            runtimeActivity.setColor("#72af9c");
//            //runtimeActivity.setColor("#d07b62");
//
//
//            runtimeStatusBar = new com.bitdubai.fermat_api.layer.all_definition.navigation_structure.StatusBar();
//            runtimeStatusBar.setColor("#72af9c");
//
//            runtimeActivity.setStatusBar(runtimeStatusBar);
//
//
//            runtimeTabStrip = new TabStrip();
//
//            runtimeTabStrip.setTabsColor("#8bba9e");
//
//            runtimeTabStrip.setTabsTextColor("#FFFFFF");
//
//            runtimeTabStrip.setTabsIndicateColor("#72af9c");
//
//            runtimeTab = new Tab();
//            runtimeTab.setLabel("Balance");
//            runtimeTab.setFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_BALANCE);
//            runtimeTabStrip.addTab(runtimeTab);
//
//            runtimeTab = new Tab();
//            runtimeTab.setLabel("Send");
//            runtimeTab.setFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_SEND);
//            runtimeTabStrip.addTab(runtimeTab);
//
//            runtimeTab = new Tab();
//            runtimeTab.setLabel("Receive");
//            runtimeTab.setFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_RECEIVE);
//            runtimeTabStrip.addTab(runtimeTab);
//
//            runtimeTab = new Tab();
//            runtimeTab.setLabel("Transactions");
//            runtimeTab.setFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_TRANSACTIONS);
//            runtimeTabStrip.addTab(runtimeTab);
//
//            runtimeTab = new Tab();
//            runtimeTab.setLabel("Contacts");
//            runtimeTab.setFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_CONTACTS);
//            runtimeTabStrip.addTab(runtimeTab);
//
//
//
//
//
//            runtimeTabStrip.setDividerColor(0x72af9c);
//            //runtimeTabStrip.setBackgroundColor("#72af9c");
//            runtimeActivity.setTabStrip(runtimeTabStrip);
//
//            runtimeFragment = new Fragment();
//            runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_BALANCE);
//            runtimeActivity.addFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_BALANCE,runtimeFragment);
//
//
//            runtimeFragment = new Fragment();
//            runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_SEND);
//            runtimeActivity.addFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_SEND,runtimeFragment);
//
//
//            runtimeFragment = new Fragment();
//            runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_RECEIVE);
//            runtimeActivity.addFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_RECEIVE,runtimeFragment);
//
//            runtimeFragment = new Fragment();
//            runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_TRANSACTIONS);
//            runtimeActivity.addFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_TRANSACTIONS,runtimeFragment);
//
//            runtimeFragment = new Fragment();
//            runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_CONTACTS);
//            runtimeActivity.addFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_CONTACTS,runtimeFragment);
//
//
//            // Testing purpose Mati
//            //setNavigationStructureXml(runtimeWalletNavigationStructure);
//
//            //getNavigationStructure("fasf");
//
//            //WalletRuntimeEngineDatabaseFactory walletRuntimeEngineDatabaseFactory = new WalletRuntimeEngineDatabaseFactory();
//
//            //WalletRuntimeNavigationStructureDao walletRuntimeNavigationStructureDao = new WalletRuntimeNavigationStructureDao();
//
//
//            /**
//             * End of WalletNavigationStructure basic fragments.
//             */
//
//
//
//
//
//            // WalletNavigationStructure adults
//
//            runtimeActivity= new Activity();
//            runtimeActivity.setType(Activities.CWP_WALLET_STORE_MAIN);
//            //   runtimeSubApp.addActivity(runtimeActivity);
//
//            runtimeFragment = new Fragment();
//            runtimeFragment.setType(Fragments.CWP_WALLET_STORE_MAIN);
//            runtimeActivity.addFragment(Fragments.CWP_WALLET_STORE_MAIN,runtimeFragment);
//
//            runtimeWalletNavigationStructure = new WalletNavigationStructure();
//            // runtimeSubApp.addWallet(runtimeWalletNavigationStructure);
//            publicKey="adults_wallet";
//            runtimeWalletNavigationStructure.setPublicKey(publicKey);
//            listWallets.put(publicKey, runtimeWalletNavigationStructure);
//
//            runtimeActivity= new Activity();
//            runtimeActivity.setType(Activities.CWP_WALLET_ADULTS_ALL_MAIN);
//            //runtimeSubApp.addActivity(runtimeActivity);
//
//            runtimeTitleBar = new TitleBar();
//            runtimeTitleBar.setLabel("Adults wallet");
//            runtimeActivity.setTitleBar(runtimeTitleBar);
//            runtimeActivity.setColor("#F0E173");
//
//            runtimeSideMenu = new SideMenu();
//
//            runtimeMenuItem = new MenuItem();
//            runtimeMenuItem.setLabel("Contacts");
//            runtimeMenuItem.setLinkToActivity(Activities.CWP_WALLET_RUNTIME_ADULTS_ALL_CONTACTS);
//            runtimeSideMenu.addMenuItem(runtimeMenuItem);
//
//            runtimeMenuItem = new MenuItem();
//            runtimeMenuItem.setLabel("Accounts");
//            runtimeMenuItem.setLinkToActivity(Activities.CWP_WALLET_RUNTIME_ADULTS_ALL_ACCOUNTS);
//            runtimeSideMenu.addMenuItem(runtimeMenuItem);
//
//            runtimeMenuItem = new MenuItem();
//            runtimeMenuItem.setLabel("Banks");
//            runtimeMenuItem.setLinkToActivity(Activities.CWP_WALLET_RUNTIME_ADULTS_ALL_BANKS);
//            runtimeSideMenu.addMenuItem(runtimeMenuItem);
//
//            runtimeMenuItem = new MenuItem();
//            runtimeMenuItem.setLabel("Coupons");
//            runtimeMenuItem.setLinkToActivity(Activities.CWP_WALLET_RUNTIME_ADULTS_ALL_COUPONS);
//            runtimeSideMenu.addMenuItem(runtimeMenuItem);
//
//            runtimeMenuItem = new MenuItem();
//            runtimeMenuItem.setLabel("Discounts");
//            runtimeMenuItem.setLinkToActivity(Activities.CWP_WALLET_RUNTIME_ADULTS_ALL_DISCOUNTS);
//            runtimeSideMenu.addMenuItem(runtimeMenuItem);
//
//            runtimeMenuItem = new MenuItem();
//            runtimeMenuItem.setLabel("Vouchers");
//            runtimeMenuItem.setLinkToActivity(Activities.CWP_WALLET_RUNTIME_ADULTS_ALL_VOUCHERS);
//            runtimeSideMenu.addMenuItem(runtimeMenuItem);
//
//            runtimeMenuItem = new MenuItem();
//            runtimeMenuItem.setLabel("Gift Cards");
//            runtimeMenuItem.setLinkToActivity(Activities.CWP_WALLET_RUNTIME_ADULTS_ALL_GIFT_CARDS);
//            runtimeSideMenu.addMenuItem(runtimeMenuItem);
//
//            runtimeMenuItem = new MenuItem();
//            runtimeMenuItem.setLabel("Clones");
//            runtimeMenuItem.setLinkToActivity(Activities.CWP_WALLET_RUNTIME_ADULTS_ALL_CLONES);
//            runtimeSideMenu.addMenuItem(runtimeMenuItem);
//
//            runtimeMenuItem = new MenuItem();
//            runtimeMenuItem.setLabel("Childs");
//            runtimeMenuItem.setLinkToActivity(Activities.CWP_WALLET_RUNTIME_ADULTS_ALL_CHILDS);
//            runtimeSideMenu.addMenuItem(runtimeMenuItem);
//
//            runtimeMenuItem = new MenuItem();
//            runtimeMenuItem.setLabel("Exit");
//            runtimeMenuItem.setLinkToActivity(Activities.CWP_WALLET_MANAGER_MAIN);
//            runtimeSideMenu.addMenuItem(runtimeMenuItem);
//
//            runtimeActivity.setSideMenu(runtimeSideMenu);
//
//            runtimeTabStrip = new TabStrip();
//            runtimeTab = new Tab();
//            runtimeTab.setLabel("Home");
//            runtimeTab.setFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_HOME);
//            runtimeTabStrip.addTab(runtimeTab);
//            runtimeTab = new Tab();
//            runtimeTab.setLabel("Balance");
//            runtimeTab.setFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_BALANCE);
//            runtimeTabStrip.addTab(runtimeTab);
//            runtimeTab = new Tab();
//            runtimeTab.setLabel("Send");
//            runtimeTab.setFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SEND);
//            runtimeTabStrip.addTab(runtimeTab);
//            runtimeTab = new Tab();
//            runtimeTab.setLabel("Receive");
//            runtimeTab.setFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_RECEIVE);
//            runtimeTabStrip.addTab(runtimeTab);
//            runtimeTab = new Tab();
//            runtimeTab.setLabel("Shops");
//            runtimeTab.setFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOPS);
//            runtimeTabStrip.addTab(runtimeTab);
//            runtimeTab = new Tab();
//            runtimeTab.setLabel("Refill");
//            runtimeTab.setFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_REFFIL);
//            runtimeTabStrip.addTab(runtimeTab);
//            runtimeTab = new Tab();
//            runtimeTab.setLabel("Discounts");
//            runtimeTab.setFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_DISCOUNTS);
//            runtimeTabStrip.addTab(runtimeTab);
//            runtimeTabStrip.setDividerColor(0xFFFFFFFF);
//            runtimeActivity.setTabStrip(runtimeTabStrip);
//
//            runtimeFragment = new Fragment();
//            runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_HOME);
//            runtimeActivity.addFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_HOME,runtimeFragment);
//
//            runtimeFragment = new Fragment();
//            runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_BALANCE);
//            runtimeActivity.addFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_BALANCE,runtimeFragment);
//
//            runtimeFragment = new Fragment();
//            runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SEND);
//            runtimeActivity.addFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SEND,runtimeFragment);
//
//            runtimeFragment = new Fragment();
//            runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_RECEIVE);
//            runtimeActivity.addFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_RECEIVE,runtimeFragment);
//
//            runtimeFragment = new Fragment();
//            runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOPS);
//            runtimeActivity.addFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOPS,runtimeFragment);
//
//            runtimeFragment = new Fragment();
//            runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_REFFIL);
//            runtimeActivity.addFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_REFFIL,runtimeFragment);
//
//            runtimeFragment = new Fragment();
//            runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_DISCOUNTS);
//            runtimeActivity.addFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_DISCOUNTS,runtimeFragment);
//
//            runtimeActivity= new Activity();
//            runtimeActivity.setType(Activities.CWP_WALLET_RUNTIME_ADULTS_ALL_CONTACTS_CHAT);
//            runtimeWalletNavigationStructure.addActivity(runtimeActivity);
//
//            runtimeTitleBar = new TitleBar();
//            runtimeTitleBar.setLabel("");
//            runtimeActivity.setTitleBar(runtimeTitleBar);
//            runtimeActivity.setColor("#F0E173");
//
//            runtimeTabStrip = new TabStrip();
//            runtimeTab = new Tab();
//            runtimeTab.setLabel("");
//            runtimeTabStrip.addTab(runtimeTab);
//            runtimeActivity.setTabStrip(runtimeTabStrip);
//
//            runtimeFragment = new Fragment();
//            runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_CONTACTS_CHAT);
//            runtimeActivity.addFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_CONTACTS_CHAT,runtimeFragment);
//
//
//
//            runtimeActivity= new Activity();
//            runtimeActivity.setType(Activities.CWP_WALLET_RUNTIME_ADULTS_ALL_CONTACTS);
//            runtimeWalletNavigationStructure.addActivity(runtimeActivity);
//
//            runtimeTitleBar = new TitleBar();
//            runtimeTitleBar.setLabel("Contacts");
//            runtimeActivity.setTitleBar(runtimeTitleBar);
//            runtimeActivity.setColor("#F0E173");
//
//            runtimeTabStrip = new TabStrip();
//            runtimeTab = new Tab();
//            runtimeTab.setLabel("");
//            runtimeTabStrip.addTab(runtimeTab);
//            runtimeActivity.setTabStrip(runtimeTabStrip);
//
//            runtimeFragment = new Fragment();
//            runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_CONTACTS);
//            runtimeActivity.addFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_CONTACTS,runtimeFragment);
//
//
//            runtimeActivity= new Activity();
//            runtimeActivity.setType(Activities.CWP_WALLET_RUNTIME_ADULTS_ALL_AVAILABLE_BALANCE);
//            runtimeWalletNavigationStructure.addActivity(runtimeActivity);
//
//            runtimeTitleBar = new TitleBar();
//            runtimeTitleBar.setLabel("Available balance");
//            runtimeActivity.setTitleBar(runtimeTitleBar);
//            runtimeActivity.setColor("#F0E173");
//
//            runtimeTabStrip = new TabStrip();
//            runtimeTab = new Tab();
//            runtimeTab.setLabel("");
//            runtimeTabStrip.addTab(runtimeTab);
//            runtimeActivity.setTabStrip(runtimeTabStrip);
//
//            runtimeFragment = new Fragment();
//            runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_ADULTS_ALL_AVAILABLE_BALANCE);
//            runtimeActivity.addFragment(Fragments.CWP_WALLET_RUNTIME_ADULTS_ALL_AVAILABLE_BALANCE,runtimeFragment);
//
//            runtimeActivity= new Activity();
//            runtimeActivity.setType(Activities.CWP_WALLET_ADULTS_ALL_SEND_HISTORY);
//            runtimeWalletNavigationStructure.addActivity(runtimeActivity);
//
//            runtimeTitleBar = new TitleBar();
//            runtimeTitleBar.setLabel("Sent History");
//            runtimeActivity.setTitleBar(runtimeTitleBar);
//            runtimeActivity.setColor("#F0E173");
//
//            runtimeTabStrip = new TabStrip();
//            runtimeTab = new Tab();
//            runtimeTab.setLabel("");
//            runtimeTabStrip.addTab(runtimeTab);
//            runtimeActivity.setTabStrip(runtimeTabStrip);
//
//            runtimeFragment = new Fragment();
//            runtimeFragment.setType(Fragments.CWP_WALLET_ADULTS_ALL_SEND_HISTORY);
//            runtimeActivity.addFragment(Fragments.CWP_WALLET_ADULTS_ALL_SEND_HISTORY,runtimeFragment);
//
//            runtimeActivity= new Activity();
//            runtimeActivity.setType(Activities.CWP_WALLET_RUNTIME_ADULTS_ALL_CONTACTS_SEND);
//            runtimeWalletNavigationStructure.addActivity(runtimeActivity);
//
//            runtimeTitleBar = new TitleBar();
//            runtimeTitleBar.setLabel("Send To Contact");
//            runtimeActivity.setTitleBar(runtimeTitleBar);
//            runtimeActivity.setColor("#F0E173");
//
//            runtimeTabStrip = new TabStrip();
//            runtimeTab = new Tab();
//            runtimeTab.setLabel("");
//            runtimeTabStrip.addTab(runtimeTab);
//            runtimeActivity.setTabStrip(runtimeTabStrip);
//
//            runtimeFragment = new Fragment();
//            runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_CONTACTS_SEND);
//            runtimeActivity.addFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_CONTACTS_SEND,runtimeFragment);
//
//            runtimeActivity= new Activity();
//            runtimeActivity.setType(Activities.CWP_WALLET_ADULTS_ALL_CHAT_TRX);
//            runtimeWalletNavigationStructure.addActivity(runtimeActivity);
//
//            runtimeTitleBar = new TitleBar();
//            runtimeTitleBar.setLabel("");
//            runtimeActivity.setTitleBar(runtimeTitleBar);
//            runtimeActivity.setColor("#F0E173");
//
//            runtimeTabStrip = new TabStrip();
//            runtimeTab = new Tab();
//            runtimeTab.setLabel("");
//            runtimeTabStrip.addTab(runtimeTab);
//            runtimeActivity.setTabStrip(runtimeTabStrip);
//
//            runtimeFragment = new Fragment();
//            runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_CHAT_TRX);
//            runtimeActivity.addFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_CHAT_TRX,runtimeFragment);
//
//            runtimeActivity= new Activity();
//            runtimeActivity.setType(Activities.CWP_WALLET_ADULTS_ALL_REQUESTS_RECEIVED_HISTORY);
//            runtimeWalletNavigationStructure.addActivity(runtimeActivity);
//
//            runtimeTitleBar = new TitleBar();
//            runtimeTitleBar.setLabel("");
//            runtimeActivity.setTitleBar(runtimeTitleBar);
//            runtimeActivity.setColor("#F0E173");
//
//            runtimeTabStrip = new TabStrip();
//            runtimeTab = new Tab();
//            runtimeTab.setLabel("");
//            runtimeTabStrip.addTab(runtimeTab);
//            runtimeActivity.setTabStrip(runtimeTabStrip);
//
//            runtimeFragment = new Fragment();
//            runtimeFragment.setType(Fragments.CWP_WALLET_ADULTS_ALL_REQUESTS_RECEIVED_HISTORY);
//            runtimeActivity.addFragment(Fragments.CWP_WALLET_ADULTS_ALL_REQUESTS_RECEIVED_HISTORY,runtimeFragment);
//
//            runtimeActivity= new Activity();
//            runtimeActivity.setType(Activities.CWP_WALLET_RUNTIME_ADULTS_ALL_CONTACTS_RECEIVE);
//            runtimeWalletNavigationStructure.addActivity(runtimeActivity);
//
//            runtimeTitleBar = new TitleBar();
//            runtimeTitleBar.setLabel("Receive From Contact");
//            runtimeActivity.setTitleBar(runtimeTitleBar);
//            runtimeActivity.setColor("#F0E173");
//
//            runtimeTabStrip = new TabStrip();
//            runtimeTab = new Tab();
//            runtimeTab.setLabel("");
//            runtimeTabStrip.addTab(runtimeTab);
//            runtimeActivity.setTabStrip(runtimeTabStrip);
//
//            runtimeFragment = new Fragment();
//            runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_CONTACTS_RECEIVE);
//            runtimeActivity.addFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_CONTACTS_RECEIVE,runtimeFragment);
//
//            runtimeActivity= new Activity();
//            runtimeActivity.setType(Activities.CWP_WALLET_ADULTS_ALL_DAILY_DISCOUNT);
//            runtimeWalletNavigationStructure.addActivity(runtimeActivity);
//
//            runtimeTitleBar = new TitleBar();
//            runtimeTitleBar.setLabel("");
//            runtimeActivity.setTitleBar(runtimeTitleBar);
//            runtimeActivity.setColor("#F0E173");
//
//            runtimeFragment = new Fragment();
//            runtimeFragment.setType(Fragments.CWP_WALLET_ADULTS_ALL_DAILY_DISCOUNT);
//            runtimeActivity.addFragment(Fragments.CWP_WALLET_ADULTS_ALL_DAILY_DISCOUNT,runtimeFragment);
//
//            runtimeActivity= new Activity();
//            runtimeActivity.setType(Activities.CWP_WALLET_ADULTS_ALL_DAILY_DISCOUNT);
//            runtimeWalletNavigationStructure.addActivity(runtimeActivity);
//
//            runtimeTitleBar = new TitleBar();
//            runtimeTitleBar.setLabel("");
//            runtimeActivity.setTitleBar(runtimeTitleBar);
//            runtimeActivity.setColor("#F0E173");
//
//            runtimeFragment = new Fragment();
//            runtimeFragment.setType(Fragments.CWP_WALLET_ADULTS_ALL_DAILY_DISCOUNT);
//            runtimeActivity.addFragment(Fragments.CWP_WALLET_ADULTS_ALL_DAILY_DISCOUNT,runtimeFragment);
//
//            runtimeActivity= new Activity();
//            runtimeActivity.setType(Activities.CWP_WALLET_ADULTS_ALL_MONTHLY_DISCOUNT);
//            runtimeWalletNavigationStructure.addActivity(runtimeActivity);
//
//            runtimeTitleBar = new TitleBar();
//            runtimeTitleBar.setLabel("");
//            runtimeActivity.setTitleBar(runtimeTitleBar);
//            runtimeActivity.setColor("#F0E173");
//
//            runtimeFragment = new Fragment();
//            runtimeFragment.setType(Fragments.CWP_WALLET_ADULTS_ALL_MONTHLY_DISCOUNT);
//            runtimeActivity.addFragment(Fragments.CWP_WALLET_ADULTS_ALL_MONTHLY_DISCOUNT,runtimeFragment);
//
//
//            runtimeActivity= new Activity();
//            runtimeActivity.setType(Activities.CWP_WALLET_RUNTIME_ADULTS_ALL_CONTACTS_NEW_SEND);
//            runtimeWalletNavigationStructure.addActivity(runtimeActivity);
//
//            runtimeTitleBar = new TitleBar();
//            runtimeTitleBar.setLabel("Send to new contact");
//            runtimeActivity.setTitleBar(runtimeTitleBar);
//            runtimeActivity.setColor("#F0E173");
//
//            runtimeFragment = new Fragment();
//            runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_CONTACTS_NEW_SEND);
//            runtimeActivity.addFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_CONTACTS_NEW_SEND,runtimeFragment);
//
//            runtimeActivity= new Activity();
//            runtimeActivity.setType(Activities.CWP_WALLET_RUNTIME_ADULTS_ALL_CONTACTS_NEW_RECEIVE);
//            runtimeWalletNavigationStructure.addActivity(runtimeActivity);
//
//            runtimeTitleBar = new TitleBar();
//            runtimeTitleBar.setLabel("Receive from new contact");
//            runtimeActivity.setTitleBar(runtimeTitleBar);
//            runtimeActivity.setColor("#F0E173");
//
//            runtimeFragment = new Fragment();
//            runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_CONTACTS_NEW_RECEIVE);
//            runtimeActivity.addFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_CONTACTS_NEW_RECEIVE,runtimeFragment);
//
//            /**
//             * End of WalletNavigationStructure Adults tabs.
//             */
//
//            runtimeActivity= new Activity();
//            runtimeActivity.setType(Activities.CWP_WALLET_ADULTS_ALL_SHOPS);
//            runtimeActivity.setColor("#76dc4a");
//            //  runtimeSubApp.addActivity(runtimeActivity);
//
//
//
//            runtimeTitleBar = new TitleBar();
//            runtimeTitleBar.setLabel("My Shop");
//
//            runtimeActivity.setTitleBar(runtimeTitleBar);
//
//
//            runtimeTabStrip = new TabStrip();
//            runtimeTab = new Tab();
//            runtimeTab.setLabel("Shop");
//            runtimeTab.setFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOP_SHOP);
//            runtimeTabStrip.addTab(runtimeTab);
//            runtimeTab = new Tab();
//            runtimeTab.setLabel("Products");
//            runtimeTab.setFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOP_PRODUCTS);
//            runtimeTabStrip.addTab(runtimeTab);
//            runtimeTab = new Tab();
//            runtimeTab.setLabel("Reviews");
//            runtimeTab.setFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOP_REVIEWS);
//            runtimeTabStrip.addTab(runtimeTab);
//            runtimeTab = new Tab();
//            runtimeTab.setLabel("Chat");
//            runtimeTab.setFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOP_CHAT);
//            runtimeTabStrip.addTab(runtimeTab);
//            runtimeTab = new Tab();
//            runtimeTab.setLabel("History");
//            runtimeTab.setFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOP_HISTORY);
//            runtimeTabStrip.addTab(runtimeTab);
//            runtimeTab = new Tab();
//            runtimeTab.setLabel("Map");
//            runtimeTab.setFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOP_MAP);
//            runtimeTabStrip.addTab(runtimeTab);
//
//
//            runtimeTabStrip.setDividerColor(0xFFFFFFFF);
//            runtimeTabStrip.setIndicatorColor(0xFFFFFFFF);
//            runtimeTabStrip.setIndicatorHeight(9);
//            runtimeTabStrip.setBackgroundColor(0xFF76dc4a);
//            runtimeTabStrip.setTextColor(0xFFFFFFFF);
//            runtimeActivity.setTabStrip(runtimeTabStrip);
//
//            runtimeFragment = new Fragment();
//            runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOP_SHOP);
//            runtimeActivity.addFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOP_SHOP,runtimeFragment);
//
//            runtimeFragment = new Fragment();
//            runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOP_PRODUCTS);
//            runtimeActivity.addFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOP_PRODUCTS,runtimeFragment);
//
//            runtimeFragment = new Fragment();
//            runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOP_REVIEWS);
//            runtimeActivity.addFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOP_REVIEWS,runtimeFragment);
//
//            runtimeFragment = new Fragment();
//            runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOP_CHAT);
//            runtimeActivity.addFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOP_CHAT,runtimeFragment);
//
//            runtimeFragment = new Fragment();
//            runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOP_HISTORY);
//            runtimeActivity.addFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOP_HISTORY,runtimeFragment);
//
//            runtimeFragment = new Fragment();
//            runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOP_MAP);
//            runtimeActivity.addFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_SHOP_MAP,runtimeFragment);
//            /**
//             * End of SHOPS tabs.
//             */
//
//
//
//            //Account Details
//
//            runtimeActivity= new Activity();
//            runtimeActivity.setType(Activities.CWP_WALLET_RUNTIME_ADULTS_ALL_ACCOUNT_DETAIL);
//            runtimeActivity.setColor("#F0E173");
//            //runtimeSubApp.addActivity(runtimeActivity);
//
//
//
//            runtimeTitleBar = new TitleBar();
//            runtimeTitleBar.setLabel("Account details");
//
//            runtimeActivity.setTitleBar(runtimeTitleBar);
//
//
//            runtimeTabStrip = new TabStrip();
//            runtimeTab = new Tab();
//            runtimeTab.setLabel("Debits");
//            runtimeTab.setFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_ACCOUNTS_DEBITS);
//            runtimeTabStrip.addTab(runtimeTab);
//            runtimeTab = new Tab();
//            runtimeTab.setLabel("Credits");
//            runtimeTab.setFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_ACCOUNT_CREDITS);
//            runtimeTabStrip.addTab(runtimeTab);
//            runtimeTab = new Tab();
//            runtimeTab.setLabel("All");
//            runtimeTab.setFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_ACCOUNTS_ALL);
//            runtimeTabStrip.addTab(runtimeTab);
//
//            runtimeActivity.setTabStrip(runtimeTabStrip);
//
//            runtimeFragment = new Fragment();
//            runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_ACCOUNTS_DEBITS);
//            runtimeActivity.addFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_ACCOUNTS_DEBITS,runtimeFragment);
//
//            runtimeFragment = new Fragment();
//            runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_ACCOUNT_CREDITS);
//            runtimeActivity.addFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_ACCOUNT_CREDITS,runtimeFragment);
//
//            runtimeFragment = new Fragment();
//            runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_ACCOUNTS_ALL);
//            runtimeActivity.addFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_ACCOUNTS_ALL,runtimeFragment);
//
//
//
//        /*------------------------------*/
//
//            runtimeActivity= new Activity();
//            runtimeActivity.setType(Activities.CWP_WALLET_ADULTS_ALL_REFFILS);
//            // runtimeSubApp.addActivity(runtimeActivity);
////-----------------------------------------------------------------------------------
//            runtimeActivity= new Activity();
//            runtimeActivity.setType(Activities.CWP_WALLET_ADULTS_ALL_REQUESTS_RECEIVED);
//            //runtimeSubApp.addActivity(runtimeActivity);
//
//            runtimeFragment = new Fragment();
//            runtimeFragment.setType(Fragments.CWP_WALLET_ADULTS_ALL_REQUESTS_RECEIVED);
//            runtimeActivity.addFragment(Fragments.CWP_WALLET_ADULTS_ALL_REQUESTS_RECEIVED,runtimeFragment);
////------------------------------------------------------------------------------------
//            runtimeActivity= new Activity();
//            runtimeActivity.setType(Activities.CWP_WALLET_ADULTS_ALL_REQUEST_SEND);
//            //runtimeSubApp.addActivity(runtimeActivity);
//
//            runtimeFragment = new Fragment();
//            runtimeFragment.setType(Fragments.CWP_WALLET_ADULTS_ALL_REQUEST_SEND);
//            runtimeActivity.addFragment(Fragments.CWP_WALLET_ADULTS_ALL_REQUEST_SEND,runtimeFragment);
////-----------------------------------------------------------------------------------
//            runtimeActivity= new Activity();
//            runtimeActivity.setType(Activities.CWP_WALLET_RUNTIME_ADULTS_ALL_ACCOUNTS);
//            //runtimeSubApp.addActivity(runtimeActivity);
//
//            runtimeTitleBar = new TitleBar();
//            runtimeTitleBar.setLabel("Account details");
//            runtimeActivity.setTitleBar(runtimeTitleBar);
//
//            runtimeTabStrip = new TabStrip();
//            runtimeTab = new Tab();
//            runtimeTab.setLabel("Debits");
//            runtimeTab.setFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_ACCOUNTS_DEBITS);
//            runtimeTabStrip.addTab(runtimeTab);
//
//            runtimeTab = new Tab();
//            runtimeTab.setLabel("Credits");
//            runtimeTab.setFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_ACCOUNT_CREDITS);
//            runtimeTabStrip.addTab(runtimeTab);
//
//            runtimeTab = new Tab();
//            runtimeTab.setLabel("All");
//            runtimeTab.setFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_ACCOUNTS_ALL);
//            runtimeTabStrip.addTab(runtimeTab);
//
//
//            runtimeActivity.setTabStrip(runtimeTabStrip);
//
//            runtimeFragment = new Fragment();
//            runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_ACCOUNTS_DEBITS);
//            runtimeActivity.addFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_ACCOUNTS_DEBITS,runtimeFragment);
//            //listFragments.put(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_ACCOUNTS_DEBITS,runtimeFragment);
//
//            runtimeFragment = new Fragment();
//            runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_ACCOUNT_CREDITS);
//            runtimeActivity.addFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_ACCOUNT_CREDITS,runtimeFragment);
//
//            runtimeFragment = new Fragment();
//            runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_ACCOUNTS_ALL);
//            runtimeActivity.addFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI_ACCOUNTS_ALL,runtimeFragment);
//            //  listApps.put(Apps.CRYPTO_WALLET_PLATFORM,runtimeApp);
//            //lastApp = Apps.CRYPTO_WALLET_PLATFORM;
//            /**
//             * End of WalletNavigationStructure Accounts tabs.
//             */

        /*}catch(Exception e){
            String message = CantFactoryReset.DEFAULT_MESSAGE;
            FermatException cause = FermatException.wrapException(e);
            String context = "Error on method Factory Reset, setting the structure of the apps";
            String possibleReason = "some null definition";
            throw new CantFactoryReset(message, cause, context, possibleReason);

        }*/

    }

    private WalletNavigationStructure createCryptoBrokerWalletNavigationStructure() {
        WalletNavigationStructure runtimeWalletNavigationStructure;
        SideMenu runtimeSideMenu;
        MenuItem runtimeMenuItem;
        Activity runtimeActivity;
        TitleBar runtimeTitleBar;
        StatusBar runtimeStatusBar;
        Header runtimeHeader;
        Fragment runtimeFragment;
        TabStrip runtimeTabStrip;
        Tab runtimeTab;

        final String cryptoBrokerWalletPublicKey = "crypto_broker_wallet";
        final String statusBarColor = "#0e738b";

        runtimeWalletNavigationStructure = new WalletNavigationStructure();
        runtimeWalletNavigationStructure.setWalletCategory(WalletCategory.REFERENCE_WALLET.getCode());
        runtimeWalletNavigationStructure.setWalletType(WalletType.REFERENCE.getCode());
        runtimeWalletNavigationStructure.setPublicKey(cryptoBrokerWalletPublicKey);


        // Side Menu
        runtimeSideMenu = new SideMenu();

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Home");
        runtimeMenuItem.setLinkToActivity(Activities.CBP_CRYPTO_BROKER_WALLET_HOME);
        runtimeMenuItem.setAppLinkPublicKey(cryptoBrokerWalletPublicKey);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Contracts History");
        runtimeMenuItem.setLinkToActivity(Activities.CBP_CRYPTO_BROKER_WALLET_CONTRACTS_HISTORY);
        runtimeMenuItem.setAppLinkPublicKey(cryptoBrokerWalletPublicKey);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Earnings");
        runtimeMenuItem.setLinkToActivity(Activities.CBP_CRYPTO_BROKER_WALLET_EARNINGS);
        runtimeMenuItem.setAppLinkPublicKey(cryptoBrokerWalletPublicKey);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Settings");
        runtimeMenuItem.setLinkToActivity(Activities.CBP_CRYPTO_BROKER_WALLET_SETTINGS);
        runtimeMenuItem.setAppLinkPublicKey(cryptoBrokerWalletPublicKey);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);


        // Activity: Home
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CBP_CRYPTO_BROKER_WALLET_HOME);
        runtimeActivity.setActivityType(Activities.CBP_CRYPTO_BROKER_WALLET_HOME.getCode());
        runtimeActivity.setColor("#1189a5");
        runtimeActivity.setSideMenu(runtimeSideMenu);
        runtimeWalletNavigationStructure.addActivity(runtimeActivity);
        runtimeWalletNavigationStructure.setStartActivity(Activities.CBP_CRYPTO_BROKER_WALLET_HOME);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Crypto Broker Wallet");
        runtimeTitleBar.setLabelSize(18);
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setFont("Roboto-Regular.ttf");
        runtimeTitleBar.setTitleColor("#ffffff");
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeStatusBar = new StatusBar();
        runtimeStatusBar.setColor(statusBarColor);
        runtimeActivity.setStatusBar(runtimeStatusBar);

        runtimeHeader = new Header();
        runtimeHeader.setLabel("Market rate");
        runtimeActivity.setHeader(runtimeHeader);

        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.CBP_CRYPTO_BROKER_WALLET_MARKET_RATE_STATISTICS.getKey());
        runtimeActivity.addFragment(Fragments.CBP_CRYPTO_BROKER_WALLET_MARKET_RATE_STATISTICS.getKey(), runtimeFragment);

        runtimeTabStrip = new TabStrip();
        runtimeTabStrip.setTabsColor("#1278a6");
        runtimeTabStrip.setTabsTextColor("#FFFFFF");
        runtimeTabStrip.setTabsIndicateColor("#3ec8e8");
        runtimeTabStrip.setDividerColor(0x72af9c);
        runtimeActivity.setTabStrip(runtimeTabStrip);

        runtimeTab = new Tab();
        runtimeTab.setLabel("Negotiations");
        runtimeTab.setFragment(Fragments.CBP_CRYPTO_BROKER_WALLET_OPEN_NEGOTIATIONS_TAB);
        runtimeTabStrip.addTab(runtimeTab);
        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.CBP_CRYPTO_BROKER_WALLET_OPEN_NEGOTIATIONS_TAB.getKey());
        runtimeActivity.addFragment(Fragments.CBP_CRYPTO_BROKER_WALLET_OPEN_NEGOTIATIONS_TAB.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.CBP_CRYPTO_BROKER_WALLET_OPEN_NEGOTIATIONS_TAB.getKey());

        runtimeTab = new Tab();
        runtimeTab.setLabel("Contracts");
        runtimeTab.setFragment(Fragments.CBP_CRYPTO_BROKER_WALLET_OPEN_CONTRACTS_TAB);
        runtimeTabStrip.addTab(runtimeTab);
        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.CBP_CRYPTO_BROKER_WALLET_OPEN_CONTRACTS_TAB.getKey());
        runtimeActivity.addFragment(Fragments.CBP_CRYPTO_BROKER_WALLET_OPEN_CONTRACTS_TAB.getKey(), runtimeFragment);

        // TODO falta agregar un footer a navigation structure
        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.CBP_CRYPTO_BROKER_WALLET_STOCK_STATISTICS.getKey());
        runtimeActivity.addFragment(Fragments.CBP_CRYPTO_BROKER_WALLET_STOCK_STATISTICS.getKey(), runtimeFragment);


        // Activity: Open Negotiation details
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CBP_CRYPTO_BROKER_WALLET_OPEN_NEGOTIATION_DETAILS);
        runtimeActivity.setActivityType(Activities.CBP_CRYPTO_BROKER_WALLET_OPEN_NEGOTIATION_DETAILS.getCode());
        runtimeActivity.setBackActivity(Activities.CBP_CRYPTO_BROKER_WALLET_HOME);
        runtimeActivity.setColor("#1189a5");
        runtimeWalletNavigationStructure.addActivity(runtimeActivity);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Negotiation Details");
        runtimeTitleBar.setLabelSize(18);
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setFont("Roboto-Regular.ttf");
        runtimeTitleBar.setTitleColor("#ffffff");
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeStatusBar = new StatusBar();
        runtimeStatusBar.setColor(statusBarColor);
        runtimeActivity.setStatusBar(runtimeStatusBar);

        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.CBP_CRYPTO_BROKER_WALLET_OPEN_NEGOTIATION_DETAILS.getKey());
        runtimeActivity.addFragment(Fragments.CBP_CRYPTO_BROKER_WALLET_OPEN_NEGOTIATION_DETAILS.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.CBP_CRYPTO_BROKER_WALLET_OPEN_NEGOTIATION_DETAILS.getKey());


        // Activity: Close Negotiation details
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CBP_CRYPTO_BROKER_WALLET_CLOSE_NEGOTIATION_DETAILS);
        runtimeActivity.setActivityType(Activities.CBP_CRYPTO_BROKER_WALLET_CLOSE_NEGOTIATION_DETAILS.getCode());
        runtimeActivity.setColor("#1189a5");
        runtimeWalletNavigationStructure.addActivity(runtimeActivity);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Negotiation Details");
        runtimeTitleBar.setLabelSize(18);
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setFont("Roboto-Regular.ttf");
        runtimeTitleBar.setTitleColor("#ffffff");
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeStatusBar = new StatusBar();
        runtimeStatusBar.setColor(statusBarColor);
        runtimeActivity.setStatusBar(runtimeStatusBar);

        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.CBP_CRYPTO_BROKER_WALLET_CLOSE_NEGOTIATION_DETAILS.getKey());
        runtimeActivity.addFragment(Fragments.CBP_CRYPTO_BROKER_WALLET_CLOSE_NEGOTIATION_DETAILS.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.CBP_CRYPTO_BROKER_WALLET_CLOSE_NEGOTIATION_DETAILS.getKey());


        // Activity: Open Contract Details
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CBP_CRYPTO_BROKER_WALLET_OPEN_CONTRACT_DETAILS);
        runtimeActivity.setActivityType(Activities.CBP_CRYPTO_BROKER_WALLET_OPEN_CONTRACT_DETAILS.getCode());
        runtimeActivity.setBackActivity(Activities.CBP_CRYPTO_BROKER_WALLET_HOME);
        runtimeActivity.setColor("#1189a5");
        runtimeWalletNavigationStructure.addActivity(runtimeActivity);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Contract Details");
        runtimeTitleBar.setColor("#FFFFFF");
        runtimeTitleBar.setLabelSize(16);
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeStatusBar = new StatusBar();
        runtimeStatusBar.setColor(statusBarColor);
        runtimeActivity.setStatusBar(runtimeStatusBar);

        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.CBP_CRYPTO_BROKER_WALLET_OPEN_CONTRACT_DETAILS.getKey());
        runtimeActivity.addFragment(Fragments.CBP_CRYPTO_BROKER_WALLET_OPEN_CONTRACT_DETAILS.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.CBP_CRYPTO_BROKER_WALLET_OPEN_CONTRACT_DETAILS.getKey());


        // Activity: Close Contract Details
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CBP_CRYPTO_BROKER_WALLET_CLOSE_CONTRACT_DETAILS);
        runtimeActivity.setActivityType(Activities.CBP_CRYPTO_BROKER_WALLET_CLOSE_CONTRACT_DETAILS.getCode());
        runtimeActivity.setBackActivity(Activities.CBP_CRYPTO_BROKER_WALLET_CONTRACTS_HISTORY);
        runtimeActivity.setColor("#1189a5");
        runtimeWalletNavigationStructure.addActivity(runtimeActivity);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Contract Details");
        runtimeTitleBar.setLabelSize(18);
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setFont("Roboto-Regular.ttf");
        runtimeTitleBar.setTitleColor("#ffffff");
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeStatusBar = new StatusBar();
        runtimeStatusBar.setColor(statusBarColor);
        runtimeActivity.setStatusBar(runtimeStatusBar);

        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.CBP_CRYPTO_BROKER_WALLET_CLOSE_CONTRACT_DETAILS.getKey());
        runtimeActivity.addFragment(Fragments.CBP_CRYPTO_BROKER_WALLET_CLOSE_CONTRACT_DETAILS.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.CBP_CRYPTO_BROKER_WALLET_CLOSE_CONTRACT_DETAILS.getKey());


        // Activity: Contracts History
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CBP_CRYPTO_BROKER_WALLET_CONTRACTS_HISTORY);
        runtimeActivity.setActivityType(Activities.CBP_CRYPTO_BROKER_WALLET_CONTRACTS_HISTORY.getCode());
        runtimeActivity.setColor("#1189a5");
        runtimeActivity.setSideMenu(runtimeSideMenu);
        runtimeWalletNavigationStructure.addActivity(runtimeActivity);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Contracts History");
        runtimeTitleBar.setLabelSize(18);
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setFont("Roboto-Regular.ttf");
        runtimeTitleBar.setTitleColor("#ffffff");
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeStatusBar = new StatusBar();
        runtimeStatusBar.setColor(statusBarColor);
        runtimeActivity.setStatusBar(runtimeStatusBar);

        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.CBP_CRYPTO_BROKER_WALLET_CONTRACTS_HISTORY.getKey());
        runtimeActivity.addFragment(Fragments.CBP_CRYPTO_BROKER_WALLET_CONTRACTS_HISTORY.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.CBP_CRYPTO_BROKER_WALLET_CONTRACTS_HISTORY.getKey());


        // Activity: Earnings
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CBP_CRYPTO_BROKER_WALLET_EARNINGS);
        runtimeActivity.setActivityType(Activities.CBP_CRYPTO_BROKER_WALLET_EARNINGS.getCode());
        runtimeActivity.setColor("#1189a5");
        runtimeActivity.setSideMenu(runtimeSideMenu);
        runtimeWalletNavigationStructure.addActivity(runtimeActivity);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Earnings");
        runtimeTitleBar.setLabelSize(18);
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setFont("Roboto-Regular.ttf");
        runtimeTitleBar.setTitleColor("#ffffff");
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeStatusBar = new StatusBar();
        runtimeStatusBar.setColor(statusBarColor);
        runtimeActivity.setStatusBar(runtimeStatusBar);

        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.CBP_CRYPTO_BROKER_WALLET_EARNINGS.getKey());
        runtimeActivity.addFragment(Fragments.CBP_CRYPTO_BROKER_WALLET_EARNINGS.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.CBP_CRYPTO_BROKER_WALLET_EARNINGS.getKey());


        // Activity: Settings
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CBP_CRYPTO_BROKER_WALLET_SETTINGS);
        runtimeActivity.setActivityType(Activities.CBP_CRYPTO_BROKER_WALLET_SETTINGS.getCode());
        runtimeActivity.setColor("#1189a5");
        runtimeActivity.setSideMenu(runtimeSideMenu);
        runtimeWalletNavigationStructure.addActivity(runtimeActivity);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Settings");
        runtimeTitleBar.setLabelSize(18);
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setFont("Roboto-Regular.ttf");
        runtimeTitleBar.setTitleColor("#ffffff");
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeStatusBar = new StatusBar();
        runtimeStatusBar.setColor(statusBarColor);
        runtimeActivity.setStatusBar(runtimeStatusBar);

        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.CBP_CRYPTO_BROKER_WALLET_SETTINGS.getKey());
        runtimeActivity.addFragment(Fragments.CBP_CRYPTO_BROKER_WALLET_SETTINGS.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.CBP_CRYPTO_BROKER_WALLET_SETTINGS.getKey());


        return runtimeWalletNavigationStructure;

    }

    private WalletNavigationStructure createCryptoCustomerWalletNavigationStructure() {
        WalletNavigationStructure runtimeWalletNavigationStructure;
        SideMenu runtimeSideMenu;
        MenuItem runtimeMenuItem;
        Activity runtimeActivity;
        TitleBar runtimeTitleBar;
        StatusBar runtimeStatusBar;
        Header runtimeHeader;
        Fragment runtimeFragment;
        TabStrip runtimeTabStrip;
        Tab runtimeTab;

        final String cryptoCustomerWalletPublicKey = "crypto_customer_wallet";
        final String statusBarColor = "#990c75";

        runtimeWalletNavigationStructure = new WalletNavigationStructure();
        runtimeWalletNavigationStructure.setWalletCategory(WalletCategory.REFERENCE_WALLET.getCode());
        runtimeWalletNavigationStructure.setWalletType(WalletType.REFERENCE.getCode());
        runtimeWalletNavigationStructure.setPublicKey(cryptoCustomerWalletPublicKey);


        // Side Menu
        runtimeSideMenu = new SideMenu();

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Home");
        runtimeMenuItem.setLinkToActivity(Activities.CBP_CRYPTO_CUSTOMER_WALLET_HOME);
        runtimeMenuItem.setAppLinkPublicKey(cryptoCustomerWalletPublicKey);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Contracts History");
        runtimeMenuItem.setLinkToActivity(Activities.CBP_CRYPTO_CUSTOMER_WALLET_CONTRACTS_HISTORY);
        runtimeMenuItem.setAppLinkPublicKey(cryptoCustomerWalletPublicKey);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Broker List");
        runtimeMenuItem.setLinkToActivity(Activities.CBP_CRYPTO_CUSTOMER_WALLET_BROKER_LIST);
        runtimeMenuItem.setAppLinkPublicKey(cryptoCustomerWalletPublicKey);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Settings");
        runtimeMenuItem.setLinkToActivity(Activities.CBP_CRYPTO_CUSTOMER_WALLET_SETTINGS);
        runtimeMenuItem.setAppLinkPublicKey(cryptoCustomerWalletPublicKey);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);


        // Activity: Home
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CBP_CRYPTO_CUSTOMER_WALLET_HOME);
        runtimeActivity.setActivityType(Activities.CBP_CRYPTO_CUSTOMER_WALLET_HOME.getCode());
        runtimeActivity.setColor("#1189a5");
        runtimeActivity.setSideMenu(runtimeSideMenu);
        runtimeWalletNavigationStructure.addActivity(runtimeActivity);
        runtimeWalletNavigationStructure.setStartActivity(Activities.CBP_CRYPTO_CUSTOMER_WALLET_HOME);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Crypto Customer Wallet");
        runtimeTitleBar.setLabelSize(18);
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setTitleColor("#ffffff");
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeStatusBar = new StatusBar();
        runtimeStatusBar.setColor(statusBarColor);
        runtimeActivity.setStatusBar(runtimeStatusBar);

        runtimeHeader = new Header();
        runtimeHeader.setLabel("Market rate");
        runtimeActivity.setHeader(runtimeHeader);

        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.CBP_CRYPTO_CUSTOMER_WALLET_MARKET_RATE_STATISTICS.getKey());
        runtimeActivity.addFragment(Fragments.CBP_CRYPTO_CUSTOMER_WALLET_MARKET_RATE_STATISTICS.getKey(), runtimeFragment);


        runtimeTabStrip = new TabStrip();
        runtimeTabStrip.setTabsColor("#502681");
        runtimeTabStrip.setTabsTextColor("#FFFFFF");
        runtimeTabStrip.setTabsIndicateColor("#dbdbdb");
        runtimeTabStrip.setDividerColor(0x72af9c);
        runtimeActivity.setTabStrip(runtimeTabStrip);

        runtimeTab = new Tab();
        runtimeTab.setLabel("Negotiations");
        runtimeTab.setFragment(Fragments.CBP_CRYPTO_CUSTOMER_WALLET_OPEN_NEGOTIATIONS_TAB);
        runtimeTabStrip.addTab(runtimeTab);
        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.CBP_CRYPTO_CUSTOMER_WALLET_OPEN_NEGOTIATIONS_TAB.getKey());
        runtimeActivity.addFragment(Fragments.CBP_CRYPTO_CUSTOMER_WALLET_OPEN_NEGOTIATIONS_TAB.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.CBP_CRYPTO_CUSTOMER_WALLET_OPEN_NEGOTIATIONS_TAB.getKey());

        runtimeTab = new Tab();
        runtimeTab.setLabel("Contracts");
        runtimeTab.setFragment(Fragments.CBP_CRYPTO_CUSTOMER_WALLET_OPEN_CONTRACTS_TAB);
        runtimeTabStrip.addTab(runtimeTab);
        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.CBP_CRYPTO_CUSTOMER_WALLET_OPEN_CONTRACTS_TAB.getKey());
        runtimeActivity.addFragment(Fragments.CBP_CRYPTO_CUSTOMER_WALLET_OPEN_CONTRACTS_TAB.getKey(), runtimeFragment);


        // Activity: Start Negotiation
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CBP_CRYPTO_CUSTOMER_WALLET_START_NEGOTIATION);
        runtimeActivity.setActivityType(Activities.CBP_CRYPTO_CUSTOMER_WALLET_START_NEGOTIATION.getCode());
        runtimeActivity.setBackActivity(Activities.CBP_CRYPTO_CUSTOMER_WALLET_BROKER_LIST);
        runtimeActivity.setColor("#1189a5");
        runtimeWalletNavigationStructure.addActivity(runtimeActivity);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Start a Negotiation");
        runtimeTitleBar.setLabelSize(18);
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setTitleColor("#ffffff");
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeStatusBar = new StatusBar();
        runtimeStatusBar.setColor(statusBarColor);
        runtimeActivity.setStatusBar(runtimeStatusBar);

        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.CBP_CRYPTO_CUSTOMER_WALLET_START_NEGOTIATION.getKey());
        runtimeActivity.addFragment(Fragments.CBP_CRYPTO_CUSTOMER_WALLET_START_NEGOTIATION.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.CBP_CRYPTO_CUSTOMER_WALLET_START_NEGOTIATION.getKey());


        // Activity: Open Negotiation details
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CBP_CRYPTO_CUSTOMER_WALLET_OPEN_NEGOTIATION_DETAILS);
        runtimeActivity.setActivityType(Activities.CBP_CRYPTO_CUSTOMER_WALLET_OPEN_NEGOTIATION_DETAILS.getCode());
        runtimeActivity.setBackActivity(Activities.CBP_CRYPTO_CUSTOMER_WALLET_HOME);
        runtimeActivity.setColor("#1189a5");
        runtimeWalletNavigationStructure.addActivity(runtimeActivity);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Negotiation Details");
        runtimeTitleBar.setLabelSize(18);
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setTitleColor("#ffffff");
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeStatusBar = new StatusBar();
        runtimeStatusBar.setColor(statusBarColor);
        runtimeActivity.setStatusBar(runtimeStatusBar);

        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.CBP_CRYPTO_CUSTOMER_WALLET_OPEN_NEGOTIATION_DETAILS.getKey());
        runtimeActivity.addFragment(Fragments.CBP_CRYPTO_CUSTOMER_WALLET_OPEN_NEGOTIATION_DETAILS.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.CBP_CRYPTO_CUSTOMER_WALLET_OPEN_NEGOTIATION_DETAILS.getKey());


        // Activity: Close Negotiation details
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CBP_CRYPTO_CUSTOMER_WALLET_CLOSE_NEGOTIATION_DETAILS);
        runtimeActivity.setActivityType(Activities.CBP_CRYPTO_CUSTOMER_WALLET_CLOSE_NEGOTIATION_DETAILS.getCode());
        runtimeActivity.setColor("#1189a5");
        runtimeWalletNavigationStructure.addActivity(runtimeActivity);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Negotiation Details");
        runtimeTitleBar.setLabelSize(18);
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setTitleColor("#ffffff");
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeStatusBar = new StatusBar();
        runtimeStatusBar.setColor(statusBarColor);
        runtimeActivity.setStatusBar(runtimeStatusBar);

        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.CBP_CRYPTO_CUSTOMER_WALLET_CLOSE_NEGOTIATION_DETAILS.getKey());
        runtimeActivity.addFragment(Fragments.CBP_CRYPTO_CUSTOMER_WALLET_CLOSE_NEGOTIATION_DETAILS.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.CBP_CRYPTO_CUSTOMER_WALLET_CLOSE_NEGOTIATION_DETAILS.getKey());

        // Activity: Open Contract Details
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CBP_CRYPTO_CUSTOMER_WALLET_OPEN_CONTRACT_DETAILS);
        runtimeActivity.setActivityType(Activities.CBP_CRYPTO_CUSTOMER_WALLET_OPEN_CONTRACT_DETAILS.getCode());
        runtimeActivity.setBackActivity(Activities.CBP_CRYPTO_CUSTOMER_WALLET_HOME);
        runtimeActivity.setColor("#1189a5");
        runtimeWalletNavigationStructure.addActivity(runtimeActivity);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Contract Details");
        runtimeTitleBar.setLabelSize(18);
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setTitleColor("#ffffff");
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeStatusBar = new StatusBar();
        runtimeStatusBar.setColor("#0e738b");
        runtimeActivity.setStatusBar(runtimeStatusBar);

        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.CBP_CRYPTO_CUSTOMER_WALLET_OPEN_CONTRACT_DETAILS.getKey());
        runtimeActivity.addFragment(Fragments.CBP_CRYPTO_CUSTOMER_WALLET_OPEN_CONTRACT_DETAILS.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.CBP_CRYPTO_CUSTOMER_WALLET_OPEN_CONTRACT_DETAILS.getKey());


        // Activity: Close Contract Details
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CBP_CRYPTO_CUSTOMER_WALLET_CLOSE_CONTRACT_DETAILS);
        runtimeActivity.setActivityType(Activities.CBP_CRYPTO_CUSTOMER_WALLET_CLOSE_CONTRACT_DETAILS.getCode());
        runtimeActivity.setBackActivity(Activities.CBP_CRYPTO_CUSTOMER_WALLET_CONTRACTS_HISTORY);
        runtimeActivity.setColor("#1189a5");
        runtimeWalletNavigationStructure.addActivity(runtimeActivity);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Contract Details");
        runtimeTitleBar.setLabelSize(18);
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setTitleColor("#ffffff");
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeStatusBar = new StatusBar();
        runtimeStatusBar.setColor(statusBarColor);
        runtimeActivity.setStatusBar(runtimeStatusBar);

        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.CBP_CRYPTO_BROKER_WALLET_CLOSE_CONTRACT_DETAILS.getKey());
        runtimeActivity.addFragment(Fragments.CBP_CRYPTO_BROKER_WALLET_CLOSE_CONTRACT_DETAILS.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.CBP_CRYPTO_BROKER_WALLET_CLOSE_CONTRACT_DETAILS.getKey());


        // Activity: Contracts History
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CBP_CRYPTO_CUSTOMER_WALLET_CONTRACTS_HISTORY);
        runtimeActivity.setActivityType(Activities.CBP_CRYPTO_CUSTOMER_WALLET_CONTRACTS_HISTORY.getCode());
        runtimeActivity.setColor("#1189a5");
        runtimeActivity.setSideMenu(runtimeSideMenu);
        runtimeWalletNavigationStructure.addActivity(runtimeActivity);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Contract History");
        runtimeTitleBar.setLabelSize(18);
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setTitleColor("#ffffff");
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeStatusBar = new StatusBar();
        runtimeStatusBar.setColor(statusBarColor);
        runtimeActivity.setStatusBar(runtimeStatusBar);

        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.CBP_CRYPTO_CUSTOMER_WALLET_CONTRACTS_HISTORY.getKey());
        runtimeActivity.addFragment(Fragments.CBP_CRYPTO_CUSTOMER_WALLET_CONTRACTS_HISTORY.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.CBP_CRYPTO_CUSTOMER_WALLET_CONTRACTS_HISTORY.getKey());


        // Activity: Broker List
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CBP_CRYPTO_CUSTOMER_WALLET_BROKER_LIST);
        runtimeActivity.setActivityType(Activities.CBP_CRYPTO_CUSTOMER_WALLET_BROKER_LIST.getCode());
        runtimeActivity.setColor("#1189a5");
        runtimeActivity.setSideMenu(runtimeSideMenu);
        runtimeWalletNavigationStructure.addActivity(runtimeActivity);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Broker List");
        runtimeTitleBar.setLabelSize(18);
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setTitleColor("#ffffff");
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeStatusBar = new StatusBar();
        runtimeStatusBar.setColor(statusBarColor);
        runtimeActivity.setStatusBar(runtimeStatusBar);

        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.CBP_CRYPTO_CUSTOMER_WALLET_BROKER_LIST.getKey());
        runtimeActivity.addFragment(Fragments.CBP_CRYPTO_CUSTOMER_WALLET_BROKER_LIST.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.CBP_CRYPTO_CUSTOMER_WALLET_BROKER_LIST.getKey());


        // Activity: Settings
        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CBP_CRYPTO_CUSTOMER_WALLET_SETTINGS);
        runtimeActivity.setActivityType(Activities.CBP_CRYPTO_CUSTOMER_WALLET_SETTINGS.getCode());
        runtimeActivity.setColor("#1189a5");
        runtimeActivity.setSideMenu(runtimeSideMenu);
        runtimeWalletNavigationStructure.addActivity(runtimeActivity);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Settings");
        runtimeTitleBar.setLabelSize(18);
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setTitleColor("#ffffff");
        runtimeActivity.setTitleBar(runtimeTitleBar);

        runtimeStatusBar = new StatusBar();
        runtimeStatusBar.setColor(statusBarColor);
        runtimeActivity.setStatusBar(runtimeStatusBar);

        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.CBP_CRYPTO_CUSTOMER_WALLET_SETTINGS.getKey());
        runtimeActivity.addFragment(Fragments.CBP_CRYPTO_CUSTOMER_WALLET_SETTINGS.getKey(), runtimeFragment);
        runtimeActivity.setStartFragment(Fragments.CBP_CRYPTO_CUSTOMER_WALLET_SETTINGS.getKey());


        return runtimeWalletNavigationStructure;
    }

    private void loadLastWalletNavigationStructure() throws CantFactoryReset {
        String walletCategory = null;
        String walletType = null;
        String screenSize = null;
        String screenDensity = null;
        String skinName = null;
        String languageName = null;

        String publicKey = "reference_wallet";

//        try {
//
//            walletResourcesManger.installCompleteWallet(WalletCategory.REFERENCE_WALLET.getCode(),WalletType.REFERENCE.getCode(), "bitDubai", "medium", "default", "en", "1.0.0",publicKey);
//
//        } catch (WalletResourcesInstalationException e) {
//            e.printStackTrace();
//        }


        try {
            /**
             * Esto es hasta que tengamos las cosas andando y conectadas
             */


//            WalletNavigationStructure walletNavigationStructure = getNavigationStructure(publicKey);
            //          if(walletNavigationStructure==null){
            setNavigationStructureXml(startWalletNavigationStructure());
            WalletNavigationStructure walletNavigationStructure = getNavigationStructure(publicKey);
            //        }
            //listWallets.put(publicKey, walletNavigationStructure);
            //walletNavigationStructureOpen=walletNavigationStructure;
        } catch (Exception e) {
            String message = CantFactoryReset.DEFAULT_MESSAGE;
            FermatException cause = FermatException.wrapException(e);
            String context = "Error on method Factory Reset, setting the structure of the apps";
            String possibleReason = "some null definition";
            throw new CantFactoryReset(message, cause, context, possibleReason);

        }
    }

    private void removeNavigationStructureXml(String publicKey) {
        if (publicKey != null) {
            String navigationStructureName = publicKey + ".xml";
            try {
                PluginTextFile pluginTextFile = pluginFileSystem.getTextFile(pluginId, NAVIGATION_STRUCTURE_FILE_PATH, navigationStructureName, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
                pluginTextFile.delete();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @param walletPublicKey
     * @return
     * @throws CantGetWalletFactoryProjectNavigationStructureException
     */
    @Override
    public WalletNavigationStructure getNavigationStructure(String walletPublicKey) {
        WalletNavigationStructure walletNavigationStructure = null;
        if (walletPublicKey != null) {
            String navigationStructureName = walletPublicKey + ".xml";

            try {

                PluginTextFile pluginTextFile = pluginFileSystem.getTextFile(pluginId, NAVIGATION_STRUCTURE_FILE_PATH, navigationStructureName, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
                pluginTextFile.loadFromMedia();
                String xml = pluginTextFile.getContent();


                walletNavigationStructure = (WalletNavigationStructure) XMLParser.parseXML(xml, walletNavigationStructure);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (CantCreateFileException e) {
                e.printStackTrace();
            } catch (CantLoadFileException e) {
                e.printStackTrace();
            }
        }

        return walletNavigationStructure;
    }

    @Override
    public String parseNavigationStructureXml(WalletNavigationStructure walletNavigationStructure) {
        String xml = null;
        if (walletNavigationStructure != null) {
            xml = XMLParser.parseObject(walletNavigationStructure);
        }
        return xml;
    }


    @Override
    public void setNavigationStructureXml(WalletNavigationStructure walletNavigationStructure) {
        String publiKey = walletNavigationStructure.getPublicKey();
        try {
            String navigationStructureXml = parseNavigationStructureXml(walletNavigationStructure);
            String navigationStructureName = publiKey + ".xml";
            try {
                PluginTextFile newFile = pluginFileSystem.createTextFile(pluginId, NAVIGATION_STRUCTURE_FILE_PATH, navigationStructureName, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
                newFile.setContent(navigationStructureXml);
                newFile.persistToMedia();
            } catch (CantPersistFileException e) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WPD_WALLET_FACTORY_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                //throw new CantSetWalletFactoryProjectNavigationStructureException(CantSetWalletFactoryProjectNavigationStructureException.DEFAULT_MESSAGE, e, "Can't create or overwrite navigation structure file.", "");
            } catch (CantCreateFileException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WPD_WALLET_FACTORY_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            //throw new CantSetWalletFactoryProjectNavigationStructureException(CantSetWalletFactoryProjectNavigationStructureException.DEFAULT_MESSAGE, e, "Can't convert navigation structure to xml format", "");
        }
    }


    /**
     * Meanwhile
     *
     * @return
     */

    private WalletNavigationStructure startWalletNavigationStructure() {

        Activity runtimeActivity;
        Fragment runtimeFragment;
        WalletNavigationStructure runtimeWalletNavigationStructure;
        TitleBar runtimeTitleBar;
        SideMenu runtimeSideMenu;
        MainMenu runtimeMainMenu;
        MenuItem runtimeMenuItem;
        TabStrip runtimeTabStrip;
        StatusBar runtimeStatusBar;
        Header runtimeHeader;

        Tab runtimeTab;

        String publicKey;

        runtimeWalletNavigationStructure = new WalletNavigationStructure();
        runtimeWalletNavigationStructure.setWalletCategory(WalletCategory.REFERENCE_WALLET.getCode());
        runtimeWalletNavigationStructure.setWalletType(WalletType.REFERENCE.getCode());
        publicKey = "reference_wallet";
        runtimeWalletNavigationStructure.setPublicKey(publicKey);
        //listWallets.put(publicKey, runtimeWalletNavigationStructure);
        walletNavigationStructureOpen = runtimeWalletNavigationStructure;

        runtimeActivity = new Activity();
        runtimeActivity.setActivityType(Activities.CWP_WALLET_RUNTIME_WALLET_BASIC_WALLET_BITDUBAI_VERSION_1_MAIN.getCode());
        runtimeActivity.setType(Activities.CWP_WALLET_RUNTIME_WALLET_BASIC_WALLET_BITDUBAI_VERSION_1_MAIN);
        runtimeActivity.setColor("#12aca1");
        runtimeWalletNavigationStructure.addActivity(runtimeActivity);
        runtimeWalletNavigationStructure.setStartActivity(runtimeActivity.getType());

        runtimeHeader = new Header();
        runtimeHeader.setLabel("Balance");
        runtimeActivity.setHeader(runtimeHeader);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Bitcoin wallet");
        runtimeTitleBar.setLabelSize(18);
        runtimeTitleBar.setIsTitleTextStatic(true);
        runtimeTitleBar.setFont("Roboto-Regular.ttf");
        runtimeTitleBar.setTitleColor("#ffffff");
        runtimeTitleBar.setColor("#12aca1");

        runtimeActivity.setTitleBar(runtimeTitleBar);
        runtimeActivity.setColor("#12aca1");
        //runtimeActivity.setColor("#d07b62");


        runtimeStatusBar = new com.bitdubai.fermat_api.layer.all_definition.navigation_structure.StatusBar();
        runtimeStatusBar.setColor("#12aca1");

        runtimeActivity.setStatusBar(runtimeStatusBar);


        runtimeTabStrip = new TabStrip();

        runtimeTabStrip.setTabsColor("#06356f");

        runtimeTabStrip.setTabsTextColor("#b9bfcd");

        runtimeTabStrip.setTabsIndicateColor("#12aca1");


        runtimeTab = new Tab();
        runtimeTab.setLabel("Sent");
        runtimeTab.setFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_SEND);
        runtimeTabStrip.addTab(runtimeTab);

        runtimeTab = new Tab();
        runtimeTab.setLabel("Received");
        runtimeTab.setFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_RECEIVE);
        runtimeTabStrip.addTab(runtimeTab);


        runtimeTabStrip.setDividerColor(0x72af9c);
        //runtimeTabStrip.setBackgroundColor("#72af9c");
        runtimeActivity.setTabStrip(runtimeTabStrip);

        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_BALANCE.getKey());
        runtimeActivity.addFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_BALANCE.getKey(), runtimeFragment);


        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_SEND.getKey());
        runtimeFragment.setBack(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_CONTACTS.getKey());
        runtimeActivity.addFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_SEND.getKey(), runtimeFragment);


        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_RECEIVE.getKey());
        runtimeActivity.addFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_RECEIVE.getKey(), runtimeFragment);

        //Navigation

        runtimeSideMenu = new SideMenu();
        runtimeSideMenu.setBackgroundColor("#ffffff");
        runtimeSideMenu.setNavigationIconColor("#ffffff");

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Home");
        runtimeMenuItem.setIcon("home");
        runtimeMenuItem.setLinkToActivity(Activities.CWP_WALLET_RUNTIME_WALLET_BASIC_WALLET_BITDUBAI_VERSION_1_MAIN);
        runtimeMenuItem.setAppLinkPublicKey(publicKey);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Contacts");
        runtimeMenuItem.setIcon("contacts");
        runtimeMenuItem.setLinkToActivity(Activities.CWP_WALLET_RUNTIME_WALLET_BASIC_WALLET_BITDUBAI_VERSION_1_CONTACTS);
        runtimeMenuItem.setAppLinkPublicKey(publicKey);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Payment request");
        runtimeMenuItem.setIcon("request");
        runtimeMenuItem.setLinkToActivity(Activities.CWP_WALLET_RUNTIME_WALLET_BASIC_WALLET_BITDUBAI_VERSION_1_PAYMENT_REQUEST);
        runtimeMenuItem.setAppLinkPublicKey(publicKey);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Settings");
        runtimeMenuItem.setIcon("settings");
        runtimeMenuItem.setLinkToActivity(Activities.CCP_BITCOIN_WALLET_SETTINGS_ACTIVITY);
        runtimeMenuItem.setAppLinkPublicKey(publicKey);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Logout");
        runtimeMenuItem.setLinkToActivity(Activities.DEVELOP_MODE);
        runtimeMenuItem.setAppLinkPublicKey(publicKey);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeActivity.setSideMenu(runtimeSideMenu);

        //fin navigation


//        /**
//         * Menu
//         */
//
        runtimeMainMenu = new MainMenu();
        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setIcon("send");
        runtimeMainMenu.addMenuItem(runtimeMenuItem);


        runtimeActivity.setMainMenu(runtimeMainMenu);
//
//        /**
//         *  Fin de menu
//         */

        /**
         * Wizard
         */
         /* Adding WizardTypes */
        Wizard runtimeWizard = new Wizard();
        // step 1 wizard create from scratch
        WizardPage runtimeWizardPage = new WizardPage();
        runtimeWizardPage.setFragment(Fragments.CCP_BITCOIN_WALLET_NO_IDENTITY_FRAGMENT.getKey());
        runtimeWizard.addPage(runtimeWizardPage);
            /* Adding wizard */
        runtimeActivity.addWizard(WizardTypes.CCP_WALLET_BITCOIN_START_WIZARD.getKey(), runtimeWizard);

        /**
         * Transaction Activity
         */

        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CWP_WALLET_RUNTIME_WALLET_BASIC_WALLET_BITDUBAI_VERSION_1_TRANSACTIONS);
        runtimeActivity.setBackActivity(Activities.CWP_WALLET_RUNTIME_WALLET_BASIC_WALLET_BITDUBAI_VERSION_1_MAIN);

        runtimeWalletNavigationStructure.addActivity(runtimeActivity);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("bitdubai bitcoin Wallet");
        runtimeTitleBar.setLabelSize(16);
        runtimeTitleBar.setTitleColor("#ffffff");
        runtimeTitleBar.setColor("#12aca1");
        runtimeActivity.setTitleBar(runtimeTitleBar);
        runtimeActivity.setColor("#12aca1");
        //runtimeActivity.setColor("#d07b62");


        runtimeStatusBar = new com.bitdubai.fermat_api.layer.all_definition.navigation_structure.StatusBar();
        runtimeStatusBar.setColor("#12aca1");

        runtimeTabStrip = new TabStrip();

        runtimeTabStrip.setTabsColor("#1173aa");

        runtimeTabStrip.setTabsTextColor("#FFFFFF");

        runtimeTabStrip.setTabsIndicateColor("#FFFFFF");

        runtimeTab = new Tab();
        runtimeTab.setLabel("Sent");
        runtimeTab.setFragment(Fragments.CCP_BITCOIN_WALLET_TRANSACTIONS_SENT_HISTORY);
        runtimeTabStrip.addTab(runtimeTab);

        runtimeTab = new Tab();
        runtimeTab.setLabel("Received");
        runtimeTab.setFragment(Fragments.CCP_BITCOIN_WALLET_TRANSACTIONS_RECEIVED_HISTORY);
        runtimeTabStrip.addTab(runtimeTab);

        runtimeTabStrip.setDividerColor(0x72af9c);
        //runtimeTabStrip.setBackgroundColor("#72af9c");
        runtimeActivity.setTabStrip(runtimeTabStrip);

        runtimeActivity.setStatusBar(runtimeStatusBar);

        runtimeActivity.setStartFragment(Fragments.CCP_BITCOIN_WALLET_TRANSACTIONS_SENT_HISTORY.getKey());

        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.CCP_BITCOIN_WALLET_TRANSACTIONS_SENT_HISTORY.getKey());
        runtimeActivity.addFragment(Fragments.CCP_BITCOIN_WALLET_TRANSACTIONS_SENT_HISTORY.getKey(), runtimeFragment);
        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.CCP_BITCOIN_WALLET_TRANSACTIONS_RECEIVED_HISTORY.getKey());
        runtimeActivity.addFragment(Fragments.CCP_BITCOIN_WALLET_TRANSACTIONS_RECEIVED_HISTORY.getKey(), runtimeFragment);
//Navigation

        runtimeSideMenu = new SideMenu();
        runtimeSideMenu.setBackgroundColor("#ffffff");
        runtimeSideMenu.setNavigationIconColor("#ffffff");

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Home");
        runtimeMenuItem.setIcon("home");
        runtimeMenuItem.setLinkToActivity(Activities.CWP_WALLET_RUNTIME_WALLET_BASIC_WALLET_BITDUBAI_VERSION_1_MAIN);
        runtimeMenuItem.setAppLinkPublicKey(publicKey);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Contacts");
        runtimeMenuItem.setIcon("contacts");
        runtimeMenuItem.setLinkToActivity(Activities.CWP_WALLET_RUNTIME_WALLET_BASIC_WALLET_BITDUBAI_VERSION_1_CONTACTS);
        runtimeMenuItem.setAppLinkPublicKey(publicKey);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Payment request");
        runtimeMenuItem.setIcon("request");
        runtimeMenuItem.setLinkToActivity(Activities.CWP_WALLET_RUNTIME_WALLET_BASIC_WALLET_BITDUBAI_VERSION_1_PAYMENT_REQUEST);
        runtimeMenuItem.setAppLinkPublicKey(publicKey);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Settings");
        runtimeMenuItem.setIcon("settings");
        runtimeMenuItem.setLinkToActivity(Activities.CCP_BITCOIN_WALLET_SETTINGS_ACTIVITY);
        runtimeMenuItem.setAppLinkPublicKey(publicKey);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Logout");
        runtimeMenuItem.setLinkToActivity(Activities.CWP_WALLET_RUNTIME_WALLET_BASIC_WALLET_BITDUBAI_VERSION_1_MAIN);
        runtimeMenuItem.setAppLinkPublicKey(publicKey);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeActivity.setSideMenu(runtimeSideMenu);

        //fin navigation


        /**
         * Payment request Activity
         */

        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CWP_WALLET_RUNTIME_WALLET_BASIC_WALLET_BITDUBAI_VERSION_1_PAYMENT_REQUEST);
        runtimeActivity.setColor("#8bba9e");
        runtimeActivity.setBackActivity(Activities.CWP_WALLET_RUNTIME_WALLET_BASIC_WALLET_BITDUBAI_VERSION_1_MAIN);
        runtimeActivity.setBackPublicKey(publicKey);
        runtimeWalletNavigationStructure.addActivity(runtimeActivity);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Payment's request");
        runtimeTitleBar.setLabelSize(16);
        runtimeTitleBar.setTitleColor("#ffffff");
        runtimeTitleBar.setColor("#12aca1");
        runtimeActivity.setTitleBar(runtimeTitleBar);
        runtimeActivity.setColor("#12aca1");
        //runtimeActivity.setColor("#d07b62");


        runtimeStatusBar = new com.bitdubai.fermat_api.layer.all_definition.navigation_structure.StatusBar();
        runtimeStatusBar.setColor("#12aca1");

        runtimeTabStrip = new TabStrip();

        runtimeTabStrip.setTabsColor("#1173aa");

        runtimeTabStrip.setTabsTextColor("#FFFFFF");

        runtimeTabStrip.setTabsIndicateColor("#FFFFFF");

        runtimeTab = new Tab();
        runtimeTab.setLabel("sent");
        runtimeTab.setFragment(Fragments.CCP_BITCOIN_WALLET_REQUEST_SENT_HISTORY);
        runtimeTabStrip.addTab(runtimeTab);

        runtimeTab = new Tab();
        runtimeTab.setLabel("Received");
        runtimeTab.setFragment(Fragments.CCP_BITCOIN_WALLET_REQUEST_RECEIVED_HISTORY);
        runtimeTabStrip.addTab(runtimeTab);

        runtimeTabStrip.setDividerColor(0x72af9c);
        //runtimeTabStrip.setBackgroundColor("#72af9c");
        runtimeActivity.setTabStrip(runtimeTabStrip);

        runtimeActivity.setStatusBar(runtimeStatusBar);

        runtimeActivity.setStartFragment(Fragments.CCP_BITCOIN_WALLET_REQUEST_SENT_HISTORY.getKey());

        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.CCP_BITCOIN_WALLET_REQUEST_SENT_HISTORY.getKey());
        runtimeActivity.addFragment(Fragments.CCP_BITCOIN_WALLET_REQUEST_SENT_HISTORY.getKey(), runtimeFragment);
        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.CCP_BITCOIN_WALLET_REQUEST_RECEIVED_HISTORY.getKey());
        runtimeActivity.addFragment(Fragments.CCP_BITCOIN_WALLET_REQUEST_RECEIVED_HISTORY.getKey(), runtimeFragment);

        //Navigation

        runtimeSideMenu = new SideMenu();
        runtimeSideMenu.setBackgroundColor("#ffffff");
        runtimeSideMenu.setNavigationIconColor("#ffffff");

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Home");
        runtimeMenuItem.setIcon("home");
        runtimeMenuItem.setLinkToActivity(Activities.CWP_WALLET_RUNTIME_WALLET_BASIC_WALLET_BITDUBAI_VERSION_1_MAIN);
        runtimeMenuItem.setAppLinkPublicKey(publicKey);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Contacts");
        runtimeMenuItem.setIcon("contacts");
        runtimeMenuItem.setLinkToActivity(Activities.CWP_WALLET_RUNTIME_WALLET_BASIC_WALLET_BITDUBAI_VERSION_1_CONTACTS);
        runtimeMenuItem.setAppLinkPublicKey(publicKey);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Payment request");
        runtimeMenuItem.setIcon("request");
        runtimeMenuItem.setLinkToActivity(Activities.CWP_WALLET_RUNTIME_WALLET_BASIC_WALLET_BITDUBAI_VERSION_1_PAYMENT_REQUEST);
        runtimeMenuItem.setAppLinkPublicKey(publicKey);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Settings");
        runtimeMenuItem.setIcon("settings");
        runtimeMenuItem.setLinkToActivity(Activities.CCP_BITCOIN_WALLET_SETTINGS_ACTIVITY);
        runtimeMenuItem.setAppLinkPublicKey(publicKey);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Logout");
        runtimeMenuItem.setLinkToActivity(Activities.CWP_WALLET_RUNTIME_WALLET_BASIC_WALLET_BITDUBAI_VERSION_1_MAIN);
        runtimeMenuItem.setAppLinkPublicKey(publicKey);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeActivity.setSideMenu(runtimeSideMenu);

        //fin navigation
        /**
         * Contacts Activity
         */

        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CWP_WALLET_RUNTIME_WALLET_BASIC_WALLET_BITDUBAI_VERSION_1_CONTACTS);
        runtimeActivity.setActivityType(Activities.CWP_WALLET_RUNTIME_WALLET_BASIC_WALLET_BITDUBAI_VERSION_1_CONTACTS.getCode());
        runtimeActivity.setColor("#12aca1");
        runtimeActivity.setBackActivity(Activities.CWP_WALLET_RUNTIME_WALLET_BASIC_WALLET_BITDUBAI_VERSION_1_MAIN);
        runtimeActivity.setBackPublicKey(publicKey);

        runtimeWalletNavigationStructure.addActivity(runtimeActivity);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Contacts");
        runtimeTitleBar.setLabelSize(16);
        runtimeTitleBar.setTitleColor("#ffffff");
        runtimeTitleBar.setColor("#12aca1");
        runtimeActivity.setTitleBar(runtimeTitleBar);
        runtimeActivity.setColor("#12aca1");
        //runtimeActivity.setColor("#d07b62");

        runtimeStatusBar = new com.bitdubai.fermat_api.layer.all_definition.navigation_structure.StatusBar();
        runtimeStatusBar.setColor("#12aca1");

        runtimeTabStrip = new TabStrip();

        runtimeTabStrip.setTabsColor("#1173aa");

        runtimeTabStrip.setTabsTextColor("#FFFFFF");

        runtimeTabStrip.setTabsIndicateColor("#FFFFFF");

        runtimeActivity.setStatusBar(runtimeStatusBar);

        runtimeActivity.setStartFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_CONTACTS.getKey());

        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_CONTACTS.getKey());
        runtimeActivity.addFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_CONTACTS.getKey(), runtimeFragment);

        //Navigation

        runtimeSideMenu = new SideMenu();
        runtimeSideMenu.setBackgroundColor("#ffffff");
        runtimeSideMenu.setNavigationIconColor("#ffffff");

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Home");
        runtimeMenuItem.setIcon("home");
        runtimeMenuItem.setLinkToActivity(Activities.CWP_WALLET_RUNTIME_WALLET_BASIC_WALLET_BITDUBAI_VERSION_1_MAIN);
        runtimeMenuItem.setAppLinkPublicKey(publicKey);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Contacts");
        runtimeMenuItem.setIcon("contacts");
        runtimeMenuItem.setLinkToActivity(Activities.CWP_WALLET_RUNTIME_WALLET_BASIC_WALLET_BITDUBAI_VERSION_1_CONTACTS);
        runtimeMenuItem.setAppLinkPublicKey(publicKey);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Payment request");
        runtimeMenuItem.setIcon("request");
        runtimeMenuItem.setLinkToActivity(Activities.CWP_WALLET_RUNTIME_WALLET_BASIC_WALLET_BITDUBAI_VERSION_1_PAYMENT_REQUEST);
        runtimeMenuItem.setAppLinkPublicKey(publicKey);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Settings");
        runtimeMenuItem.setIcon("settings");
        runtimeMenuItem.setLinkToActivity(Activities.CCP_BITCOIN_WALLET_SETTINGS_ACTIVITY);
        runtimeMenuItem.setAppLinkPublicKey(publicKey);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Logout");
        runtimeMenuItem.setLinkToActivity(Activities.CWP_WALLET_RUNTIME_WALLET_BASIC_WALLET_BITDUBAI_VERSION_1_MAIN);
        runtimeMenuItem.setAppLinkPublicKey(publicKey);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeActivity.setSideMenu(runtimeSideMenu);

        //fin navigation


        /**
         * Send form Activity
         */

        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CCP_BITCOIN_WALLET_SEND_FORM_ACTIVITY);
        runtimeActivity.setActivityType(Activities.CCP_BITCOIN_WALLET_SEND_FORM_ACTIVITY.getCode());
        runtimeActivity.setColor("#12aca1");
        runtimeActivity.setBackActivity(Activities.CWP_WALLET_RUNTIME_WALLET_BASIC_WALLET_BITDUBAI_VERSION_1_MAIN);
        runtimeActivity.setBackPublicKey(publicKey);

        runtimeWalletNavigationStructure.addActivity(runtimeActivity);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Send");
        runtimeTitleBar.setLabelSize(16);
        runtimeTitleBar.setTitleColor("#ffffff");
        runtimeTitleBar.setColor("#12aca1");
        runtimeActivity.setTitleBar(runtimeTitleBar);
        runtimeActivity.setColor("#12aca1");
        //runtimeActivity.setColor("#d07b62");

        runtimeStatusBar = new com.bitdubai.fermat_api.layer.all_definition.navigation_structure.StatusBar();
        runtimeStatusBar.setColor("#12aca1");

        runtimeTabStrip = new TabStrip();

        runtimeTabStrip.setTabsColor("#1173aa");

        runtimeTabStrip.setTabsTextColor("#FFFFFF");

        runtimeTabStrip.setTabsIndicateColor("#FFFFFF");

        runtimeActivity.setStatusBar(runtimeStatusBar);

        runtimeActivity.setStartFragment(Fragments.CCP_BITCOIN_WALLET_SEND_FORM_FRAGMENT.getKey());

        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.CCP_BITCOIN_WALLET_SEND_FORM_FRAGMENT.getKey());
        runtimeActivity.addFragment(Fragments.CCP_BITCOIN_WALLET_SEND_FORM_FRAGMENT.getKey(), runtimeFragment);

        /**
         * Request form Activity
         */

        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CCP_BITCOIN_WALLET_REQUEST_FORM_ACTIVITY);
        runtimeActivity.setActivityType(Activities.CCP_BITCOIN_WALLET_REQUEST_FORM_ACTIVITY.getCode());
        runtimeActivity.setColor("#12aca1");
        runtimeActivity.setBackActivity(Activities.CCP_BITCOIN_WALLET_CONTACT_DETAIL_ACTIVITY);
        runtimeActivity.setBackPublicKey(publicKey);

        runtimeWalletNavigationStructure.addActivity(runtimeActivity);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Request");
        runtimeTitleBar.setLabelSize(16);
        runtimeTitleBar.setColor("#12aca1");
        runtimeActivity.setTitleBar(runtimeTitleBar);
        runtimeActivity.setColor("#12aca1");
        //runtimeActivity.setColor("#d07b62");

        runtimeStatusBar = new com.bitdubai.fermat_api.layer.all_definition.navigation_structure.StatusBar();
        runtimeStatusBar.setColor("#12aca1");

        runtimeTabStrip = new TabStrip();

        runtimeTabStrip.setTabsColor("#1173aa");

        runtimeTabStrip.setTabsTextColor("#FFFFFF");

        runtimeTabStrip.setTabsIndicateColor("#FFFFFF");

        runtimeActivity.setStatusBar(runtimeStatusBar);

        runtimeActivity.setStartFragment(Fragments.CCP_BITCOIN_WALLET_REQUEST_FORM_FRAGMENT.getKey());

        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.CCP_BITCOIN_WALLET_REQUEST_FORM_FRAGMENT.getKey());
        runtimeActivity.addFragment(Fragments.CCP_BITCOIN_WALLET_REQUEST_FORM_FRAGMENT.getKey(), runtimeFragment);

        /**
         * No identity Activity
         */

        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CCP_BITCOIN_WALLET_NO_IDENTITY_ACTIVITY);
        runtimeActivity.setActivityType(Activities.CCP_BITCOIN_WALLET_NO_IDENTITY_ACTIVITY.getCode());
        runtimeActivity.setColor("#12aca1");
        //runtimeActivity.setBackPublicKey(publicKey);

        runtimeWalletNavigationStructure.addActivity(runtimeActivity);
//        runtimeWalletNavigationStructure.setStartActivity(Activities.CCP_BITCOIN_WALLET_NO_IDENTITY_ACTIVITY);

        runtimeStatusBar = new com.bitdubai.fermat_api.layer.all_definition.navigation_structure.StatusBar();
        runtimeStatusBar.setColor("#12aca1");

        runtimeActivity.setStatusBar(runtimeStatusBar);

        runtimeActivity.setStartFragment(Fragments.CCP_BITCOIN_WALLET_NO_IDENTITY_FRAGMENT.getKey());

        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.CCP_BITCOIN_WALLET_NO_IDENTITY_FRAGMENT.getKey());
        runtimeActivity.addFragment(Fragments.CCP_BITCOIN_WALLET_NO_IDENTITY_FRAGMENT.getKey(), runtimeFragment);

        /**
         * Contact detail Activity
         */

        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CCP_BITCOIN_WALLET_CONTACT_DETAIL_ACTIVITY);
        runtimeActivity.setActivityType(Activities.CCP_BITCOIN_WALLET_CONTACT_DETAIL_ACTIVITY.getCode());
        runtimeActivity.setColor("#12aca1");
        runtimeActivity.setBackActivity(Activities.CWP_WALLET_RUNTIME_WALLET_BASIC_WALLET_BITDUBAI_VERSION_1_CONTACTS);

        runtimeWalletNavigationStructure.addActivity(runtimeActivity);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Contact detail");
        runtimeTitleBar.setLabelSize(16);
        runtimeTitleBar.setColor("#12aca1");
        runtimeActivity.setTitleBar(runtimeTitleBar);
        runtimeActivity.setColor("#12aca1");
        //runtimeActivity.setColor("#d07b62");

        runtimeStatusBar = new com.bitdubai.fermat_api.layer.all_definition.navigation_structure.StatusBar();
        runtimeStatusBar.setColor("#12aca1");

        runtimeTabStrip = new TabStrip();

        runtimeTabStrip.setTabsColor("#1173aa");

        runtimeTabStrip.setTabsTextColor("#FFFFFF");

        runtimeTabStrip.setTabsIndicateColor("#FFFFFF");

        runtimeActivity.setStatusBar(runtimeStatusBar);

        runtimeActivity.setStartFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_DETAIL_CONTACTS.getKey());

        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_DETAIL_CONTACTS.getKey());
        runtimeActivity.addFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_DETAIL_CONTACTS.getKey(), runtimeFragment);

        /**
         * Contacts Activity
         */

        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CCP_BITCOIN_WALLET_SETTINGS_ACTIVITY);
        runtimeActivity.setActivityType(Activities.CCP_BITCOIN_WALLET_SETTINGS_ACTIVITY.getCode());
        runtimeActivity.setColor("#12aca1");
        runtimeActivity.setBackActivity(Activities.CWP_WALLET_RUNTIME_WALLET_BASIC_WALLET_BITDUBAI_VERSION_1_MAIN);
        runtimeActivity.setBackPublicKey(publicKey);

        runtimeWalletNavigationStructure.addActivity(runtimeActivity);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Settings");
        runtimeTitleBar.setLabelSize(16);
        runtimeTitleBar.setTitleColor("#ffffff");
        runtimeTitleBar.setColor("#12aca1");
        runtimeActivity.setTitleBar(runtimeTitleBar);
        runtimeActivity.setColor("#12aca1");
        //runtimeActivity.setColor("#d07b62");

        runtimeStatusBar = new com.bitdubai.fermat_api.layer.all_definition.navigation_structure.StatusBar();
        runtimeStatusBar.setColor("#12aca1");

        runtimeTabStrip = new TabStrip();

        runtimeTabStrip.setTabsColor("#1173aa");

        runtimeTabStrip.setTabsTextColor("#FFFFFF");

        runtimeTabStrip.setTabsIndicateColor("#FFFFFF");

        runtimeActivity.setStatusBar(runtimeStatusBar);

        runtimeActivity.setStartFragment(Fragments.CCP_BITCOIN_WALLET_SETTINGS_FRAGMENT.getKey());

        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.CCP_BITCOIN_WALLET_SETTINGS_FRAGMENT.getKey());
        runtimeActivity.addFragment(Fragments.CCP_BITCOIN_WALLET_SETTINGS_FRAGMENT.getKey(), runtimeFragment);

        //Navigation

        runtimeSideMenu = new SideMenu();
        runtimeSideMenu.setBackgroundColor("#ffffff");
        runtimeSideMenu.setNavigationIconColor("#ffffff");

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Home");
        runtimeMenuItem.setIcon("home");
        runtimeMenuItem.setLinkToActivity(Activities.CWP_WALLET_RUNTIME_WALLET_BASIC_WALLET_BITDUBAI_VERSION_1_MAIN);
        runtimeMenuItem.setAppLinkPublicKey(publicKey);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Contacts");
        runtimeMenuItem.setIcon("contacts");
        runtimeMenuItem.setLinkToActivity(Activities.CWP_WALLET_RUNTIME_WALLET_BASIC_WALLET_BITDUBAI_VERSION_1_CONTACTS);
        runtimeMenuItem.setAppLinkPublicKey(publicKey);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Payment request");
        runtimeMenuItem.setIcon("request");
        runtimeMenuItem.setLinkToActivity(Activities.CWP_WALLET_RUNTIME_WALLET_BASIC_WALLET_BITDUBAI_VERSION_1_PAYMENT_REQUEST);
        runtimeMenuItem.setAppLinkPublicKey(publicKey);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Settings");
        runtimeMenuItem.setIcon("settings");
        runtimeMenuItem.setLinkToActivity(Activities.CCP_BITCOIN_WALLET_SETTINGS_ACTIVITY);
        runtimeMenuItem.setAppLinkPublicKey(publicKey);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeMenuItem = new MenuItem();
        runtimeMenuItem.setLabel("Logout");
        runtimeMenuItem.setLinkToActivity(Activities.CWP_WALLET_RUNTIME_WALLET_BASIC_WALLET_BITDUBAI_VERSION_1_MAIN);
        runtimeMenuItem.setAppLinkPublicKey(publicKey);
        runtimeSideMenu.addMenuItem(runtimeMenuItem);

        runtimeActivity.setSideMenu(runtimeSideMenu);

        //fin navigation
        /**
         * Contact detail Activity
         */

        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CCP_BITCOIN_WALLET_CONTACT_DETAIL_ACTIVITY);
        runtimeActivity.setActivityType(Activities.CCP_BITCOIN_WALLET_CONTACT_DETAIL_ACTIVITY.getCode());
        runtimeActivity.setColor("#12aca1");
        runtimeActivity.setBackActivity(Activities.CWP_WALLET_RUNTIME_WALLET_BASIC_WALLET_BITDUBAI_VERSION_1_CONTACTS);
        runtimeActivity.setBackPublicKey(publicKey);

        runtimeWalletNavigationStructure.addActivity(runtimeActivity);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Contact detail");
        runtimeTitleBar.setLabelSize(16);
        runtimeTitleBar.setColor("#12aca1");
        runtimeActivity.setTitleBar(runtimeTitleBar);
        runtimeActivity.setColor("#12aca1");
        //runtimeActivity.setColor("#d07b62");

        runtimeStatusBar = new com.bitdubai.fermat_api.layer.all_definition.navigation_structure.StatusBar();
        runtimeStatusBar.setColor("#12aca1");

        runtimeTabStrip = new TabStrip();

        runtimeTabStrip.setTabsColor("#1173aa");

        runtimeTabStrip.setTabsTextColor("#FFFFFF");

        runtimeTabStrip.setTabsIndicateColor("#FFFFFF");

        runtimeActivity.setStatusBar(runtimeStatusBar);

        runtimeActivity.setStartFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_DETAIL_CONTACTS.getKey());

        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_DETAIL_CONTACTS.getKey());
        runtimeActivity.addFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_DETAIL_CONTACTS.getKey(), runtimeFragment);

        /**
         * Add connection Activity
         */

        runtimeActivity = new Activity();
        runtimeActivity.setType(Activities.CCP_BITCOIN_WALLET_ADD_CONNECTION_ACTIVITY);
        runtimeActivity.setActivityType(Activities.CCP_BITCOIN_WALLET_ADD_CONNECTION_ACTIVITY.getCode());
        runtimeActivity.setColor("#12aca1");
        runtimeActivity.setBackActivity(Activities.CWP_WALLET_RUNTIME_WALLET_BASIC_WALLET_BITDUBAI_VERSION_1_CONTACTS);
        runtimeActivity.setBackPublicKey(publicKey);

        runtimeWalletNavigationStructure.addActivity(runtimeActivity);

        runtimeTitleBar = new TitleBar();
        runtimeTitleBar.setLabel("Add Fermat user");
        runtimeTitleBar.setLabelSize(16);
        runtimeTitleBar.setTitleColor("#ffffff");
        runtimeTitleBar.setColor("#12aca1");
        runtimeActivity.setTitleBar(runtimeTitleBar);
        runtimeActivity.setColor("#12aca1");
        //runtimeActivity.setColor("#d07b62");

        runtimeStatusBar = new com.bitdubai.fermat_api.layer.all_definition.navigation_structure.StatusBar();
        runtimeStatusBar.setColor("#12aca1");

        runtimeTabStrip = new TabStrip();

        runtimeTabStrip.setTabsColor("#1173aa");

        runtimeTabStrip.setTabsTextColor("#FFFFFF");

        runtimeTabStrip.setTabsIndicateColor("#FFFFFF");

        runtimeActivity.setStatusBar(runtimeStatusBar);

        runtimeActivity.setStartFragment(Fragments.CCP_BITCOIN_WALLET_ADD_CONNECTION_FRAGMENT.getKey());

        runtimeFragment = new Fragment();
        runtimeFragment.setType(Fragments.CCP_BITCOIN_WALLET_ADD_CONNECTION_FRAGMENT.getKey());
        runtimeActivity.addFragment(Fragments.CCP_BITCOIN_WALLET_ADD_CONNECTION_FRAGMENT.getKey(), runtimeFragment);


        return runtimeWalletNavigationStructure;
    }
}
