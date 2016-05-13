package org.fermat.fermat_dap_plugin.layer.sub_app_module.asset.factory.developer.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededPluginReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.enums.SubAppsPublicKeys;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Resource;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.CantGetSettingsException;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.CantPersistSettingsException;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.SettingsNotFoundException;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.core.PluginInfo;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.CantLoadWalletsException;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.exceptions.ActorIdentityNotSelectedException;
import com.bitdubai.fermat_api.layer.modules.exceptions.CantGetSelectedActorIdentityException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginBinaryFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletManager;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantCalculateBalanceException;
import org.fermat.fermat_dap_api.layer.all_definition.enums.State;
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
import org.fermat.fermat_dap_plugin.layer.sub_app_module.asset.factory.developer.version_1.structure.AssetFactorySupAppModuleManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.exceptions.CantListWalletsException;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * TODO explain here the main functionality of the plug-in.
 * <p/>
 * Created by Franklin on 07/09/15.
 */
@PluginInfo(difficulty = PluginInfo.Dificulty.LOW,
        maintainerMail = "nerioindriago@gmail.com",
        createdBy = "franklin",
        layer = Layers.SUB_APP_MODULE,
        platform = Platforms.DIGITAL_ASSET_PLATFORM,
        plugin = Plugins.BITDUBAI_ASSET_FACTORY_MODULE)
