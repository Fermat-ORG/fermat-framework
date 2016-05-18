package org.fermat.fermat_dap_plugin.layer.sub_app_module.asset.factory.developer.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.SubAppsPublicKeys;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Resource;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.CantGetSettingsException;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.CantPersistSettingsException;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.SettingsNotFoundException;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_api.layer.core.PluginInfo;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.CantLoadWalletsException;
import com.bitdubai.fermat_api.layer.modules.ModuleManagerImpl;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.exceptions.ActorIdentityNotSelectedException;
import com.bitdubai.fermat_api.layer.modules.exceptions.CantGetSelectedActorIdentityException;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.Broadcaster;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginBinaryFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletManager;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantCalculateBalanceException;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.exceptions.CantListWalletsException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.InstalledWallet;

import org.fermat.fermat_dap_api.layer.all_definition.enums.State;
import org.fermat.fermat_dap_api.layer.all_definition.exceptions.CantGetIdentityAssetIssuerException;
import org.fermat.fermat_dap_api.layer.dap_identity.asset_issuer.exceptions.CantGetAssetIssuerIdentitiesException;
import org.fermat.fermat_dap_api.layer.dap_identity.asset_issuer.interfaces.IdentityAssetIssuer;
import org.fermat.fermat_dap_api.layer.dap_identity.asset_issuer.interfaces.IdentityAssetIssuerManager;
import org.fermat.fermat_dap_api.layer.dap_middleware.dap_asset_factory.exceptions.CantCreateAssetFactoryException;
import org.fermat.fermat_dap_api.layer.dap_middleware.dap_asset_factory.exceptions.CantCreateEmptyAssetFactoryException;
import org.fermat.fermat_dap_api.layer.dap_middleware.dap_asset_factory.exceptions.CantDeleteAsserFactoryException;
import org.fermat.fermat_dap_api.layer.dap_middleware.dap_asset_factory.exceptions.CantGetAssetFactoryException;
import org.fermat.fermat_dap_api.layer.dap_middleware.dap_asset_factory.exceptions.CantPublishAssetFactoy;
import org.fermat.fermat_dap_api.layer.dap_middleware.dap_asset_factory.exceptions.CantSaveAssetFactoryException;
import org.fermat.fermat_dap_api.layer.dap_middleware.dap_asset_factory.interfaces.AssetFactory;
import org.fermat.fermat_dap_api.layer.dap_middleware.dap_asset_factory.interfaces.AssetFactoryManager;
import org.fermat.fermat_dap_api.layer.dap_module.asset_factory.AssetFactorySettings;
import org.fermat.fermat_dap_api.layer.dap_module.asset_factory.interfaces.AssetFactoryModuleManager;
import org.fermat.fermat_dap_plugin.layer.sub_app_module.asset.factory.developer.version_1.AssetFactorySubAppModulePluginRoot;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Created by franklin on 20/09/15.
 */
@PluginInfo(difficulty = PluginInfo.Dificulty.LOW,
        maintainerMail = "nerioindriago@gmail.com",
        createdBy = "franklin",
        layer = Layers.SUB_APP_MODULE,
        platform = Platforms.DIGITAL_ASSET_PLATFORM,
        plugin = Plugins.BITDUBAI_ASSET_FACTORY_MODULE)
public class AssetFactorySupAppModuleManager extends ModuleManagerImpl<AssetFactorySettings> implements AssetFactoryModuleManager, Serializable {

    private final AssetFactoryManager                   assetFactoryManager;
    private final IdentityAssetIssuerManager            identityAssetIssuerManager;
    private final BitcoinWalletManager                  bitcoinWalletManager;
    private final ErrorManager                          errorManager;
    private final EventManager                          eventManager;
    private final Broadcaster                           broadcaster;
    private final PluginFileSystem                      pluginFileSystem;
    private final UUID                                  pluginId;
    private final AssetFactorySubAppModulePluginRoot    assetFactorySubAppModulePluginRoot;

