package com.bitdubai.fermat_cry_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.Addon;
import com.bitdubai.fermat_api.DealsWithPluginIdentity;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.developer.DealsWithLogManagers;
import com.bitdubai.fermat_api.layer.all_definition.developer.LogManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.dmp_world.wallet.exceptions.CantStartAgentException;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.DealsWithLogger;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_cry_api.layer.crypto_network.bitcoin.BitcoinCryptoNetworkManager;
import com.bitdubai.fermat_cry_api.layer.crypto_network.bitcoin.exceptions.CantConnectToBitcoinNetwork;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.CryptoVault;
import com.bitdubai.fermat_cry_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.exceptions.CantCreateBlockStoreFileException;
import com.bitdubai.fermat_cry_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.structure.BitcoinCryptoNetworkMonitoringAgent;
import org.bitcoinj.core.Wallet;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * Created by ciencias on 20.01.15.
 */

/**
 * This plugin interfaces the bitcoin network. It primary mission is to hold the bitcoins for each user on this device.
 * 
 * It handles a bitcoin wallet for each user process transactions upon request from other plugins.
 * 
 * It also monitors the bitcoin network for incoming transactions for any of the device's users.
 * 
 * 
 * * * * * * * *
 */

public class BitcoinCryptoNetworkPluginRoot implements BitcoinCryptoNetworkManager, DealsWithErrors, DealsWithPluginFileSystem, DealsWithLogger, LogManagerForDevelopers, Service, Plugin{

    /**
     * BitcoinCryptoNetworkManager interface member variables
     */
    CryptoVault cryptoVault;
    BitcoinCryptoNetworkMonitoringAgent bitcoinCryptoNetworkMonitoringAgent;

    /**
     * DealsWithErrors interface member variables
     */
    ErrorManager errorManager;

    /**
     * DealsWithPluginIdentity interface member variable
     */
    UUID pluginId;

    /**
     * DealsWithLogManager interface member variable
     */
    LogManager logManager;
    LogLevel logLevel;
    static Map<String, LogLevel> newLoggingLevel = new HashMap<String, LogLevel>();


    @Override
    public LogLevel getLoggingLevel() {
        return logLevel;
    }

    @Override
    public void changeLoggingLevel(LogLevel newLoggingLevel) {
        logLevel = newLoggingLevel;
    }

    @Override
    public List<String> getClassesFullPath() {
        /**
         * I create the filters and loaders for reflection.
         */
        List<ClassLoader> classLoadersList = new LinkedList<ClassLoader>();
        classLoadersList.add(ClasspathHelper.contextClassLoader());
        classLoadersList.add(ClasspathHelper.staticClassLoader());

        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setScanners(new SubTypesScanner(false), new ResourcesScanner())
                .setUrls(ClasspathHelper.forClassLoader(classLoadersList.toArray(new ClassLoader[0])))
                        /**
                         * I filter by the package name of the plug in Root.
                         */
                .filterInputsBy(new FilterBuilder().include(FilterBuilder.prefix(this.getClass().getName()))));

        Set<Class<?>> classes = reflections.getSubTypesOf(Object.class);
        Iterator<Class<?>> iterator = classes.iterator();

        /**
         * I insert the classes in the List and return it.
         */
        List<String> returnedClasses = new ArrayList<String>();
        while (iterator.hasNext()){
            String fullClass = iterator.next().getName();
            returnedClasses.add(fullClass);
        }
        if (returnedClasses.isEmpty()){
            returnedClasses.add("com.bitdubai.fermat_cry_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.BitcoinCryptoNetworkPluginRoot");
            returnedClasses.add("com.bitdubai.fermat_cry_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.structure.BitcoinCryptoNetworkMonitoringAgent");
            returnedClasses.add("com.bitdubai.fermat_cry_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.structure.BitcoinEventListeners");
            returnedClasses.add("com.bitdubai.fermat_cry_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.structure.StoredBlockChain");
            returnedClasses.add("com.bitdubai.fermat_cry_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.structure.BitcoinNetworkConfiguration");
        }


