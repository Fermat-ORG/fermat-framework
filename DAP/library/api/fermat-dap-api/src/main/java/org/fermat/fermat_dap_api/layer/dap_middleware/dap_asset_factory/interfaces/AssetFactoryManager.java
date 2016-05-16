package org.fermat.fermat_dap_api.layer.dap_middleware.dap_asset_factory.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Resource;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginBinaryFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.exceptions.CantListWalletsException;

import org.fermat.fermat_dap_api.layer.all_definition.enums.State;
import org.fermat.fermat_dap_api.layer.dap_middleware.dap_asset_factory.exceptions.CantCreateAssetFactoryException;
import org.fermat.fermat_dap_api.layer.dap_middleware.dap_asset_factory.exceptions.CantCreateEmptyAssetFactoryException;
import org.fermat.fermat_dap_api.layer.dap_middleware.dap_asset_factory.exceptions.CantDeleteAsserFactoryException;
import org.fermat.fermat_dap_api.layer.dap_middleware.dap_asset_factory.exceptions.CantGetAssetFactoryException;
import org.fermat.fermat_dap_api.layer.dap_middleware.dap_asset_factory.exceptions.CantPublishAssetFactoy;
import org.fermat.fermat_dap_api.layer.dap_middleware.dap_asset_factory.exceptions.CantSaveAssetFactoryException;

import java.util.List;

//import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.exceptions.WalletsListFailedToLoadException;
//import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.interfaces.InstalledWallet;

/**
 * Created by franklin on 07/09/15.
 */
public interface AssetFactoryManager {//extends FermatManager {
    //Getters
    /**
     * This method returns the information stored about the Asset Factory
     */
    AssetFactory getAssetFactoryByPublicKey(String assetPublicKey) throws CantGetAssetFactoryException, CantCreateFileException;

    /**
     * This method returns the information stored about the all Asset Factory by issuerIdentityKey.
     */
    List<AssetFactory> getAssetFactoryByIssuer(String issuerIdentityPublicKey) throws CantGetAssetFactoryException, CantCreateFileException;

    /**
     * This method returns the information stored about the all Asset Factory by state
     */
    List<AssetFactory> getAssetFactoryByState(State state, BlockchainNetworkType networkType) throws CantGetAssetFactoryException, CantCreateFileException;

    /**
     * This method returns the information stored about the all Asset Factory
     */
    List<AssetFactory> getAssetFactoryAll(BlockchainNetworkType networkType) throws CantGetAssetFactoryException, CantCreateFileException;

    /**
     * This method get the binary resource persisted in device
     */
    PluginBinaryFile getAssetFactoryResource(Resource resource) throws FileNotFoundException, CantCreateFileException;

    //CRUD
    /**
     * This method create an empty object AssetFactory
     */
    AssetFactory createEmptyAssetFactory() throws CantCreateEmptyAssetFactoryException, CantCreateAssetFactoryException;

    /**
     * This method save object AssetFactory in database
     */
    void saveAssetFactory(AssetFactory assetFactory) throws CantSaveAssetFactoryException, CantCreateFileException, CantPersistFileException;

    /**
     * This method mark object AssetFactory in database with close
     */
    void markAssetFactoryState(State state, String assetPublicKey) throws CantSaveAssetFactoryException, CantGetAssetFactoryException, CantCreateFileException, CantPersistFileException;

    /**
     * This method remove object AssetFactory in database
     */
    void removeAssetFactory(String publicKey) throws CantDeleteAsserFactoryException;

    /**
     * This method retrieves the bitcoin wallet and check if you have available balance
     */
    long getAvailableBalance(long amount);

    /**
     * TThis method publishes the asset digital object with the number and amount of Asset, start the transaction
     */
    void publishAsset(AssetFactory assetFactory) throws CantSaveAssetFactoryException;

    /**
     * TThis method list all wallet installed in device, start the transaction
     */
    List<com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.InstalledWallet> getInstallWallets()  throws CantListWalletsException;

    /**
     * TThis method check what all information is complete the Asset Factory
     */
    boolean isReadyToPublish(String assetPublicKey) throws CantPublishAssetFactoy;

    List<org.fermat.fermat_dap_api.layer.dap_identity.asset_issuer.interfaces.IdentityAssetIssuer> getActiveIdentities();
}