public final class AssetFactorySubAppModulePluginRoot extends AbstractPlugin implements
        AssetFactoryModuleManager {

    @NeededPluginReference(platform = Platforms.DIGITAL_ASSET_PLATFORM, layer = Layers.MIDDLEWARE, plugin = Plugins.ASSET_FACTORY)
    private AssetFactoryManager assetFactoryManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_FILE_SYSTEM)
    private PluginFileSystem pluginFileSystem;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER)
    private ErrorManager errorManager;

    @NeededPluginReference(platform = Platforms.DIGITAL_ASSET_PLATFORM, layer = Layers.IDENTITY, plugin = Plugins.ASSET_ISSUER)
    IdentityAssetIssuerManager identityAssetIssuerManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_CURRENCY_PLATFORM, layer = Layers.BASIC_WALLET, plugin = Plugins.BITCOIN_WALLET)
    BitcoinWalletManager bitcoinWalletManager;

    private SettingsManager<AssetFactorySettings> settingsManager;
    private BlockchainNetworkType selectedNetwork;

    AssetFactorySettings settings = null;
    String publicKeyApp;
    // TODO ADDED ERROR MANAGER REFERENCE, PLEASE MAKE USE OF THE ERROR MANAGER.

    public AssetFactorySubAppModulePluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

    private AssetFactorySupAppModuleManager assetFactorySupAppModuleManager;

    @Override
    public void start() throws CantStartPluginException {
        try {
        assetFactorySupAppModuleManager = new AssetFactorySupAppModuleManager(
                assetFactoryManager,
                identityAssetIssuerManager,
                errorManager);

            this.serviceStatus = ServiceStatus.STARTED;
        } catch (Exception exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_FACTORY, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
            throw new CantStartPluginException(exception);
        }
    }

    @Override
    public IdentityAssetIssuer getLoggedIdentityAssetIssuer() {
        try {
            List<IdentityAssetIssuer> identities = assetFactorySupAppModuleManager.getActiveIdentities();
            return (identities == null || identities.isEmpty()) ? null : assetFactorySupAppModuleManager.getActiveIdentities().get(0);
        } catch (Exception exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_FACTORY, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
            exception.printStackTrace();
            return null;
        }
    }

    @Override
    public void saveAssetFactory(AssetFactory assetFactory) throws CantSaveAssetFactoryException, CantCreateFileException, CantPersistFileException {
        assetFactory.setNetworkType(getSelectedNetwork());
        assetFactorySupAppModuleManager.saveAssetFactory(assetFactory);
    }

    @Override
    public void removeAssetFactory(String publicKey) throws CantDeleteAsserFactoryException {
        assetFactorySupAppModuleManager.removeAssetFactory(publicKey);
    }

    @Override
    public void publishAsset(AssetFactory assetFactory) throws CantSaveAssetFactoryException {
        assetFactorySupAppModuleManager.publishAssetFactory(assetFactory);
    }

    @Override
    public AssetFactory newAssetFactoryEmpty() throws CantCreateEmptyAssetFactoryException, CantCreateAssetFactoryException {
        return assetFactorySupAppModuleManager.newAssetFactoryEmpty();
    }

    @Override
    public AssetFactory getAssetFactoryByPublicKey(String assetPublicKey) throws CantGetAssetFactoryException, CantCreateFileException {
        return assetFactorySupAppModuleManager.getAssetFactory(assetPublicKey);
    }

    @Override
    public List<AssetFactory> getAssetFactoryByIssuer(String issuerIdentityPublicKey) throws CantGetAssetFactoryException, CantCreateFileException {
        return assetFactorySupAppModuleManager.getAssetsFactoryByIssuer(issuerIdentityPublicKey);
    }

    @Override
    public List<AssetFactory> getAssetFactoryByState(State state) throws CantGetAssetFactoryException, CantCreateFileException {
        return assetFactorySupAppModuleManager.getAssetsFactoryByState(state, selectedNetwork);
    }

    @Override
    public List<AssetFactory> getAssetFactoryAll() throws CantGetAssetFactoryException, CantCreateFileException {
        return assetFactorySupAppModuleManager.getAssetsFactoryAll(selectedNetwork);
    }

    @Override
    public PluginBinaryFile getAssetFactoryResource(Resource resource) throws FileNotFoundException, CantCreateFileException {
        return assetFactorySupAppModuleManager.getAssetFactoryResource(resource);
    }

    @Override
    public List<com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.InstalledWallet> getInstallWallets() throws CantListWalletsException {
        return assetFactorySupAppModuleManager.getInstallWallets();
    }

    @Override
    public boolean isReadyToPublish(String assetPublicKey) throws CantPublishAssetFactoy {
        return assetFactorySupAppModuleManager.isReadyToPublish(assetPublicKey);
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

    public List<AssetFactory> test() {
        List<AssetFactory> assetFactory = null;
        try {
            assetFactory = getAssetFactoryAll();

        } catch (Exception exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_FACTORY, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
            System.out.println("******* Test Asset Factory Module, Error. Franklin ******");
            exception.printStackTrace();
        }
        return assetFactory;
    }

    @Override
    public SettingsManager<AssetFactorySettings> getSettingsManager() {
        if (this.settingsManager != null)
            return this.settingsManager;

        this.settingsManager = new SettingsManager<>(
                pluginFileSystem,
                pluginId
        );

        return this.settingsManager;
    }

    @Override
    public ActiveActorIdentityInformation getSelectedActorIdentity() throws CantGetSelectedActorIdentityException, ActorIdentityNotSelectedException {
        try {
            List<IdentityAssetIssuer> identities = assetFactoryManager.getActiveIdentities();
            return (identities == null || identities.isEmpty()) ? null : assetFactoryManager.getActiveIdentities().get(0);
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

        try {
            settings = settingsManager.loadAndGetSettings(publicKeyApp);
        } catch (Exception e) {
            settings = null;
        }

        if (settings != null && settings.getBlockchainNetwork() != null) {
            settings.setBlockchainNetwork(Arrays.asList(BlockchainNetworkType.values()));
        } else {
            int position = 0;
            List<BlockchainNetworkType> list = Arrays.asList(BlockchainNetworkType.values());

            for (BlockchainNetworkType networkType : list) {

                if (Objects.equals(networkType.getCode(), BlockchainNetworkType.getDefaultBlockchainNetworkType().getCode())) {
                    settings.setBlockchainNetworkPosition(position);
                    break;
                } else {
                    position++;
                }
            }
            settings.setBlockchainNetwork(list);
        }

        try {
            settingsManager.persistSettings(publicKeyApp, settings);
        } catch (CantPersistSettingsException exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_FACTORY, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
            exception.printStackTrace();
        }
    }

    @Override
    public int[] getMenuNotifications() {
        return new int[0];
    }
}