    private SettingsManager<AssetFactorySettings> settingsManager;
    private BlockchainNetworkType selectedNetwork;
    AssetFactorySettings settings = null;
    String publicKeyApp;

    /**
     * constructor
     *
     * @param assetFactoryManager
     */
    public AssetFactorySupAppModuleManager(AssetFactoryManager assetFactoryManager,
                                           IdentityAssetIssuerManager identityAssetIssuerManager,
                                           BitcoinWalletManager bitcoinWalletManager,
                                           ErrorManager errorManager,
                                           EventManager eventManager,
                                           Broadcaster broadcaster,
                                           PluginFileSystem pluginFileSystem,
                                           UUID pluginId,
                                           AssetFactorySubAppModulePluginRoot assetFactorySubAppModulePluginRoot) {

        super(pluginFileSystem, pluginId);

        this.assetFactoryManager                    = assetFactoryManager;
        this.identityAssetIssuerManager             = identityAssetIssuerManager;
        this.bitcoinWalletManager                   = bitcoinWalletManager;
        this.errorManager                           = errorManager;
        this.eventManager                           = eventManager;
        this.broadcaster                            = broadcaster;
        this.pluginFileSystem                       = pluginFileSystem;
        this.pluginId                               = pluginId;
        this.assetFactorySubAppModulePluginRoot     = assetFactorySubAppModulePluginRoot;
    }

    public AssetFactory getAssetFactory(String assetPublicKey) throws CantGetAssetFactoryException, CantCreateFileException {
        return assetFactoryManager.getAssetFactoryByPublicKey(assetPublicKey);
    }

    public AssetFactory newAssetFactoryEmpty() throws CantCreateEmptyAssetFactoryException, CantCreateAssetFactoryException {
        return assetFactoryManager.createEmptyAssetFactory();
    }

    @Override
    public AssetFactory getAssetFactoryByPublicKey(String assetPublicKey) throws CantGetAssetFactoryException, CantCreateFileException {
        return this.getAssetFactory(assetPublicKey);
    }

    @Override
    public List<AssetFactory> getAssetFactoryByIssuer(String issuerIdentityPublicKey) throws CantGetAssetFactoryException, CantCreateFileException {
        return this.getAssetsFactoryByIssuer(issuerIdentityPublicKey);
    }

    @Override
    public List<AssetFactory> getAssetFactoryByState(State state) throws CantGetAssetFactoryException, CantCreateFileException {
        return this.getAssetsFactoryByState(state, selectedNetwork);
    }

    @Override
    public List<AssetFactory> getAssetFactoryAll() throws CantGetAssetFactoryException, CantCreateFileException {
        return this.getAssetsFactoryAll(selectedNetwork);
    }

    @Override
    public IdentityAssetIssuer getLoggedIdentityAssetIssuer() {
        try {
            List<IdentityAssetIssuer> identities = this.getActiveIdentities();
            return (identities == null || identities.isEmpty()) ? null : this.getActiveIdentities().get(0);
        } catch (Exception exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_FACTORY, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
            exception.printStackTrace();
            return null;
        }
    }

    @Override
    public IdentityAssetIssuer getActiveAssetIssuerIdentity() throws CantGetIdentityAssetIssuerException {
        try {
            return identityAssetIssuerManager.getIdentityAssetIssuer();
        } catch (CantGetAssetIssuerIdentitiesException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_FACTORY, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetIdentityAssetIssuerException(e);
        }
    }

    @Override
    public void saveAssetFactory(AssetFactory assetFactory) throws CantSaveAssetFactoryException, CantCreateFileException, CantPersistFileException {
        assetFactory.setNetworkType(getSelectedNetwork());
        assetFactoryManager.saveAssetFactory(assetFactory);
    }

    @Override
    public void removeAssetFactory(String publicKey) throws CantDeleteAsserFactoryException {
        assetFactoryManager.removeAssetFactory(publicKey);
    }

    @Override
    public void publishAsset(AssetFactory assetFactory) throws CantSaveAssetFactoryException {
        this.publishAssetFactory(assetFactory);
    }