        /**
         * I return the values.
         */
        return returnedClasses;
    }

    @Override
    public void setLoggingLevelPerClass(Map<String, LogLevel> newLoggingLevel) {
        /**
         * I will check the current values and update the LogLevel in those which is different
         */

        for (Map.Entry<String, LogLevel> pluginPair : BitcoinCryptoNetworkPluginRoot.newLoggingLevel.entrySet()){
            /**
             * if the incoming value is different from what I already have, then Ill updated it
             */
            if (newLoggingLevel.containsKey(pluginPair.getKey())){


                if (pluginPair.getValue() != newLoggingLevel.get(pluginPair.getKey())){
                    pluginPair.setValue(newLoggingLevel.get(pluginPair.getKey()));
                }
            }
        }

    }

    /**
     * DealswithPluginFileSystem interface member variable
     */
    PluginFileSystem pluginFileSystem;

    /**
     * Service Interface member variables.
     */
    ServiceStatus serviceStatus = ServiceStatus.CREATED;


    /**
     * DealsWithError interface implementation
     * @param errorManager
     */
    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }

    @Override
    public void setLogManager(LogLevel logLevel, LogManager logManager) {
        this.logManager = logManager;
        this.logLevel = logLevel;
    }

    /**
     * DealsWithPluginFileSystem interface implementation
      * @param pluginFileSystem
     */
    @Override
    public void setPluginFileSystem(PluginFileSystem pluginFileSystem) {
        this.pluginFileSystem = pluginFileSystem;
    }

    /**
     * PlatformService Interface implementation.
     */

    @Override
    public void start() {
        /**
         * I will initialize the Root map with all the classes in default state of Minimal logging
         */
        try{
            for (String c : getClassesFullPath()){
                BitcoinCryptoNetworkPluginRoot.newLoggingLevel.put(c, LogLevel.MINIMAL_LOGGING);
            }
        } catch (Exception e){
            // no big deal if I coudln't fill the class now, we will do it later.
        }
        this.serviceStatus = ServiceStatus.STARTED;
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
        this.serviceStatus = ServiceStatus.STOPPED;
    }

    @Override
    public ServiceStatus getStatus() {
        return serviceStatus;
    }

    /**
     * Plugin interface implementation
     * @param uuid
     */
    @Override
    public void setId(UUID uuid) {
        this.pluginId = uuid;
    }

    @Override
    public void setVault(CryptoVault cryptoVault) {
        this.cryptoVault = cryptoVault;
    }

    @Override
    public void connectToBitcoinNetwork() throws CantConnectToBitcoinNetwork {
        bitcoinCryptoNetworkMonitoringAgent = new BitcoinCryptoNetworkMonitoringAgent((Wallet) cryptoVault.getWallet(), cryptoVault.getUserId());
        bitcoinCryptoNetworkMonitoringAgent.setPluginFileSystem(pluginFileSystem);
        bitcoinCryptoNetworkMonitoringAgent.setLogManager(null, this.logManager);
        bitcoinCryptoNetworkMonitoringAgent.setErrorManager(errorManager);
        bitcoinCryptoNetworkMonitoringAgent.setPluginId(pluginId);

        try {
            bitcoinCryptoNetworkMonitoringAgent.configureBlockChain();
        } catch (CantCreateBlockStoreFileException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_CRYPTO_NETWORK, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantConnectToBitcoinNetwork("Couldn't connect to Bitcoin Network.", e,"UserId : " + cryptoVault.getUserId().toString(), "Blockchain not saved " +
                    "on disk.");


        }

        bitcoinCryptoNetworkMonitoringAgent.configurePeers();

        try {
            bitcoinCryptoNetworkMonitoringAgent.start();
        } catch (CantStartAgentException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_CRYPTO_NETWORK, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantConnectToBitcoinNetwork("Couldn't connect to Bitcoin Network.", e,"UserId : " + cryptoVault.getUserId().toString(), "Error starting Agent.");
        }
    }

    @Override
    public void disconnectFromBitcoinNetwork() {
        bitcoinCryptoNetworkMonitoringAgent.stop();
    }

    @Override
    public Object getBroadcasters() {
        return bitcoinCryptoNetworkMonitoringAgent.getPeers();
    }

    public static LogLevel getLogLevelByClass(String className){
    /**
     * sometimes the classname may be passed dinamically with an $moretext
     * I need to ignore whats after this.
     */
    String[] correctedClass = className.split((Pattern.quote("$")));
    LogLevel logLevel = BitcoinCryptoNetworkPluginRoot.newLoggingLevel.get(correctedClass[0]);
    return logLevel;
    }
}