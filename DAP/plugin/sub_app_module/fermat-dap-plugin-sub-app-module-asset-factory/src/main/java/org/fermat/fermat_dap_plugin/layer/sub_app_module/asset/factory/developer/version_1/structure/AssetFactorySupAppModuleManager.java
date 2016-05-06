package org.fermat.fermat_dap_plugin.layer.sub_app_module.asset.factory.developer.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Resource;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.exceptions.ActorIdentityNotSelectedException;
import com.bitdubai.fermat_api.layer.modules.exceptions.CantGetSelectedActorIdentityException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginBinaryFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
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
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.exceptions.CantListWalletsException;

import java.util.List;

/**
 * Created by franklin on 20/09/15.
 */
public class AssetFactorySupAppModuleManager  {

    private final AssetFactoryManager assetFactoryManager;
    private final IdentityAssetIssuerManager identityAssetIssuerManager;
    private ErrorManager  errorManager;

    /**
     * constructor
     * @param assetFactoryManager
     */
    public AssetFactorySupAppModuleManager(final AssetFactoryManager assetFactoryManager,
                                           final IdentityAssetIssuerManager identityAssetIssuerManager,
                                           ErrorManager errorManager) {
        this.assetFactoryManager = assetFactoryManager;
        this.identityAssetIssuerManager = identityAssetIssuerManager;
        this.errorManager = errorManager;
    }

    public AssetFactory getAssetFactory(String assetPublicKey)  throws CantGetAssetFactoryException, CantCreateFileException {
        return assetFactoryManager.getAssetFactoryByPublicKey(assetPublicKey);
    }

    public AssetFactory newAssetFactoryEmpty()  throws CantCreateEmptyAssetFactoryException, CantCreateAssetFactoryException {
        return assetFactoryManager.createEmptyAssetFactory();
    }

    public void saveAssetFactory(AssetFactory assetFactory) throws CantSaveAssetFactoryException, CantCreateFileException, CantPersistFileException {
        assetFactoryManager.saveAssetFactory(assetFactory);
    }

    public void removeAssetFactory(String publicKey) throws CantDeleteAsserFactoryException {
        assetFactoryManager.removeAssetFactory(publicKey);
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

    public List<com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.InstalledWallet> getInstallWallets() throws CantListWalletsException {
        return  assetFactoryManager.getInstallWallets();
    }

    public boolean isReadyToPublish(String assetPublicKey) throws CantPublishAssetFactoy
    {
        return assetFactoryManager.isReadyToPublish(assetPublicKey);
    }

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