    public void publishAssetFactory(AssetFactory assetFactory) throws CantSaveAssetFactoryException {
        assetFactoryManager.publishAsset(assetFactory);
    }

    public List<AssetFactory> getAssetsFactoryByIssuer(String issuerIdentityPublicKey) throws CantGetAssetFactoryException, CantCreateFileException {
        return assetFactoryManager.getAssetFactoryByIssuer(issuerIdentityPublicKey);
    }

    public List<AssetFactory> getAssetsFactoryByState(State state, BlockchainNetworkType networkType) throws CantGetAssetFactoryException, CantCreateFileException {
        return assetFactoryManager.getAssetFactoryByState(state, networkType);
    }

    public List<AssetFactory> getAssetsFactoryAll(BlockchainNetworkType networkType) throws CantGetAssetFactoryException, CantCreateFileException {
        return assetFactoryManager.getAssetFactoryAll(networkType);
    }

    public PluginBinaryFile getAssetFactoryResource(Resource resource) throws FileNotFoundException, CantCreateFileException {
        return assetFactoryManager.getAssetFactoryResource(resource);
    }

    public List<InstalledWallet> getInstallWallets() throws CantListWalletsException {
        return assetFactoryManager.getInstallWallets();
    }

    public boolean isReadyToPublish(String assetPublicKey) throws CantPublishAssetFactoy {
        return assetFactoryManager.isReadyToPublish(assetPublicKey);
    }

    @Override
    public long getBitcoinWalletBalance(String walletPublicKey) throws CantLoadWalletsException, CantCalculateBalanceException {
        return bitcoinWalletManager.loadWallet(walletPublicKey).getBalance(BalanceType.AVAILABLE).getBalance(selectedNetwork);
    }

    @Override
    public void changeNetworkType(BlockchainNetworkType networkType) {
        if (networkType == null) {
            selectedNetwork = BlockchainNetworkType.getDefaultBlockchainNetworkType();
        } else {
            selectedNetwork = networkType;
        }
    }

    @Override
    public BlockchainNetworkType getSelectedNetwork() {
        if (selectedNetwork == null) {
            try {
                if (settings == null) {
                    settingsManager = getSettingsManager();
                }
                settings = settingsManager.loadAndGetSettings(SubAppsPublicKeys.DAP_FACTORY.getCode());
                selectedNetwork = settings.getBlockchainNetwork().get(settings.getBlockchainNetworkPosition());
            } catch (CantGetSettingsException exception) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_FACTORY, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
                exception.printStackTrace();
            } catch (SettingsNotFoundException e) {
                //TODO: Only enter while the Active Actor Wallet is not open.
                selectedNetwork = BlockchainNetworkType.getDefaultBlockchainNetworkType();
//                e.printStackTrace();
            }
        }
        return selectedNetwork;
    }

    public ActiveActorIdentityInformation getSelectedActorIdentity() throws CantGetSelectedActorIdentityException, ActorIdentityNotSelectedException {
        try {
            List<IdentityAssetIssuer> identities = assetFactoryManager.getActiveIdentities();
            return (identities == null || identities.isEmpty()) ? null : identities.get(0);
        } catch (Exception exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_FACTORY, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
            exception.printStackTrace();
            return null;
        }
    }

    @Override
    public void createIdentity(String name, String phrase, byte[] profile_img) throws Exception {
        identityAssetIssuerManager.createNewIdentityAssetIssuer(name, profile_img);
    }

    @Override
    public void setAppPublicKey(String publicKey) {
        this.publicKeyApp = publicKey;
    }

    @Override
    public int[] getMenuNotifications() {
        return new int[0];
    }

    public List<IdentityAssetIssuer> getActiveIdentities() {
        try {
            return identityAssetIssuerManager.getIdentityAssetIssuersFromCurrentDeviceUser();
        } catch (Exception exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_FACTORY, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
            exception.printStackTrace();
        }
        return null;
    }
}
