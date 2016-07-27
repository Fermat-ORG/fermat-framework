package com.bitdubai.fermat_pip_plugin.layer.android_core_module.developer.bitdubai.version_1;


import com.bitdubai.fermat_api.AppsStatus;
import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractModule;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededPluginReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.enums.NetworkStatus;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantGetBitcoinNetworkStatusException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantGetCommunicationNetworkStatusException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantGetModuleManagerException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.CantGetSettingsException;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.CantPersistSettingsException;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.SettingsNotFoundException;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.exceptions.ActorIdentityNotSelectedException;
import com.bitdubai.fermat_api.layer.modules.exceptions.CantGetSelectedActorIdentityException;
import com.bitdubai.fermat_api.layer.modules.interfaces.ModuleManager;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantGetBlockchainConnectionStatusException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.manager.BlockchainManager;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.interfaces.NetworkClientManager;
import com.bitdubai.fermat_pip_api.layer.module.android_core.interfaces.AndroidCoreModule;
import com.bitdubai.fermat_pip_api.layer.module.android_core.interfaces.AndroidCoreSettings;
import com.bitdubai.fermat_pip_api.layer.module.android_core.interfaces.AndroidCoreSettingsManager;

import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.Transaction;


/**
 * TODO: This plugin do .
 * <p/>
 * TODO: DETAIL...............................................
 * <p/>
 * <p/>
 * Created by Natalia   19/01/2016
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class AndroidCoreModulePluginRoot extends AbstractModule<AndroidCoreSettings, ActiveActorIdentityInformation> implements AndroidCoreModule {

    @NeededPluginReference(platform = Platforms.COMMUNICATION_PLATFORM, layer = Layers.COMMUNICATION, plugin = Plugins.NETWORK_CLIENT)
    private NetworkClientManager wsCommunicationsCloudClientManager;

    @NeededPluginReference(platform = Platforms.BLOCKCHAINS, layer = Layers.CRYPTO_NETWORK, plugin = Plugins.BITCOIN_NETWORK)
    private BlockchainManager<ECKey, Transaction> bitcoinNetworkManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_FILE_SYSTEM)
    private PluginFileSystem pluginFileSystem;

    public AndroidCoreModulePluginRoot() {
        super(new PluginVersionReference(new Version()));
    }


    @Override
    public void start() throws CantStartPluginException {
        super.start();

    }

    /**
     * Module Manager Implementation
     */


    @Override
    public NetworkStatus getFermatNetworkStatus() throws CantGetCommunicationNetworkStatusException {
        try {
            if (this.wsCommunicationsCloudClientManager.getConnection().isConnected())
                return NetworkStatus.CONNECTED;
            else
                return NetworkStatus.DISCONNECTED;
        } catch (Exception e) {
            throw new CantGetCommunicationNetworkStatusException(CantGetCommunicationNetworkStatusException.DEFAULT_MESSAGE, e, "", "Cant Get Cloud Cient Network Connection Status");
        }
    }

    @Override
    public NetworkStatus getBitcoinNetworkStatus(BlockchainNetworkType blockchainNetworkType) throws CantGetBitcoinNetworkStatusException {
        try {
            if (bitcoinNetworkManager.getBlockchainConnectionStatus(blockchainNetworkType).isConnected())
                return NetworkStatus.CONNECTED;
            else
                return NetworkStatus.DISCONNECTED;
        } catch (CantGetBlockchainConnectionStatusException e) {
            throw new CantGetBitcoinNetworkStatusException(CantGetBitcoinNetworkStatusException.DEFAULT_MESSAGE, e, "", "Cant Get Bitcoin Network Connection Status");
        }
    }

    @Override
    public NetworkStatus getPrivateNetworkStatus() {
        return NetworkStatus.CONNECTED;
    }


    @Override
    public SettingsManager getSettingsManager() {
        return new AndroidCoreSettingsManager(pluginFileSystem, pluginId);
    }

    @Override
    public ActiveActorIdentityInformation getSelectedActorIdentity() throws CantGetSelectedActorIdentityException, ActorIdentityNotSelectedException {
        return null;
    }

    @Override
    public void createIdentity(String name, String phrase, byte[] profile_img) throws Exception {

    }

    @Override
    public void setAppPublicKey(String publicKey) {

    }

    @Override
    public int[] getMenuNotifications() {
        return new int[0];
    }


    @Override
    public ModuleManager<AndroidCoreSettings, ActiveActorIdentityInformation> getModuleManager() throws CantGetModuleManagerException {
        return this;
    }

    @Override
    public void persistSettings(String publicKey, AndroidCoreSettings settings) throws CantPersistSettingsException {
        getSettingsManager().persistSettings(publicKey, settings);
    }

    @Override
    public AndroidCoreSettings loadAndGetSettings(String publicKey) throws CantGetSettingsException, SettingsNotFoundException {
        AndroidCoreSettings androidCoreSettings = null;
        try {
            androidCoreSettings = (AndroidCoreSettings) getSettingsManager().loadAndGetSettings(publicKey);
        } catch (Exception e) {
            if (androidCoreSettings == null) {
                androidCoreSettings = new AndroidCoreSettings(AppsStatus.ALPHA);
                try {
                    getSettingsManager().persistSettings(publicKey, androidCoreSettings);
                } catch (CantPersistSettingsException e1) {
                    throw new CantGetSettingsException(e1, "Settings manager fail in android core module", "");
                }
            }
        }
        return androidCoreSettings;
    }
}
