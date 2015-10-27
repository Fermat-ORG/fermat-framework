/*
 * @#WalletPublisherModulePluginRoot.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_wpd_plugin.layer.sub_app_module.wallet_publisher.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.developer.LogManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_wpd_api.layer.wpd_identity.publisher.exceptions.CantSingMessageException;
import com.bitdubai.fermat_wpd_api.layer.wpd_identity.publisher.interfaces.PublisherIdentity;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletFactoryProjectState;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_factory.exceptions.CantGetWalletFactoryProjectException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_factory.interfaces.DealsWithWalletFactory;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_factory.interfaces.WalletFactoryProject;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_factory.interfaces.WalletFactoryProjectManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_publisher.interfaces.DealsWithWalletPublisherMiddlewarePlugin;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_publisher.interfaces.WalletPublisherMiddlewarePlugin;
import com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_publisher.exceptions.CantGetPublishedComponentInformationException;
import com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_publisher.exceptions.CantPublishComponentException;
import com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_publisher.interfaces.InformationPublishedComponent;
import com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_publisher.interfaces.WalletPublisherModuleManager;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.DealsWithLogger;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.DealsWithEvents;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.platform_info.interfaces.DealsWithPlatformInfo;
import com.bitdubai.fermat_pip_api.layer.platform_service.platform_info.interfaces.PlatformInfoManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.platform_info.interfaces.exceptions.CantLoadPlatformInformationException;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_publisher.developer.bitdubai.version_1.WalletPublisherMiddlewarePluginRoot</code> is
 * the responsible to communicate the user interface whit the middleware layer.
 * <p/>
 * <p/>
 * Created by loui on 05/02/15.
 * Update by Roberto Requena - (rart3001@gmail.com) on 04/08/2015
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class WalletPublisherModuleModulePluginRootPlugin implements Service, DealsWithPlatformInfo, DealsWithWalletFactory, DealsWithWalletPublisherMiddlewarePlugin, DealsWithEvents, DealsWithErrors, DealsWithLogger, LogManagerForDevelopers, Plugin, WalletPublisherModuleManager {
    /**
     * Represent the newLoggingLevel
     */
    static Map<String, LogLevel> newLoggingLevel = new HashMap<>();
    /**
     * Represent the logManager
     */
    private LogManager logManager;
    /**
     * Represent the errorManager
     */
    private ErrorManager errorManager;

    /**
     * DealWithEvents Interface member variables.
     */
    private EventManager eventManager;

    /**
     * Represent the plugin id
     */
    private UUID pluginId;

    /**
     * Represent the status of the service
     */
    private ServiceStatus serviceStatus;

    /**
     * Represent the listenersAdded
     */
    private List<FermatEventListener> listenersAdded;

    /**
     * Represent the walletPublisherMiddlewarePlugin
     */
    private WalletPublisherMiddlewarePlugin walletPublisherMiddlewarePlugin;

    /**
     * Represent the walletFactoryProjectManager
     */
    private WalletFactoryProjectManager walletFactoryProjectManager;

    /**
     * Represent the platformInfoManager
     */
    private PlatformInfoManager platformInfoManager;

    /**
     * Constructor
     */
    public WalletPublisherModuleModulePluginRootPlugin() {
        serviceStatus = ServiceStatus.CREATED;
        listenersAdded = new ArrayList<>();
    }


    /**
     * This method validate is all required resource are injected into
     * the plugin root by the platform
     *
     * @throws CantStartPluginException
     */
    private void validateInjectedResources() throws CantStartPluginException {

        /*
         * Validate If all resources are not null
         */
        if (logManager == null || errorManager == null ||
                walletPublisherMiddlewarePlugin == null) {

            StringBuffer contextBuffer = new StringBuffer();
            contextBuffer.append("Plugin ID: " + pluginId);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("logManager: " + logManager);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("errorManager: " + errorManager);
            contextBuffer.append(CantStartPluginException.CONTEXT_CONTENT_SEPARATOR);
            contextBuffer.append("walletPublisherMiddlewarePlugin: " + walletPublisherMiddlewarePlugin);

            String context = contextBuffer.toString();
            String possibleCause = "No all required resource are injected";
            CantStartPluginException pluginStartException = new CantStartPluginException("CAN'T START MODULE", null, context, possibleCause);

            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WPD_WALLET_PUBLISHER_MIDDLEWARE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, pluginStartException);
            throw pluginStartException;
        }

    }

    /**
     * (non-Javadoc)
     *
     * @see Service#start()
     */
    @Override
    public void start() throws CantStartPluginException {

        /*
         * Validate required resources
         */
        validateInjectedResources();

        FermatEventListener fermatEventListener;
        FermatEventHandler fermatEventHandler;

        this.serviceStatus = ServiceStatus.STARTED;

    }

    /**
     * (non-Javadoc)
     *
     * @see Service#pause()
     */
    @Override
    public void pause() {
        this.serviceStatus = ServiceStatus.PAUSED;
    }

    /**
     * (non-Javadoc)
     *
     * @see Service#resume()
     */
    @Override
    public void resume() {
        this.serviceStatus = ServiceStatus.STARTED;
    }

    /**
     * (non-Javadoc)
     *
     * @see Service#stop()
     */
    @Override
    public void stop() {
        /**
         * I will remove all the event listeners registered with the event manager.
         */

        for (FermatEventListener fermatEventListener : listenersAdded) {
            eventManager.removeListener(fermatEventListener);
        }

        listenersAdded.clear();
        this.serviceStatus = ServiceStatus.STOPPED;

    }

    /**
     * (non-Javadoc)
     *
     * @see Service#getStatus()
     */
    @Override
    public ServiceStatus getStatus() {
        return this.serviceStatus;
    }


    /**
     * DealWithEvents Interface implementation.
     */

    @Override
    public void setEventManager(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    /**
     * (non-Javadoc)
     *
     * @see DealsWithErrors#setErrorManager(ErrorManager)
     */
    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }


    /**
     * (non-Javadoc)
     *
     * @see DealsWithWalletFactory#setWalletFactoryProjectManager(WalletFactoryProjectManager)
     */
    @Override
    public void setWalletFactoryProjectManager(WalletFactoryProjectManager walletFactoryProjectManager) {
        this.walletFactoryProjectManager = walletFactoryProjectManager;
    }

    /**
     * (non-Javadoc)
     *
     * @see DealsWithPlatformInfo#setPlatformInfoManager(PlatformInfoManager)
     */
    @Override
    public void setPlatformInfoManager(PlatformInfoManager platformInfoManager) {
        this.platformInfoManager = platformInfoManager;
    }

    /**
     * (non-Javadoc)
     *
     * @see Plugin#setId(UUID)
     */
    @Override
    public void setId(UUID pluginId) {
        this.pluginId = pluginId;
    }


    /**
     * (non-Javadoc)
     *
     * @see DealsWithLogger#setLogManager(LogManager)
     */
    @Override
    public void setLogManager(LogManager logManager) {
        this.logManager = logManager;
    }

    /**
     * (non-Javadoc)
     *
     * @see LogManagerForDevelopers#getClassesFullPath()
     */
    @Override
    public List<String> getClassesFullPath() {
        List<String> returnedClasses = new ArrayList<String>();
        returnedClasses.add("com.bitdubai.fermat_dmp_plugin.layer.module.wallet_publisher.developer.bitdubai.version_1.WalletPublisherModuleModulePluginRootPlugin");

        /**
         * I return the values.
         */
        return returnedClasses;
    }

    /**
     * (non-Javadoc)
     *
     * @see LogManagerForDevelopers#setLoggingLevelPerClass(Map<String, LogLevel>)
     */
    @Override
    public void setLoggingLevelPerClass(Map<String, LogLevel> newLoggingLevel) {
        /**
         * I will check the current values and update the LogLevel in those which is different
         */

        for (Map.Entry<String, LogLevel> pluginPair : newLoggingLevel.entrySet()) {
            /**
             * if this path already exists in the Root.bewLoggingLevel I'll update the value, else, I will put as new
             */
            if (WalletPublisherModuleModulePluginRootPlugin.newLoggingLevel.containsKey(pluginPair.getKey())) {
                WalletPublisherModuleModulePluginRootPlugin.newLoggingLevel.remove(pluginPair.getKey());
                WalletPublisherModuleModulePluginRootPlugin.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            } else {
                WalletPublisherModuleModulePluginRootPlugin.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            }
        }

    }

    /**
     * (non-Javadoc)
     *
     * @see WalletPublisherModuleManager#getProjectsReadyToPublish()
     */
    @Override
    public List<WalletFactoryProject> getProjectsReadyToPublish() throws CantGetWalletFactoryProjectException {

        return (List<WalletFactoryProject>) walletFactoryProjectManager.getWalletFactoryProjectByState(WalletFactoryProjectState.CLOSED);

    }

    /**
     * (non-Javadoc)
     *
     * @see DealsWithWalletPublisherMiddlewarePlugin#setWalletPublisherMiddlewarePlugin(WalletPublisherMiddlewarePlugin)
     */
    @Override
    public void setWalletPublisherMiddlewarePlugin(WalletPublisherMiddlewarePlugin walletPublisherMiddlewarePlugin) {
        this.walletPublisherMiddlewarePlugin = walletPublisherMiddlewarePlugin;
    }

    /**
     * (non-Javadoc)
     *
     * @see WalletPublisherModuleManager#getPublishedComponents(PublisherIdentity)
     */
    @Override
    public List<InformationPublishedComponent> getPublishedComponents(PublisherIdentity publisherIdentity) throws CantGetPublishedComponentInformationException {
        return walletPublisherMiddlewarePlugin.getWalletPublisherMiddlewareManagerInstance().getPublishedComponents(publisherIdentity.getPublicKey());
    }

    /**
     * (non-Javadoc)
     *
     * @see WalletPublisherModuleManager#getPublishedWallets(PublisherIdentity)
     */
    @Override
    public List<InformationPublishedComponent> getPublishedWallets(PublisherIdentity publisherIdentity) throws CantGetPublishedComponentInformationException {
        return walletPublisherMiddlewarePlugin.getWalletPublisherMiddlewareManagerInstance().getPublishedWallets(publisherIdentity.getPublicKey());
    }

    /**
     * (non-Javadoc)
     *
     * @see WalletPublisherModuleManager#getPublishedSkins(PublisherIdentity)
     */
    @Override
    public List<InformationPublishedComponent> getPublishedSkins(PublisherIdentity publisherIdentity) throws CantGetPublishedComponentInformationException {
        return walletPublisherMiddlewarePlugin.getWalletPublisherMiddlewareManagerInstance().getPublishedSkins(publisherIdentity.getPublicKey());
    }

    /**
     * (non-Javadoc)
     *
     * @see WalletPublisherModuleManager#getPublishedLanguages(PublisherIdentity)
     */
    @Override
    public List<InformationPublishedComponent> getPublishedLanguages(PublisherIdentity publisherIdentity) throws CantGetPublishedComponentInformationException {
        return walletPublisherMiddlewarePlugin.getWalletPublisherMiddlewareManagerInstance().getPublishedLanguages(publisherIdentity.getPublicKey());
    }

    /**
     * (non-Javadoc)
     *
     * @see WalletPublisherModuleManager#getInformationPublishedComponentWithDetails(UUID)
     */
    @Override
    public InformationPublishedComponent getInformationPublishedComponentWithDetails(UUID idInformationPublishedComponent) throws CantGetPublishedComponentInformationException {
        return walletPublisherMiddlewarePlugin.getWalletPublisherMiddlewareManagerInstance().getInformationPublishedComponentWithDetails(idInformationPublishedComponent);
    }

    /**
     * (non-Javadoc)
     *
     * @see WalletPublisherModuleManager#publishSkin(WalletFactoryProject, byte[], byte[], List, URL, String, Version, Version, PublisherIdentity)
     */
    @Override
    public void publishSkin(WalletFactoryProject walletFactoryProject, byte[] icon, byte[] mainScreenShot, List<byte[]> screenShotDetails, URL videoUrl, String observations, Version initialWalletVersion, Version finalWalletVersion, PublisherIdentity publisherIdentity) throws CantPublishComponentException {

        try {

            /**
             * Create the signature
             */
            String signature = createSignature(walletFactoryProject, icon, mainScreenShot, screenShotDetails, videoUrl, observations, initialWalletVersion, finalWalletVersion, new Version(1, 0, 0), new Version(1, 0, 0), new URL(publisherIdentity.getWebsiteurl()), publisherIdentity);

            /*
             * Publish the wallet
             */
            walletPublisherMiddlewarePlugin.getWalletPublisherMiddlewareManagerInstance().publishSkin(walletFactoryProject, icon, mainScreenShot, screenShotDetails, videoUrl, observations, initialWalletVersion, finalWalletVersion, new URL(publisherIdentity.getWebsiteurl()), publisherIdentity.getPublicKey(), signature);

            /*
             * Mark the project like publish
             */
            walletFactoryProjectManager.markProkectAsPublished(walletFactoryProject);

        } catch (Exception exception) {
            throw new CantPublishComponentException(CantPublishComponentException.DEFAULT_MESSAGE, exception, "WalletPublisherModuleModulePluginRootPlugin", "unknown");
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see WalletPublisherModuleManager#publishLanguage(WalletFactoryProject, byte[], byte[], List, URL, String, Version, Version, PublisherIdentity)
     */
    @Override
    public void publishLanguage(WalletFactoryProject walletFactoryProject, byte[] icon, byte[] mainScreenShot, List<byte[]> screenShotDetails, URL videoUrl, String observations, Version initialWalletVersion, Version finalWalletVersion, PublisherIdentity publisherIdentity) throws CantPublishComponentException {

        try {

            /**
             * Create the signature
             */
            String signature = createSignature(walletFactoryProject, icon, mainScreenShot, screenShotDetails, videoUrl, observations, initialWalletVersion, finalWalletVersion, new Version(1, 0, 0), new Version(1, 0, 0), new URL(publisherIdentity.getWebsiteurl()), publisherIdentity);

            /*
             * Publish the wallet
             */
            walletPublisherMiddlewarePlugin.getWalletPublisherMiddlewareManagerInstance().publishLanguage(walletFactoryProject, icon, mainScreenShot, screenShotDetails, videoUrl, observations, initialWalletVersion, finalWalletVersion, new URL(publisherIdentity.getWebsiteurl()), publisherIdentity.getPublicKey(), signature);

            /*
             * Mark the project like publish
             */
            walletFactoryProjectManager.markProkectAsPublished(walletFactoryProject);

        } catch (Exception exception) {
            throw new CantPublishComponentException(CantPublishComponentException.DEFAULT_MESSAGE, exception, "WalletPublisherModuleModulePluginRootPlugin", "unknown");
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see WalletPublisherModuleManager#publishWallet(WalletFactoryProject, byte[], byte[], List, URL, String, Version, Version, PublisherIdentity)
     */
    public void publishWallet(WalletFactoryProject walletFactoryProject, byte[] icon, byte[] mainScreenShot, List<byte[]> screenShotDetails, URL videoUrl, String observations, Version initialPlatformVersion, Version finalPlatformVersion, PublisherIdentity publisherIdentity) throws CantPublishComponentException {

        try {

            /**
             * Create the signature
             */
            String signature = createSignature(walletFactoryProject, icon, mainScreenShot, screenShotDetails, videoUrl, observations, new Version(1, 0, 0), new Version(1, 0, 0), initialPlatformVersion, finalPlatformVersion, new URL(publisherIdentity.getWebsiteurl()), publisherIdentity);

            /*
             * Publish the wallet
             */
            walletPublisherMiddlewarePlugin.getWalletPublisherMiddlewareManagerInstance().publishWallet(walletFactoryProject, icon, mainScreenShot, screenShotDetails, videoUrl, observations, initialPlatformVersion, finalPlatformVersion, new URL(publisherIdentity.getWebsiteurl()), publisherIdentity.getPublicKey(), signature);

            /*
             * Mark the project like publish
             */
            walletFactoryProjectManager.markProkectAsPublished(walletFactoryProject);

        } catch (Exception exception) {
            throw new CantPublishComponentException(CantPublishComponentException.DEFAULT_MESSAGE, exception, "WalletPublisherModuleModulePluginRootPlugin", "unknown");
        }
    }


    /**
     * Create a signature that represent all data pass by parameters
     *
     * @param walletFactoryProject
     * @param icon
     * @param mainScreenShot
     * @param screenShotDetails
     * @param videoUrl
     * @param observations
     * @param initialWalletVersion
     * @param finalWalletVersion
     * @param initialPlatformVersion
     * @param finalPlatformVersion
     * @param publisherWebsiteUrl
     * @param publisherIdentity
     * @return String signature
     */
    private String createSignature(WalletFactoryProject walletFactoryProject, byte[] icon, byte[] mainScreenShot, List<byte[]> screenShotDetails, URL videoUrl, String observations, Version initialWalletVersion, Version finalWalletVersion, Version initialPlatformVersion, Version finalPlatformVersion, URL publisherWebsiteUrl, PublisherIdentity publisherIdentity) throws CantSingMessageException {

        /*
         * Construct a string with all the data
         */
        StringBuffer stringDataToSing = new StringBuffer("");

        stringDataToSing.append(walletFactoryProject.getProjectPublicKey());
        stringDataToSing.append(walletFactoryProject.getName());
        stringDataToSing.append(icon);
        stringDataToSing.append(mainScreenShot);

        for (byte[] data : screenShotDetails) {
            stringDataToSing.append(data);
        }

        stringDataToSing.append(videoUrl);
        stringDataToSing.append(observations);
        stringDataToSing.append(initialWalletVersion);
        stringDataToSing.append(finalWalletVersion);
        stringDataToSing.append(initialPlatformVersion);
        stringDataToSing.append(finalWalletVersion);
        stringDataToSing.append(finalPlatformVersion);
        stringDataToSing.append(publisherWebsiteUrl);

        /*
         * Sing the data and return
         */
        return publisherIdentity.createMessageSignature(stringDataToSing.toString());
    }


    /**
     * (non-Javadoc)
     *
     * @see @see WalletPublisherModuleManager#getPlatformVersions()
     */
    public List<Version> getPlatformVersions() throws com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_publisher.exceptions.CantLoadPlatformInformationException {

        List<Version> versions = new ArrayList<>();
        try {

            versions.add(platformInfoManager.getPlatformInfo().getVersion());

        } catch (CantLoadPlatformInformationException e) {
            e.printStackTrace();

            new com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_publisher.exceptions.CantLoadPlatformInformationException(e.getLocalizedMessage(), e, "Wallet Publisher", "");
        }

        return versions;

    }


}
